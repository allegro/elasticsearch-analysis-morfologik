package pl.allegro.tech.elasticsearch.plugin.analysis.morfologik

import org.apache.http.HttpHost
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.AnalyzeRequest
import org.elasticsearch.client.indices.AnalyzeResponse
import org.junit.AfterClass
import org.junit.BeforeClass
import spock.lang.Specification
import spock.lang.Unroll


class MorfologikPluginIntegrationTest extends Specification {
    static final String ELASTIC_VERSION = System.properties['elasticsearchVersion']
    static final String MORFOLOGIK_FILE_NAME = "elasticsearch-analysis-morfologik-${ELASTIC_VERSION}.zip"
    static final String MORFOLOGIK_PLUGIN_PATH = "build/distributions/$MORFOLOGIK_FILE_NAME"

    static final URI CUSTOM_DICTIONARY_PATH = MorfologikPluginIntegrationTest.class.getResource("/polish-wo-brev.dict").toURI()
    static final URI CUSTOM_DICTIONARY_PATH_META = MorfologikPluginIntegrationTest.class.getResource("/polish-wo-brev.info").toURI()

    static final ElasticsearchWithPluginContainer container = new ElasticsearchWithPluginContainer("docker.elastic.co/elasticsearch/elasticsearch-oss:$ELASTIC_VERSION")
    static RestHighLevelClient elasticsearchClient

    @BeforeClass
    static void setupContainerWithPlugin() {
        container.withPlugin(new File(MORFOLOGIK_PLUGIN_PATH))
        container.withCustomConfigFile(new File(CUSTOM_DICTIONARY_PATH))
        container.withCustomConfigFile(new File(CUSTOM_DICTIONARY_PATH_META))
        container.start()
        elasticsearchClient = createClient()
    }

    @AfterClass
    static void stopContainer() {
        container.stop()
    }

    def "morfologik analyzer should work"() {
        given:
        AnalyzeRequest request = AnalyzeRequest.withGlobalAnalyzer(AnalysisMorfologikPlugin.ANALYZER_NAME, "jestem")
        expect:
        analyzeAndGetFirstTermResult(request) == "być"
    }

    def "morfologik token filter should work"() {
        given:
        AnalyzeRequest requestWithFilter = AnalyzeRequest.buildCustomAnalyzer("standard")
                .addTokenFilter(AnalysisMorfologikPlugin.FILTER_NAME)
                .build("jestem")
        expect:
        analyzeAndGetFirstTermResult(requestWithFilter) == "być"
    }

    @Unroll
    def "morfologik token filter test: #text, dictionary: #dictionary => #analyzedText"() {
        expect:
        analyzeAndGetFirstTermResult(prepareAnalyzerRequest(text, dictionary)) == analyzedText

        /**
         * dictionaries:
         * null - default, Polish dictionary from morfologik-polish
         * polish-wo-brev.dict - eg. custom dictionary (Polish dictionary without abbreviations like: pl => plac;
         * source: https://github.com/gilek/morfologik-wo-brev)
         */
        where:
        text | dictionary            | analyzedText
        "pl" | "polish-wo-brev.dict" | "pl"
        "pl" | null                  | "plac"
        "s"  | null                  | "sekunda strona"
        "s"  | "polish-wo-brev.dict" | "s"
    }

    private static AnalyzeRequest prepareAnalyzerRequest(String text, dictionary = null) {
        def morfologikFilterSettings = ["type": AnalysisMorfologikPlugin.FILTER_NAME, "dictionary": dictionary]
        AnalyzeRequest.buildCustomAnalyzer("standard")
                .addTokenFilter(morfologikFilterSettings)
                .build(text)
    }

    private static String analyzeAndGetFirstTermResult(AnalyzeRequest analyzeRequest) {
        AnalyzeResponse result = elasticsearchClient.indices().analyze(analyzeRequest, RequestOptions.DEFAULT)
        return result.tokens.collect { it.term }.join(" ")
    }

    static RestHighLevelClient createClient() {
        return new RestHighLevelClient(RestClient.builder(
                HttpHost.create(container.httpHostAddress)
        ))
    }
}

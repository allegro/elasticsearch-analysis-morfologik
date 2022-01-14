package pl.allegro.tech.elasticsearch.plugin.analysis.morfologik


import spock.lang.Specification
import spock.lang.Unroll

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

import static groovy.json.JsonOutput.toJson

class MorfologikPluginIntegrationTest extends Specification {
    static final String ELASTIC_VERSION = System.properties['elasticsearchVersion']
    static final String MORFOLOGIK_FILE_NAME = "elasticsearch-analysis-morfologik-${ELASTIC_VERSION}.zip"
    static final String MORFOLOGIK_PLUGIN_PATH = "build/distributions/$MORFOLOGIK_FILE_NAME"

    static final URI CUSTOM_DICTIONARY_PATH = MorfologikPluginIntegrationTest.class.getResource("/polish-wo-brev.dict").toURI()
    static final URI CUSTOM_DICTIONARY_PATH_META = MorfologikPluginIntegrationTest.class.getResource("/polish-wo-brev.info").toURI()

    static final String ELASTIC_DOCKER_IMAGE = "docker.elastic.co/elasticsearch/elasticsearch:$ELASTIC_VERSION"
    static final ElasticsearchWithPluginContainer container = new ElasticsearchWithPluginContainer(ELASTIC_DOCKER_IMAGE)

    private HttpClient httpClient = HttpClient.newBuilder().build()

    void setupSpec() {
        container.withPlugin(new File(MORFOLOGIK_PLUGIN_PATH))
        container.withCustomConfigFile(new File(CUSTOM_DICTIONARY_PATH))
        container.withCustomConfigFile(new File(CUSTOM_DICTIONARY_PATH_META))
        container.start()
    }

    void cleanupSpec() {
        container.stop()
    }

    def "morfologik analyzer should work"() {
        given:
        def request = [
                "analyzer": AnalysisMorfologikPlugin.ANALYZER_NAME,
                "text"    : "jestem"
        ]

        expect:
        analyzeAndGetTokensResult(request) == "być"
    }

    def "morfologik token filter should work"() {
        given:
        def request = [
                "tokenizer": "standard",
                "filter"   : [AnalysisMorfologikPlugin.FILTER_NAME],
                "text"     : "jestem"
        ]

        expect:
        analyzeAndGetTokensResult(request) == "być"
    }

    @Unroll
    def "morfologik token filter test: #text, dictionary: #dictionary => #analyzedText"() {
        expect:
        def request = [
                "tokenizer": "standard",
                "filter"   : [["type": AnalysisMorfologikPlugin.FILTER_NAME, "dictionary": dictionary]],
                "text"     : text
        ]
        analyzeAndGetTokensResult(request) == analyzedText

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

    private String analyzeAndGetTokensResult(Map<String, Object> analyzeRequest) {
        def response = sendAnalyzeRequest(container.httpHostAddress, toJson(analyzeRequest))
        response.result.tokens.collect { it.token }.join(" ")
    }

    private def sendAnalyzeRequest(def elasticsearchHost, def requestBody) {
        def request = HttpRequest.newBuilder()
                .uri(URI.create("http://$elasticsearchHost/_analyze"))
                .method("GET", HttpRequest.BodyPublishers.ofString(requestBody as String))
                .header("Content-Type", "application/json")
                .build()
        return new AnalyzeResponse(httpClient.send(request, HttpResponse.BodyHandlers.ofString()))
    }
}

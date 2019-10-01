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

import static pl.allegro.tech.elasticsearch.plugin.analysis.morfologik.AnalysisMorfologikPlugin.ANALYZER_NAME
import static pl.allegro.tech.elasticsearch.plugin.analysis.morfologik.AnalysisMorfologikPlugin.FILTER_NAME

class MorfologikPluginIntegrationTest extends Specification {
    static final String ELASTIC_VERSION = System.properties['elasticsearchVersion']
    static final String MORFOLOGIK_FILE_NAME = "elasticsearch-analysis-morfologik-${ELASTIC_VERSION}.zip"
    static final String MORFOLOGIK_PLUGIN_PATH = "build/distributions/$MORFOLOGIK_FILE_NAME"
    static final ElasticsearchWithPluginContainer container = new ElasticsearchWithPluginContainer("docker.elastic.co/elasticsearch/elasticsearch-oss:$ELASTIC_VERSION")
    static RestHighLevelClient elasticsearchClient

    @BeforeClass
    void setupContainerWithPlugin() {
        container.withPlugin(new File(MORFOLOGIK_PLUGIN_PATH));
        container.start()
        elasticsearchClient = createClient()
    }

    @AfterClass
    void stopContainer() {
        container.stop()
    }

    def "morfologik analyzer should work"() {
        given:
            AnalyzeRequest request = AnalyzeRequest.withGlobalAnalyzer(ANALYZER_NAME, "jestem")
        expect:
            analyzeAndGetFirstTermResult(request) == "być"
    }

    def "morfologik token filter should work"() {
        given:
            AnalyzeRequest requestWithFilter = AnalyzeRequest.buildCustomAnalyzer("standard")
                    .addTokenFilter(FILTER_NAME)
                    .build("jestem")
        expect:
            analyzeAndGetFirstTermResult(requestWithFilter) == "być"
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

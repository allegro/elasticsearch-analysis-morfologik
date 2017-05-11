package pl.allegro.tech.elasticsearch.plugin.analysis.morfologik

import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.transport.client.PreBuiltTransportClient
import pl.allegro.tech.embeddedelasticsearch.EmbeddedElastic
import spock.lang.Specification

import static java.util.concurrent.TimeUnit.MINUTES
import static pl.allegro.tech.elasticsearch.plugin.analysis.morfologik.AnalysisMorfologikPlugin.ANALYZER_NAME
import static pl.allegro.tech.elasticsearch.plugin.analysis.morfologik.AnalysisMorfologikPlugin.FILTER_NAME
import static pl.allegro.tech.embeddedelasticsearch.PopularProperties.CLUSTER_NAME
import static pl.allegro.tech.embeddedelasticsearch.PopularProperties.HTTP_PORT
import static pl.allegro.tech.embeddedelasticsearch.PopularProperties.TRANSPORT_TCP_PORT

class MorfologikPluginIntegrationTest extends Specification {

    static final String ELASTIC_VERSION = System.properties['elasticsearchVersion']

    static final String ELS_CLUSTER_NAME = "test-cluster"
    static final int ELS_PORT = 9301
    static final int ELS_HTTP_PORT = 9201

    static final String MORFOLOGIK_PLUGIN_PATH =
            "build/distributions/elasticsearch-analysis-morfologik-" + ELASTIC_VERSION + ".zip"

    static final embeddedElastic = EmbeddedElastic.builder()
            .withEsJavaOpts("-Xms128m -Xmx512m")
            .withElasticVersion(ELASTIC_VERSION)
            .withSetting(TRANSPORT_TCP_PORT, ELS_PORT)
            .withSetting(CLUSTER_NAME, ELS_CLUSTER_NAME)
            .withSetting(HTTP_PORT, ELS_HTTP_PORT)
            .withPlugin(new File(MORFOLOGIK_PLUGIN_PATH).toURI().toURL().toString())
            .withStartTimeout(1, MINUTES)
            .build()
            .start()

    static final elasticsearchClient = createClient()

    def "morfologik analyzer should work"() {
        when:
        def result = elasticsearchClient.admin().indices()
                .analyze(new AnalyzeRequest()
                .analyzer(ANALYZER_NAME)
                .text("jestem")).get()

        then:
        result.tokens.get(0).term == "być"
    }

    def "morfologik token filter should work"() {
        when:
        def result = elasticsearchClient.admin().indices()
                .analyze(new AnalyzeRequest()
                .tokenizer("standard")
                .addTokenFilter(FILTER_NAME)
                .text("jestem")).get()

        then:
        result.tokens.get(0).term == "być"
    }

    def cleanupSpec() {
        embeddedElastic.stop()
    }

    static def createClient() {
        def transportClient = new PreBuiltTransportClient(
                Settings.builder()
                        .put("cluster.name", ELS_CLUSTER_NAME).build()
        )
        transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.loopbackAddress, ELS_PORT))
        transportClient
    }

}
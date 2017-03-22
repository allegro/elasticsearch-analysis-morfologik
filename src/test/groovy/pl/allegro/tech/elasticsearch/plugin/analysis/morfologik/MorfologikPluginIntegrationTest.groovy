package pl.allegro.tech.elasticsearch.plugin.analysis.morfologik

import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.transport.InetSocketTransportAddress
import pl.allegro.tech.embeddedelasticsearch.EmbeddedElastic
import spock.lang.Specification

import static org.elasticsearch.common.settings.Settings.settingsBuilder
import static pl.allegro.tech.elasticsearch.plugin.analysis.morfologik.AnalysisMorfologikPlugin.ANALYZER_NAME
import static pl.allegro.tech.elasticsearch.plugin.analysis.morfologik.AnalysisMorfologikPlugin.FILTER_NAME
import static pl.allegro.tech.embeddedelasticsearch.PopularProperties.CLUSTER_NAME
import static pl.allegro.tech.embeddedelasticsearch.PopularProperties.TRANSPORT_TCP_PORT

class MorfologikPluginIntegrationTest extends Specification {

    static final String ELASTIC_VERSION = System.properties['elasticsearchVersion']

    static final String ELS_CLUSTER_NAME = "test-cluster"
    static final int ELS_PORT = 9300

    static final String MORFOLOGIK_PLUGIN_PATH = "build/distributions/elasticsearch-analysis-morfologik-" +
            ELASTIC_VERSION + "-plugin.zip"

    static final embeddedElastic = EmbeddedElastic.builder()
            .withElasticVersion(ELASTIC_VERSION)
            .withSetting(TRANSPORT_TCP_PORT, ELS_PORT)
            .withSetting(CLUSTER_NAME, ELS_CLUSTER_NAME)
            .withPlugin(new File(MORFOLOGIK_PLUGIN_PATH).toURI().toString())
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
                    .tokenFilters(FILTER_NAME)
                    .text("jestem")).get()

        then:
            result.tokens.get(0).term == "być"
    }

    def cleanupSpec() {
        embeddedElastic.stop()
    }

    static def createClient() {
        def transportClient = TransportClient.builder()
                .settings(
                settingsBuilder()
                        .put("cluster.name", ELS_CLUSTER_NAME)
                        .build())
                .build();
        transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.loopbackAddress, ELS_PORT));
        transportClient
    }

}
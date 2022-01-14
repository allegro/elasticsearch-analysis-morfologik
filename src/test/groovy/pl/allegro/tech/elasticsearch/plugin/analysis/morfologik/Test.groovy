package pl.allegro.tech.elasticsearch.plugin.analysis.morfologik


import org.apache.http.HttpHost
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse
import org.elasticsearch.client.RequestConverters
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.ToXContent
import org.elasticsearch.common.xcontent.XContentBuilder
import org.elasticsearch.common.xcontent.XContentHelper
import spock.lang.Specification
import spock.lang.Unroll

class Test extends Specification {
    static RestHighLevelClient elasticsearchClient = new RestHighLevelClient(RestClient.builder(HttpHost.create("localhost:9200")));

    def "test"() {
        def morfologikFilterSettings = ["type": AnalysisMorfologikPlugin.FILTER_NAME, "dictionary": "test"]

        def definition = new AnalyzeRequest.NameOrDefinition(morfologikFilterSettings)


//        print(definition.definition)


        def content = definition.toXContent(XContentBuilder.builder(RequestConverters.REQUEST_BODY_CONTENT_TYPE.xContent()), ToXContent.EMPTY_PARAMS)


//        elasticsearchClient.

        expect:
        1 == 1
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
//        Map.of("type", "morfologik_stem")

        return new AnalyzeRequest()
                .tokenizer("standard")
                .addTokenFilter(Map.of("type", "morfologik_stem"))
//                .addTokenFilter("morfologik_stem")
                .text(text)
    }

    private static String analyzeAndGetFirstTermResult(AnalyzeRequest analyzeRequest) {
//        def entity = RequestConverters.createEntity(analyzeRequest, RequestConverters.REQUEST_BODY_CONTENT_TYPE)
        def content = XContentHelper.toXContent(analyzeRequest, RequestConverters.REQUEST_BODY_CONTENT_TYPE, ToXContent.EMPTY_PARAMS, true)
        print(content.utf8ToString())

        AnalyzeResponse result = elasticsearchClient.indices().analyze(analyzeRequest, RequestOptions.DEFAULT)
        return result.tokens.collect { it.term }.join(" ")
    }
}

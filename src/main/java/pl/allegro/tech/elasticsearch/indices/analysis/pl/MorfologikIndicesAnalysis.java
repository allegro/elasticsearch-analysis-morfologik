package pl.allegro.tech.elasticsearch.indices.analysis.pl;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.morfologik.MorfologikAnalyzer;
import org.apache.lucene.analysis.morfologik.MorfologikFilter;
import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.lucene.Lucene;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.analysis.AnalyzerScope;
import org.elasticsearch.index.analysis.PreBuiltAnalyzerProviderFactory;
import org.elasticsearch.index.analysis.PreBuiltTokenFilterFactoryFactory;
import org.elasticsearch.index.analysis.TokenFilterFactory;
import org.elasticsearch.indices.analysis.IndicesAnalysisService;

import static pl.allegro.tech.elasticsearch.plugin.analysis.morfologik.AnalysisMorfologikPlugin.ANALYZER_NAME;
import static pl.allegro.tech.elasticsearch.plugin.analysis.morfologik.AnalysisMorfologikPlugin.FILTER_NAME;

public class MorfologikIndicesAnalysis extends AbstractComponent {

    @Inject
    public MorfologikIndicesAnalysis(Settings settings, IndicesAnalysisService indicesAnalysisService) {
        super(settings);

        indicesAnalysisService.analyzerProviderFactories()
            .put(ANALYZER_NAME, new PreBuiltAnalyzerProviderFactory(ANALYZER_NAME, AnalyzerScope.INDICES, prepareMorfologikAnalyzer()));

        indicesAnalysisService.tokenFilterFactories().put(FILTER_NAME, new PreBuiltTokenFilterFactoryFactory(new TokenFilterFactory() {
            @Override
            public String name() {
                return FILTER_NAME;
            }

            @Override
            public TokenStream create(TokenStream tokenStream) {
                return new MorfologikFilter(tokenStream);
            }
        }));
    }

    private MorfologikAnalyzer prepareMorfologikAnalyzer() {
        MorfologikAnalyzer analyzer = new MorfologikAnalyzer();
        analyzer.setVersion(Lucene.VERSION);
        return analyzer;
    }

}

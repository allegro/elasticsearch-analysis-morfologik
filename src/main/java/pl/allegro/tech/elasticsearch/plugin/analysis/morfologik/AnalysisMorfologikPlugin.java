package pl.allegro.tech.elasticsearch.plugin.analysis.morfologik;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.index.analysis.AnalyzerProvider;
import org.elasticsearch.index.analysis.TokenFilterFactory;
import org.elasticsearch.indices.analysis.AnalysisModule.AnalysisProvider;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;
import pl.allegro.tech.elasticsearch.index.analysis.pl.MorfologikAnalyzerProvider;
import pl.allegro.tech.elasticsearch.index.analysis.pl.MorfologikTokenFilterFactory;

import java.util.Map;

import static java.util.Collections.singletonMap;

public class AnalysisMorfologikPlugin extends Plugin implements AnalysisPlugin {

    public static final String ANALYZER_NAME = "morfologik";
    public static final String FILTER_NAME = "morfologik_stem";

    @Override
    public Map<String, AnalysisProvider<TokenFilterFactory>> getTokenFilters() {
        return singletonMap(FILTER_NAME, MorfologikTokenFilterFactory::new);
    }

    @Override
    public Map<String, AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
        return singletonMap(ANALYZER_NAME, MorfologikAnalyzerProvider::new);
    }
}

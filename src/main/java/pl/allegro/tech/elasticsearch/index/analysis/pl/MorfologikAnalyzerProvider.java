package pl.allegro.tech.elasticsearch.index.analysis.pl;

import org.apache.lucene.analysis.morfologik.MorfologikAnalyzer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;

public class MorfologikAnalyzerProvider extends AbstractIndexAnalyzerProvider<MorfologikAnalyzer> {

    private final MorfologikAnalyzer analyzer;

    public MorfologikAnalyzerProvider(IndexSettings indexSettings, String name, Settings settings) {
        super(indexSettings, name, settings);
        analyzer = new MorfologikAnalyzer();
        analyzer.setVersion(version);
    }

    @Override
    public MorfologikAnalyzer get() {
        return analyzer;
    }
}
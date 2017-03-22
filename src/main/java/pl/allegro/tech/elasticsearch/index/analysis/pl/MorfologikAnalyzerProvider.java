package pl.allegro.tech.elasticsearch.index.analysis.pl;

import org.apache.lucene.analysis.morfologik.MorfologikAnalyzer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;
import org.elasticsearch.index.settings.IndexSettingsService;

class MorfologikAnalyzerProvider extends AbstractIndexAnalyzerProvider<MorfologikAnalyzer> {

    private final MorfologikAnalyzer analyzer;

    @Inject
    public MorfologikAnalyzerProvider(Index index, IndexSettingsService indexSettingsService,
                                      @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettingsService.getSettings(), name, settings);
        analyzer = new MorfologikAnalyzer();
        analyzer.setVersion(version);
    }

    @Override
    public MorfologikAnalyzer get() {
        return analyzer;
    }

}

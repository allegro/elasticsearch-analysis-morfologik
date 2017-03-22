package pl.allegro.tech.elasticsearch.plugin.analysis.morfologik;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.plugins.Plugin;
import pl.allegro.tech.elasticsearch.index.analysis.pl.MorfologikAnalysisBinderProcessor;
import pl.allegro.tech.elasticsearch.indices.analysis.pl.MorfologikIndicesAnalysisModule;

import java.util.Collection;
import java.util.Collections;

public class AnalysisMorfologikPlugin extends Plugin {

    public static final String ANALYZER_NAME = "morfologik";
    public static final String FILTER_NAME = "morfologik_stem";

    @Inject
    public AnalysisMorfologikPlugin() {
    }

    @Override
    public String name() {
        return "analysis-morfologik";
    }

    @Override
    public String description() {
        return "Morfologik Polish Lemmatizer plugin for Elasticsearch";
    }

    @Override
    public Collection<Module> nodeModules() {
        return Collections.<Module>singletonList(new MorfologikIndicesAnalysisModule());
    }

    public void onModule(AnalysisModule module) {
        module.addProcessor(new MorfologikAnalysisBinderProcessor());
    }
}

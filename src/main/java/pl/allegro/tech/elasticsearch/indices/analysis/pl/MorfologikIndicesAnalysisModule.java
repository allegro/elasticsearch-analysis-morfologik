package pl.allegro.tech.elasticsearch.indices.analysis.pl;

import org.elasticsearch.common.inject.AbstractModule;

public class MorfologikIndicesAnalysisModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MorfologikIndicesAnalysis.class).asEagerSingleton();
    }

}

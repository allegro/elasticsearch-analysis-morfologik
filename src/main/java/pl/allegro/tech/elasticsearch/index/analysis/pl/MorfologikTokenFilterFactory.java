package pl.allegro.tech.elasticsearch.index.analysis.pl;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.morfologik.MorfologikFilter;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;

public class MorfologikTokenFilterFactory extends AbstractTokenFilterFactory {

    public MorfologikTokenFilterFactory(IndexSettings indexSettings, String name, Settings settings) {
        super(indexSettings, name, settings);
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new MorfologikFilter(tokenStream);
    }
}
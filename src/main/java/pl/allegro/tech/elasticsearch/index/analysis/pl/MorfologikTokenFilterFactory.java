package pl.allegro.tech.elasticsearch.index.analysis.pl;

import morfologik.stemming.Dictionary;
import morfologik.stemming.polish.PolishStemmer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.morfologik.MorfologikFilter;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;

import java.io.IOException;


/**
 * Morfologik Token Filter Factory with configurable dictionary, default Polish.
 */
public class MorfologikTokenFilterFactory extends AbstractTokenFilterFactory {

    /**
     * Parameter "dictionary" - name of file of custom dictionary from elasticsearch /config directory.
     */
    public static final String DICTIONARY_PARAM = "dictionary";

    private final Dictionary dictionary;

    /**
     * Constructs a new MorfologikTokenFilterFactory.
     *
     * @param indexSettings - index level settings
     * @param env           - elasticsearch environment
     * @param name          - component name
     * @param settings      - component settings
     * @throws IOException - exception during creating a component
     */
    public MorfologikTokenFilterFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) throws IOException {
        super(indexSettings, name, settings);
        dictionary = getDictionary(env, settings);
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new MorfologikFilter(tokenStream, dictionary);
    }

    private Dictionary getDictionary(Environment env, Settings settings) throws IOException {
        String dictionaryParam = settings.get(DICTIONARY_PARAM);
        if (dictionaryParam == null) {
            return new PolishStemmer().getDictionary();
        }
        return Dictionary.read(env.configFile().resolve(dictionaryParam));
    }
}

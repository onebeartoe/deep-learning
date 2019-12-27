
package org.onebeartoe.deep.learning.nlp.language.pos;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

/**
 *
 * @author Roberto Marquez
 */
public class PartsOfSpeechService
{
    POSTaggerME tagger;
    
    public PartsOfSpeechService() throws IOException
    {
        POSModel model;
        
        try (InputStream modelStream = getClass().getResourceAsStream("/en-pos-maxent.bin"))
        {
            model = new POSModel(modelStream);
        }

        tagger = new POSTaggerME(model);
    }
    
    public List<PartsOfSpeechTagging> findAdjectivesNounsAndPronouns(String sentence)
    {
        return findPartsOfSpeech(sentence, adjectivesNounsAndPronouns);
    }
    
    private List<PartsOfSpeechTagging> findPartsOfSpeech(String sentence, Predicate<PartsOfSpeech> predicate)
    {
        String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(sentence);
        
        String[] tags = tagger.tag(whitespaceTokenizerLine);

        List<PartsOfSpeechTagging> posList = new ArrayList();
        
        for (int i = 0; i < whitespaceTokenizerLine.length; i++) 
        {
            String word = whitespaceTokenizerLine[i].trim();

            String tag = tags[i].trim();

            try
            {
                PartsOfSpeech pos = PartsOfSpeech.valueOf(tag);

                if( predicate.test(pos) )
                {
                    PartsOfSpeechTagging tagging = new PartsOfSpeechTagging();
                    
                    tagging.partOfSpeech = pos;
                    tagging.word = word;

                    posList.add(tagging);
                }
            }
            catch(IllegalArgumentException e)
            {
                System.err.println(tag + " is not supported - " + word);
            }
        }        
        
        return posList;        
    }

    Predicate<PartsOfSpeech> adjectives = pos ->
    {
        return pos.equals(PartsOfSpeech.JJ)
                || pos.equals(PartsOfSpeech.JJR)
                || pos.equals(PartsOfSpeech.JJS);
    };
    
    Predicate<PartsOfSpeech> nouns = pos ->
    {
        return pos.equals(PartsOfSpeech.NN)
                || pos.equals(PartsOfSpeech.NNS)
                || pos.equals(PartsOfSpeech.NNP)
                || pos.equals(PartsOfSpeech.NNPS);
    };
    
    Predicate<PartsOfSpeech> pronouns = pos ->
    {
        return pos.equals(PartsOfSpeech.PRP)
                || pos.equals(PartsOfSpeech.PRP$)
                || pos.equals(PartsOfSpeech.WP)
                || pos.equals(PartsOfSpeech.WP$);
    };
    
    Predicate<PartsOfSpeech> adjectivesNounsAndPronouns = pos -> 
    {
        return adjectives.test(pos)
                || nouns.test(pos)
                || pronouns.test(pos);
    };
}

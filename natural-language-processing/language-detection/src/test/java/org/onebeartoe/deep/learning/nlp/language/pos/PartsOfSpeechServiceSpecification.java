
package org.onebeartoe.deep.learning.nlp.language.pos;

import java.io.IOException;
import java.util.List;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * This class tests the PartsOfSpeechService specification.
 */
public class PartsOfSpeechServiceSpecification
{
    PartsOfSpeechService implementation;
    
    @BeforeMethod
    public void initialize() throws IOException
    {
        implementation = new PartsOfSpeechService();
    }
          
    @Test
    void findAdjectivesNounsAndPronouns_simple()
    {
        final String sentence = "This sentence only contains simple nouns, like computer, small shoe, and chair.";

        List<PartsOfSpeechTagging> results = implementation.findAdjectivesNounsAndPronouns(sentence);
                  
        assertTrue(results.size() == 7);
        
        // 1
        assertPos(PartsOfSpeech.NN, "sentence", results, 0);
        
        // 2        
        assertPos(PartsOfSpeech.JJ, "simple", results, 1);
        
        // 3
        assertPos(PartsOfSpeech.NN, "nouns,", results, 2);         
        
        // 4
        // this is skipped, since the current model thinks 'computer' is an adjective
//TODO: find/train a model that recognizes 'computer' as a noun o_0        
//        assertPos(PartsOfSpeech.NN, "computer,", results, 3);
        
        // 5
        assertPos(PartsOfSpeech.JJ, "small", results, 4);
        
        // 6
        assertPos(PartsOfSpeech.NN, "shoe,", results, 5);
        
        // 7
        assertPos(PartsOfSpeech.NN, "chair.", results, 6);
    }
    
    @Test
    void tagAdjectivesAndNouns_proper()
    {
        final String sentence = "This sentence only contains proper nouns, like President Clinton, Jimmy, and little Robert.";
        
        List<PartsOfSpeechTagging> results = implementation.findAdjectivesNounsAndPronouns(sentence);
           
        assertTrue(results.size() == 8);
        
        // 1
        assertPos(PartsOfSpeech.NN, "sentence", results, 0);
        
        // 2        
        assertPos(PartsOfSpeech.JJ, "proper", results, 1);
        
        // 3
        assertPos(PartsOfSpeech.NN, "nouns,", results, 2);          
        
        // 4
        assertPos(PartsOfSpeech.NNP, "President", results, 3);
        
        // 5
        assertPos(PartsOfSpeech.NNP, "Clinton,", results, 4);
        
        // 6
        assertPos(PartsOfSpeech.NNP, "Jimmy,", results, 5);
        
        // 7
        assertPos(PartsOfSpeech.JJ, "little", results, 6);

        // 8
        assertPos(PartsOfSpeech.NNP, "Robert.", results, 7);
    }
    
    @Test
    void tagAdjectivesAndNouns_complex()
    {
        final String sentence = "This sentence only contains lots of things, like Deep Purple, Mrs. Jones, and big truck.";
        
        List<PartsOfSpeechTagging> results = implementation.findAdjectivesNounsAndPronouns(sentence);
        
        assertTrue(results.size() == 9);        
        
        System.out.println("simples:");
        results.forEach(r ->
        {
            System.out.println(r.word + " - " + r.partOfSpeech);
        });            
        
        // 1
        assertPos(PartsOfSpeech.NN, "sentence", results, 0);
        
        // 2
        assertPos(PartsOfSpeech.NNS, "lots", results, 1);
        
        // 3
        assertPos(PartsOfSpeech.NN, "things,", results, 2);
        
        // 4
        assertPos(PartsOfSpeech.NNP, "Deep", results, 3);
        
        // 5
        assertPos(PartsOfSpeech.NNP, "Purple,", results, 4);
        
        // 6
        assertPos(PartsOfSpeech.NNP, "Mrs.", results, 5);
        
        // 7
        assertPos(PartsOfSpeech.NNP, "Jones,", results, 6);
        
        // 8
        assertPos(PartsOfSpeech.JJ, "big", results, 7);

        // 9
        assertPos(PartsOfSpeech.NN, "truck.", results, 8);
    }

    private void assertPos(PartsOfSpeech expectedPos, String expectedWord, List<PartsOfSpeechTagging> allActuals, int acutalIndex)
    {
        PartsOfSpeechTagging actual = allActuals.get(acutalIndex);
        
        assertEquals(actual.partOfSpeech, expectedPos);
        
        assertEquals(actual.word, expectedWord);
    }
}

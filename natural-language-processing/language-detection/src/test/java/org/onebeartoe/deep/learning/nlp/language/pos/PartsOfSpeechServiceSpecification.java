
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
        String sentence = "This sentence only contains simple nouns, like computer, small shoe, and chair.";
//DT:This  NN:sentence  RB:only  VBZ:contains  JJ:simple  NN:nouns,  IN:like  DT:cat,  JJ:small  NN:shoe,  CC:and  NN:chair.          
        List<PartsOfSpeech> results = implementation.findAdjectivesNounsAndPronouns(sentence);
        
        
        System.out.println("simples:");
        results.forEach(r ->
        {
            System.out.println(r.word + " ");
        });
        
//TODO: !!!!! ADD THIS BACK !!!!
//        assertTrue(results.size() == 9);
        
        // 1
        PartsOfSpeech expected0 = PartsOfSpeech.NN;
        expected0.word = "sentence";
        PartsOfSpeech actual = results.get(0);
        assertPos(actual, expected0);
        
        // 2
        PartsOfSpeech expected1 = PartsOfSpeech.NN;
        expected1.word = "nouns";
        actual = results.get(1);
        assertPos(actual, expected1);        
        
        // 3
        
        // 4
        
        // 5
        
        // 6
        
        // 7
        
        // 8
        
        // 9
    }
    
    @Test
    void tagAdjectivesAndNouns_proper()
    {
        String sentence = "This sentence only contains proper nouns, like President Clinton, Jimmy, and little Robert.";
        
        assertTrue(false);
    }
    
    @Test
    void tagAdjectivesAndNouns_complex()
    {
        String sentence = "This sentence only contains lots of things, like Deep Purple, Mrs. Jones, and big truck.";
        
        
        assertTrue(false);
    }

    private void assertPos(PartsOfSpeech actual, PartsOfSpeech expected)
    {
        assertEquals(actual, expected);
        
        assertEquals(actual.word, expected.word);
    }
}

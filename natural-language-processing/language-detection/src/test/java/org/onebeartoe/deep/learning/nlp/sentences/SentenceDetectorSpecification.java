
package org.onebeartoe.deep.learning.nlp.sentences;

import java.io.IOException;
import java.util.List;
import static org.onebeartoe.deep.learning.nlp.sentences.SentenceType.DECLARATIVE;
import static org.onebeartoe.deep.learning.nlp.sentences.SentenceType.EXCLAMATORY;
import static org.onebeartoe.deep.learning.nlp.sentences.SentenceType.INTERROGATIVE;
import static org.onebeartoe.deep.learning.nlp.sentences.SentenceType.INVALID_SYNTAX;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * This class tests the SentenceDetector specification.
 */
public class SentenceDetectorSpecification
{
    private SentenceDetector implementation;
    
    @BeforeMethod
    public void initialize() throws IOException
    {        
        implementation = new SentenceDetector();
    }

    @Test
    public void findSentences_singleDeclaritive()
    {
        String sentence = "I am good, thanks.";
        
        List<SentenceClassification> results = implementation.findSentences(sentence);
        
        assertTrue(results.size() == 1);
        
        assertTrue(results.get(0).getType() == DECLARATIVE);
    }

    @Test
//TODO: spell check this    
    public void findSentences_single_Interrogitave()
    {
        String sentence = "how are you?";
        
        List<SentenceClassification> results = implementation.findSentences(sentence);
        
        assertTrue(results.size() == 1);

        assertTrue(results.get(0).getType() == INTERROGATIVE);
    }
    

    @Test
//TODO: spell check this    
    public void findSentences_single_Interrogitave_withExclamationMarks()
    {
        String sentence = "what the heck is going on here!!!?!!";
        
        List<SentenceClassification> results = implementation.findSentences(sentence);
        
        assertTrue(results.size() == 1);

        assertTrue(results.get(0).getType() == INTERROGATIVE);
    }

    @Test
//TODO: spell check this    
    public void findSentences_multiple()
    {
        String sentence = "I am good thanks. how are you? That is a nice hat!";
        
        List<SentenceClassification> results = implementation.findSentences(sentence);
        
        assertTrue(results.size() == 3);
        
        assertTrue(results.get(0).getType() == DECLARATIVE);
        
        assertTrue(results.get(1).getType() == INTERROGATIVE);
        
        assertTrue(results.get(2).getType() == EXCLAMATORY);
    }
    
    @Test
    public void findSentences_invalidSyntax()
    {
        String invalid1 = null;
        
        List<SentenceClassification> results1 = implementation.findSentences(invalid1);
        
        assertTrue(results1.get(0).getType() == INVALID_SYNTAX);

        String invalid2 = "  \t  \n   ";
        
        List<SentenceClassification> results2 = implementation.findSentences(invalid2);
        
        assertTrue(results2.get(0).getType() == INVALID_SYNTAX);

        // no aphanumeric characters
        String invalid3 =  " *&^%( \t  )  ";
        
        List<SentenceClassification> results3 = implementation.findSentences(invalid3);
        
        assertTrue(results3.get(0).getType() == INVALID_SYNTAX);
    }
}

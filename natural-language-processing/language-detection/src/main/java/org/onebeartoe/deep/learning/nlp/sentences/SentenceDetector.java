
package org.onebeartoe.deep.learning.nlp.sentences;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.Span;
import static org.onebeartoe.deep.learning.nlp.sentences.SentenceType.INVALID_SYNTAX;

/**
 * This class provides methods to detect sentences.
 */
public class SentenceDetector
{
    private final SentenceDetectorME sentenceDetector;
    
    public SentenceDetector() throws IOException
    {
        InputStream modelIn = getClass().getResourceAsStream("/en-sent.bin");
        
        SentenceModel model = new SentenceModel(modelIn);

        modelIn.close();
        
        sentenceDetector = new SentenceDetectorME(model);
    }
    
    public List<SentenceClassification> findSentences(String paragraph)
    {
        String sentences[] = sentenceDetector.sentDetect(paragraph);
                                
        List<SentenceClassification> results = new ArrayList();
        
        for (String sentence : sentences) 
        {
            SentenceClassification classification = new SentenceClassification();
            
            classification.setText(sentence);
            
            System.out.println(sentence);
            
            SentenceType type = getType(sentence);
            
            results.add(classification);
        }

        return results;
    }

    private SentenceType getType(String sentence)
    {
        SentenceType type;
        
        if(sentence == null)
        {
            type = INVALID_SYNTAX;
        }
        else if( sentence.isEmpty() )
        {
            type = INVALID_SYNTAX;
        }
        else
        {
            sentence.matches("");
            
            // check the ending for punctuation
            
            int endIndex = sentence.lastIndexOf(sentence);
            
//TODO: finish this
type = INVALID_SYNTAX;
        }
                
        return type;
    }
}
            
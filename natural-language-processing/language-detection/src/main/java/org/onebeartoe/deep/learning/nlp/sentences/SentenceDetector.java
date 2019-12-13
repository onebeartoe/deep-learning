
package org.onebeartoe.deep.learning.nlp.sentences;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.Span;
import static org.onebeartoe.deep.learning.nlp.sentences.SentenceType.DECLARATIVE;
import static org.onebeartoe.deep.learning.nlp.sentences.SentenceType.EXCLAMATORY;
import static org.onebeartoe.deep.learning.nlp.sentences.SentenceType.INTERROGATIVE;
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
        List<SentenceClassification> results = new ArrayList();
        
        if(paragraph == null)
        {
            System.err.println("null paragraphs are not evaluated");
            
            SentenceClassification classification = new SentenceClassification();
            
            classification.setType(INVALID_SYNTAX);
            
            results.add(classification);
        }
        else
        {
            String [] sentences = sentenceDetector.sentDetect(paragraph);

            if(sentences.length == 0)
            {
                String [] empty = {paragraph};

                sentences = empty;
            }
            
            for (String sentence : sentences) 
            {
                SentenceClassification classification = new SentenceClassification();

                classification.setText(sentence);

                System.out.println(sentence);

                SentenceType type = getType(sentence);

                classification.setType(type);

                results.add(classification);
            }            
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
        else if( sentence.matches("[^\\w]+") )
        {
            // the string does not contain a single alphanumeric character
            
            type = INVALID_SYNTAX;
        }
        else
        {
            // there is at least one alphanumeric character
            
            // find the index of the last alphanumeric, starting from the end, using $
            int lastIndex = sentence.replaceAll("[^a-zA-Z0-9]*$", "").length() - 1;
            
            if(lastIndex == -1)
            {
                // no puctuation
                
                type = DECLARATIVE;
            }
            else
            {
                // check the ending for punctuation
                
                int start = lastIndex + 1;
                
                int end = sentence.length();

                String punctuation = sentence.substring(start , end);

                if( isInterrogativeWithExclamation(punctuation) )
                {
                    type = INTERROGATIVE;
                }
                else if( punctuation.endsWith("?") )
                {
                    type = INTERROGATIVE;
                }
                else if( punctuation.endsWith("!") )
                {
                    type = EXCLAMATORY;
                }
                else
                {
                    type = DECLARATIVE;
                }
            }
        }
                
        return type;
    }

    private boolean isInterrogativeWithExclamation(String punctuation)
    {
        // zero or more exclamation marks, followed 1 or more question marks, followed by once or more exclamation marks

        String regularExpression = "[!]*[?]+[!]+";
        
        return punctuation.matches(regularExpression);
    }
}
            
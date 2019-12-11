
package org.onebeartoe.deep.learning.nlp.named.entity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import org.onebeartoe.deep.learning.nlp.named.entity.results.DetectedNamedEntity;

/**
 * This class provides a base functionality for a named entity recognizer.
 * 
 * To load different models implement/override the #getModelClasspathLocation() method.
 */
public abstract class NamedEntityRecognizer
{
    private final NameFinderME nameFinder;
    
    private final TokenizerModel tokenModel;
    
    public NamedEntityRecognizer() throws IOException
    {
        // load the tokenizer model 
        String tokenizerModelPath = "/en-token.bin";
        
        InputStream inputStreamTokenizer = getClass().getResourceAsStream(tokenizerModelPath);
        
        tokenModel = new TokenizerModel(inputStreamTokenizer); 
       
        inputStreamTokenizer.close();
        
        // load the named entity model model for people names
        String modelClasspath = getModelClasspathLocation();

        InputStream inputStream = getClass().getResourceAsStream(modelClasspath);

        TokenNameFinderModel model = new TokenNameFinderModel(inputStream);

        inputStream.close();
        
        nameFinder = new NameFinderME(model);        
    }
    
    public List<DetectedNamedEntity> findNames(String sentence) throws IOException
    {
        TokenizerME tokenizer = new TokenizerME(tokenModel); 

        String tokens[] = tokenizer.tokenize(sentence); 
       
        Span nameSpans[] = nameFinder.find(tokens);        

        List<DetectedNamedEntity> results = new ArrayList();
        
        // map Spans to results
        for(Span span: nameSpans)        
        {
            int start = span.getStart();
            
            int endExclusive = span.getEnd();
            
            StringBuilder sb = new StringBuilder();
            
            for(int i = start; i < endExclusive; i++)
            {
                sb.append(tokens[i]);
                
                if(i != endExclusive - 1)
                {
                    // only append if there is more than one token
                    sb.append(" ");
                }
            }
            
            String name = sb.toString();
            
            System.out.println(span.toString() + "  " + name);
            
            DetectedNamedEntity result = new DetectedNamedEntity();
            
            result.setName(name);
            
            result.setLocation(start);
            
            results.add(result);
        } 

        return results;
    }

    protected abstract String getModelClasspathLocation();
}

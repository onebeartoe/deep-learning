
package org.onebeartoe.deep.learning.nlp.named.entity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import opennlp.tools.tokenize.TokenizerME; 
import opennlp.tools.tokenize.TokenizerModel; 

import opennlp.tools.namefind.NameFinderME; 
import opennlp.tools.namefind.TokenNameFinderModel; 
import opennlp.tools.util.Span;  
import org.onebeartoe.deep.learning.nlp.named.entity.results.DetectedNamedEntity;

/**
 *This class detects English names of people that appear in a text string.
 */
public class PersonNameDetector extends NamedEntityRecognizer
{
    public PersonNameDetector() throws IOException
    {
        
    }
    
//TODO refactor this to use the NamedEntityRecognizer
    public List<DetectedNamedEntity> findNames(String sentence) //throws IOException
    {
        return super.findNames(sentence);
        
        
//        String tokenizerModelPath = "/en-token.bin";
//        InputStream inputStreamTokenizer = getClass().getResourceAsStream(tokenizerModelPath);
//        TokenizerModel tokenModel = new TokenizerModel(inputStreamTokenizer); 
//
//        TokenizerME tokenizer = new TokenizerME(tokenModel); 
//
//
//        String tokens[] = tokenizer.tokenize(sentence); 
//       
//        // load the named entity model model for people names
//        String modelClasspath = "/en-ner-person.bin";
//        InputStream inputStream = getClass().getResourceAsStream(modelClasspath);
//
//        TokenNameFinderModel model = new TokenNameFinderModel(inputStream);
//
//        NameFinderME nameFinder = new NameFinderME(model);       
//
//
//        Span nameSpans[] = nameFinder.find(tokens);        
//
//        List<DetectedNamedEntity> results = new ArrayList();
//        
//        // map Spans to results
//        for(Span s: nameSpans)        
//        {
//            int index = s.getStart();
//            
//            String name = tokens[index];
//            
//            System.out.println(s.toString() + "  " + name);
//            
//            DetectedNamedEntity result = new DetectedNamedEntity();
//            
//            result.setName(name);
//            
//            result.setLocation(index);
//            
//            results.add(result);
//        } 
//
//        return results;
    }

    @Override
    protected String getModelClasspathLocation()
    {
        return "/en-ner-person.bin";
    }    
}


package org.onebeartoe.deep.learning.nlp.language.detection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import opennlp.tools.langdetect.Language;

import opennlp.tools.langdetect.LanguageDetector;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;

public class LanguageDetectionService
{
    private LanguageDetector languageDetector;
    
    private Map<String, String> languageMappings;
    
    public LanguageDetectionService() throws IOException, URISyntaxException
    {
        InputStream modelInputStream = getClass().getResourceAsStream("/language/langdetect-183.bin");

        LanguageDetectorModel trainedModel = new LanguageDetectorModel(modelInputStream);
        
        languageDetector = new LanguageDetectorME(trainedModel);

        loadMappings();
    }
    
    /**
     * This uses the Apache NLP trained language model to predict languages.
     * 
     * @param sentence
     * @return the language code for the predicted language with the highest confidence score
     */
    public String detectLanguage(String sentence)
    {
//        Language[] languages = languageDetector.predictLanguages(sentence);

        Language language = languageDetector.predictLanguage(sentence);
        
        String code = language.getLang();

        System.out.println("Predicted language: "+ code);
                
        return code;
    }
  
    /**
     * This method assumes the mappings input file only had lines with two strings,
     * separated by whitespace, with the first string being the code, and the second string
     * being the name of the language.
     * 
     * @throws URISyntaxException
     * @throws IOException 
     */
    private void loadMappings() throws URISyntaxException, IOException
    {
        InputStream instream = getClass().getResourceAsStream("/language/mappings.text");
        
        InputStreamReader reader = new InputStreamReader(instream);
        
        BufferedReader bufferedReader = new BufferedReader(reader);
        
        languageMappings = new HashMap();
        
        bufferedReader.lines()
                      .forEach(line ->
        {
            String[] split = line.trim()
                    .split("\\W");
            
            String code = split[0];
            
            String name = split[1];
            
            languageMappings.put(code, name);
        });
    }
    
    public String mapLanguage(String code) throws UnknownLanguageException
    {
        String language = languageMappings.get(code);
        
        if(language == null)
        {
            throw new UnknownLanguageException("unknown language; code -> " + code);
        }
        
        return language;
    }
}


package org.onebeartoe.deep.learning.nlp.language.detection;

import java.io.File;
import java.io.IOException;
import opennlp.tools.langdetect.Language;

import opennlp.tools.langdetect.LanguageDetector;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class LanguageDetectionService
{
//TODO: convert this main methjod to a unit test
    public static void main(String[] args) throws IOException
    {
//            LanguageMapper languageMapper = new LanguageMapper();
	    	
	    	// load the trained Language Detector Model file
	    	File modelFile = new File("src/main/resources/language/langdetect-183.bin");
	    	
	    	LanguageDetectorModel trainedModel = new LanguageDetectorModel(modelFile);
	    	
	        // load the model
	    	LanguageDetector languageDetector = new LanguageDetectorME(trainedModel);
	    	
	        // use the model for predicting the language
	    	//Spanish
//	        Language[] languages = languageDetector.predictLanguages("Puedo darte ejemplos de los métodos");
	    	
	        // French
	    	//Language[] languages = ld.predictLanguages("Je peux vous donner quelques exemples de méthodes qui ont fonctionné pour moi.");
	    	
	    	// English
	    	Language[] languages = languageDetector.predictLanguages("I can give you some examples of methods that have worked for me.");
	    		        
                String language = languages[0].getLang();
                
                String laguage = mapLanguage(language);
                
	        System.out.println("Predicted language: "+ laguage);
	        
// confidence for rest of the languages
	        for(Language l : languages)
                {
	            System.out.println(l.getLang() + "  confidence:" + l.getConfidence());
	        }

        SentenceModel model;
//        model.
        
        SentenceDetectorME sd = null;
        
        sd.sentDetect(laguage);
        
        sd.sentPosDetect(laguage);
    }
    
//TODO: make this not a static method    
    public static String mapLanguage(String code)
    {
        String language = null;
        
        if(code.equals("eng"))
        {
            language = "English";
        }
        else
        {
            language = "Unknown Language";
        }
        
        return language;
    }
}

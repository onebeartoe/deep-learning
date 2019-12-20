
package org.onebeartoe.deep.learning.natural.language.processing;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.onebeartoe.deep.learning.nlp.language.detection.LanguageDetectionService;
import org.onebeartoe.deep.learning.nlp.language.detection.UnknownLanguageException;
import org.onebeartoe.deep.learning.recurrent.neural.network.sentiment.SentimentClassification;
import org.onebeartoe.deep.learning.recurrent.neural.network.sentiment.SentimentService;

/**
 *
 * @author Roberto Marquez
 */
public class SentimentQuestion extends InterviewQuestion
{
    private LanguageDetectionService languageDetectionService;
    
    private SentimentService sentimentService;
    
    public SentimentQuestion(LanguageDetectionService languageDetectionService, SentimentService sentimentService)
    {
        this.languageDetectionService = languageDetectionService;

        this.sentimentService = sentimentService;
    }

    @Override
    public ValidationResult validateResponse(String response)
    {
        String detectedLanguageCode = languageDetectionService.detectLanguage(response);
        
        if( ! detectedLanguageCode.equals("eng") )
        {
            String language = "Unknown Language";
            
            try
            {
                language = languageDetectionService.mapLanguage(detectedLanguageCode);
            } 
            catch (UnknownLanguageException ex)
            {
                String message = ex.getMessage();
                
                System.out.println("I've encountered some trouble understanding you; " + message);
            }
            
            String message = String.format("I've detected you are speaking %s", language);
            
            System.out.println(message);
            
            System.out.println("Unfortunately, I only know English");
        }
        
        SentimentClassification classification = sentimentService.classify(response);
        
        
        
//TODO: complete this!!!!!!
System.out.println("I AM NOT DONE!!! I AM NOT DONE!!! ");
        ValidationResult result = new ValidationResult();
result.valid = true;
        
        return result;
    }

    @Override
    public String getValidResponseConfirmation()
    {
        return "some invalid resoponse";
    }

    @Override
    public List<Recommendation> getRecomendations()
    {
        List<Recommendation> recommendations = new ArrayList();
        
        Recommendation r1 = new Recommendation();
        
        recommendations.add(r1);
        recommendations.add(r1);
        
        return recommendations;
    }    
}

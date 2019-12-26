
package org.onebeartoe.deep.learning.natural.language.processing;

import java.util.List;
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
    
    private boolean positiveSentiment;
    
    private String language = "Unknown Language";
    
    public SentimentQuestion(LanguageDetectionService languageDetectionService, SentimentService sentimentService)
    {
        this.languageDetectionService = languageDetectionService;

        this.sentimentService = sentimentService;
    }

    @Override
    public ValidationResult validateResponse(String response)
    {
        String detectedLanguageCode = languageDetectionService.detectLanguage(response);
        
//        boolean valid;
        
        ValidationResult result = new ValidationResult();
        
        try
        {
            language = languageDetectionService.mapLanguage(detectedLanguageCode);
        } 
        catch (UnknownLanguageException ex)
        {
            String message = ex.getMessage();

            System.out.println("I've encountered some trouble understanding you; " + message);
        }
        
        if( ! detectedLanguageCode.equals("eng") )
        {            
            String message = String.format("I've detected you are speaking %s", language);
            
            System.out.println(message);
            
            System.out.println("Unfortunately, I only know English");
            
            isAnswered = false;
        }
        else
        {
            isAnswered = true;
            
            result.answer = response;
            
            SentimentClassification classification = sentimentService.classify(response);
            
            positiveSentiment = classification.getPositive() > classification.getNegative();
        }

        result.valid = isAnswered;

        return result;
    }

    @Override
    public String getValidResponseConfirmation()
    {
        String response;
        
        if(positiveSentiment)
        {
            response = "I am glad to hear that!";
        }
        else
        {
            response = "I hope you feel better.";
        }
        
        return response;
    }

    @Override
    public List<Recommendation> getRecomendations()
    {
        if(isAnswered)
        {
            Recommendation r1 = new Recommendation();
            
            if(positiveSentiment)
            {
                r1.recomendation = "Since you are feeling okay, try getting some fresh air."
                                    + "It will make you feel even better!";
            }
            else
            {
                r1.recomendation = "I remember you mentioned that you are not feeling well. Try getting some fresh air."
                                    + "It will make you feel a little better!";
            }            

            recommendations.add(r1);
        }
        

        
        return recommendations;
    }    

    public String getLanguage()
    {
        return language;
    }
}

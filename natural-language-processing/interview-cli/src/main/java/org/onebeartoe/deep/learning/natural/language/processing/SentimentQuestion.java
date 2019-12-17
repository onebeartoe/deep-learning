/*
 */
package org.onebeartoe.deep.learning.natural.language.processing;

/**
 *
 * @author Roberto Marquez
 */
public class SentimentQuestion extends InterviewQuestion
{

    public SentimentQuestion()
    {
    }

    @Override
    public ValidationResult validateResponse(String response)
    {
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
    
}

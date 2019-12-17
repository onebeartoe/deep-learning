
package org.onebeartoe.deep.learning.natural.language.processing;

import java.util.ArrayList;
import java.util.List;

/**
 * This POJO is an abstraction for an interview question.
 */
public abstract class InterviewQuestion
{
    private String imperative;
    
    private String response;
    
    private boolean isAnswered;
    
    protected String answer;

    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    /*
    @Override
    public InterviewQuestion clone()
    {
        InterviewQuestion clone = new InterviewQuestion();
        
        clone.setImperative( getImperative() );
        clone.setResponse( getResponse() );
        clone.setAnswered( isAnswered() );
        
        return clone;
    }
*/
    
    public boolean isAnswered()
    {
        return isAnswered;
    }

    public String getImperative()
    {
        return imperative;
    }

    public void setImperative(String imperative)
    {
        this.imperative = imperative;
    }

    public String getResponse()
    {
        return response;
    }
    
    public void setAnswered(boolean answered)
    {
        isAnswered = answered;
    }

    public boolean setResponse(String response)
    {
        this.response = response;
        
        ValidationResult result = validateResponse(response);
        
        answer = result.answer;
        
        return result.valid;
    }
    
    public abstract ValidationResult validateResponse(String response);

    public abstract String getValidResponseConfirmation();

    public List<Recommendation> getRecomendations()
    {
        return new ArrayList();
    }

    protected class ValidationResult
    {
        boolean valid;
        
        String answer;
    }
}

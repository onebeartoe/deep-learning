
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
    
    @Deprecated //"is the really deprecated")
    protected boolean isAnswered;
    
    protected String answer;

    private int attemptedAnswers;
    
    private int attemptedAnswersThreshold;

    protected List<Recommendation> recommendations;
    
    
    public InterviewQuestion()
    {
        attemptedAnswers = 0;
        
        attemptedAnswersThreshold = 2;
        
        recommendations = new ArrayList();
    }
    
    
    
    public String getAnswer()
    {
        return answer;
    }

    @Deprecated //"is the really deprecated")
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
    
    @Deprecated //"is the really deprecated")
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
    
    @Deprecated //"is the really deprecated")
    public void setAnswered(boolean answered)
    {
        isAnswered = answered;
    }
    
    public boolean thresholdReached()
    {
        return attemptedAnswers >= attemptedAnswersThreshold;
    }

    public ValidationResult setResponse(String response)
    {
        attemptedAnswers++;
        
        this.response = response;
        
        ValidationResult result = validateResponse(response);
        
        result.threadholdReached = attemptedAnswers == attemptedAnswersThreshold;

        if(result.threadholdReached)
        {
//            thresholdReached = true;
        }
        
        if(result.valid)
        {
            isAnswered = true;
        }
        
        answer = result.answer;
        
        return result;// result.valid;
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
        
        boolean threadholdReached;
    }
}


package org.onebeartoe.deep.learning.natural.language.processing;

/**
 * This pojo is an abstraction for an interview question.
 */
public class InterviewQuestion
{
    private String imperative;
    
    private String response;
    
    private boolean isAnswered;
    
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

    public void setResponse(String response)
    {
        this.response = response;
    }

    boolean getAnswered()
    {
        return isAnswered;
    }
}

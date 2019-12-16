
package org.onebeartoe.deep.learning.natural.language.processing;

import java.util.List;

/**
 * This class is an abstraction of an interview.
 */
public class Interview
{
    private List<InterviewQuestion> questions;

    private int currentQuestion;
    
    private boolean complete = false;
    
    public Interview(List<InterviewQuestion> questions)
    {
        this.questions = questions;
        
        currentQuestion = 0;
    }
    
    public InterviewQuestion currentQuestion()
    {
        InterviewQuestion ours = questions.get(currentQuestion);
        
        InterviewQuestion theirs = ours.clone();
        
        return theirs;
    }

//TODO: !!!!!!!!!!!!!! clone all the questions and return the clones !!!!!!!!!!!!
    public List<InterviewQuestion> getQuestions()
    {
        return questions;
    }
    
    public boolean isComplete()
    {        
//TODO: check if each question is answered instead
//TODO: add a unit test for this method        
        return complete;
    }
    
    public void setResponse(int index, String resonse)
    {
        InterviewQuestion question = questions.get(index);

        boolean validResponse = true;
        
        if(validResponse)
        {
            question.setAnswered(true);
            
            question.setResponse(resonse);
            
            currentQuestion++;
            
            if(currentQuestion == questions.size() )
            {
//TODO: remove this set and check if each question is answered instead                
                complete = true;
            }
        }
    }
}

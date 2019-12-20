
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
    
    @Deprecated //"is the really deprecated")
    public InterviewQuestion currentQuestion()
    {
        InterviewQuestion ours = questions.get(currentQuestion);
        
        return ours;
    }

//TODO: !!!!!!!!!!!!!! clone all the questions and return the clones !!!!!!!!!!!!
    public List<InterviewQuestion> getQuestions()
    {
        return questions;
    }
    
    public boolean isComplete()
    {
//TODO: add a unit test for this method        
        return complete;
    }
    
    public InterviewQuestion setCurrentQuestionResponse(String resonse)
    {
        InterviewQuestion question = questions.get(currentQuestion);
        
        InterviewQuestion.ValidationResult result = question.setResponse(resonse);
        
        boolean validResponse = result.valid;

        if(validResponse)
        {
            question.setAnswered(true);

            currentQuestion++;
        }
        else if(result.thresholdReached)
        {
            currentQuestion++;
        }
        
        if(currentQuestion == questions.size() )
        {
            complete = true;
        }                        
        
        return question;
    }

    @Deprecated //"is the really deprecated")
    public void markAsAnswered(int questionIndex)
    {
        InterviewQuestion question = questions.get(questionIndex);
        
        question.setAnswered(true);
    }
}

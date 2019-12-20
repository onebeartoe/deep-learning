
package org.onebeartoe.deep.learning.natural.language.processing;

import java.util.List;
import java.util.function.Predicate;

/**
 * This class is an abstraction of an interview.
 */
public class Interview
{
    private List<InterviewQuestion> questions;

    private int currentQuestion;
    
    private boolean complete = false;
    
//    private int invalidResponseCount;
    
    public Interview(List<InterviewQuestion> questions)
    {
        this.questions = questions;
        
        currentQuestion = 0;
        
//        invalidResponseCount = 0;
    }
    
    @Deprecated //"is the really deprecated")
    public InterviewQuestion currentQuestion()
    {
        InterviewQuestion ours = questions.get(currentQuestion);
        
//        InterviewQuestion theirs = ours.clone();
//        
//        return theirs;

        return ours;
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
//TODO: remove this set and check if each question is answered instead                
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

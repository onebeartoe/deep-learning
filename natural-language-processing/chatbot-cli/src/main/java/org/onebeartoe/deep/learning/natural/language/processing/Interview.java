
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
    
    Interview(List<InterviewQuestion> questions)
    {
        this.questions = questions;
        
        currentQuestion = 0;
    }
    
    InterviewQuestion currentQuestion()
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
    
    boolean isComplete()
    {
//        boolean complete = true;
//        
//        for(InterviewQuestion question : questions)
//        {
//            if( !question.isAnswered() )
//            {
//                // a question is not answered and the interview is not complete
//                
//                complete = false;
//                
//                break;
//            }
//        }
        
        return complete;
    }
    
    void setResponse(int index, String resonse)
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
                complete = true;
            }
        }
    }
}

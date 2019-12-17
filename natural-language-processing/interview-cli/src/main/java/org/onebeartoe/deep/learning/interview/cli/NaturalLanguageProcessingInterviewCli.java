
package org.onebeartoe.deep.learning.interview.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.onebeartoe.deep.learning.interview.InterviewService;
import org.onebeartoe.deep.learning.natural.language.processing.Interview;
import org.onebeartoe.deep.learning.natural.language.processing.InterviewQuestion;
import org.onebeartoe.deep.learning.natural.language.processing.Recommendation;

/**
 * This class is the main entry point for the command line interface for the 
 * natural language processing interview chatbot.
 */
public class NaturalLanguageProcessingInterviewCli
{
    public static void main(String[] args) throws IOException
    {        
        InterviewService interviewService = new InterviewService();
        
        Interview interview = interviewService.get();
        
        System.out.println("Welcome to the chatbot interview!");
        
        InputStreamReader instreamReader = new InputStreamReader(System.in);
        
        BufferedReader lineReader = new BufferedReader(instreamReader);
        
        int questionIndex = 0;
        
//TODO: refactor interview#isComplete() to interview#isNotOver()
        while( !interview.isComplete() )
        {
            InterviewQuestion currentQuestion = interview.currentQuestion();
            
            String imperitive = currentQuestion.getImperative();
            
            System.out.println(imperitive);
            
            String line = lineReader.readLine();
            
            System.out.println("Great.  You jus typed: ");
            System.out.println(line);
            
//TODO:            
// refactor this to interview#setCurrentQuestionResponse()
// the interview#setCurrentQuestionResponse() method keep track of the 'invalid resonse count'
// and moves on if the threshold is reached.
//TODO:
// the interview#setCurrentQuestionResponse() method returns a reponse type and if the 
//          response type is 'THRESHOLD-REACHED' then that message is relyed to the user and
//          the interview moves on to the next question.
            InterviewQuestion question = interview.setResponse(questionIndex, line);

            if( ! question.isAnswered() )
            {
                System.out.println("I wasn't able to process your response.");

                System.out.println(imperitive);

                String secondLine = lineReader.readLine();
                
                InterviewQuestion secondAttemptQuestion = interview.setResponse(questionIndex, secondLine);
                
                if( !secondAttemptQuestion.isAnswered() )
                {
                    System.out.println("I still could not process your response.  Let's move on with the interview.");
                    
                    interview.markAsAnswered(questionIndex);
                }
            }
            
            if( question.isAnswered() )
            {
                String confirmation = question.getValidResponseConfirmation();
                
                System.out.println(confirmation);
                
                List<Recommendation> recomendations = question.getRecomendations();
            }            

//TODO: remove this index counter and relay on the interview's currentQuestion() method            
            questionIndex++;
        }
        
        System.out.println("Thanks for participating in the interview!");
    }
}

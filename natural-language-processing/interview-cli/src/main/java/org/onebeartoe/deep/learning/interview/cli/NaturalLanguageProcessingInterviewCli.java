
package org.onebeartoe.deep.learning.interview.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.List;
import org.onebeartoe.deep.learning.interview.InterviewService;
import org.onebeartoe.deep.learning.natural.language.processing.Interview;
import org.onebeartoe.deep.learning.natural.language.processing.InterviewQuestion;
import org.onebeartoe.deep.learning.natural.language.processing.Recommendation;
import org.onebeartoe.deep.learning.natural.language.processing.ValidationResult;

/**
 * This class is the main entry point for the command line interface for the 
 * natural language processing interview chatbot.
 */
public class NaturalLanguageProcessingInterviewCli
{
    public static void main(String[] args) throws IOException, URISyntaxException
    {        
        InterviewService interviewService = new InterviewService();
        
        Interview interview = interviewService.get();
        
        System.out.println("Welcome to the chatbot interview!");
        
        InputStreamReader instreamReader = new InputStreamReader(System.in);
        
        BufferedReader lineReader = new BufferedReader(instreamReader);
                
//TODO: refactor interview#isComplete() to interview#isNotOver()
        while( !interview.isComplete() )
        {
            InterviewQuestion currentQuestion = interview.currentQuestion();
            
            String imperitive = currentQuestion.getImperative();
            
            System.out.println(imperitive);
            
            String line = lineReader.readLine();

            ValidationResult result = interview.setCurrentQuestionResponse(line);
            
            if(result.responseContainedQuestion)
            {
                System.out.println("I am not sure about your question: " + result.questionInResponse);
            }

            if( ! result.valid )
            {
                System.out.println("I wasn't able to process your response.");
                
                if( result.thresholdReached )
                {
                    System.out.println(":(  Let's move on with the interview.");
                }
            }
            else
            {
                String confirmation = currentQuestion.getValidResponseConfirmation();
                
                System.out.println(confirmation);
                
                List<Recommendation> recomendations = currentQuestion.getRecomendations();
                
                if(recomendations.size() > 0)
                {
                    System.out.println("Here are some recomendations for " + result.answer);
                
                    recomendations.forEach( System.out::println );
                }
            }
        }
        
        System.out.println("Thanks for participating in the interview!");
    }
}

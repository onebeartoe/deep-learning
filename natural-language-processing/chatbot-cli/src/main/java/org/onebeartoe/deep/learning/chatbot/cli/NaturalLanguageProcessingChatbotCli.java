
package org.onebeartoe.deep.learning.chatbot.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.onebeartoe.deep.learning.chatbot.InterviewService;
import org.onebeartoe.deep.learning.natural.language.processing.Interview;
import org.onebeartoe.deep.learning.natural.language.processing.InterviewQuestion;

/**
 *
 * @author Roberto Marquez
 */
public class NaturalLanguageProcessingChatbotCli
{
    public static void main(String[] args) throws IOException
    {        
        InterviewService interviewService = new InterviewService();
        
        Interview interview = interviewService.get();
        
        System.out.println("Welcome to the chatbot interview!");
        
        InputStreamReader instreamReader = new InputStreamReader(System.in);
        
        BufferedReader lineReader = new BufferedReader(instreamReader);
        
        int questionIndex = 0;
        
        while( !interview.isComplete() )
        {
            InterviewQuestion currentQuestion = interview.currentQuestion();
            
            String imperitive = currentQuestion.getImperative();
            
            System.out.println(imperitive);
            
            String line = lineReader.readLine();
            
            System.out.println("Great.  You jus typed: ");
            System.out.println(line);
            
            interview.setResponse(questionIndex, line);
            
            questionIndex++;
        }
        
        System.out.println("Thanks for participating in the interview!");
    }
}

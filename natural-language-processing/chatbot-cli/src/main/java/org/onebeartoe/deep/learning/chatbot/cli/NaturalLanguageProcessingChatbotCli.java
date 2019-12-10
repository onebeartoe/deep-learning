
package org.onebeartoe.deep.learning.chatbot.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.onebeartoe.deep.learning.chatbot.InterviewService;
import org.onebeartoe.deep.learning.natural.language.processing.Interview;

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
        
        System.out.println("Welcome to the chatbot interview!  How are you?");
        
        InputStreamReader instreamReader = new InputStreamReader(System.in);
        
        BufferedReader lineReader = new BufferedReader(instreamReader);
                
        String line = lineReader.readLine();
        
        while( !interview.isComplete() )
        {
            System.out.println("Great.  You jus typed: ");
            System.out.println(line);
            
            line = lineReader.readLine();
        }
    }
}


package org.onebeartoe.deep.learning.chatbot.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Roberto Marquez
 */
public class NaturalLanguageProcessingChatbotCli
{
    public static void main(String[] args) throws IOException
    {
        System.out.println("Welcome to chatbot.  How are you?");
        
        InputStreamReader instreamReader = new InputStreamReader(System.in);
        
        BufferedReader lineReader = new BufferedReader(instreamReader);
                
        String line = lineReader.readLine();
        
        while(true)
        {
            System.out.println("Great.  You jus typed: ");
            System.out.println(line);
            
            line = lineReader.readLine();
        }
    }
}

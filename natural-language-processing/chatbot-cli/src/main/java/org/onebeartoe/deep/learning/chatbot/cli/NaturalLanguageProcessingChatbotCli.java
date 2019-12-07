
package org.onebeartoe.deep.learning.chatbot.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Roberto Marquez
 */
public class NaturalLanguageProcessingChatbotCli
{
    public static void main(String[] args) throws IOException
    {
        System.out.println("ploop");
        final List l = new ArrayList();
        
        l.add("w");
        
        System.out.println("l = " + l.toString());
        
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

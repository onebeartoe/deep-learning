
package org.onebeartoe.user.story;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.reporters.Files;

/**
 * This class generates a report on user story completion.
 */
public class StoryCompletionReport
{
    public static void main(String[] args) throws FileNotFoundException
    {
        StoryCompletionReport app = new StoryCompletionReport();
        
        String inputDir = "documentation/";
        
        String acceptedToken = "-*=ACCEPTED=*-";

        app.generateReport(inputDir, acceptedToken);
    }

    private void generateReport(String inputDirPath, String acceptedToken) throws FileNotFoundException
    {
        File inputDir = new File(inputDirPath);
     
        if( !inputDir.exists() )
        {
            throw new FileNotFoundException("the input directory does not exist");
        }
        
        if( !inputDir.isDirectory() )
        {
            throw new IllegalArgumentException("the input path exists but is not a directory");
        }
        
        File [] allFiles = inputDir.listFiles();
        
        List<File> storyFiles = new ArrayList();
        
        Arrays.asList(allFiles)
                .forEach(f ->
                {
                    String name = f.getName();
                    
                    if( name.endsWith(".story") )
                    {
                        storyFiles.add(f);
                    }
                });
        
        int count = 0;
        
        for(File storyFile :storyFiles)
        {
            try
            {
                String contents = Files.readFile(storyFile);
                
                if( !contents.contains(acceptedToken) )
                {
                    System.out.println(storyFile.getPath() + " is not complete");
                    
                    count++;
                }
            } 
            catch (IOException ex)
            {
                Logger.getLogger(StoryCompletionReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        String countMessage = String.format("%d stories are not complete", count);
        System.out.println(countMessage);
    }
}

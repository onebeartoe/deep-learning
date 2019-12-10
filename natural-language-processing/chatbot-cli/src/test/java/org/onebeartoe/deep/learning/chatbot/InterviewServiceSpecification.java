
package org.onebeartoe.deep.learning.chatbot;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import org.onebeartoe.deep.learning.natural.language.processing.Interview;
import org.onebeartoe.deep.learning.natural.language.processing.InterviewQuestion;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * This class tests the InterviewService specification.
 */
public class InterviewServiceSpecification
{
    private InterviewService implementation;
    
    private Properties properties;
    
    @BeforeClass
    private void initializeProperties() throws IOException
    {
        InputStream propertiesStream = getClass().getResourceAsStream("/interview-questions.properties");
        
        properties = new Properties();
        
        properties.load(propertiesStream);
    }
    
    @BeforeMethod
    public void initialize() throws IOException
    {
        implementation = new InterviewService();        
    }
    
    /**
     * This verifies the name interview question is present.
     */
    @Test
    public void get_nameQuestionIsPresent()
    {
        Interview interview = implementation.get();
        
        List<InterviewQuestion> questions = interview.getQuestions();
        
        String expected = properties.getProperty("nameQuestion");
        
        boolean found = false;
        
        for(InterviewQuestion question : questions)
        {
            String imparative = question.getImperative();
            
            if( imparative.equals(expected) )
            {
                found = true;
                
                break;
            }
        }
        
        assertTrue(found);
    }
}

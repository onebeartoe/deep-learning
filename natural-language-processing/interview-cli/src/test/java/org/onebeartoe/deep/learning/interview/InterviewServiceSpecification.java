
package org.onebeartoe.deep.learning.interview;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;
import org.onebeartoe.deep.learning.natural.language.processing.Interview;
import org.onebeartoe.deep.learning.natural.language.processing.InterviewQuestion;
import org.onebeartoe.deep.learning.natural.language.processing.ValidationResult;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
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
    
    private Interview interview;
    
    private Properties properties;
    
    @BeforeClass
    private void initializeProperties() throws IOException
    {
        InputStream propertiesStream = getClass().getResourceAsStream("/interview-questions.properties");
        
        properties = new Properties();
        
        properties.load(propertiesStream);
    }
    
    @BeforeMethod
    public void initialize() throws IOException, URISyntaxException
    {
        implementation = new InterviewService();

        interview = implementation.get();
    }

    @Test
    public void interview_happyPath()
    {
        interview_happyPath_name();

        interview_happyPath_sentiment();
        
        interview_happyPath_projectPercetage();

        interview_happyPath_date();
        
        assertTrue( interview.isComplete() );
    }

    private void interview_happyPath_date()
    {
        String expectedDate = "December 2";
        
        String response = String.format("I like %s", expectedDate);
        
        ValidationResult result = interview.setCurrentQuestionResponse(response);
        
        assertTrue(result.valid);
        
        String actualDate = interview.getDateOfInterest();

        assertEquals(actualDate, expectedDate);
    }
    
    private void interview_happyPath_name()
    {
        String expectedName = "Bob";
        
        ValidationResult result = interview.setCurrentQuestionResponse(expectedName);
        
        assertTrue(result.valid);

        String actualName = interview.getIntervieweeName();
        
        assertEquals(actualName, expectedName);        
    }
    
    /**
     * how are you question
     */
    private void interview_happyPath_sentiment()
    {
        ValidationResult result = interview.setCurrentQuestionResponse("I am great, thanks");

        assertTrue(result.valid);
        
        String expectedLanguage = "English";
        
        String acutalLanguage = interview.getIntervieweeLanguage();
        
        assertEquals(acutalLanguage, expectedLanguage);
    }

    private void interview_happyPath_projectPercetage()
    {
        String expectedPercent = "70%";

        String response = String.format("I am working on an Aruino project.  It is %s done.", expectedPercent);

        ValidationResult result = interview.setCurrentQuestionResponse(response);

        assertTrue(result.valid);
        
        String project = interview.getProject();
        
        assertNotNull(project);
        
        String actualPercentageComplete = interview.getProjectPercentage();
        
        assertEquals(actualPercentageComplete, expectedPercent);
    }
    
    @Test void interview_invalidResonse_all()
    {
        // name question
        ValidationResult result = interview.setCurrentQuestionResponse("apple");
        assertFalse(result.valid);

        result = interview.setCurrentQuestionResponse("apple");
        assertFalse(result.valid);
        
        // sentiment question, Spanish fails
        result = interview.setCurrentQuestionResponse("Muey buien, gracias");
        assertFalse(result.valid);
        
        result = interview.setCurrentQuestionResponse("Muey buien, gracias");
        assertFalse(result.valid);
        
        // project question
        result = interview.setCurrentQuestionResponse("I work on nothing.");
        assertFalse(result.valid);
        
        result = interview.setCurrentQuestionResponse("I don't need no stinking project.");
        assertFalse(result.valid);

        // date question
        result = interview.setCurrentQuestionResponse("Who needs a date?");
        assertFalse(result.valid);
        
        result = interview.setCurrentQuestionResponse("The date is here.");
        assertFalse(result.valid);
        
        assertTrue( interview.isComplete() );
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
    
    /**
     * This verifies all interview question have non-null values.
     */
    @Test
    public void get_noNullQuestions()
    {
        Interview interview = implementation.get();
        
        List<InterviewQuestion> questions = interview.getQuestions();
        
        questions.forEach(q ->
        {
            String question = q.getImperative();
            
            assertNotNull(question);
        });
    }
}

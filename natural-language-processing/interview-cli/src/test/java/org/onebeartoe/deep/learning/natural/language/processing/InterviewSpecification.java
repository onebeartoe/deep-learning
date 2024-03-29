
package org.onebeartoe.deep.learning.natural.language.processing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import org.onebeartoe.deep.learning.interview.InterviewService;
import org.onebeartoe.deep.learning.nlp.language.detection.LanguageDetectionService;
import org.onebeartoe.deep.learning.recurrent.neural.network.sentiment.SentimentService;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

/**
 * This class verifies the Interview specification.
 */
public class InterviewSpecification
{
//    private Interview implementation_makeThisLocalToEachTest_ifStillNeded;
    
    private Interview implementation;
    
    private int questionCount;
    
    private List<InterviewQuestion> sampleQuestions;
    
    @BeforeMethod
    public void setupMethodData() throws IOException, URISyntaxException, FileNotFoundException, GeneralSecurityException
    {
        sampleQuestions = new ArrayList();

        LanguageDetectionService languageDetectionService = new LanguageDetectionService();
    
        SentimentService sentimentService = new SentimentService();
        
        InterviewQuestion q1 = new SentimentQuestion(languageDetectionService, sentimentService);
        q1.setImperative("how are you?");
        
        InterviewQuestion q2 = new ProjectAndPercentageQuestion(null, null);
        q2.setImperative("what percent is your project done?");
        
        sampleQuestions.add(q1);
        sampleQuestions.add(q2);
        
        questionCount = sampleQuestions.size();
        
        InterviewService interviewService = new InterviewService();
        
        implementation = interviewService.get();
    }

    @Test(description = "verify the response is set")
    public void setCurrentQuestionResponse()
    {
        String response = "this is a pretty valid response";
        
        int questionIndex = 0;
        
        implementation.setCurrentQuestionResponse(response);
        
        List<InterviewQuestion> questions = implementation.getQuestions();
        
        InterviewQuestion question = questions.get(questionIndex);
        
        String actual = question.getResponse();
        
        String expected = response;
        
        assertEquals(actual, expected);
    }

    @Test
    public void setCurrentQuestionResponse_detectResonseDoesNotContainsQuestion()
    {
        String response = "Yes.";
        
        ValidationResult result = implementation.setCurrentQuestionResponse(response);
        
        boolean actual = result.responseContainedQuestion;
        
        boolean expected = false;
        
        assertEquals(actual, expected);
    }
    
    @Test
    public void setCurrentQuestionResponse_detectResonseContainsQuestion()
    {
        String expectedQuestion = "What is your name?";
        
        String response = String.format("Yes.  %s", expectedQuestion);
        
        ValidationResult result = implementation.setCurrentQuestionResponse(response);
        
        boolean actual = result.responseContainedQuestion;
        
        boolean expected = true;
        
        assertEquals(actual, expected);
        
        String actualQuestion = result.questionInResponse;
        
        assertEquals(actualQuestion, expectedQuestion);
    }
}

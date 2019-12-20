
package org.onebeartoe.deep.learning.natural.language.processing;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.onebeartoe.deep.learning.nlp.language.detection.LanguageDetectionService;
import org.onebeartoe.deep.learning.recurrent.neural.network.sentiment.SentimentService;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

/**
 * This class verifies the Interview specification.
 */
public class InterviewSpecification
{
    private Interview implementation;
    
    private int questionCount;
    
    private List<InterviewQuestion> sampleQuestions;
    
    @BeforeMethod
    public void setupMethodData() throws IOException, URISyntaxException
    {
        sampleQuestions = new ArrayList();

        LanguageDetectionService languageDetectionService = new LanguageDetectionService();
    
        SentimentService sentimentService = new SentimentService();
        
        InterviewQuestion q1 = new SentimentQuestion(languageDetectionService, sentimentService);
        q1.setImperative("how are you?");
        
        InterviewQuestion q2 = new ProjectAndPercentageQuestion();
        q2.setImperative("what percent is your project done?");
        
        sampleQuestions.add(q1);
        sampleQuestions.add(q2);
        
        questionCount = sampleQuestions.size();
        
        implementation = new Interview(sampleQuestions);        
    }
    
    @Test
    public void construction()
    {        
        List<InterviewQuestion> questionsAfterConstruction = implementation.getQuestions();
        
        int expected = questionCount;
        
        int actual = questionsAfterConstruction.size();
        
        assertEquals(actual, expected);
    }    

    
    
    /**
     * This test verifies that no matter how many times the current question is 
     * requested that the interview is not complete util all question are answered.
     */
    @Test
    public void isComplete_fewQuestionsAnswered()
    {
        String response = "this is a very valid answer";
        
        int firstQuestionIndex = 0;
        
        implementation.setResponse(firstQuestionIndex, response);
        
        int iterationCount = questionCount + 3;  // add 3 for good measure 
        
        for(int i=0; i<iterationCount; i++)
        {
            implementation.currentQuestion();
        }
        
        boolean actual = implementation.isComplete();
        
        boolean expected = false;
        
        assertEquals(actual, expected);
    }
    
    @Test
    public void isComplete_allQuestionsAnswred()
    {
        int iterationCount = sampleQuestions.size();
        
        String response = "this is a very valid answer";
        
        for(int i=0; i<iterationCount; i++)
        {
            implementation.setResponse(i, response);
        }

        boolean actual = implementation.isComplete();
        
        boolean expected = true;
        
        assertEquals(actual, expected);
    }
    
    @Test
    public void setResponse()
    {
        String response = "this is a pretty valid response";
        
        int questionIndex = 0;
        
        implementation.setResponse(questionIndex, response);
        
        List<InterviewQuestion> questions = implementation.getQuestions();
        
        InterviewQuestion question = questions.get(questionIndex);
        
        String actual = question.getResponse();
        
        String expected = response;
        
        assertEquals(actual, expected);
    }
}

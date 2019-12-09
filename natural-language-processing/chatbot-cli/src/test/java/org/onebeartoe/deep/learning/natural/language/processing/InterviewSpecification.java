
package org.onebeartoe.deep.learning.natural.language.processing;

import java.util.ArrayList;
import java.util.List;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

/**
 * This class verifies the Interview specification.
 */
public class InterviewSpecification
{
    private Interview specification;
    
    private int questionCount;
    
    private List<InterviewQuestion> sampleQuestions;
    
    @BeforeMethod
    public void setupMethodData()
    {
        sampleQuestions = new ArrayList();
        
        InterviewQuestion q1 = new InterviewQuestion();
        q1.setImperative("how are you?");
        
        InterviewQuestion q2 = new InterviewQuestion();
        q2.setImperative("what is your favorite color?");
        
        sampleQuestions.add(q1);
        sampleQuestions.add(q2);
        
        questionCount = sampleQuestions.size();
        
        specification = new Interview(sampleQuestions);        
    }
    
    @Test
    public void construction()
    {        
        List<InterviewQuestion> questionsAfterConstruction = specification.getQuestions();
        
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
        
        specification.setResponse(firstQuestionIndex, response);
        
        int iterationCount = questionCount + 3;  // add 3 for good measure 
        
        for(int i=0; i<iterationCount; i++)
        {
            specification.currentQuestion();
        }
        
        boolean actual = specification.isComplete();
        
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
            specification.setResponse(i, response);
        }

        boolean actual = specification.isComplete();
        
        boolean expected = true;
        
        assertEquals(actual, expected);
    }
    
    @Test
    public void setResponse()
    {
        String response = "this is a pretty valid response";
        
        int questionIndex = 0;
        
        specification.setResponse(questionIndex, response);
        
        List<InterviewQuestion> questions = specification.getQuestions();
        
        InterviewQuestion question = questions.get(questionIndex);
        
        String actual = question.getResponse();
        
        String expected = response;
        
        assertEquals(actual, expected);
    }
}

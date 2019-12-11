
package org.onebeartoe.deep.learning.interview;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.onebeartoe.deep.learning.natural.language.processing.Interview;
import org.onebeartoe.deep.learning.natural.language.processing.InterviewQuestion;

/**
 * This class configures an Interview instance to use with the chatbot application.
 */
public class InterviewService
{
    private Properties properties;
    
    public InterviewService() throws IOException
    {
        InputStream propertiesStream = getClass().getResourceAsStream("/interview-questions.properties");
        
        properties = new Properties();
        
        properties.load(propertiesStream);
    }
    
    public Interview get()
    {
        List<InterviewQuestion> questions = new ArrayList();
        
        InterviewQuestion nameQuestion = new InterviewQuestion();
        
        String question = properties.getProperty("nameQuestion");
        
        nameQuestion.setImperative(question);
        
        questions.add(nameQuestion);
        
        Interview interview = new Interview(questions);

        return interview;
    }
}

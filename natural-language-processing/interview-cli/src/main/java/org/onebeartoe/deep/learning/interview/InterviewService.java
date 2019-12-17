
package org.onebeartoe.deep.learning.interview;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.onebeartoe.deep.learning.natural.language.processing.Interview;
import org.onebeartoe.deep.learning.natural.language.processing.InterviewQuestion;
import org.onebeartoe.deep.learning.natural.language.processing.SentimentQuestion;
import org.onebeartoe.deep.learning.natural.language.processing.UserNameQuestion;

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
        
        InterviewQuestion nameQuestion = new UserNameQuestion();
        String question = properties.getProperty("nameQuestion");
        nameQuestion.setImperative(question);
        
        InterviewQuestion sentimentQuestion = new SentimentQuestion();
        String question2 = properties.getProperty("sentimentQuestion");
        sentimentQuestion.setImperative(question2);
        
        questions.add(nameQuestion);
        questions.add(sentimentQuestion);
        
        Interview interview = new Interview(questions);

        return interview;
    }
}


package org.onebeartoe.deep.learning.interview;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.onebeartoe.deep.learning.natural.language.processing.Interview;
import org.onebeartoe.deep.learning.natural.language.processing.InterviewQuestion;
import org.onebeartoe.deep.learning.natural.language.processing.ProjectAndPercentageQuestion;
import org.onebeartoe.deep.learning.natural.language.processing.SentimentQuestion;
import org.onebeartoe.deep.learning.natural.language.processing.UserNameQuestion;
import org.onebeartoe.deep.learning.nlp.language.detection.LanguageDetectionService;
import org.onebeartoe.deep.learning.nlp.named.entity.PercentageDetector;
import org.onebeartoe.deep.learning.nlp.named.entity.PersonNameDetector;
import org.onebeartoe.deep.learning.nlp.sentences.SentenceDetector;
import org.onebeartoe.deep.learning.recurrent.neural.network.sentiment.SentimentService;

/**
 * This class configures an Interview instance to use with the chatbot application.
 */
public class InterviewService
{
    private Properties properties;
    
    private PersonNameDetector nameDetector;

    private LanguageDetectionService languageDetectionService;
    
    private SentimentService sentimentService;    

    private SentenceDetector sentenceDetector;
    
    private PercentageDetector percentageDetector;
    
    public InterviewService() throws IOException, URISyntaxException
    {
        InputStream propertiesStream = getClass().getResourceAsStream("/interview-questions.properties");
        
        properties = new Properties();
        
        properties.load(propertiesStream);
        
        nameDetector = new PersonNameDetector();
        
        languageDetectionService = new LanguageDetectionService();
        
        sentimentService = new SentimentService();
        
        sentenceDetector = new SentenceDetector();
        
        percentageDetector = new PercentageDetector();
    }
    
    public Interview get()
    {
        List<InterviewQuestion> questions = new ArrayList();
        
        InterviewQuestion nameQuestion = new UserNameQuestion(nameDetector);
        
        String question = properties.getProperty("nameQuestion");
        nameQuestion.setImperative(question);
        
        InterviewQuestion sentimentQuestion = new SentimentQuestion(languageDetectionService, sentimentService);
        String question2 = properties.getProperty("sentimentQuestion");
        sentimentQuestion.setImperative(question2);

        String question3 = properties.getProperty("percentageQuestion");
        InterviewQuestion projectAndPercentageQuestion = new ProjectAndPercentageQuestion(percentageDetector);
        projectAndPercentageQuestion.setImperative(question3);
        
        questions.add(nameQuestion);
        questions.add(sentimentQuestion);
        questions.add(projectAndPercentageQuestion);
        
        Interview interview = new Interview(questions);

        interview.setSentenceDetector(sentenceDetector);
        
        return interview;
    }
}

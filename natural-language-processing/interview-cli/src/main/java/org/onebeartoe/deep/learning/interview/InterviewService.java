
package org.onebeartoe.deep.learning.interview;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.onebeartoe.deep.learning.natural.language.processing.DateQuestion;
import org.onebeartoe.deep.learning.natural.language.processing.Interview;
import org.onebeartoe.deep.learning.natural.language.processing.InterviewQuestion;
import org.onebeartoe.deep.learning.natural.language.processing.LocationQuestion;
import org.onebeartoe.deep.learning.natural.language.processing.MoneyQuestion;
import org.onebeartoe.deep.learning.natural.language.processing.ProjectAndPercentageQuestion;
import org.onebeartoe.deep.learning.natural.language.processing.SentimentQuestion;
import org.onebeartoe.deep.learning.natural.language.processing.UserNameQuestion;
import org.onebeartoe.deep.learning.nlp.language.detection.LanguageDetectionService;
import org.onebeartoe.deep.learning.nlp.named.entity.DateDetector;
import org.onebeartoe.deep.learning.nlp.named.entity.LocationDetector;
import org.onebeartoe.deep.learning.nlp.named.entity.MoneyDetector;
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

    private DateDetector dateDetector;
    
    private MoneyDetector moneyDetector;
    
    private LocationDetector locationDetector;
    
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
        
        dateDetector = new DateDetector();
        
        moneyDetector = new MoneyDetector();
        
        locationDetector = new LocationDetector();
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
        
        String question4 = properties.getProperty("moneyQuestion");
        InterviewQuestion moneyQuestion = new MoneyQuestion(moneyDetector);
        moneyQuestion.setImperative(question4);

        String question5 = properties.getProperty("dateQuestion");
        InterviewQuestion dateQuestion = new DateQuestion(dateDetector);
        dateQuestion.setImperative(question5);
        
        String question6 = properties.getProperty("locationQuestion");
        InterviewQuestion locationQuestion = new LocationQuestion(locationDetector);
        locationQuestion.setImperative(question6);

        questions.add(nameQuestion);
        questions.add(sentimentQuestion);
        questions.add(projectAndPercentageQuestion);
        questions.add(moneyQuestion);
        questions.add(dateQuestion);
        questions.add(locationQuestion);
        
        Interview interview = new Interview(questions);

        interview.setSentenceDetector(sentenceDetector);
        
        return interview;
    }
}

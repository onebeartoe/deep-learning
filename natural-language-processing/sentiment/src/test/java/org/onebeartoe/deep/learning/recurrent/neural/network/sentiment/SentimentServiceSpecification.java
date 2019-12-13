
package org.onebeartoe.deep.learning.recurrent.neural.network.sentiment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * This class verifies the SentimentService specification.
 */
public class SentimentServiceSpecification
{
    private SentimentService implementation;
    
    private Properties sentiments;
    
    @BeforeClass
    public void initialize() throws IOException
    {
        implementation = new SentimentService();
        
        sentiments = new Properties();

        InputStream inStream = getClass().getResourceAsStream("/sentiments.text");
        
        sentiments.load(inStream);
    }

    @Test
    public void classify_negative1()
    {
        String text = sentiments.getProperty("negative1");
        
        SentimentClassification classification = implementation.classify(text);
        
        assertTrue( classification.getPositive() < classification.getNegative() );
    }

    @Test
    public void classify_negative2()
    {
        String text = sentiments.getProperty("negative2");
        
        SentimentClassification classification = implementation.classify(text);
        
        assertTrue( classification.getPositive() < classification.getNegative() );
    }

    @Test
    public void classify_negative3()
    {
        String text = "I don't feel well";
        
        SentimentClassification classification = implementation.classify(text);
        
        assertTrue( classification.getPositive() < classification.getNegative() );
    }

    @Test
    public void classify_negative4()
    {
        String text = "I feel crappy";
        
        SentimentClassification classification = implementation.classify(text);
        
        assertTrue( classification.getPositive() < classification.getNegative() );
    }
    
    @Test
    public void classify_positive1()
    {
        String text = sentiments.getProperty("positive1");
        
        SentimentClassification classification = implementation.classify(text);
        
        assertTrue( classification.getPositive() > classification.getNegative() );
    }
    
    @Test
    public void classify_positive2()
    {
        String text = sentiments.getProperty("positive2");
        
        SentimentClassification classification = implementation.classify(text);
        
        assertTrue( classification.getPositive() > classification.getNegative() );
    }
    
    @Test
    public void classify_positive3()
    {
        String text = sentiments.getProperty("positive3");
        
        SentimentClassification classification = implementation.classify(text);
        
        assertTrue( classification.getPositive() > classification.getNegative() );
    }
    
    @Test
    public void classify_positive4()
    {
        String text = "I am good.";
        
        SentimentClassification classification = implementation.classify(text);
        
        assertTrue( classification.getPositive() > classification.getNegative() );
    }

    @Test
    public void classify_positive_yes()
    {
//        String text = "okay";
        String text = "yes";
//        String text = "Yes";
        
        SentimentClassification classification = implementation.classify(text);
        
        assertTrue( classification.getPositive() > classification.getNegative() );
    }

    @Test
    public void classify_negitive_no()
    {
        String text = "No";
        
        SentimentClassification classification = implementation.classify(text);
        
        assertTrue( classification.getPositive() < classification.getNegative() );
    }
}


package org.onebeartoe.deep.learning.nlp.named.entity;

import java.io.IOException;
import java.util.List;
import org.onebeartoe.deep.learning.nlp.named.entity.results.DetectedNamedEntity;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * This class tests the MoneyDetector specification
 */
public class MoneyDetectorSpecification
{
    private MoneyDetector implementation;
    
    @BeforeMethod
    public void initialize() throws IOException
    {
        implementation = new MoneyDetector();
    }
    
    @Test
    public void findMoney_withDollarSign() throws IOException
    {
        String expected = "$10";
        
        findSingleMoney(expected);
    }

//TODO: re-enable this once the regular expression finder is used (for 'bucks' as a money indicator)    
    @Test(
enabled = false)
    public void findMoney_withBucks() throws IOException
    {
        String expected = "10 bucks";
        
        findSingleMoney(expected);
    }

//TODO: re-enable this once the regular expression finder is used (for 'bucks' as a money indicator)    
    @Test(
enabled = false)
    public void findMoney_withComma() throws IOException
    {
        String expected = "$10,501";
//        String expected = "$105.01";
        
        findSingleMoney(expected);
    }    
    
    private void findSingleMoney(String expected) throws IOException
    {
        String sentence = String.format("The kids have %s for lunch.", expected);
        
        List<DetectedNamedEntity> results = implementation.findMoney(sentence);
        
        assertTrue(results.size() == 1);
        
        String actual = results.get(0).getName();
        
        assertEquals(actual, expected);
    }
}

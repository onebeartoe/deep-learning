
package org.onebeartoe.deep.learning.nlp.named.entity;


import java.io.IOException;
import java.util.List;
import org.onebeartoe.deep.learning.nlp.named.entity.results.DetectedNamedEntity;
import org.testng.Assert;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * This class tests the PercentageDetector specification.
 */
public class PercentageDetectorSpecificaiton
{
    private PercentageDetector implementation;
    
    @BeforeMethod
    public void initialize() throws IOException
    {
        implementation = new PercentageDetector();
    }    

    @Test
    public void findPercentages_single() throws IOException
    {
        String expected = "50%";
        
        String sentence = String.format("The project is %s complete.", expected);
        
        List<DetectedNamedEntity> results = implementation.findPercentages(sentence);
        
        assertTrue(results.size() == 1);
        
        assertEquals(results.get(0).getName(), expected);
    }    

//TODO: enable this once the regular expression finder is implemnted to look for 
//      precentages expressed as words    
    @Test(enabled = false)
    public void findPercentages_single_words() throws IOException
    {
        String expected = "half";
        
        String sentence = String.format("The project is %s complete.", expected);
        
        List<DetectedNamedEntity> results = implementation.findPercentages(sentence);
        
        assertTrue(results.size() == 1);
        
        assertEquals(results.get(0).getName(), expected);
    }   
}

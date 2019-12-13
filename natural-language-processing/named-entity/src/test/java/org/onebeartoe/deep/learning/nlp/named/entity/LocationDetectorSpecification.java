
package org.onebeartoe.deep.learning.nlp.named.entity;

import java.io.IOException;
import java.util.List;
import org.onebeartoe.deep.learning.nlp.named.entity.results.DetectedNamedEntity;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * This class tests the LocationDetector specification.
 */
public class LocationDetectorSpecification
{
    private LocationDetector implementation;
    
    @BeforeMethod
    public void initialize() throws IOException
    {
        implementation = new LocationDetector();
    }
    
    @Test
    public void findLocations_single() throws IOException
    {
        String expected = "San Antonio";

        String sentence = String.format("I am from %s", expected);
        
        List<DetectedNamedEntity> results = implementation.detectLocations(sentence);
        
        assertTrue(results.size() == 1);
        
        String actual = results.get(0).getName();
        
        assertEquals(actual, expected);        
    }

    @Test
    public void findLocations_multiple() throws IOException
    {
        String expected1 = "London";
        
        String expected2 = "Paris";

        String sentence = String.format("They have visited %s and %s", expected1, expected2);
        
        List<DetectedNamedEntity> results = implementation.detectLocations(sentence);
        
        assertTrue(results.size() == 2);
        
        String actual1 = results.get(0).getName();
        
        assertEquals(actual1, expected1);

        String actual2 = results.get(1).getName();
        
        assertEquals(actual2, expected2);        
    }
}

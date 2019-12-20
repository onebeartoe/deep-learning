
package org.onebeartoe.deep.learning.nlp.named.entity;

import java.io.IOException;
import java.util.List;
import org.onebeartoe.deep.learning.nlp.named.entity.results.DetectedNamedEntity;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * This class tests the PersonNameDetector specification.
 */
public class PersonNameDetectorSpecification
{
    private PersonNameDetector implementation;
    
    @BeforeMethod
    public void initialize() throws IOException
    {
        implementation = new PersonNameDetector();
    }

    @Test
    public void findNames_singleName() throws IOException
    {
        String expected = "Margaret";
        
        String sentence = String.format("My name is %s", expected);
        
        List<DetectedNamedEntity> results = implementation.findNames(sentence);
        
        assertTrue(results.size() == 1);
        
        String actual = results.get(0).getName();
        
        assertEquals(actual, expected);
    }
    
    @Test
    public void findNames_multipleNames() throws IOException
    {
        String expected1 = "Peter";
        
        String expected2 = "Lisa";
        
        String expected3 = "Maggie";
        
        String sentence = String.format("My name is %s, my daughter is %s, and my infant daughter is %s", 
                                        expected1, expected2, expected3);
        
        List<DetectedNamedEntity> results = implementation.findNames(sentence);
        
        assertTrue(results.size() == 3);
        
        String actual1 = results.get(0).getName();
        
        String actual2 = results.get(1).getName();
        
        String actual3 = results.get(2).getName();
        
        assertEquals(actual1, expected1);
        
        assertEquals(actual2, expected2);
        
        assertEquals(actual3, expected3);
    }
    
    @Test
    public void findNames_none() throws IOException
    {
        String sentence = "This sentence shall not be named.";
        
        List<DetectedNamedEntity> results = implementation.findNames(sentence);
        
        assertTrue(results.size() == 0);        
    }
}


package org.onebeartoe.deep.learning.nlp.named.entity;

import java.io.IOException;
import java.util.List;
import org.onebeartoe.deep.learning.nlp.named.entity.results.DetectedNamedEntity;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * This class tests the DateDetector specification.
 */
public class DateDetectorSpecification
{
    private DateDetector implementation;
    
    @BeforeMethod
    public void initialize() throws IOException
    {
        implementation = new DateDetector();
    }
    
    @Test
    public void findDates_multiple_Weekday() throws IOException
    {
        String expected1 = "Last year";
        
        String expected2 = "this year";
        
        String expected3 = "Friday";
        
        String expected4 = "Saturday";
        
        String sentence = String.format("%s my birthday was on %s.  %s it is on %s!",
                                         expected1, expected2, expected3, expected4);
                
        List<DetectedNamedEntity> results = implementation.findDates(sentence);
        
        assertTrue(results.size() == 4);
        
        String actual1 = results.get(0).getName();
        
        assertEquals(actual1, expected1);
        
        String actual2 = results.get(1).getName();
        
        assertEquals(actual2, expected2);
        
        String actual3 = results.get(2).getName();
        
        assertEquals(actual3, expected3);
        
        String actual4 = results.get(3).getName();
        
        assertEquals(actual4, expected4);
    }
    
    @Test
    public void findDates_single_Tense() throws IOException
    {
        String expected = "today";
        
        findSingleDate(expected);
    }

//TODO: move this test to the     
    @Test(
enabled = false)
    public void findDates_single_Time() throws IOException
    {
        String expected = "1:30";
//        String expected = "1 o'clock";
        
        findSingleDate(expected);
    }
    
    @Test
    public void findDates_single_Weekday() throws IOException
    {
        String expected = "Wednesday";
        
        findSingleDate(expected);
    }
    
//TODO: re-enable this test by using RegexNameFinder
//                                  
//          https://opennlp.apache.org/docs/1.8.4/apidocs/opennlp-tools/opennlp/tools/namefind/RegexNameFinder.html
    @Test(
enabled = false)
    public void findDates_single_longDateFormat() throws IOException
    {
        String expected = "2019/12/10";
//        String expected = "2019-12-10";
        
        findSingleDate(expected);
    }
    
    @Test
    public void findDates_single_dateNoYear() throws IOException
    {
//        December
        String expected = "November 14";
//        String expected = "12 of November";
        
        findSingleDate(expected);
    }
    
    @Test(
enabled = false)
    public void findDates_single_dateNoYear_cardinal() throws IOException
    {
//        December
        String expected = "November 14th";
//        String expected = "12 of November";
        
        findSingleDate(expected);
    }
    
    
    
    @Test
    public void findDates_single_Date() throws IOException
    {
        String expected = "November 12 2010";
//        String expected = "2019/12/10";
//        String expected = "2019-12-10";
        
        findSingleDate(expected);
    }
    
    @Test
    public void findDates_single_DayOfMonth() throws IOException
    {
        String expected = "March 3 1999";
        
        findSingleDate(expected);
    }    
    
    private void findSingleDate(String expected) throws IOException
    {
        String sentence = String.format("I am going to the store %s.", expected);
        
        List<DetectedNamedEntity> results = implementation.findDates(sentence);
        
        assertTrue(results.size() == 1);
        
        String actual = results.get(0).getName();
        
        assertEquals(actual, expected);
    }
}

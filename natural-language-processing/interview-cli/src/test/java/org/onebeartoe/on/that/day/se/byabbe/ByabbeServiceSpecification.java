
package org.onebeartoe.on.that.day.se.byabbe;

import org.onebeartoe.on.that.day.se.byabbe.model.BirthsOnThisDay;
import org.onebeartoe.on.that.day.se.byabbe.model.EventsOnThisDay;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Roberto Marquez
 */
public class ByabbeServiceSpecification
{
    private ByabbeService implementation;

    @BeforeMethod
    public void initialize()
    {
        implementation = new ByabbeService();
    }
    
    /**
     * Test of retrieveEvents method, of class ByabbeService.
     */
    @Test
    public void testRetrieveEvents()
    {
        int month = 12;
        
        int day = 2;
        
        EventsOnThisDay events = implementation.retrieveEvents(month, day);
        
        assertNotNull(events);
    }

    /**
     * Test of retrieveBirths method, of class ByabbeService.
     */
    @Test
    public void testRetrieveBirths()
    {
        int month = 12;
        
        int day = 2;
        
        BirthsOnThisDay events = implementation.retrieveBirths(month, day);
        
        assertNotNull(events);        
    }    
}


package org.onebeartoe.on.that.day.se.byabbe;

import java.util.List;
import org.onebeartoe.on.that.day.se.byabbe.model.OnThisDay;
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
        
        OnThisDay events = implementation.retrieveEvents(month, day);
        
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
        
        OnThisDay events = implementation.retrieveBirths(month, day);
        
        assertNotNull(events);        
    }    
}

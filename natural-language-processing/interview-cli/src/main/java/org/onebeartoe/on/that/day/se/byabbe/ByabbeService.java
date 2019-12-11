
package org.onebeartoe.on.that.day.se.byabbe;

import java.util.List;

/**
 * This class provides methods to query the 'on this day' service provided by 
 *      https://byabbe.se/on-this-day/
 * 
 * 
 */
public class ByabbeService
{
//TODO: see if this works
//         https://stackoverflow.com/questions/21725093/rest-assured-deserialize-response-json-as-listpojo    
    public List<ByabbeOnThisDay> retrieveEvents(int month, int day)
    {
        String type = "events";

        return null;
    }
    
    public List<ByabbeOnThisDay> retrieveBirths(int month, int day)
    {
        return null;
    }
    
    private List<ByabbeOnThisDay> retrieve(int month, int day, String type)
    {
        String prototype = "https://byabbe.se/on-this-day/%s/%s/%s.json";
        
        String url = String.format(prototype, month, day, type);
        
        return null;
    }
}

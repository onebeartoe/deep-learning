
package org.onebeartoe.on.that.day.se.byabbe;


import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.onebeartoe.on.that.day.se.byabbe.model.BirthsOnThisDay;
import org.onebeartoe.on.that.day.se.byabbe.model.EventsOnThisDay;

/**
 * This class provides methods to query the 'on this day' service provided by 
 *      https://byabbe.se/on-this-day/
 */
public class ByabbeService
{
    private Client client;

//    private WebTarget birthsTarget;
//
//    private WebTarget eventsTarget;
    
    Map<String, EventsOnThisDay> eventsMap;
    
    Map<String, BirthsOnThisDay> birthsMap;

    private final String prototype = "https://byabbe.se/on-this-day/%s/%s/%s.json";
    
    public ByabbeService()
    {
        client = ClientBuilder.newClient();
        
        eventsMap = new HashMap();
        
        birthsMap = new HashMap();
    }
    
    public EventsOnThisDay retrieveEvents(int month, int day)
    {
        String key = month + "-" + day;
        
        EventsOnThisDay onThisDay = eventsMap.get(key);
        
        if(onThisDay == null)
        {
            String type = "events";

            String url = buildUrl(month, day, type);

            WebTarget eventsTarget = client.target(url);

            onThisDay = eventsTarget.request(MediaType.APPLICATION_JSON)
                                    .get(EventsOnThisDay.class);
            
            eventsMap.put(key, onThisDay);
        }

        return onThisDay;
    }
    
    public BirthsOnThisDay retrieveBirths(int month, int day)
    {
        String key = month + "-" + day;
        
        BirthsOnThisDay births = birthsMap.get(key);
        
        if(births == null)
        {
            String type = "births";

            String url = buildUrl(month, day, type);

            WebTarget birthsTarget = client.target(url);

            births = birthsTarget.request(MediaType.APPLICATION_JSON)
                                 .get(BirthsOnThisDay.class);
    
            birthsMap.put(key, births);             
        }
        
        return births;
    }
    
    private String buildUrl(int month, int day, String type)
    {
        return String.format(prototype, month, day, type);
    }
}

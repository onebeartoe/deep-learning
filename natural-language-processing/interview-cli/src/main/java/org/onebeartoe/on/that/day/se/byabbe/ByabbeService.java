
package org.onebeartoe.on.that.day.se.byabbe;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.onebeartoe.on.that.day.se.byabbe.model.BirthsOnThisDay;
import org.onebeartoe.on.that.day.se.byabbe.model.EventsOnThisDay;

/**
 * This class provides methods to query the 'on this day' service provided by 
 *      https://byabbe.se/on-this-day/
 * 
 * 
 */
public class ByabbeService
{
    private Client client;

    private WebTarget birthsTarget;

    private WebTarget eventsTarget;

    private final String prototype = "https://byabbe.se/on-this-day/%s/%s/%s.json";
    
    public ByabbeService()
    {
        client = ClientBuilder.newClient();
    }
    
    public EventsOnThisDay retrieveEvents(int month, int day)
    {
        String type = "events";
        
        String url = buildUrl(month, day, type);
                
        eventsTarget = client.target(url);
        
        EventsOnThisDay onThisDay = eventsTarget.request(MediaType.APPLICATION_JSON)
                                          .get(EventsOnThisDay.class);

        return onThisDay;
    }
    
    public BirthsOnThisDay retrieveBirths(int month, int day)
    {
        String type = "births";
        
        String url = buildUrl(month, day, type);
        
        birthsTarget = client.target(url);

        BirthsOnThisDay onThisDay = birthsTarget.request(MediaType.APPLICATION_JSON)
                                          .get(BirthsOnThisDay.class);
        
        return onThisDay;
    }
    
    private String buildUrl(int month, int day, String type)
    {
        return String.format(prototype, month, day, type);
    }
}

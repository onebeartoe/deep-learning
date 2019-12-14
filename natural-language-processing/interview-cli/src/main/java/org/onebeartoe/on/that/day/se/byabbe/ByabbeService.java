
package org.onebeartoe.on.that.day.se.byabbe;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.onebeartoe.on.that.day.se.byabbe.model.OnThisDay;

/**
 * This class provides methods to query the 'on this day' service provided by 
 *      https://byabbe.se/on-this-day/
 * 
 * 
 */
public class ByabbeService
{
    private Client client;

    private WebTarget target;    
    
//TODO: see if this RestAssured example works
//         https://stackoverflow.com/questions/21725093/rest-assured-deserialize-response-json-as-listpojo    
    public List<ByabbeOnThisDay> retrieveEvents(int month, int day)
    {
        String type = "events";

        return retrieve(month, day, type);
    }
    
    public List<ByabbeOnThisDay> retrieveBirths(int month, int day)
    {
        return null;
    }
    
    private List<ByabbeOnThisDay> retrieve(int month, int day, String type)
    {
        String prototype = "https://byabbe.se/on-this-day/%s/%s/%s.json";
        
        String url = String.format(prototype, month, day, type);
        
        client = ClientBuilder.newClient();
        
        target = client.target(url);
        
        OnThisDay get = target.request(MediaType.APPLICATION_JSON)
                .get(OnThisDay.class);
        
        List<ByabbeOnThisDay> results = null;
        
        return results;
    }
}


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
    public OnThisDay retrieveEvents(int month, int day)
    {
        String type = "events";

        return retrieve(month, day, type);
    }
    
    public OnThisDay retrieveBirths(int month, int day)
    {
        String type = "births";

        return retrieve(month, day, type);
    }
    
    private OnThisDay retrieve(int month, int day, String type)
    {
        String prototype = "https://byabbe.se/on-this-day/%s/%s/%s.json";
        
        String url = String.format(prototype, month, day, type);
        
        client = ClientBuilder.newClient();
        
        target = client.target(url);
        
        OnThisDay onThisDay = target.request(MediaType.APPLICATION_JSON)
                                    .get(OnThisDay.class);
                
        return onThisDay;
    }
}

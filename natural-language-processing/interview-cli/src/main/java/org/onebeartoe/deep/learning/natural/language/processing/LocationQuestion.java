
package org.onebeartoe.deep.learning.natural.language.processing;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.onebeartoe.deep.learning.nlp.named.entity.LocationDetector;
import org.onebeartoe.deep.learning.nlp.named.entity.results.DetectedNamedEntity;
import org.onebeartoe.web.search.SearchRequest;
import org.onebeartoe.web.search.SearchResult;
import org.onebeartoe.web.search.WebSearch;

/**
 * This class abstracts the interview's location question.
 */
public class LocationQuestion extends SearchRecommendingQuestion
{
    LocationDetector locationDetector;    
    
    public LocationQuestion(LocationDetector locationDetector, WebSearch webSearch)
    {
        this.locationDetector = locationDetector;
        
        this.webSearch = webSearch;
    }

    @Override
    public ValidationResult validateResponse(String response)
    {
        List<DetectedNamedEntity> locations = locationDetector.detectLocations(response);
        
        ValidationResult result = new ValidationResult();
        
        if( locations.isEmpty() )
        {
            result.valid = false;
        }
        else
        {
            result.valid = true;
            
            result.answer = locations.get(0).getName();
        }             
        
        return result;
    }

    @Override
    public String getSearchQuery()
    {
        return "things to do in " + answer;
    }

    @Override
    public String getValidResponseConfirmation()
    {
        return String.format("Oh, I hear it is nice in %s!", answer);
    }
}

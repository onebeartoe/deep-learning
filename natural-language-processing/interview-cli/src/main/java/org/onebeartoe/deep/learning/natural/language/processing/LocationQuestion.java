
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
public class LocationQuestion extends InterviewQuestion
{
    LocationDetector locationDetector;
    
    WebSearch webSearch;
    
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
    public List<Recommendation> getRecomendations()
    {
        String query = "things to do in " + answer;
        
        SearchRequest request = new SearchRequest(query);
        
        List<SearchResult> results = null;
        
        try
        {
            List<SearchResult> allResults = webSearch.search(request);
            
            results = new ArrayList();
            
            for(int i = 0; i < 2; i++) // assume we get at least 2 results
            {
                results.add(allResults.get(i));
            }
        } 
        catch (GeneralSecurityException | IOException ex)
        {
            Logger.getLogger(LocationQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(results == null)
        {
            SearchResult result = new SearchResult();
            
            result.content = "online recommendations are not available for " + answer;
            
            results = new ArrayList();
            
            results.add(result);
        }
        
        List<Recommendation> recommendations = searchResultToRecommendation(results);
        
        return recommendations;
    }
    
    private List<Recommendation> searchResultToRecommendation(List<SearchResult> results)
    {
        List<Recommendation> recommendations = new ArrayList();
        
        results.forEach(r ->         
        {
            Recommendation recommendation = new Recommendation();
            
            recommendation.title = r.title;
            recommendation.link = r.link;
            recommendation.content = r.content;
            
            recommendations.add(recommendation);
        });
        
        return recommendations;
    }

    @Override
    public String getValidResponseConfirmation()
    {
        return String.format("Oh, I hear it is nice in %s!", answer);
    }
}

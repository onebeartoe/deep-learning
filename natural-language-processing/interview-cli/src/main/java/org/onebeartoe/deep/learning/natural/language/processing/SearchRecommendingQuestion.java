
package org.onebeartoe.deep.learning.natural.language.processing;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.onebeartoe.web.search.SearchRequest;
import org.onebeartoe.web.search.SearchResult;
import org.onebeartoe.web.search.WebSearch;

/**
 * This class provides online search template methods for finding recommendations
 */
public abstract class SearchRecommendingQuestion extends InterviewQuestion
{
    WebSearch webSearch;
    
    int resultLimit = 2;
    
    @Override
    public List<Recommendation> getRecomendations()
    {
        String query = getSearchQuery();
        
        SearchRequest request = new SearchRequest(query);
        
        List<SearchResult> results = null;
        
        try
        {
            List<SearchResult> allResults = webSearch.search(request);
            
            results = new ArrayList();
            
            for(int i = 0; i < resultLimit; i++) // assume we get at least 2 results
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

    public String getSearchQuery()
    {
        return answer;
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
}

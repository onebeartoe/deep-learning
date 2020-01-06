
package org.onebeartoe.web.search.google.custom;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.CustomsearchRequestInitializer;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.onebeartoe.web.search.SearchRequest;
import org.onebeartoe.web.search.SearchResult;
import org.onebeartoe.web.search.WebSearch;

/**
 * This class implement the WebSearch interface via Google Custom Search APIs.
 * 
 * An API key is need to use this implementation:
 * 
 *      https://developers.google.com/custom-search/v1/overview
 * 
 * A search engine ID and context (cx) is also needed:
 * 
 *      https://cse.google.com/all
 * 
 * Here is a link to the admin console:
 * 
 *      https://console.developers.google.com/apis
 * 
 * This StackOverflow answer helped with this implementation:
 * 
 *      https://stackoverflow.com/a/46963307/803890
 * 
 */
public class GoogleCustomSearch implements WebSearch
{
    private Customsearch cs;
    
    private String cx;
    
    public GoogleCustomSearch() throws FileNotFoundException, IOException, GeneralSecurityException
    {
        Properties properties = new Properties();

        // see 'src/main/resources/google-custom-search.properties' for the expected 
        // values in this properties file
        String propertiesPath = "/home/roberto/.onebeartoe/google-custom-search/google-custom-search.properties";
                
        File propertiesFile = new File(propertiesPath);
        
        try
        {
            InputStream inStream = new FileInputStream(propertiesFile);

            properties.load(inStream);

            cx = properties.getProperty("cx"); // the search engin ID

            String apiKey = properties.getProperty("apiKey");

            CustomsearchRequestInitializer requestInitializer = new CustomsearchRequestInitializer(apiKey);        

            String applicationName = properties.getProperty("applicationName");

            //Instance Customsearch
            cs = new Customsearch.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null) 
                           .setApplicationName(applicationName)
                           .setGoogleClientRequestInitializer(requestInitializer) 
                           .build();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) throws IOException, FileNotFoundException, GeneralSecurityException //throws IOException //throws GeneralSecurityException, IOException
    {
        String query = String.join(" ", args);
        
//query = "space opera";
        
        SearchRequest request = new SearchRequest(query);
        
        GoogleCustomSearch gcs = new GoogleCustomSearch();
        
        List<SearchResult> searchResults = gcs.search(request);
        
        searchResults.forEach(result ->
        {
            System.out.println(result.title);
            System.out.println(result.link);
            System.out.println(result.content);
            System.out.println();            
        });
    }

    @Override
    public List<SearchResult> search(SearchRequest request) throws IOException 
    {        
        String searchQuery = request.query;
        
        List<SearchResult> results = new ArrayList();
        
        if(cs == null || cx == null)
        {
            // the properties were not loaded
            SearchResult searchResult = new SearchResult();
            
            searchResult.content = "online search service is not available";
            
            searchResult.link = "not-available";
            
            searchResult.title = "service error";
                    
            results.add(searchResult);
        }
        else
        {
            //Set search parameter
            Customsearch.Cse.List list = cs.cse().list(searchQuery).setCx(cx); 

            //Execute search
            Search result = list.execute();

            Search.SearchInformation searchInformation = result.getSearchInformation();
            searchInformation.getTotalResults();                


            List<Result> items = result.getItems();            

            if( items.isEmpty() )
            {
                System.err.println("no items were found: " + searchQuery);
            }
            else
            {
                for(Result ri : items) 
                {
                    SearchResult searchResult = new SearchResult();

                    searchResult.title = ri.getTitle();

                    searchResult.link = ri.getLink();

                    searchResult.content = ri.getSnippet();

                    searchResult.kind = result.getKind();                

                    results.add(searchResult);
                }
            }
        }
        
        return results;
    }    
}

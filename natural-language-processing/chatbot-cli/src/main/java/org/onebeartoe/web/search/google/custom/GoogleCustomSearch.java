
package org.onebeartoe.web.search.google.custom;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.CustomsearchRequestInitializer;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
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
    public static void main(String[] args) throws GeneralSecurityException, IOException
    {
        GoogleCustomSearch gcs = new GoogleCustomSearch();
        
        gcs.search(null);
    }

    @Override
    public List<SearchResult> search(SearchRequest request) throws GeneralSecurityException, IOException
    {
//TODO: move this property reading code to its own method        
        Properties properties = new Properties();
        
        String propertiesPath = "/home/roberto/.onebeartoe/google-custom-search/google-custom-search.properties";
        
//        propertiesPath = "src/main/resources/google-custom-search.properties";
        
        File propertiesFile = new File(propertiesPath);
        
        InputStream inStream = new FileInputStream(propertiesFile);
        
        properties.load(inStream);
        
        String searchQuery = "space opera"; //The query to search

        
        
String cx = properties.getProperty("cx"); // the search engin ID
        
//!!!!!!!!!!!!!!!!!!!!  DO NOT COMMIT!!!!!!!!!!!!!!!!!        
String apiKey = properties.getProperty("apiKey");
        CustomsearchRequestInitializer requestInitializer = new CustomsearchRequestInitializer(apiKey);        
        
        
        String applicationName = properties.getProperty("applicationName");
        
        //Instance Customsearch
        Customsearch cs = new Customsearch.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null) 
                       .setApplicationName(applicationName)
                       .setGoogleClientRequestInitializer(requestInitializer) 
                       .build();

        //Set search parameter
        Customsearch.Cse.List list = cs.cse().list(searchQuery).setCx(cx); 

        //Execute search
        Search result = list.execute();
        if (result.getItems()!=null){
            for (Result ri : result.getItems()) {
                //Get title, link, body etc. from search
                System.out.println(ri.getTitle() + ", " + ri.getLink());
            }
        }
        
        return null;
    }    
}

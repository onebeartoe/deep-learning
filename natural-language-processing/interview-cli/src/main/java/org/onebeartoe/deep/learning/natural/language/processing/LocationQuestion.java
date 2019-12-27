
package org.onebeartoe.deep.learning.natural.language.processing;

import java.util.List;
import org.onebeartoe.deep.learning.nlp.named.entity.LocationDetector;
import org.onebeartoe.deep.learning.nlp.named.entity.results.DetectedNamedEntity;

/**
 * This class abstracts the interview's location question.
 */
public class LocationQuestion extends InterviewQuestion
{
    LocationDetector locationDetector;
    
    public LocationQuestion(LocationDetector locationDetector)
    {
        this.locationDetector = locationDetector;
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
    public String getValidResponseConfirmation()
    {
        return String.format("Oh, I hear it is nice in %s!", answer);
    }
}

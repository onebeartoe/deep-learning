
package org.onebeartoe.deep.learning.natural.language.processing;

import java.util.List;
import org.onebeartoe.deep.learning.nlp.named.entity.DateDetector;
import org.onebeartoe.deep.learning.nlp.named.entity.results.DetectedNamedEntity;

/**
 * This class abstracts the question about the interviewee's date of interest.
 */
public class DateQuestion extends InterviewQuestion
{
    DateDetector dateDetector;
    
    public DateQuestion(DateDetector dateDetector)
    {
        this.dateDetector = dateDetector;
    }
    
    @Override
    public ValidationResult validateResponse(String response)
    {
        List<DetectedNamedEntity> dates = dateDetector.findDates(response);
        
        ValidationResult result = new ValidationResult();
        
        if( dates.isEmpty() )
        {
            result.valid = false;
        }
        else
        {
            result.valid = true;
            
            result.answer = dates.get(0).getName();
            
//            result.answer = answer;
        }             
        
        return result;
    }

    @Override
    public String getValidResponseConfirmation()
    {
        return String.format("Okay, %s it is.", answer);
    }
}

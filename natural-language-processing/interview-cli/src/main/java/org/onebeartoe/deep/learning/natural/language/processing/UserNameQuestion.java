
package org.onebeartoe.deep.learning.natural.language.processing;

import java.util.ArrayList;
import java.util.List;
import org.onebeartoe.deep.learning.nlp.named.entity.PersonNameDetector;
import org.onebeartoe.deep.learning.nlp.named.entity.results.DetectedNamedEntity;

/**
 * This class is an abstraction of the 'what is your name interview question?' question.
 */
public class UserNameQuestion extends InterviewQuestion
{
    private PersonNameDetector nameDetector;
    
    public UserNameQuestion(PersonNameDetector nameDetector)
    {
        this.nameDetector = nameDetector;
    }

    @Override
    public ValidationResult validateResponse(String response)
    {
        List<DetectedNamedEntity> names = nameDetector.findNames(response);
        
        boolean valid = true;
        
        ValidationResult result = new ValidationResult();
        
        if( names.size() == 0 )
        {
            valid = false;
        }
        else
        {
            DetectedNamedEntity entity = names.get(0);
            
            String name = entity.getName();
            
            result.answer = name;
        }

        result.valid = valid;
        
        return result;
    }

    @Override
    public String getValidResponseConfirmation()
    {
        return String.format("Okay.  Nice to meet you, %s", answer);
    }
}

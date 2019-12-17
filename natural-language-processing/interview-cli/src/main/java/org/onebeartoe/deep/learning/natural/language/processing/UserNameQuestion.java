
package org.onebeartoe.deep.learning.natural.language.processing;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is an abstraction of the 'what is your name interview question?' question.
 */
public class UserNameQuestion extends InterviewQuestion
{
    @Override
    public ValidationResult validateResponse(String response)
    {
//TODO: complete this!!!!!!        
        ValidationResult result = new ValidationResult();
System.out.println("I AM NOT DONE!!! I AM NOT DONE!!! ");        
result.valid = true;
        
        return result;
    }

    @Override
    public String getValidResponseConfirmation()
    {
        return String.format("Okay.  Nice to meet you, %s", answer);
    }
}

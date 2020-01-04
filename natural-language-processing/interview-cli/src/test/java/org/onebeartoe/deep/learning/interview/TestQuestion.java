/*
 */
package org.onebeartoe.deep.learning.interview;

import org.onebeartoe.deep.learning.natural.language.processing.InterviewQuestion;
import org.onebeartoe.deep.learning.natural.language.processing.ValidationResult;

/**
 *
 * @author Roberto Marquez
 */
public class TestQuestion extends InterviewQuestion
{
    @Override
    public ValidationResult validateResponse(String response)
    {
        return new ValidationResult();
    }

    @Override
    public String getValidResponseConfirmation()
    {
        return "";
    }
}

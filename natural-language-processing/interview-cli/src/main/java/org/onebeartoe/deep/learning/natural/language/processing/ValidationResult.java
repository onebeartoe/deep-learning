
package org.onebeartoe.deep.learning.natural.language.processing;

/**
 * This class is an abstraction of a setting a response to an interview question.
 */
public class ValidationResult
{
    public boolean valid;

    public String answer;
    
    String response;

    public boolean thresholdReached;

    public boolean responseContainedQuestion;

    public String questionInResponse;
}

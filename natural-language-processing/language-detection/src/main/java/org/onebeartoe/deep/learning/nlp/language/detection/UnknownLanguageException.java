
package org.onebeartoe.deep.learning.nlp.language.detection;

/**
 * This exception is thrown when trying to decode an invalid or unknown language code.
 */
public class UnknownLanguageException extends Exception
{
    public UnknownLanguageException(String message)
    {
        super(message);
    }
}

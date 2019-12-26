
package org.onebeartoe.deep.learning.nlp.named.entity;

import java.io.IOException;

/**
 *This class detects English names of people that appear in a text string.
 */
public class PersonNameDetector extends NamedEntityRecognizer
{
    public PersonNameDetector() throws IOException
    {
        // this empty constructor is used to avoid the
        // unreported exception java.io.IOException in default constructor
    }

    @Override
    protected String getModelClasspathLocation()
    {
        return "/en-ner-person.bin";
    }    
}

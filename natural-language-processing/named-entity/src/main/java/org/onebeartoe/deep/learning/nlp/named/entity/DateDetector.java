
package org.onebeartoe.deep.learning.nlp.named.entity;

import java.io.IOException;
import java.util.List;
import org.onebeartoe.deep.learning.nlp.named.entity.results.DetectedNamedEntity;

/**
 * This class uses a the NameFinderME class to detect dates in text.
 */
public class DateDetector extends NamedEntityRecognizer
{

//is en-ner-time.bin the one to use for this?

    public DateDetector() throws IOException
    {
        
    }
    
    @Override
    protected String getModelClasspathLocation()
    {
//        return "/en-ner-time.bin";
        return "/en-ner-date.bin";
    }
    
    public List<DetectedNamedEntity> findDates(String sentence) throws IOException
    {
        return findNames(sentence);
    }
}

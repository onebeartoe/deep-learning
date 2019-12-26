
package org.onebeartoe.deep.learning.nlp.named.entity;

import java.io.IOException;
import java.util.List;
import org.onebeartoe.deep.learning.nlp.named.entity.results.DetectedNamedEntity;

/**
 * This class uses a the NameFinderME class to detect dates in text.
 */
public class DateDetector extends NamedEntityRecognizer
{
    public DateDetector() throws IOException
    {
        
    }
    
    @Override
    protected String getModelClasspathLocation()
    {
        return "/en-ner-date.bin";
    }
    
    public List<DetectedNamedEntity> findDates(String sentence)
    {
        return findNames(sentence);
    }
}

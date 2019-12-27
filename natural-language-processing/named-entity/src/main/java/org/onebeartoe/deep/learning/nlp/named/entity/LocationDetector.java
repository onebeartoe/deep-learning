
package org.onebeartoe.deep.learning.nlp.named.entity;

import java.io.IOException;
import java.util.List;
import org.onebeartoe.deep.learning.nlp.named.entity.results.DetectedNamedEntity;

/**
 * This class uses Apache NLP to detect location names.
 */
public class LocationDetector extends NamedEntityRecognizer
{
    public LocationDetector() throws IOException
    {
        
    }

    public List<DetectedNamedEntity> detectLocations(String sentence)
    {
        return findNames(sentence);
    }

    @Override
    protected String getModelClasspathLocation()
    {
        return "/en-ner-location.bin";
    }
}

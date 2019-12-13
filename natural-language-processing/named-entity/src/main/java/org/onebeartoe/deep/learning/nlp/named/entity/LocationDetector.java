/*
 */
package org.onebeartoe.deep.learning.nlp.named.entity;

import java.io.IOException;
import java.util.List;
import org.onebeartoe.deep.learning.nlp.named.entity.results.DetectedNamedEntity;

/**
 *
 * @author Roberto Marquez
 */
class LocationDetector extends NamedEntityRecognizer
{
    public LocationDetector() throws IOException
    {
        
    }    

    public List<DetectedNamedEntity> detectLocations(String sentence) throws IOException
    {
        return findNames(sentence);
    }

    @Override
    protected String getModelClasspathLocation()
    {
        return "/en-ner-location.bin";
    }
}
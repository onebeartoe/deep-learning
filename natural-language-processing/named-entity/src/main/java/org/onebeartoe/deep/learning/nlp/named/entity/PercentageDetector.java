
package org.onebeartoe.deep.learning.nlp.named.entity;

import java.io.IOException;
import java.util.List;
import org.onebeartoe.deep.learning.nlp.named.entity.results.DetectedNamedEntity;

/**
 * This class provides methods to detect percentages in text.
 */
public class PercentageDetector extends NamedEntityRecognizer
{
    public PercentageDetector() throws IOException
    {
        
    } 

    List<DetectedNamedEntity> findPercentages(String sentence) throws IOException
    {
        return findNames(sentence);
    }

    @Override
    protected String getModelClasspathLocation()
    {
        return "/en-ner-percentage.bin";
    }    

    @Override
    protected boolean separateTokensWithSpace()
    {
        return false;
    }
}


package org.onebeartoe.deep.learning.nlp.named.entity;

import java.io.IOException;
import java.util.List;
import org.onebeartoe.deep.learning.nlp.named.entity.results.DetectedNamedEntity;

/**
 *
 * @author Roberto Marquez
 */
public class MoneyDetector extends NamedEntityRecognizer
{
    public MoneyDetector() throws IOException
    {
        
    }
    
    @Override
    protected String getModelClasspathLocation()
    {
        return "/en-ner-money.bin";
    }
    
    public List<DetectedNamedEntity> findMoney(String sentence)
    {
        return findNames(sentence);
    }

    @Override
    protected boolean separateTokensWithSpace()
    {
        return false;
    }
}

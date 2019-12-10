
package org.onebeartoe.deep.learning.nlp.named.entity.results;

/**
 * This class is an abstraction of a single detection result.
 */
public class DetectedNamedEntity
{
    private String name;
    
    private long location;

    /**
     * 
     * @return the name of detected entity
     */
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the word index into the string that contains the named entity; It
     *          looks like punctuation marks count as word tokens.
     */
    public long getLocation()
    {
        return location;
    }

    public void setLocation(long location)
    {
        this.location = location;
    }
    
    
}


package org.onebeartoe.deep.learning.nlp.sentences;

/**
 * This is an abstraction of a sentence classification.
 */
public class SentenceClassification
{
    private String text;
    
    private SentenceType type;

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public SentenceType getType()
    {
        return type;
    }

    public void setType(SentenceType type)
    {
        this.type = type;
    }
    
    
}

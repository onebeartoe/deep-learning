
package org.onebeartoe.deep.learning.recurrent.neural.network.sentiment;

/**
 * This class is an abstraction of a sentiment classification.
 */
public class SentimentClassification
{
    private double positive;
    
    private double negative;
    
    private String text;
    
    private int category;

    public int getCategory()
    {
        return category;
    }

    public void setCategory(int category)
    {
        this.category = category;
    }

    public double getPositive()
    {
        return positive;
    }

    public void setPositive(double positive)
    {
        this.positive = positive;
    }

    public double getNegative()
    {
        return negative;
    }

    public void setNegative(double negative)
    {
        this.negative = negative;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}

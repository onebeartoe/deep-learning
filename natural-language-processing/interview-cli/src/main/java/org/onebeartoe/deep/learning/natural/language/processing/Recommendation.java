
package org.onebeartoe.deep.learning.natural.language.processing;

/**
 *
 * @author Roberto Marquez
 */
public class Recommendation
{
    public String title;
    
    public String link;
    
    public String content;
    
    @Override
    public String toString()
    {        
        return String.format("%s\n%s\n%s\n", title, link, content);
    }
}

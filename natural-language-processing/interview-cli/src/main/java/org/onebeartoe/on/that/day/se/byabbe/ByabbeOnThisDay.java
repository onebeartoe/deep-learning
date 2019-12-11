
package org.onebeartoe.on.that.day.se.byabbe;

/**
 *
 * This POJO abstracts an 'on this day' reposnse for the byabbe.se service.
 */
public class ByabbeOnThisDay
{
    public String year;
    
    public String description;
    
    public wikipedia wiki;
    
    class wikipedia
    {
        public String title;

        public String wikipedia;
    }
}

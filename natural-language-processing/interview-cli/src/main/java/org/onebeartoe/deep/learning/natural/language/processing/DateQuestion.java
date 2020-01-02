
package org.onebeartoe.deep.learning.natural.language.processing;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.onebeartoe.deep.learning.nlp.named.entity.DateDetector;
import org.onebeartoe.deep.learning.nlp.named.entity.results.DetectedNamedEntity;
import org.onebeartoe.on.that.day.se.byabbe.ByabbeService;
import org.onebeartoe.on.that.day.se.byabbe.model.Birth;
import org.onebeartoe.on.that.day.se.byabbe.model.BirthsOnThisDay;
import org.onebeartoe.on.that.day.se.byabbe.model.Event;
import org.onebeartoe.on.that.day.se.byabbe.model.EventsOnThisDay;

/**
 * This class abstracts the question about the interviewee's date of interest.
 */
public class DateQuestion extends InterviewQuestion
{
    DateDetector dateDetector;
    
    ByabbeService byabbeService;
    
    public DateQuestion(DateDetector dateDetector)
    {
        this.dateDetector = dateDetector;
        
        byabbeService = new ByabbeService();
    }
    
    @Override
    public List<Recommendation> getRecomendations()
    {        
        String[] split = answer.split("\\W");
        
        String monthName = split[0].toUpperCase();
        
        int day = Integer.valueOf(split[1]);
        
        int month = Month.valueOf(monthName).getValue();

        return recommendations(month, day);
    }    
    
    @Override
    public String getValidResponseConfirmation()
    {
        return String.format("Okay, %s it is.", answer);
    }

    private List<Recommendation> recommendations(int month, int day)
    {
        EventsOnThisDay events = byabbeService.retrieveEvents(month, day);
        
        List<Recommendation> eventRecommendatons = eventsToRecommendations(events);
        
        List<Recommendation> allRecommendations = new ArrayList();
        
        allRecommendations.addAll(eventRecommendatons);
        
        BirthsOnThisDay births = byabbeService.retrieveBirths(month, day);
        
        List<Recommendation> birthRecommendatons = birthsToRecommendations(births);
        
        allRecommendations.addAll(birthRecommendatons);
        
        return allRecommendations;
    }

    @Override
    public ValidationResult validateResponse(String response)
    {
        List<DetectedNamedEntity> dates = dateDetector.findDates(response);
        
        ValidationResult result = new ValidationResult();
        
        if( dates.isEmpty() )
        {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
            result.valid = false;
        }
        else
        {
            result.valid = true;
            
            result.answer = dates.get(0).getName();
        }             
        
        return result;
    }

    private List<Recommendation> eventsToRecommendations(EventsOnThisDay onThisDay)
    {
        List<Recommendation> recommendations = new ArrayList();
        
        List<Event> events = onThisDay.getEvents();

        for(int i = 0; i < 2; i++)
        {
            Event event = events.get(i);
                    
            Recommendation r = new Recommendation();
            
            r.title = event.getWikipedia().get(0).getTitle();
            r.link = event.getWikipedia().get(0).getWikipedia();
            r.content = event.getDescription();
            
            recommendations.add(r);
        }        
        
        return recommendations;
    }

    private List<Recommendation> birthsToRecommendations(BirthsOnThisDay onThisDay)
    {
        List<Recommendation> recommendations = new ArrayList();
        
        List<Birth> births = onThisDay.getBirths();

        for(int i = 0; i < 2; i++)
        {
            Birth birth = births.get(i);
                    
            Recommendation r = new Recommendation();
            
            r.title = birth.getWikipedia().get(0).getTitle();
            r.link = birth.getWikipedia().get(0).getWikipedia();
            r.content = birth.getDescription();
            
            recommendations.add(r);
        }        
        
        return recommendations;        
    }
}

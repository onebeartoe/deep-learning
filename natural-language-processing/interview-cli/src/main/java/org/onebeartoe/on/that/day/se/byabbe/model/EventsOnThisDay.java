
package org.onebeartoe.on.that.day.se.byabbe.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventsOnThisDay 
{

private String wikipedia;
private String date;
private List<Event> events = null;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public String getWikipedia() {
return wikipedia;
}

public void setWikipedia(String wikipedia) {
this.wikipedia = wikipedia;
}

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

public List<Event> getEvents() {
return events;
}

public void setEvents(List<Event> events) {
this.events = events;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}

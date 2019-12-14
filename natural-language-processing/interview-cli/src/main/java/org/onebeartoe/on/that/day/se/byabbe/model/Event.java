
package org.onebeartoe.on.that.day.se.byabbe.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event {

private String year;
private String description;
private List<Wikipedium> wikipedia = null;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public String getYear() {
return year;
}

public void setYear(String year) {
this.year = year;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public List<Wikipedium> getWikipedia() {
return wikipedia;
}

public void setWikipedia(List<Wikipedium> wikipedia) {
this.wikipedia = wikipedia;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
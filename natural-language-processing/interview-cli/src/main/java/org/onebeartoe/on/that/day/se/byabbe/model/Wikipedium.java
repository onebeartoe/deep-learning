
package org.onebeartoe.on.that.day.se.byabbe.model;

import java.util.HashMap;
import java.util.Map;

public class Wikipedium {

private String title;
private String wikipedia;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public String getWikipedia() {
return wikipedia;
}

public void setWikipedia(String wikipedia) {
this.wikipedia = wikipedia;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
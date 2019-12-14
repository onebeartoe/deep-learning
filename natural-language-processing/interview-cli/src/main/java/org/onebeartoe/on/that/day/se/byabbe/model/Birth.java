
package org.onebeartoe.on.that.day.se.byabbe.model;

import java.util.List;

public class Birth {

private String year;
private String description;
private List<Wikipedium> wikipedia = null;

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

}
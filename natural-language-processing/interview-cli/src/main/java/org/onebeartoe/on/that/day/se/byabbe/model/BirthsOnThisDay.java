
package org.onebeartoe.on.that.day.se.byabbe.model;





import java.util.List;

public class BirthsOnThisDay {

private String wikipedia;
private String date;
private List<Birth> births = null;

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

public List<Birth> getBirths() {
return births;
}

public void setBirths(List<Birth> births) {
this.births = births;
}

}

package org.onebeartoe.deep.learning.gan.djl.desktop;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

//public class PersonCellFactory implements Callback<ListView<String>, ListCell<String>> 
public class CategoryCellFactory implements Callback<ListView<Category>, ListCell<Category>> 
{
    @Override
//    public ListCell<String> call(ListView<String> param) {
    public ListCell<Category> call(ListView<Category> param) {
        return new ListCell<>(){
            @Override
//            public void updateItem(String person, boolean empty) {
            public void updateItem(Category person, boolean empty) {
                super.updateItem(person, empty);
                if (empty || person == null) {
                    setText(null);
                } else {
//                    setText(person + " " + "farto");
                    setText(person.name);
                }
            }
        };
    }
}
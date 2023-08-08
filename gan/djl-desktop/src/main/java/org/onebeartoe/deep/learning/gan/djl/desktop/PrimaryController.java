
package org.onebeartoe.deep.learning.gan.djl.desktop;

import ai.djl.examples.inference.BigGAN;
import ai.djl.ModelException;
import ai.djl.translate.TranslateException;

import java.io.IOException;

import java.util.logging.Logger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.net.URISyntaxException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

public class PrimaryController implements Initializable 
{
    private BigGAN bigGan;

//TODO: document the in-memory search feature.
//          https://gist.github.com/Da9el00/cde4381b8f17299693bab886ec700a78 
//    
//          https://www.youtube.com/watch?v=VUVqamT8Npc&ab_channel=Randomcode    
    
    private List<String> words = new ArrayList<>(
            Arrays.asList("test", "dog","Human", "Days of our life", "The best day",
                    "Friends", "Animal", "Human", "Humans", "Bear", "Life",
                    "This is some text", "Words", "222", "Bird", "Dog", "A few words",
                    "Subscribe!", "SoftwareEngineeringStudent", "You got this!!",
                    "Super Human", "Super", "Like")
    );

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<Category> listView;
    
    private List<Category> categories;
    
    @FXML
    private FlowPane flowPane;
    
    private Logger logger;

    @FXML
    void listViewItemSelected(MouseEvent event) throws IOException, ModelException, TranslateException
    {
        listView.setDisable(true);
        final int selectedIndex = listView.getSelectionModel().getSelectedIndex();
    
        Category selectedCategory = listView.getSelectionModel().getSelectedItem();
        int categoryIndex = selectedCategory.index;
        
        System.out.println("categoryIndex = " + categoryIndex);
        
        ObservableList<Node> children = flowPane.getChildren();
        children.clear();

        GanTask task = new GanTask(categoryIndex, bigGan);
        task.valueProperty().addListener( new ChangeListener<List<ImageView>>()
        {
            @Override
            public void changed(ObservableValue<? extends List<ImageView>> ov, List<ImageView> t, List<ImageView> t1) 
            {
                
                
                int size = t1.size();
                System.out.println("size = " + size);
                
                t1.stream()
                  .forEach(imageView -> 
                  {
                      imageView.setPreserveRatio(true);
                      
                      children.add(imageView);

                      System.out.println("child addeddddwww");
                  });                
                
                listView.setDisable(false);
            }
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
    
    @FXML
    void search(ActionEvent event) 
    {
        listView.getItems().clear();
        
        List<Category> searchList = searchList(searchBar.getText(), categories);
        
        listView.getItems().addAll(searchList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        bigGan = new BigGAN();
        
        logger = Logger.getLogger( getClass().getName() );
        
        try 
        {
            words = bigGan.categoryNames();
        } 
        catch (URISyntaxException | IOException ex) 
        {
            ex.printStackTrace();
        }
        
        listView.setCellFactory( new CategoryCellFactory() );
        
        categories = new ArrayList();
        for(int i=0; i<words.size(); i++)
        {
            Category c = new Category(i, words.get(i) );
            categories.add(c);
        }
        
        listView.getItems().addAll(categories);
        

        double gap = 5;
        flowPane.setHgap(gap);
        flowPane.setVgap(gap);
    }
        
    private List<Category> searchList(String searchWords, List<Category> listOfStrings)
    {
        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));

        return listOfStrings.stream().filter(input -> 
        {
            return searchWordsArray.stream()
                                   .allMatch(word -> input.name.toLowerCase().contains(word.toLowerCase()));
        }).collect(Collectors.toList());
    }

    @FXML
    private void switchToSecondary() throws IOException 
    {
        System.out.println("switching to 2nd");
        
        App.setRoot("secondary");
    }
}

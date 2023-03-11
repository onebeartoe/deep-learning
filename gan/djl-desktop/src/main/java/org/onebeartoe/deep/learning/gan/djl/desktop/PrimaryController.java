
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

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
    private ListView<String> listView;
    
    @FXML
    private TilePane listTilePane;
    
    private Logger logger;

    @FXML
    void listViewItemSelected(MouseEvent event) throws IOException, ModelException, TranslateException
    {
        listView.setDisable(true);
        final int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        
        System.out.println("selectedIndex = " + selectedIndex);
        
        ObservableList<Node> children = listTilePane.getChildren();
        children.clear();

        GanTask task = new GanTask(selectedIndex, bigGan);
        task.valueProperty().addListener( new ChangeListener<ImageView>()
        {
            @Override
            public void changed(ObservableValue<? extends ImageView> ov, ImageView t, ImageView t1) 
            {
                children.add(t1);
                
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
        
        listView.getItems().addAll(searchList(searchBar.getText(),words));
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
        
        listView.getItems().addAll(words);
    }

    private List<String> searchList(String searchWords, List<String> listOfStrings)
    {
        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));

        return listOfStrings.stream().filter(input -> 
        {
            return searchWordsArray.stream().allMatch(word ->
                    input.toLowerCase().contains(word.toLowerCase()));
        }).collect(Collectors.toList());
    }

    @FXML
    private void switchToSecondary() throws IOException 
    {
        System.out.println("switching to 2nd");
        
        App.setRoot("secondary");
    }
}

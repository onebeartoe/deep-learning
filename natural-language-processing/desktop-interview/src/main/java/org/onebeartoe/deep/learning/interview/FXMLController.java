
package org.onebeartoe.deep.learning.interview;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Instant;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.HostServices;
import javafx.application.Platform;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import javafx.stage.FileChooser;
import javafx.stage.FileChooserBuilder;

import org.onebeartoe.application.duration.DurationService;
import org.onebeartoe.application.logging.SysoutLoggerFactory;

public class FXMLController implements Initializable 
{
    private Logger logger;
    
    @FXML
    private SplitPane outerSplitPane;
        
    @FXML
    private TextField textField;
    
    @FXML
    private TextArea chatHistoryArea;
        
    @FXML
    private TilePane tilePane;
    
    private File currentDir;

    private FileChooser fileChooser;
    
    private File contentFile;
    
    private File styleFile;

    private DurationService durationService;

    private final boolean guiDevelopment = false;
        
    private void applyStyle() throws IOException
    {                

        
        Instant start = Instant.now();

        
            
        Instant end = Instant.now();
        String durationMessage = durationService.durationMessage(start, end);
        logger.info(durationMessage);
    }
    
    @FXML
    private void handleApplyStyleButtonAction(ActionEvent event)
    {
        logger.info("the apply style button was clicked");

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Missing Input File");
        
        if(contentFile == null)
        {
            alert.setContentText("Please select a content file.");
            alert.showAndWait();
        }
        else if(styleFile == null)
        {
            alert.setContentText("Please select a style file.");
            alert.showAndWait();
        }
        else
        {
            Alert waitAlert = new Alert(AlertType.INFORMATION);
            waitAlert.setTitle("Please wait");
            waitAlert.show();
            waitAlert.setOnCloseRequest( (closeWaitEvent) ->
            {
                logger.info("the wait dialog was closed");
                                
                waitAlert.close();
            });
            
            ObservableList<Node> children = tilePane.getChildren();
            children.clear();

        Task<Void> task = new Task<Void>() 
        {
            @Override 
            public Void call() throws Exception 
            {
                logger.info("-in call");

                try
                {
                    applyStyle();
                }
                catch(Exception e)
                {
                    String message = e.getMessage();

                    logger.log(Level.SEVERE, message, e);
                }
                finally
                {
                    Platform.runLater(() ->
                    {
                        waitAlert.close();
                    });
                }

                return null ;
            }
        };
        new Thread(task).start();
        
        logger.info("apply style done");
        }
    }

    @FXML
    private void onTextEntered(ActionEvent event) throws FileNotFoundException
    {
        logger.info("the content button was clicked");
  
//        HostServices hostServices = 
//  Desktop.getDesktop().browse("/home/roberto/Desktop/Screenshot%20from%202018-09-09%2020-32-43.png");
        
        String currentInput = textField.getText();
        
        textField.setText("");        
        
        chatHistoryArea.appendText("\n\n");
        
        chatHistoryArea.appendText(currentInput);
        
        logger.info("suer input: ");
        
        if(contentFile != null)
        {
            String path = contentFile.getAbsolutePath();
            
            InputStream inputStream = new FileInputStream(path);
        }
    }
    
    @FXML
    private void handleStyleButtonAction(ActionEvent event) throws FileNotFoundException 
    {
        logger.info("the style button was clicked");

//TODO: how is the Window object obtained from this context?
        styleFile = fileChooser.showOpenDialog(null);

        if(styleFile != null)
        {
            String path = styleFile.getAbsolutePath();

            InputStream inputStream = new FileInputStream(path);
            
            Image image = new Image(inputStream, 50, 50, true, true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        logger = SysoutLoggerFactory.getLogger( getClass().getName() );
        
        logger.info("url: " + url.toString() );
                
        fileChooser = FileChooserBuilder.create()
            .title("Choose a style")
            .initialDirectory(currentDir)
            .build();
        
        durationService = new DurationService();

        // have the scroll pane grow with the window resizing
        AnchorPane.setTopAnchor(outerSplitPane, 0.0);
        AnchorPane.setBottomAnchor(outerSplitPane, 0.0);
        AnchorPane.setLeftAnchor(outerSplitPane, 0.0);
        AnchorPane.setRightAnchor(outerSplitPane, 0.0);
    }
}

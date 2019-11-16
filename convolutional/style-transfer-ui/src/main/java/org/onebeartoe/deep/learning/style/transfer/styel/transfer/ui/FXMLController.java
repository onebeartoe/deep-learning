
package org.onebeartoe.deep.learning.style.transfer.styel.transfer.ui;

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
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooserBuilder;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import org.onebeartoe.application.duration.DurationService;

//TODO: Add this back one the UI is ready.
import org.deeplearning4j.examples.styletransfer.NeuralStyleTransfer;
//TODO: Revert back to the actual NeuralStyleTransfer implementation.
//import org.deeplearning4j.examples.styletransfer.mock.MockNeuralStyleTransfer;

import org.onebeartoe.application.logging.SysoutLoggerFactory;

public class FXMLController implements Initializable 
{
    private Logger logger;

//    @FXML
//    private AnchorPane root;
    
    @FXML
    private SplitPane outerSplitPane;
    
    @FXML
    private Button applyStyleButton;
    
    @FXML
    private Button contentButton;
    
    @FXML
    private Button styleButton;
    
    @FXML
    private ImageView contentImage;
    
    @FXML
    private Label contentLabel;
    
    @FXML
    private ImageView styleImage;
    
    @FXML
    private Label styleLabel;
    
    @FXML
    private TilePane tilePane;
    
    private File currentDir;

    private FileChooser fileChooser;
    
    private File contentFile;
    
    private File styleFile;

    private DurationService durationService;
    
//    private ImageIterationListener imageListener;
    
//TODO: add this back once the UI is ready
//    private MockNeuralStyleTransfer styleTransferer = new MockNeuralStyleTransfer();
//TODO: move this instantiation to the initialize() method;
//TODO: Log how long it takes to initalize the NeuralStyleTransfer object.    
    private NeuralStyleTransfer styleTransferer = new NeuralStyleTransfer();
    
    private void applyStyle() throws IOException
    {
        String contentPath = contentFile.getAbsolutePath();

//        logger.info("content: " + contentPath);
        
        String stylePath = styleFile.getAbsolutePath();

//        logger.info("style: " + stylePath);
        

Task<Void> task = new Task<Void>() 
{
    @Override 
    public Void call() throws Exception 
    {        
        Instant start = Instant.now();
Platform.runLater( () ->
{
//logger.info("in applyStyle() in Platform");
            try
            {
                styleTransferer.transferStyle(contentPath, stylePath);
            } 
            catch (IOException ex)
            {
                logger.log(Level.SEVERE, "error transfering style", ex);                
            }
            
        Instant end = Instant.now();
        String durationMessage = durationService.durationMessage(start, end);
        logger.info(durationMessage);            
            
//logger.info("at end of applyStyle() in Platform");            
});
        return null;
    }
};
new Thread(task).start();
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
//logger.info("creating task thread");            
            
            Task<Void> task = new Task<Void>() 
            {
                @Override 
                public Void call() throws Exception 
                {
//logger.info("in call of task thread--");
                    toggleButtons(true);

//logger.info("clearing grid");
                    Platform.runLater( () ->
                    {
                        ObservableList<Node> children = tilePane.getChildren();
                        children.clear();

//                        tilePane.getChildren().clear();
                            
                            
//logger.info("grid cleared");

//        logger.info("outside, on FX thread: " + Platform.isFxApplicationThread() +"\n");


                        try
                        {
//        logger.info("inside, on FX thread: " + Platform.isFxApplicationThread() +"\n");
                            applyStyle();
                        }
                        catch(Exception e)
                        {
                            String message = e.getMessage();

                            logger.log(Level.SEVERE, message, e);

                            applyStyleButton.setText("Error: see log");
                        }
                        finally
                        {
                            toggleButtons(false);
                        }                    
                    });

//logger.info("end of call of task thread");                    
                    

                    
                    return null ;
                }
            };

//logger.info("setting message listener of task thread");            
            task.messageProperty().addListener((obs, oldMessage, newMessage) -> 
            {
//                logger.info("task message: " + newMessage);
            });

//logger.info("starting task thread");
            
            new Thread(task).start();
            
//logger.info("task thread started");
        }
        
//        logger.info("apply style done");
    }

    @FXML
    private void handleContentButtonAction(ActionEvent event) throws FileNotFoundException
    {
        logger.info("the content button was clicked");
        
        contentFile = fileChooser.showOpenDialog(null);
        
        if(contentFile != null)
        {
            String path = contentFile.getAbsolutePath();
            
            InputStream inputStream = new FileInputStream(path);
            
            Image image = new Image(inputStream, 50, 50, true, true);
            
            contentImage.setImage(image);
            
            contentLabel.setText(path);
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

            styleImage.setImage(image);            
            
            styleLabel.setText(path);
        }
    }

    @FXML
    private void handleGridPaneClick(MouseEvent event) 
    {
        logger.info("the grid pane was clicked");
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

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(21);
        col1.setHgrow(Priority.NEVER);
        col1.setHalignment(HPos.CENTER);

//        tilePane.setGridLinesVisible(true);
        
//        tilePane.getColumnConstraints().addAll(col1);
//        gridPane.getColumnConstraints().addAll(col1, col1, col1, col1, col1, col1);
        
        ImageIterationListener imageListener = new ImageIterationListener(tilePane);
        
        styleTransferer.addImageIterationListerner(imageListener);

        // have the scroll pane grow with the window resizing
        AnchorPane.setTopAnchor(outerSplitPane, 0.0);
        AnchorPane.setBottomAnchor(outerSplitPane, 0.0);
        AnchorPane.setLeftAnchor(outerSplitPane, 0.0);
        AnchorPane.setRightAnchor(outerSplitPane, 0.0);
    }

    private void toggleButtons(boolean disabled)
    {
//logger.info("--toggle buttons: " + disabled);        
        applyStyleButton.setDisable(disabled);
        contentButton.setDisable(disabled);
        styleButton.setDisable(disabled);
//logger.info("__end of toggle buttons");        
    }
}

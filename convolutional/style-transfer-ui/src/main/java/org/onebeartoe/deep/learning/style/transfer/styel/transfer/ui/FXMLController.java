
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
import javafx.scene.layout.RowConstraints;
import org.onebeartoe.application.duration.DurationService;

//TODO: Add this back one the UI is ready.
//import org.deeplearning4j.examples.styletransfer.NeuralStyleTransfer;
//TODO: Revert back to the actual NeuralStyleTransfer implementation.
import org.deeplearning4j.examples.styletransfer.mock.MockNeuralStyleTransfer;

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
    private GridPane gridPane;
    
    private File currentDir;

    private FileChooser fileChooser;
    
    private File contentFile;
    
    private File styleFile;

    private DurationService durationService;
    
    private ImageIterationListener imageListener;
    
//TODO: add this back once the UI is ready
    private MockNeuralStyleTransfer styleTransferer = new MockNeuralStyleTransfer();
//TODO: move this instantiation to the initialize() method;
//TODO: Log how long it takes to initalize the NeuralStyleTransfer object.    
//    private NeuralStyleTransfer styleTransferer = new NeuralStyleTransfer();
    
    private void applyStyle() throws IOException
    {
        String contentPath = contentFile.getAbsolutePath();

        logger.info("content: " + contentPath);
        
        String stylePath = styleFile.getAbsolutePath();
        
        logger.info("style: " + stylePath);
        
        Instant start = Instant.now();

        styleTransferer.transferStyle(contentPath, stylePath);

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
            toggleButtons(true);
        
            try
            {
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
        }
        
        System.out.println("apply style done");
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

        gridPane.getColumnConstraints().addAll(col1);
//        gridPane.getColumnConstraints().addAll(col1, col1, col1, col1, col1, col1);
        
        ImageIterationListener imageListener = new ImageIterationListener(gridPane);
        
        styleTransferer.addImageIterationListerner(imageListener);

        // have the scroll pane grow with the window resizing
        AnchorPane.setTopAnchor(outerSplitPane, 0.0);
        AnchorPane.setBottomAnchor(outerSplitPane, 0.0);
        AnchorPane.setLeftAnchor(outerSplitPane, 0.0);
        AnchorPane.setRightAnchor(outerSplitPane, 0.0);
    }

    private void toggleButtons(boolean disabled)
    {
        applyStyleButton.setDisable(disabled);
        contentButton.setDisable(disabled);
        styleButton.setDisable(disabled);
    }
}

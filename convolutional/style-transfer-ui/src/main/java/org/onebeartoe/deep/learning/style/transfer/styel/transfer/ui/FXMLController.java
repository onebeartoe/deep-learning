
package org.onebeartoe.deep.learning.style.transfer.styel.transfer.ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooserBuilder;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

//TODO: add this back one the UI is ready
import org.deeplearning4j.examples.styletransfer.NeuralStyleTransfer;
import org.onebeartoe.application.logging.SysoutLoggerFactory;

public class FXMLController implements Initializable 
{
    private Logger logger;

    @FXML
    private Button applyStyleButton;
    
    @FXML
    private Button contentButton;
    
    @FXML
    private Button styleButton;
    
    @FXML
    private Label contentLabel;
    
    @FXML
    private Label styleLabel;
    
    @FXML
    private GridPane gridPane;
    
    private File currentDir;

    private FileChooser fileChooser;
    
    private File contentFile;
    
    private File styleFile;

//TODO: add this back one the UI is ready
    private NeuralStyleTransfer styleTransferer = new NeuralStyleTransfer();
            
    private void applyStyle() throws IOException
    {
        String contentPath = contentFile.getAbsolutePath();

        logger.info("content: " + contentPath);
        
        String stylePath = styleFile.getAbsolutePath();
        
        logger.info("style: " + stylePath);
        
//TODO: add this back one the UI is ready
        styleTransferer.transferStyle(contentPath, stylePath);
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
            toggleButtons(false);
        
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
                toggleButtons(true);
            }
        }
    }

    @FXML
    private void handleContentButtonAction(ActionEvent event)
    {
        logger.info("the content button was clicked");
        
        contentFile = fileChooser.showOpenDialog(null);
        
        if(contentFile != null)
        {
            String path = contentFile.getAbsolutePath();
            
            contentLabel.setText(path);
        }
    }
    
    @FXML
    private void handleStyleButtonAction(ActionEvent event) 
    {
        logger.info("the style button was clicked");

//TODO: how is the Window object obtained from this context?
        styleFile = fileChooser.showOpenDialog(null);

        if(styleFile != null)
        {
            String path = styleFile.getAbsolutePath();

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
    }

    private void toggleButtons(boolean enabled)
    {
        applyStyleButton.setDisable(enabled);
        contentButton.setDisable(enabled);
        styleButton.setDisable(enabled);
    }
}

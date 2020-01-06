
package org.onebeartoe.deep.learning.interview;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import org.onebeartoe.application.logging.SysoutLoggerFactory;
import org.onebeartoe.deep.learning.natural.language.processing.Interview;
import org.onebeartoe.deep.learning.natural.language.processing.InterviewQuestion;
import org.onebeartoe.deep.learning.natural.language.processing.Recommendation;
import org.onebeartoe.deep.learning.natural.language.processing.ValidationResult;

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

    Interview interview;
    
    InterviewQuestion currentQuestion;
    
//TODO: keep this until we know that a background thread is not needed for a long running task    
    @FXML
    private void handleApplyStyleButtonAction(ActionEvent event)
    {
        logger.info("the apply style button was clicked");

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Missing Input File");
            
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
//TODO: add some long running event here
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        logger = SysoutLoggerFactory.getLogger( getClass().getName() );
        
        logger.info("url: " + url.toString() );
  
        InterviewService interviewService = null;
        try
        {
            interviewService = new InterviewService();
        } 
        catch (IOException  | URISyntaxException | GeneralSecurityException ex)
        {
            logger.severe( ex.getMessage() );
        }
        
        chatHistoryArea.appendText("Welcome to the chatbot interview!");

//TODO: this does not work try something else
        // have the text area fill the scroll pane //grow with the window resizing        
        AnchorPane.setTopAnchor(chatHistoryArea, 0.0);
        AnchorPane.setBottomAnchor(chatHistoryArea, 0.0);
        AnchorPane.setLeftAnchor(chatHistoryArea, 0.0);
        AnchorPane.setRightAnchor(chatHistoryArea, 0.0);
        
        interview = interviewService.get();

        // have the scroll pane grow with the window resizing
        AnchorPane.setTopAnchor(outerSplitPane, 0.0);
        AnchorPane.setBottomAnchor(outerSplitPane, 0.0);
        AnchorPane.setLeftAnchor(outerSplitPane, 0.0);
        AnchorPane.setRightAnchor(outerSplitPane, 0.0);
        
        currentQuestion = interview.currentQuestion();
            
        String imperitive = currentQuestion.getImperative();

        chatHistoryArea.appendText("\n");

        chatHistoryArea.appendText(imperitive);        
    }

    @FXML
    private void onTextEntered(ActionEvent event) throws FileNotFoundException
    {
//        HostServices hostServices = 
//  Desktop.getDesktop().browse("/home/roberto/Desktop/Screenshot%20from%202018-09-09%2020-32-43.png");
        
        String currentInput = textField.getText();
     
        logger.info("the content button was clicked");
        
        textField.setText("");        
        
        chatHistoryArea.appendText("\n\n");
        
        chatHistoryArea.appendText(currentInput);
        
        ValidationResult result = interview.setCurrentQuestionResponse(currentInput);
            
        if(result.responseContainedQuestion)
        {
            chatHistoryArea.appendText("I am not sure about your question: " + result.questionInResponse);
        }
        
        if( ! result.valid )
        {
            chatHistoryArea.appendText("I wasn't able to process your response.");

            if( result.thresholdReached )
            {
                chatHistoryArea.appendText(":(  Let's move on with the interview.");
            }
        }
        else
        {
            String confirmation = currentQuestion.getValidResponseConfirmation();

            chatHistoryArea.appendText(confirmation);

            List<Recommendation> recomendations = currentQuestion.getRecomendations();

            if(recomendations.size() > 0)
            {
                chatHistoryArea.appendText("Here are some recomendations for " + result.answer);

                recomendations.forEach( chatHistoryArea::appendText );
            }
        }
        
        if( interview.isComplete() )
        {
            chatHistoryArea.appendText("\nThanks for participating in the interview!");
            
            textField.setEditable(false);
        }
    }
}


package org.onebeartoe.deep.learning.interview;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

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
    private WebView reportWebView;

    private WebEngine webEngine;
    
    Interview interview;
    
    InterviewQuestion currentQuestion;
    
    ReportService reportService;

    private void askCurrentQuestion()
    {
        currentQuestion = interview.currentQuestion();
            
        String imperitive = currentQuestion.getImperative();

        chatHistoryArea.appendText("\n");

        chatHistoryArea.appendText(imperitive);        
    }
    
    
    private ValidationResult handleApplyStyleButtonAction(String currentInput)
    {
        ValidationResult result = null;
        
        result = interview.setCurrentQuestionResponse(currentInput);

return result;        
    }
    
//TODO: keep this until we know that a background thread is not needed for a long running task    
    @FXML
    private ValidationResult handleApplyStyleButtonAction_NOT(String currentInput)
    {
        logger.info("the apply style button was clicked");
                    
        Alert waitAlert = new Alert(AlertType.INFORMATION);
        waitAlert.setTitle("Please wait");
        waitAlert.show();
        waitAlert.setOnCloseRequest( (closeWaitEvent) ->
        {
            logger.info("the wait dialog was closed");
            waitAlert.close();
        });
        
        ValidationResult result = null;
        
        result = interview.setCurrentQuestionResponse(currentInput);                    
            
        Task<Void> task = new Task<Void>() 
        {
            @Override 
            public Void call() throws Exception 
            {
                try
                {
//TODO: add some long running event here
//result = interview.setCurrentQuestionResponse(currentInput);                    
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
        
        return result;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        logger = SysoutLoggerFactory.getLogger( getClass().getName() );
        
        logger.info("url: " + url.toString() );
  
        reportService = new ReportService();
        
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
//        AnchorPane.setTopAnchor(chatHistoryArea, 0.0);
//        AnchorPane.setBottomAnchor(chatHistoryArea, 0.0);
//        AnchorPane.setLeftAnchor(chatHistoryArea, 0.0);
//        AnchorPane.setRightAnchor(chatHistoryArea, 0.0);
        
        webEngine = reportWebView.getEngine();
        
        interview = interviewService.get();

        // have the scroll pane grow with the window resizing
        AnchorPane.setTopAnchor(outerSplitPane, 0.0);
        AnchorPane.setBottomAnchor(outerSplitPane, 0.0);
        AnchorPane.setLeftAnchor(outerSplitPane, 0.0);
        AnchorPane.setRightAnchor(outerSplitPane, 0.0);

        askCurrentQuestion();
    }

    @FXML
    private void onTextEntered(ActionEvent event) throws FileNotFoundException, InterruptedException, ExecutionException
    {
//        HostServices hostServices = 
//  Desktop.getDesktop().browse("/home/roberto/Desktop/Screenshot%20from%202018-09-09%2020-32-43.png");
        
        String currentInput = textField.getText();
     
        logger.info("text was received from the input field");
        
        textField.setText("");        
        
        chatHistoryArea.appendText("\n\n");
        
        chatHistoryArea.appendText(currentInput);
        
//        ValidationResult result;
        
//        final List<ValidationResult> finalList = new ArrayList();
//        
//      final CountDownLatch latch = new CountDownLatch(1);
////final StringProperty passwordProperty = new SimpleStringProperty();
//Platform.runLater(new Runnable() {
//    @Override public void run() {
//        ValidationResult tempResult = interview.setCurrentQuestionResponse(currentInput);
//        finalList.add(tempResult);
//        
//        latch.countDown();
//        
//        latch.
//    }
//});
//latch.await();      
//System.out.println(passwordProperty.get());

System.out.println("hey");
        
//        ValidationResult result = finalList.get(0);
//        ValidationResult result = (ValidationResult) query.get();

textField.setDisable(true);

        ValidationResult result = interview.setCurrentQuestionResponse(currentInput);

textField.setDisable(false);        
                
        if(result.responseContainedQuestion)
        {
            chatHistoryArea.appendText("\nI am not sure about your question: " + result.questionInResponse);
        }
        
        if( ! result.valid )
        {
            chatHistoryArea.appendText("\nI wasn't able to process your response.");

            if( result.thresholdReached )
            {
                chatHistoryArea.appendText("\n:(  Let's move on with the interview.");
            }
        }
        else
        {
            String confirmation = currentQuestion.getValidResponseConfirmation();

            chatHistoryArea.appendText(confirmation);

            List<Recommendation> recomendations = currentQuestion.getRecomendations();

            if(recomendations.size() > 0)
            {
                chatHistoryArea.appendText("\nHere are some recomendations for " + result.answer);

                recomendations.forEach(r ->
                {
                    String recomendation = "\n" + r.toString();
                    
                    chatHistoryArea.appendText(recomendation);
                });
            }
        }
        
        if( interview.isComplete() )
        {
            textField.setDisable(true);
            
            chatHistoryArea.appendText("\nThanks for participating in the interview!");

            List<InterviewQuestion> questions = interview.getQuestions();
                        
            String reportAsStr = reportService.toHtml(questions);
            
            webEngine.loadContent(reportAsStr);
        }
        else
        {
            askCurrentQuestion();
        }
    }
}

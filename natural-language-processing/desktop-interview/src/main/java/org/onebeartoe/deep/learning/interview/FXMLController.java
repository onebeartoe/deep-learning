
package org.onebeartoe.deep.learning.interview;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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

        chatHistoryArea.appendText("\n\n");

        chatHistoryArea.appendText(imperitive);        
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
        
        // disable to avoid user input during recomendation lookup
        textField.setDisable(true);

        ValidationResult result = interview.setCurrentQuestionResponse(currentInput);

        // re-enable 
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

            chatHistoryArea.appendText("\n");
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
            
            chatHistoryArea.appendText("\n\nThanks for participating in the interview!");

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

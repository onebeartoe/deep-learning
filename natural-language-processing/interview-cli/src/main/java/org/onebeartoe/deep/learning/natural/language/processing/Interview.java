
package org.onebeartoe.deep.learning.natural.language.processing;

import java.util.ArrayList;
import java.util.List;
import org.onebeartoe.deep.learning.nlp.sentences.SentenceClassification;

import org.onebeartoe.deep.learning.nlp.sentences.SentenceDetector;
import org.onebeartoe.deep.learning.nlp.sentences.SentenceType;

/**
 * This class is an abstraction of an interview.
 */
public class Interview
{
    private List<InterviewQuestion> questions;

    private int currentQuestion;
    
    private boolean complete = false;
    
    private SentenceDetector sentenceDetector;
    
    private InterviewQuestion intervieweeNameQuesiton;
    
    private SentimentQuestion sentimentQuestion;

    public Interview(List<InterviewQuestion> questions)
    {
        this.questions = questions;
        
        currentQuestion = 0;
        
        intervieweeNameQuesiton = questions.get(0);
        
        sentimentQuestion = (SentimentQuestion) questions.get(1);
    }
    
    @Deprecated //"is the really deprecated")
    public InterviewQuestion currentQuestion()
    {
        InterviewQuestion ours = questions.get(currentQuestion);
        
        return ours;
    }   

    public String getIntervieweeLanguage()
    {        
        return sentimentQuestion.getLanguage();
    }

    public String getIntervieweeName()
    {
        return intervieweeNameQuesiton.answer;
    }

//TODO: !!!!!!!!!!!!!! clone all the questions and return the clones !!!!!!!!!!!!
    public List<InterviewQuestion> getQuestions()
    {
        return questions;
    }
    
    public boolean isComplete()
    {
//TODO: add a unit test for this method        
        return complete;
    }
    
    public ValidationResult setCurrentQuestionResponse(String resonse)
    {
        InterviewQuestion question = questions.get(currentQuestion);
        
        List<SentenceClassification> allClassifications = sentenceDetector.findSentences(resonse);
        
        StringBuilder questions = new StringBuilder();
        
        StringBuilder otherSentences = new StringBuilder();
        
        for(SentenceClassification classification : allClassifications)
        {
            if(classification.getType() == SentenceType.INTERROGATIVE)
            {
                questions.append( classification.getText() );
            }
            else
            {
                otherSentences.append( classification.getText() );
            }
        }
        
        ValidationResult result = question.setResponse( otherSentences.toString() );

        if(questions.length() > 0)
        {
            result.responseContainedQuestion = true;
            
            result.questionInResponse = questions.toString();
        }
        
        boolean validResponse = result.valid;

        if(validResponse)
        {
            question.setAnswered(true);

            currentQuestion++;
        }
        else if(result.thresholdReached)
        {
            currentQuestion++;
        }
        
        if(currentQuestion == this.questions.size() )
        {
            complete = true;
        }                        
        
        return result;
    }

    @Deprecated //"is the really deprecated")
    public void markAsAnswered(int questionIndex)
    {
        InterviewQuestion question = questions.get(questionIndex);
        
        question.setAnswered(true);
    }

    public void setSentenceDetector(SentenceDetector sentenceDetector)
    {
        this.sentenceDetector = sentenceDetector;
    }
}

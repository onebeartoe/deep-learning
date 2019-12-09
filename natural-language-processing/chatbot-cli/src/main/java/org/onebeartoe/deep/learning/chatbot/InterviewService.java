
package org.onebeartoe.deep.learning.chatbot;

import java.util.ArrayList;
import java.util.List;
import org.onebeartoe.deep.learning.natural.language.processing.Interview;
import org.onebeartoe.deep.learning.natural.language.processing.InterviewQuestion;

/**
 * This class configures an Interview instance to use with the chatbot application.
 */
public class InterviewService
{
    public Interview get()
    {
        List<InterviewQuestion> questions = new ArrayList();
        
        
        
        Interview interview = new Interview(questions);
        
        
        
        return interview;
    }
}

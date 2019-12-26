
package org.onebeartoe.deep.learning.natural.language.processing;

import java.util.List;
import org.onebeartoe.deep.learning.nlp.named.entity.PercentageDetector;
import org.onebeartoe.deep.learning.nlp.named.entity.results.DetectedNamedEntity;

/**
 * This class abstracts the project and percentage done question.
 */
public class ProjectAndPercentageQuestion extends InterviewQuestion
{
    private PercentageDetector percentageDetector;
        
    private String project;

    public String getProject()
    {
        return project;
    }

    public ProjectAndPercentageQuestion(PercentageDetector percentageDetector)
    {
        this.percentageDetector = percentageDetector;
    }

    @Override
    public ValidationResult validateResponse(String response)
    {
//TODO: user the POS (parts of speech detector) to extract the nouns and store them as keywords
        List<DetectedNamedEntity> percentages = percentageDetector.findPercentages(response);

        ValidationResult result = new ValidationResult();
        
        if(percentages.size() == 0)
        {
            result.valid = false;
        }
        else
        {
            result.valid = true;
            
            DetectedNamedEntity percentage = percentages.get(0);
            
            result.answer = percentage.getName();
            
            this.answer = result.answer;
            
            int begin = 0;
            
            int end = response.lastIndexOf(this.answer);
            
            project = response.substring(begin, end);
        }
        
        return result;
    }

    @Override
    public String getValidResponseConfirmation()
    {
        return "Oh that project sounds cool!  It must feel good to be " + answer + "done.";
    }
}

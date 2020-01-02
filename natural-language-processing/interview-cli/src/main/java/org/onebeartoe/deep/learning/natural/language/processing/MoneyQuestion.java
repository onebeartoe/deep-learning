
package org.onebeartoe.deep.learning.natural.language.processing;

import java.util.List;
import org.onebeartoe.deep.learning.nlp.named.entity.MoneyDetector;
import org.onebeartoe.deep.learning.nlp.named.entity.results.DetectedNamedEntity;
import org.onebeartoe.web.search.WebSearch;

/**
 * This class is an abstraction of the money question.
 */
public class MoneyQuestion extends SearchRecommendingQuestion
{
    private MoneyDetector moneyDetector;
    
    private ProjectAndPercentageQuestion projectQuestion;
    
    public MoneyQuestion(MoneyDetector moneyDetector, ProjectAndPercentageQuestion projectQuestion, WebSearch webSearch)
    {
        this.moneyDetector = moneyDetector;
        
        this.projectQuestion = projectQuestion;
        
        this.webSearch = webSearch;
    }

    public String getSearchQuery()
    {
        return String.format("%s %s", projectQuestion.getProject(), answer);
    }
    
    @Override
    public ValidationResult validateResponse(String response)
    {
        List<DetectedNamedEntity> monies = moneyDetector.findMoney(response);
        
        ValidationResult result = new ValidationResult();
        
        if( monies.isEmpty() )
        {
            result.valid = false;
        }
        else
        {
            result.valid = true;
            
            result.answer = monies.get(0).getName();
        }             
        
        return result;
    }

    @Override
    public String getValidResponseConfirmation()
    {
        return String.format("Alright, I've noted that you have a %s budget.", answer);
    }
}

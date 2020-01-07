
package org.onebeartoe.deep.learning.interview;
   
import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.onebeartoe.deep.learning.interview.ReportService.INVALID_ANSWER_DESCRIPTION;
import org.onebeartoe.deep.learning.natural.language.processing.InterviewQuestion;
import org.onebeartoe.deep.learning.natural.language.processing.Recommendation;
import org.onebeartoe.html.CodeGenerator;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.reporters.Files;

/**
 * This class tests the ReportService specification
 */
public class ReportServiceSpecification
{
    ReportService implementation;
    
    List<InterviewQuestion> questions;
    
    final String colorQuestion = "What is your favorite color?";

    final String colorQuestionAnswer = "orange";
    
    final String colorQuestionRecommendation = "I recommend green if you like orange.";
    
    final String divisionQuestion = "What is division by zero?";

    final String heightQuestion = "How tall are you?";
    
    final String heightQuestionAnswer = "5' 7\"";
    
    final String heightQuestionRecommendation = "Drink more milk, and maybe you will get taller.";
    
    CodeGenerator codeGenerator;
        
    /**
     * Build an interview with 2 answered questions and one unanswered question.
     * 
     * @return a populated interview
     */
    private List<InterviewQuestion> buildInterview()
    {
        InterviewQuestion colorQuestion = new TestQuestion();        
        
        colorQuestion.setImperative(this.colorQuestion);
        colorQuestion.setAnswer(colorQuestionAnswer);
        colorQuestion.setAnswered(true);
        Recommendation colorRecommendation = new Recommendation();
        colorRecommendation.title = "Colors are Great";
        colorRecommendation.link = "color.com";
        colorRecommendation.content = colorQuestionRecommendation;
        colorQuestion.addRecommendation(colorRecommendation);
        
        InterviewQuestion divisionQuestion = new TestQuestion();
        divisionQuestion.setImperative(this.divisionQuestion);
        divisionQuestion.setAnswered(false);
        
        InterviewQuestion heightQuestion = new TestQuestion();
        heightQuestion.setImperative(this.heightQuestion);
        heightQuestion.setAnswer(heightQuestionAnswer);
        heightQuestion.setAnswered(true);
        Recommendation heightRecommendation = new Recommendation();
        heightRecommendation.title = "Heights R Us";
        heightRecommendation.content = heightQuestionRecommendation;
        heightRecommendation.link = "height.net";
        heightQuestion.addRecommendation(heightRecommendation);
        
        questions = new ArrayList();
        
        questions.add(colorQuestion);
        questions.add(divisionQuestion);
        questions.add(heightQuestion);
        
        return questions;
    }

    private void contentIsPresent(String html)
    {
        assertTrue( html.contains(colorQuestion) );
        
        assertTrue( html.contains(colorQuestionAnswer) );

        assertTrue( html.contains(colorQuestionRecommendation) );

        assertTrue( html.contains(divisionQuestion) );
        
        assertTrue( html.contains(INVALID_ANSWER_DESCRIPTION) );

        assertTrue( html.contains(heightQuestion) );

        assertTrue( html.contains(heightQuestionAnswer) );
        
        assertTrue( html.contains(heightQuestionRecommendation) );
    }
    
    @BeforeClass
    public void initialize()
    {
        implementation = new ReportService();
        
        buildInterview();
        
        codeGenerator = new CodeGenerator();
    }
    
    @Test
    public void saveHtml() throws IOException
    {
        String content = implementation.toHtml(questions);
        
        String html = codeGenerator.htmlify(content);
        
        String outfileName = "report-service.html";
        
        String outfilePath = String.format("target/%s", outfileName);
        
        File outfile = new File(outfilePath);
        
        implementation.saveHtml(html, outfile);
        
        assertTrue( outfile.exists() );
        
        assertTrue(outfile.length() > 0);
        
        String fileContents = Files.readFile(outfile);
        
        contentIsPresent(fileContents);
    }
    
    @Test
    public void savePdf() throws DocumentException, IOException
    {
        String content = implementation.toHtml(questions);
        
        String html = codeGenerator.htmlify(content);
        
        String outfileName = "report-service.pdf";
        
        String outfilePath = String.format("target/%s", outfileName);
                
        File outfile = new File(outfilePath);
        
        implementation.savePdf(html, outfile);
        
        assertTrue( outfile.exists() );
        
        assertTrue(outfile.length() > 0);

//TODO: writes some assertions to verify the HTML content is actually in the PDF file, 
//             possibly using something like #contentIsPresent().        
//        assertEquals("the TODO above is complete", "no it is not");
//TODO: see HtmlToPdf.java in the onebeatoe tours project.        
    }
    
    @Test
    public void toHtml()
    {
        String html = implementation.toHtml(questions);
        
        assertTrue( html.contains("<html") );
        
        contentIsPresent(html);
    }
    
    @Test
    /**
     * Given the interview object created for this test class, verify that the 
     * indicator is present and only present once.
     */
    public void toHtml_invalidAnswer()
    {
        String html = implementation.toHtml(questions);
     
        String expected = INVALID_ANSWER_DESCRIPTION;

        int validIndex = html.indexOf(expected);
        
        // the indecator description should be present one time
        assertTrue(validIndex > 0);
        
        int invalidIndex = html.indexOf(html, validIndex);

        // the indicator description should only be present once
        assertEquals(invalidIndex, -1);
    }
}

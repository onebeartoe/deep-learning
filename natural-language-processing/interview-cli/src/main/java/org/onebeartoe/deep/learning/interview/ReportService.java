
package org.onebeartoe.deep.learning.interview;

import com.google.common.io.Files;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;
import org.onebeartoe.deep.learning.natural.language.processing.InterviewQuestion;
import org.onebeartoe.deep.learning.natural.language.processing.Recommendation;
import org.onebeartoe.html.BreakTag;
import org.onebeartoe.html.CodeGenerator;
import org.onebeartoe.html.Heading;
import org.onebeartoe.html.Paragraph;
import org.onebeartoe.html.UnorderedList;

/**
 * This class provides service methods to create interview reports in HTML format, 
 * as well as save the HTML report to disk, as well as save the HTML report as a 
 * PDF document to disk.
 */
public class ReportService
{
    public static final String INVALID_ANSWER_DESCRIPTION = "no valid answer was given";
    
    CodeGenerator codeGenerator;
    
    final String BR;

    public ReportService()
    {
        codeGenerator = new CodeGenerator();
        
        BreakTag breakTag = new BreakTag();

        BR = breakTag.toString();
    }
    
    String toHtml(List<InterviewQuestion> questions)
    {        
        String questionContent = questions.stream()
                        .map( q -> 
                        {
                            StringBuilder sb = new StringBuilder();                                                        
                            
                            String questionTag = Heading.h2( q.getImperative() ).toString();
                            
                            sb.append(questionTag);
                            
                            sb.append("\n");
                            
                            if( q.isAnswered() )
                            {
                                String answer = q.getAnswer();
                                answer = "answer: " + answer;
                                Paragraph p = new Paragraph();
                                p.setText(answer);
                                answer = p.toString();
                                
                                sb.append(answer);
                                
                                sb.append(BR);
                                
                                String recommendations = recommendations( q.getRecomendations() );
                                
                                sb.append(recommendations);
                            }
                            else
                            {
                                sb.append(INVALID_ANSWER_DESCRIPTION);
                            }
                            
                            return sb.toString();
                        })
                        .collect( Collectors.joining("\n\n"));
        
        
        String title = "Interview Report";
        title = Heading.h1(title) + "\n\n";
        
        String content = title + questionContent;
        
        return codeGenerator.htmlify(content);
    }

    private String recommendations(List<Recommendation> recommendations)
    {
        StringBuilder recommends = new StringBuilder();
                
        String h3 = Heading.h3("Recommendations")
                           .toString();
        
        recommends.append(h3);

        List<String> elements = recommendations.stream()
                              .map(r ->
                              {
                                  StringBuilder sb = new StringBuilder();
                                  
                                  String content = 
                                  r.title + 
                                          BR + 
                                          r.link
                                          +
                                          BR 
                                          + r.content;
                                  
                                sb.append(content);
                                  
                                  return sb.toString();
                              })
                              .collect( Collectors.toList() );
        
        UnorderedList ul = new UnorderedList(elements);
        
        recommends.append( ul.toString() );
        
        return recommends.toString();
    }

    void saveHtml(String html, File outfile) throws IOException
    {
        Files.write(html.getBytes(), outfile);
    }
    
    void savePdf(String html, File outfile) throws FileNotFoundException, DocumentException, IOException
    {
        Document document = new Document();
        
        OutputStream file = new FileOutputStream(outfile);
     
//        String toString = document.toString();
        
        PdfWriter writer = PdfWriter.getInstance(document, file);
        
        document.open();
        
        InputStream is = new ByteArrayInputStream(html.getBytes());
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
        
        document.close();
        
        file.close();
    }
}

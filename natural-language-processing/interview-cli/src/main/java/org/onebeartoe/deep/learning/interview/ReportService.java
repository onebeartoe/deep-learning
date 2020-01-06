
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
import org.onebeartoe.html.CodeGenerator;

/**
 * This class provides service methods to create interview reports in HTML format, 
 * as well as save the HTML report to disk, as well as save the HTML report as a 
 * PDF document to disk.
 */
public class ReportService
{
    public static final String INVALID_ANSWER_DESCRIPTION = "no valid answer was given";
    
    CodeGenerator codeGenerator;

    public ReportService()
    {
        codeGenerator = new CodeGenerator();
    }
    
    String toHtml(List<InterviewQuestion> questions)
    {
        String title = "Interview Report" + "\n\n";
        
        String content = questions.stream()
                        .map( q -> 
                        {
                            StringBuilder sb = new StringBuilder();                                                        
                            
                            sb.append( q.getImperative() );
                            
                            sb.append("\n");
                            
                            if( q.isAnswered() )
                            {
                                sb.append( q.getAnswer() );
                                
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
        
        return codeGenerator.htmlify(title + content);
    }

    private String recommendations(List<Recommendation> recommendations)
    {
        return recommendations.stream()
                              .map(r ->
                              {
                                  return r.title + r.link + r.content;
                              })
                              .collect( Collectors.joining(" "));
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

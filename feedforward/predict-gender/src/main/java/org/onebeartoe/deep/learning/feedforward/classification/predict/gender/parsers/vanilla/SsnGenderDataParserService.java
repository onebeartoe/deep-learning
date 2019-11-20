
package org.onebeartoe.deep.learning.feedforward.classification.predict.gender.parsers.vanilla;

import com.google.common.io.Files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class is used to parse gender data provided by the U.S. Social Security 
 * Administration and rearrange it to match the format that PredictGenderTrain.java
 * expects.
 * 
 *      https://catalog.data.gov/dataset/baby-names-from-social-security-card-applications-national-level-data
 * 
 * The this parser expects this format:
 * 
 * firstName,sex,count
 * 
 * and outputs to this format:
 * 
 * firstName,sex,language
 * 
 * where sex is 'F' or 'M' in the output format.
 * 
 * Output files are placed alongside the input file.
 * 
 * @author Roberto Marquez
 */
public class SsnGenderDataParserService
{
    private Logger logger = Logger.getLogger( getClass().getName() );
    
    public void parse(File infile, String language) throws IOException
    {
        logger.info("reading input from: " + infile.getAbsolutePath() );

        List<String> inputLines = Files.readLines(infile, StandardCharsets.UTF_8);
        
        String infileName = infile.getName();

        File outputDir = infile.getParentFile();
        logger.info("output dir is:" + outputDir.getAbsolutePath());
        
        // open output files for male and female
        String maleOutfileName = infileName + ".M.csv";
        File maleOutfile = new File(outputDir, maleOutfileName);
        logger.info("male outfile: " + maleOutfile.getAbsolutePath());

        String femailOutfileName = infileName + ".F.csv";
        File femaleOutfile = new File(outputDir, femailOutfileName);
        logger.info("female outfile: " + femaleOutfile.getAbsolutePath());
        
        FileOutputStream maleOutStream = new FileOutputStream(maleOutfile);
        PrintStream malePrintStream = new PrintStream(maleOutStream);
        
        FileOutputStream femaleOutStream = new FileOutputStream(femaleOutfile);
        PrintStream femalePrintStream = new PrintStream(femaleOutStream);
        
        inputLines.forEach(line -> 
        {
            parseOneLine(line, femalePrintStream, malePrintStream, language);
        });
                   
        femalePrintStream.close();
        malePrintStream.close();        
    }
    
    /**
     * For each input line, split by ',' deliminator and pick off the name 
     * and gender.
     * 
     * Place 'M' genders into the male file and 'F' genders into the female.
     * 
     * @param line
     * @param femalePrintStream
     * @param malePrintStream 
     */
    private void parseOneLine(String line, PrintStream femalePrintStream, PrintStream malePrintStream, String language)
    {
        String[] split = line.split(",");
        
        if(split.length == 3)
        {
            String name = split[0].toLowerCase();
            
            String sex = split[1].toLowerCase();
            
            String outLine = String.join(",", name, sex, language);
            
            if(sex.equals("f"))
            {
                femalePrintStream.println(outLine);
            }
            else
            {
                malePrintStream.println(outLine);
            }
        }
    }
}

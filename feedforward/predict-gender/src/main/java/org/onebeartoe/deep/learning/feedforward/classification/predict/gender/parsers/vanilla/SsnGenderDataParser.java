
package org.onebeartoe.deep.learning.feedforward.classification.predict.gender.parsers.vanilla;

import java.io.File;
import java.io.IOException;

/**
 * This class is a driver for SsnGenderDataParserService.java
 * 
 * @author Roberto Marquez
 */
public class SsnGenderDataParser
{
    public static void main(String[] args) throws IOException
    {
        String userHome = System.getProperty("user.home");
          
        String infilePath = userHome + "/Downloads/world/deep-learning/dl4j-examples-data/dl4j-examples/ssn-gender/names/yob2018.txt";
        File infile = new File(infilePath);

        String language = "english";
        
        SsnGenderDataParserService parserService = new SsnGenderDataParserService();
        
        parserService.parse(infile, language);
    }
}

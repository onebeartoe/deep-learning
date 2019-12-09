
package org.onebeartoe.deep.learning.nlp.language.detection;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * This class tests the LanguageDetectionService specification .
 */
public class LanguageDetectionServiceSpecification
{
    /*
//TODO
Use as a sample sentence:

    Declarative:

        Lorem ipsum 

    Imperative:

        What you talking about, Willis?     
     */

    /**
     * Test of mapLanguage method, of class LanguageDetectionService.
     */
    @Test
    public void testMapLanguage()
    {
        System.out.println("mapLanguage");
        String code = "";
        String expResult = "";
        String result = LanguageDetectionService.mapLanguage(code);
        assertEquals(result, expResult);
    }
    
}

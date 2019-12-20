
package org.onebeartoe.deep.learning.nlp.language.detection;

import java.io.IOException;
import java.net.URISyntaxException;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * This class tests the LanguageDetectionService specification .
 */
public class LanguageDetectionServiceSpecification
{
    private LanguageDetectionService implementation;
    
    @BeforeMethod
    public void initialize() throws IOException, URISyntaxException
    {
        implementation = new LanguageDetectionService();
    }
    
    public void detectLanguage(String sentence, String expectedCode)
    {
        String actual = implementation.detectLanguage(sentence);
        
        assertEquals(actual, expectedCode);
    }
    
    @Test
    public void detectLanguage_english()
    {        
        String sentence = "What you talking about, Willis?  Nevermind.";

        String expectedCode = "eng";

        detectLanguage(sentence, expectedCode);
    }
    
    @Test
    public void detectLanguage_spanish()
    {
        String sentence = "¿De que estas hablando, Guillermo?  No se preocupe.";

        String expectedCode = "spa";

        detectLanguage(sentence, expectedCode);
    }
    
    @Test
    public void detectLanguage_german()
    {
        String sentence = "Was redest du über, Wilhelm?   Keine Ursache.";

        String expectedCode = "deu";

        detectLanguage(sentence, expectedCode);
    }
    
    private void mapLanguage(String code, String expectedLanguage) throws UnknownLanguageException
    {
        String actual = implementation.mapLanguage(code);

        assertEquals(actual, expectedLanguage);        
    }

    @Test
    public void mapLanguage_english() throws UnknownLanguageException
    {
        String code = "eng";

        String expectedResult = "English";
        
        mapLanguage(code, expectedResult);
    }

    @Test
    public void mapLanguage_german() throws UnknownLanguageException
    {
        String code = "deu";

        String expectedResult = "German";
        
        mapLanguage(code, expectedResult);
    }

    @Test
    public void mapLanguage_spanish() throws UnknownLanguageException
    {
        String code = "spa";

        String expectedResult = "Spanish";
        
        mapLanguage(code, expectedResult);
    }

    @Test(expectedExceptions = {UnknownLanguageException.class})
    public void mapLangage_fails_invalidCode() throws UnknownLanguageException
    {
        String invalidCode = "wacka-wacka";
        
        implementation.mapLanguage(invalidCode);
    }
}

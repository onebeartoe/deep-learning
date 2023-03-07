
package ai.djl.examples.inference;

import ai.djl.ModelException;
import ai.djl.modality.cv.Image;
import ai.djl.translate.TranslateException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;


public class BigGanSpecification 
{
    private final BigGAN implementation = new BigGAN();
    
    @Test
    /**
     * This test ensures the category names are available to the application and 
     * that there are the correct number of expected categories.
     */
    public void categoryNames_success() throws URISyntaxException, IOException
    {
        List<String> names = implementation.categoryNames();                

        final int expected = 1000;  // the documentation says there are one thousand entries in the mapping file

        int actual = names.size();
        
        assertEquals(expected, actual);
    }    
    
    
    @Test
    /**
     * 
     * This test ensures the model files are available.
     * 
     * If this test fails make sure the following dependencies are in the POM file (classpath):
     * 
     *      ai.djl:basicdataset:0.19.0
     * 
     *      ai.djl.pytorch:pytorch-model-zoo:0.19.0
     * 
     *      ai.djl:model-zoo:0.19.0         
     * 
     */
    public void generate_success() throws IOException, ModelException, TranslateException
    {
        int categoryIndex = 101;

        Image output = implementation.generate(categoryIndex);
        
        assertNotNull(output);
    }
    
    @Test    
    /**
     * This test verifies that generate() method returns the correct number of 
     * images.
     */
    public void generate_multiple_success() throws IOException, ModelException, TranslateException
    {
        int [] categoryIndecies = {100, 207, 971};
        
        Image[] generatedImages = implementation.generate(categoryIndecies);    
        
        int size = generatedImages.length;

        assertTrue(size == 3);
    }
}

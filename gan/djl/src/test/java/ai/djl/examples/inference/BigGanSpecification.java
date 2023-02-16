
package ai.djl.examples.inference;

import ai.djl.ModelException;
import ai.djl.modality.cv.Image;
import ai.djl.translate.TranslateException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;


public class BigGanSpecification 
{
    private BigGAN implementation = new BigGAN();
    
    @Test
    /**
     * 
     * This test ensures the model files are available.
     * 
     * If this test fails make sure the following dependecies are in the POM file:
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
        Image[] generatedImages = implementation.generate();    
        
        int size = generatedImages.length;
                
        assertTrue(size == 5);
    }
    
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
}

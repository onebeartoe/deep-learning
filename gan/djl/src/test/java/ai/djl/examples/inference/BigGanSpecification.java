
package ai.djl.examples.inference;

import ai.djl.ModelException;
import ai.djl.examples.inference.BigGAN;
import ai.djl.modality.cv.Image;
import ai.djl.translate.TranslateException;
import java.io.IOException;
import static junit.framework.Assert.assertTrue;
import org.testng.annotations.Test;


public class BigGanSpecification 
{
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
        Image[] generatedImages = BigGAN.generate();    
        
        int size = generatedImages.length;
                
        assertTrue(size == 5);
    }
}

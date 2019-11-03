
package org.onebeartoe.deep.learning.regression.wine.quality;

import java.net.URISyntaxException;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * This class tests the specification of the WineQualityRegression class.
 */
public class WineQualityRegressionSpecification
{
    private WineQualityRegression implementation;
    
    private WineQualityRegression.RegressionArtifacts artifacts;
            
    @BeforeClass
    public void initialize() throws URISyntaxException
    {
        implementation = new WineQualityRegression();
        
        artifacts = implementation.trainAndTest();
    }

    /**
     * This test verifies the specification can predict a high quality wine.
     */
    @Test
    public void predict_highQuality()
    {
        double [] values = {7.9, 0.35, 1.00, 3.6, 0.078, 72, 37, 0.9973,  3.35, 2.00, 12.8, 8};
        
        Wine wine = Wine.fromArray(values);
        
        double quality = implementation.predict(artifacts, wine);

        System.out.println("hight quality = " + quality);
        
        assertTrue(quality > 7.0);
    }
    
    /**
     * This test verifies the specification can predict a low quality wine.
     */
    @Test
    public void predict_lowQuality()
    {
        double [] values = {8.3, 1.02, 0.00, 3.4, 0.084,  0, 11, 0.99892, 3.48, 0.00, 11,    3};
        
        Wine wine = Wine.fromArray(values);
        
        double quality = implementation.predict(artifacts, wine);
        
        assertTrue(2.0 < quality && quality < 4.5);
    }
}

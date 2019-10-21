
package org.onebeartoe.deep.learning.color.predictor;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.scene.paint.Color;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author lando
 */
public class ColorPredictorSpecification
{
    ColorPredictor implementation;
    
    @BeforeMethod
    public void setUpMethod() throws URISyntaxException, IOException 
    {
        implementation = new ColorPredictor();
    }



    /**
     * Test of predict method, of class ColorPredictor.
     */
    @Test
    public void testPredict_givenLightBackground_expectDarkForeground() 
    {
        System.out.println("predict");
        
        Color color = Color.LIGHTYELLOW;
        
        FontShade expected = FontShade.DARK;
        
        FontShade actual = implementation.predict(color);
                
        assertEquals(actual, expected);
    }

    /**
     * Test of doubleArrayOf method, of class ColorPredictor.
     */
    @org.testng.annotations.Test
    public void testDoubleArrayOf() 
    {
        Double[] input = {1.0, 2.0, 3.0};
                
        DoubleArray actual = ColorPredictor.doubleArrayOf(input);
        
        assertNotNull(actual);
        
        int expected = input.length;
        
        assertEquals(actual.data.size(), expected);
    }
    
}

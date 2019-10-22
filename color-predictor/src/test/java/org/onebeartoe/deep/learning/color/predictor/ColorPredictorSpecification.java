
package org.onebeartoe.deep.learning.color.predictor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.scene.paint.Color;
import org.onebeartoe.application.Colors;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author lando
 */
public class ColorPredictorSpecification
{
    private ColorPredictor implementation;
    
    @BeforeMethod
    public void setUpMethod() throws URISyntaxException, IOException 
    {
        implementation = new ColorPredictor();
    }

    @Test
    public void predict_givenLightBackground_expectDarkForeground() 
    {        
        Color inputColor = Color.LIGHTYELLOW;
        
        FontShade expected = FontShade.DARK;
        
        FontShade actual = implementation.predict(inputColor);
                
        assertEquals(actual, expected);
    }

    @Test
    public void predict_givenDarkBackground_expectLightForeground()
    {
        Color inputColor = Color.DARKBLUE;
        
        FontShade expected = FontShade.LIGHT;
        
        FontShade actual = implementation.predict(inputColor);
                
        assertEquals(actual, expected);
    }
    
    @Test
    public void predict_giveBuiltInDarkBackgrounds_expectLightForeground() throws IllegalAccessException
    {
        Map<String, Color> colorsMap = Colors.list();
        
        List<String> darkKeys = colorsMap.keySet()
                .stream()
                .filter( s -> s.startsWith("DARK") )
                .sorted()
                .collect( Collectors.toList() );
                
        // remove built in colors named 'dark' that are actually light 
        darkKeys.remove("DARKTURQUOISE");
        darkKeys.remove("DARKGOLDENROD");                                
        darkKeys.remove("DARKGRAY");                                
        darkKeys.remove("DARKGREY");                                
        darkKeys.remove("DARKKHAKI");                                
        darkKeys.remove("DARKORANGE");                                
        darkKeys.remove("DARKSALMON");                                
        darkKeys.remove("DARKSEAGREEN");                
        
        System.out.println("dark keys:");
        darkKeys.forEach( System.out::println );
        
        boolean errorOccured = false;
        
        for(String colorName : darkKeys)
        {
            Color inputColor = colorsMap.get(colorName);
            
            FontShade expected = FontShade.LIGHT;
        
            FontShade actual = implementation.predict(inputColor);

            if(actual != expected)
            {
                errorOccured = true;
                
                System.err.println("an error occured with: " + colorName);
            }
        }

        assertFalse(errorOccured);
    }
    
    @Test
    public void predict_givenBuiltInLightBackgrounds_expectDarkForeground() throws IllegalAccessException
    {
        Map<String, Color> colorsMap = Colors.list();
        
        List<String> lightKeys = colorsMap.keySet()
                .stream()
                .filter( s -> s.startsWith("LIGHT") )
                .sorted()
                .collect( Collectors.toList() );
        
        System.out.println("lightKeys:");
        lightKeys.forEach( System.out::println );

        
        FontShade expected = FontShade.DARK;
            
        boolean errorOccured = false;
        
        for(String colorName : lightKeys)
        {
            Color inputColor = colorsMap.get(colorName);
            
            FontShade actual = implementation.predict(inputColor);

            if(actual != expected)
            {
                errorOccured = true;
                
                System.err.println("a light shade error occured with: " + colorName);
            }
        }

        assertFalse(errorOccured);            
    }
    
    /**
     * Test of doubleArrayOf method, of class ColorPredictor.
     */
    @Test
    public void doubleArrayOf() 
    {
        Double[] input = {1.0, 2.0, 3.0};
                
        DoubleArray actual = ColorPredictor.doubleArrayOf(input);
        
        assertNotNull(actual);
        
        int expected = input.length;
        
        assertEquals(actual.data.size(), expected);
    }
    
}

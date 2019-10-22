
package org.onebeartoe.deep.learning.color.predictor;

import javafx.scene.paint.Color;

/**
 *
 * @author lando
 */
public class FormulaicPredictor 
{
    public static FontShade predict(Color color) 
    {
        double prediction = 1 - (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue() );        
//    override fun predict(color: Color) = (1 - (0.299 * color.red + 0.587 * color.green + 0.114 * color.blue))
//                .let { if (it < .5) FontShade.DARK else FontShade.LIGHT }
        return (prediction < .5) ? FontShade.DARK : FontShade.LIGHT;
    }
}

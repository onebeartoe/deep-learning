
package org.onebeartoe.deep.learning.color.predictor;

import javafx.scene.paint.Color;

/**
 *
 * @author lando
 */
 enum FontShade 
 {
    DARK(Color.BLACK, ColorPredictor.doubleArrayOf(0.0, 1.0)), 
    LIGHT(Color.WHITE, ColorPredictor.doubleArrayOf(1.0, 0.0));
    
    Color color;
    
    DoubleArray shade;

    private FontShade(Color color, DoubleArray shade)
    {
        this.color = color;
        this.shade = shade;
    }
}

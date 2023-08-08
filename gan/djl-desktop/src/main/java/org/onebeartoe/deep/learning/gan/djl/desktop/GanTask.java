
package org.onebeartoe.deep.learning.gan.djl.desktop;

import ai.djl.ModelException;
import ai.djl.examples.inference.BigGAN;
import ai.djl.modality.cv.Image;
import ai.djl.translate.TranslateException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;

/**
 * This class is used to perform work asynchronously from the GUI thread.
 * 
 * A great explanation of you to use javafx.concurrent.Task is in this video:
 * 
 *          https://www.youtube.com/watch?v=pdRX6CLP0tM&ab_channel=GenuineCoder
 * 
 */
public class GanTask extends Task<ImageView>
{
    private int selectedIndex;
    
    private BigGAN bigGan;
    
   private Logger logger;
    
    public GanTask(int selectedIndex, BigGAN bigGan)
    {
        this.selectedIndex = selectedIndex;
        
        this.bigGan = bigGan;
        
        logger = Logger.getLogger( getClass().getName() );
    }

    @Override
    protected ImageView call() throws Exception 
    {
                        
        logger.info("platform runlater start");
        ImageView imageView = oneImage(selectedIndex);

        imageView = oneImage(selectedIndex);

//TODO: is this second call needed/correct?        
        imageView = oneImage(selectedIndex);  //?!?!?!?!???? why is oneImage called twice in a row???
        logger.info("platform runlater end");        
        
        updateValue(imageView);
        
        return imageView;
    }


    ImageView oneImage(int selectedIndex) throws IOException, ModelException, TranslateException
    {
        Image someImage = bigGan.generate(selectedIndex);
        
        ImageView imageView = new ImageView();

        BufferedImage bufferedImage = (BufferedImage) someImage.getWrappedImage();

        javafx.scene.image.Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                
        imageView.setImage(image);

        return imageView;        
    }    
}


package org.onebeartoe.deep.learning.gan.djl.desktop;

import ai.djl.ModelException;
import ai.djl.examples.inference.BigGAN;
import ai.djl.modality.cv.Image;
import ai.djl.translate.TranslateException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
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
public class GanTask extends Task<List<ImageView>>
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
    protected List<ImageView> call() throws Exception 
    {
                        
        logger.info("platform runlater start");
        
        List<ImageView> viewList = new ArrayList();
        
        Stream.of(1,2,3)
                .forEach(n -> 
                {
                    
                    
            try 
            {
                ImageView imageView = oneImage(selectedIndex);
                
                viewList.add(imageView);
            } 
            catch (IOException ex) {
                Logger.getLogger(GanTask.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ModelException ex) {
                Logger.getLogger(GanTask.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TranslateException ex) {
                Logger.getLogger(GanTask.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
                    
                });

        logger.info("platform runlater end");        
        
//        updateValue(viewList);
        
        return viewList;
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

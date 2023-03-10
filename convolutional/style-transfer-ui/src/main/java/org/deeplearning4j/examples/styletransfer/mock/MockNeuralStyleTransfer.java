
package org.deeplearning4j.examples.styletransfer.mock;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Logger;
import org.deeplearning4j.examples.styletransfer.NeuralStyleTransfer;
import org.onebeartoe.application.logging.SysoutLoggerFactory;
import org.onebeartoe.deep.learning.style.transfer.ui.ImageIterationListener;
import org.onebeartoe.system.Sleeper;

/**
 * This class is used during GUI development to avoid long build/run times of the 
 * actual neural network.
 */
public class MockNeuralStyleTransfer implements NeuralStyleTransfer
{
    private ImageIterationListener imageListener;
    
    private boolean cancel;
    
    private Logger logger;

    public MockNeuralStyleTransfer()
    {
        logger = SysoutLoggerFactory.getLogger( getClass().getName() );
    }
    
    @Override
    public void transferStyle(String contentPath, String stylePath) throws FileNotFoundException
    {
        cancel = false;
        
        File imageFile = new File(contentPath);

        for(int i = 0; i < 3; i++)
//        for(int i = 0; i < 61; i++)
        {
            if(cancel)
            {
                logger.info("cancelling style transfer");
                
                break;
            }

            long fiveSeconds = 5 * 1000;

            Sleeper.sleepo(fiveSeconds);
            
            imageListener.imageCreated(imageFile);
        }
    }

    @Override
    public void addImageIterationListerner(ImageIterationListener imageListener)
    {
        this.imageListener = imageListener;
    }

    @Override
    public void cancel()
    {
        cancel = true;
    }    
}

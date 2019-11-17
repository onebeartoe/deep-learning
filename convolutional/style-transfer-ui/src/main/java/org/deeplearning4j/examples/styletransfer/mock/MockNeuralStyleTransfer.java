
package org.deeplearning4j.examples.styletransfer.mock;

import java.io.File;
import java.io.FileNotFoundException;
import org.onebeartoe.deep.learning.style.transfer.ui.ImageIterationListener;
import org.onebeartoe.system.Sleeper;

/**
 * This class is used during GUI development to avoid long build/run times of the 
 * actual neural network.
 */
public class MockNeuralStyleTransfer
{
    private ImageIterationListener imageListener;
    
    public void transferStyle(String contentPath, String stylePath) throws FileNotFoundException
    {
        File imageFile = new File(contentPath);

        for(int i = 0; i < 30; i++)
        {
            long oneMinute = 60 * 1000;
            Sleeper.sleepo(oneMinute);
            
            imageListener.imageCreated(imageFile);
        }
    }

    public void addImageIterationListerner(ImageIterationListener imageListener)
    {
        this.imageListener = imageListener;
    }
    
}

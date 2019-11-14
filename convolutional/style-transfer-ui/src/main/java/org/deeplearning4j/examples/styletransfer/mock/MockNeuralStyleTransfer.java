
package org.deeplearning4j.examples.styletransfer.mock;

import java.io.File;
import java.io.FileNotFoundException;
import org.onebeartoe.deep.learning.style.transfer.styel.transfer.ui.ImageIterationListener;

/**
 * This class is used during GUI development to avoid long build/run times for the 
 * actual neural network.
 */
public class MockNeuralStyleTransfer
{
    private ImageIterationListener imageListener;
    
    public void transferStyle(String contentPath, String stylePath) throws FileNotFoundException
    {
        File imageFile = new File(contentPath);

        imageListener.imageCreated(imageFile);
    }

    public void addImageIterationListerner(ImageIterationListener imageListener)
    {
        this.imageListener = imageListener;
    }
    
}

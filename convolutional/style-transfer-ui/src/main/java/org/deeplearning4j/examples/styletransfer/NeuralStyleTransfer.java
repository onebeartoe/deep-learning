
package org.deeplearning4j.examples.styletransfer;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.onebeartoe.deep.learning.style.transfer.ui.ImageIterationListener;

/**
 * This interfaces helps switch between the implementation and mock implementation
 * more easily during GUI development.
 */
public interface NeuralStyleTransfer
{
    void addImageIterationListerner(ImageIterationListener imageListener);
            
    void cancel();
    
    void transferStyle(String contentPath, String stylePath) throws FileNotFoundException, IOException;
}

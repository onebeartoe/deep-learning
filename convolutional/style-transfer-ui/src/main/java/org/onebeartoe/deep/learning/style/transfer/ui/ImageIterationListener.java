
package org.onebeartoe.deep.learning.style.transfer.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import org.onebeartoe.application.logging.SysoutLoggerFactory;

/**
 * This class receives messages about when an image is created.
 * 
 * @author Roberto Marquez
 */
public class ImageIterationListener
{
    private TilePane tilePane;
    
    private Logger logger;
    
    ImageIterationListener(TilePane tilePane)
    {
        logger = SysoutLoggerFactory.getLogger( getClass().getName() );

        this.tilePane = tilePane;
    }

    public void imageCreated(File imageFile) throws FileNotFoundException
    {
        InputStream inputStream = new FileInputStream(imageFile);
            
        Image image = new Image(inputStream, 50, 50, true, true);
            
        ImageView imageView = new ImageView();

        imageView.setImage(image);

        Task<Void> task = new Task<Void>() 
        {
            @Override 
            public Void call() throws Exception 
            {
                String message = "calling within task";
//                logger.info(message);

                updateMessage(message);

                return null ;
            }
        };                

        task.messageProperty().addListener((obs, oldMessage, newMessage) -> 
        {                
            

            tilePane.getChildren()
                    .add(imageView);
            
            logger.info("-+-task message: " + newMessage + "on FX thread: " + Platform.isFxApplicationThread() +"\n");
        });

        new Thread(task).start();
    }
}

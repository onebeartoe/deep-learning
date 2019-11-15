
package org.onebeartoe.deep.learning.style.transfer.styel.transfer.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.onebeartoe.application.logging.SysoutLoggerFactory;

/**
 * This class receives messages about when an image is created.
 * 
 * @author Roberto Marquez
 */
public class ImageIterationListener
{
    private GridPane gridPane;
    
    private final int COLUMNS = 10;
//    private final int COLUMNS = 6;

    private int currentColumn = 0;

    private int currentRow = 0;
    
    private Logger logger;
    
    ImageIterationListener(GridPane gridPane)
    {
        logger = SysoutLoggerFactory.getLogger( getClass().getName() );

        this.gridPane = gridPane;
    }

    public void imageCreated(File imageFile) throws FileNotFoundException
    {
        InputStream inputStream = new FileInputStream(imageFile);
            
        Image image = new Image(inputStream, 50, 50, true, true);
            
        ImageView imageView = new ImageView();

        imageView.setImage(image);

//        Platform.runLater( new Runnable()
//        {
//            @Override
//            public void run()
//            {
                logger.info("adding styled image to gridpane\n");

logger.info("at update, on FX thread: " + Platform.isFxApplicationThread() +"\n");                
                
                gridPane.add(imageView, currentColumn, currentRow);
                
                gridPane.layout();
//            }
//        });        
        
        currentColumn++;
        
        if(currentColumn == COLUMNS)
        {
            currentColumn = 0;
            
            currentRow++;
        }
    }
}

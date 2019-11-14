
package org.onebeartoe.deep.learning.style.transfer.styel.transfer.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * This class receives messages about when an image is created.
 * 
 * @author Roberto Marquez
 */
public class ImageIterationListener
{
    private GridPane gridPane;
    
    private final int COLUMNS = 3;

    private int currentColumn = 0;

    private int currentRow = 0;
    
    ImageIterationListener(GridPane gridPane)
    {
        this.gridPane = gridPane;
    }

    public void imageCreated(File imageFile) throws FileNotFoundException
    {
        InputStream inputStream = new FileInputStream(imageFile);
            
        Image image = new Image(inputStream, 50, 50, true, true);
            
        ImageView imageView = new ImageView();
        
        imageView.setImage(image);

        gridPane.add(imageView, currentColumn, currentRow);
        
        currentColumn++;
        
        if(currentColumn == COLUMNS)
        {
            currentColumn = 0;
            
            currentRow++;
        }
    }
}

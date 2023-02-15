package org.onebeartoe.deep.learning.gan.djl.desktop;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController 
{

    @FXML
    private void switchToSecondary() throws IOException 
    {
        System.out.println("switching to 2nd");
        
        App.setRoot("secondary");
    }
}

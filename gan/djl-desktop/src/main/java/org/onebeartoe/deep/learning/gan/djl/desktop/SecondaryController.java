package org.onebeartoe.deep.learning.gan.djl.desktop;

import java.io.IOException;
import javafx.fxml.FXML;

public class SecondaryController 
{
    @FXML
    private void switchToPrimary() throws IOException 
    {
        System.out.println("switching to 1st");
        
        App.setRoot("primary");
    }
}
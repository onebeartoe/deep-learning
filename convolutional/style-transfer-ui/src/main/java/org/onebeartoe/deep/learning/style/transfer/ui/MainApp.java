
//TODO: there is an other/newer version here 
//
//     https://github.com/deepjavalibrary/djl/blob/master/examples/src/main/java/ai/djl/examples/inference/StyleTransfer.java
//          This one looks like it only has 3 or 4 artists styles, though.


package org.onebeartoe.deep.learning.style.transfer.ui;

import java.net.URL;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application 
{
    @Override
    public void start(Stage stage) throws Exception 
    {
        URL url = getClass().getResource("/fxml/Scene.fxml");
        
        FXMLLoader loader = new FXMLLoader(url);
        
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("Style Transfer with Convolutional Neural Networks");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
}

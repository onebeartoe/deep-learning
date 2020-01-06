
package org.onebeartoe.deep.learning.interview;

import java.net.URL;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application 
{
    @Override
    public void start(Stage stage) throws Exception 
    {
        HostServices hostServices = getHostServices();
//it looks like this can open direcotories or files        
//        hostServices.showDocument("/home/roberto/Desktop/");
        
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

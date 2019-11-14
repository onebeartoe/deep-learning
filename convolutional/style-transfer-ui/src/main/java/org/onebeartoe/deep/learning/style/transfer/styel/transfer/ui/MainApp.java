//TODO: correct this package name
package org.onebeartoe.deep.learning.style.transfer.styel.transfer.ui;

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
        
//        Parent root = FXMLLoader.load(getClass()
//                        .getResource("/fxml/Scene.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("Style Transfer with Convolutional Neural Networks");
        stage.setScene(scene);
        stage.show();

//        FXMLController controller = loader.getController();
//        controller.stage = stage;
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

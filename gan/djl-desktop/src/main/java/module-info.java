module org.onebeartoe.deep.learning.gan.djl.desktop 
{
    requires ai.djl.api;
    
    requires gandjl;

    requires java.desktop;
    
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    opens org.onebeartoe.deep.learning.gan.djl.desktop to javafx.fxml;
    
    exports org.onebeartoe.deep.learning.gan.djl.desktop;
}

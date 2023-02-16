module org.onebeartoe.deep.learning.gan.djl.desktop 
{
    requires gandjl;

    requires javafx.controls;
    requires javafx.fxml;
    
//    opens org.onebeartoe.deep.learning.gan.djl.desktop to qandjl;
    opens org.onebeartoe.deep.learning.gan.djl.desktop to javafx.fxml;
    
    exports org.onebeartoe.deep.learning.gan.djl.desktop;
}

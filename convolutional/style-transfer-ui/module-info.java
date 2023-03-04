module org.onebeartoe.deep.learning.convolutional.style.transfer.desktop
{    
    requires java.desktop;
    requires java.logging;
   
    requires javafx.controls;
    requires javafx.fxml;
    
    requires deeplearning4j.common;
    
    requires datavec.data.image;
    
    opens org.onebeartoe.deep.learning.style.transfer.ui to javafx.fxml;
    
    exports org.onebeartoe.deep.learning.style.transfer.ui;
}

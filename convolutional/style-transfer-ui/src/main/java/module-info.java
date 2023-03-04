module org.onebeartoe.deep.learning.convolutional.style.transfer.desktop
{    
    requires java.desktop;
    requires java.logging;
   
    requires javafx.controls;
    requires javafx.fxml;
    
    requires datavec.data.image;
    
    requires deeplearning4j.common;
    
    opens org.onebeartoe.deep.learning.style.transfer.ui to javafx.fxml;
    
    exports org.onebeartoe.deep.learning.style.transfer.ui;
}

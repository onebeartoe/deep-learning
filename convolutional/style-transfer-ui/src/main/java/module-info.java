module org.onebeartoe.deep.learning.convolutional.style.transfer.desktop
{    
    requires java.desktop;
    
    requires java.logging;
   
    requires javafx.controls;
    requires javafx.fxml;
    
    requires datavec.data.image;
    
    requires deeplearning4j.common;

    // you can find the 'requires' for a non-Java-module JAR by issusing this command:
    //      $ jar --file=slf4j-api-1.7.36.jar --describe-module
    // Then you copy and paste whatever is before the '@' symbol.    
    
    opens org.onebeartoe.deep.learning.style.transfer.ui to javafx.fxml;
    
    exports org.onebeartoe.deep.learning.style.transfer.ui;
}

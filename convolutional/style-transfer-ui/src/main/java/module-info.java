module org.onebeartoe.deep.learning.convolutional.style.transfer.desktop
{
//    requires ai.djl.api;
    
//TODO: update the name of this 'requires' to org.onebeartoe.deep.learning.gan.djl
//    requires gandjl;

    requires java.desktop;
    
    requires java.logging;
   
//    requires java.*;
    
    requires javafx.controls;
    requires javafx.fxml;
//    requires javafx.swing;

    
    requires datavec.data.image;
    
    requires deeplearning4j.common;
//    requires deeplearning4j.nn;
//    requires deeplearning4j.util;
    
    // you can find the 'requires' for a non-Java-module JAR by issusing this command:
    //      $ jar --file=slf4j-api-1.7.36.jar --describe-module
    // Then you copy and paste whatever is before the '@' symbol.    
    
//    opens org.onebeartoe.deep.learning.style.transfer.ui to nd4j.api;
  //  opens org.onebeartoe.deep.learning.style.transfer.ui to deeplearning4j.nn;
    
    opens org.onebeartoe.deep.learning.style.transfer.ui to javafx.fxml;
    
    exports org.onebeartoe.deep.learning.style.transfer.ui;
}


//TODO: make this an fully qualified module( with package) name
module gandjl
{
    requires java.base;
    requires ai.djl.api;
    
    // you can find the 'requires' for a non-Java-module JAR by issusing this command:
    //      $ jar --file=slf4j-api-1.7.36.jar --describe-module
    // Then you copy and paste whatever is before the '@' symbol.
    requires  org.slf4j;
    
    exports ai.djl.examples.inference;
}
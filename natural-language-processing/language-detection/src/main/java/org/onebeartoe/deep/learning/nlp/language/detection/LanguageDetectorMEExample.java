
// This an example program provided by the tutorialkart.com
//
//      https://www.tutorialkart.com/opennlp/language-detector-example-in-apache-opennlp/

package org.onebeartoe.deep.learning.nlp.language.detection;

import java.io.*;
import opennlp.tools.langdetect.*;
import opennlp.tools.util.*;
/**
* Language Detector Example in Apache OpenNLP
*/
public class LanguageDetectorMEExample {
    private static LanguageDetectorModel model;
    public static void main(String[] args){
        // loading the training data to LanguageDetectorSampleStream
        LanguageDetectorSampleStream sampleStream = null;
        try 
        {
            File infile = new File("src/main/resources/training-data/" + "DoccatSample.txt");
                    
            InputStreamFactory dataIn = new MarkableFileInputStreamFactory( infile );
            ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
            sampleStream = new LanguageDetectorSampleStream(lineStream);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // training parameters
        TrainingParameters params = new TrainingParameters();
        params.put(TrainingParameters.ITERATIONS_PARAM, 100);
        params.put(TrainingParameters.CUTOFF_PARAM, 5);
        params.put("DataIndexer", "TwoPass");
        params.put(TrainingParameters.ALGORITHM_PARAM, "NAIVEBAYES");
        // train the model
        try {
            model = LanguageDetectorME.train(sampleStream, params, new LanguageDetectorFactory());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Completed");
        // load the model
        LanguageDetector ld = new LanguageDetectorME(model);
        // use model for predicting the language
        
        String textBlurb = "estava em uma marcenaria na Rua Bruno";
        
        textBlurb = "my name is Bob";
        
        Language[] languages = ld.predictLanguages(textBlurb);
        System.out.println("Predicted languages..");
        for(Language language:languages){
            // printing the language and the confidence score for the test data to belong to the language
            System.out.println(language.getLang()+"  confidence:"+language.getConfidence());
        }
    }
}

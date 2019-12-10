
package org.onebeartoe.deep.learning.recurrent.neural.network.sentiment;

import java.io.IOException;
import java.util.Arrays;
import opennlp.tools.doccat.BagOfWordsFeatureGenerator;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.doccat.FeatureGenerator;
import opennlp.tools.doccat.NGramFeatureGenerator;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

/**
 * This class provides sentiment service methods via a deep learning recurrent 
 * neural network.
 */
public class SentimentService
{
    private DoccatModel model;

    public SentimentService() throws IOException
    {
        String modelClasspath = "/tweets.text";
                 
        InputStreamFactory inputStreamFactory = () ->
        {
            return getClass().getResourceAsStream(modelClasspath);
        };

        ObjectStream lineStream = new PlainTextByLineStream(inputStreamFactory, "UTF-8");
        
        ObjectStream sampleStream = new DocumentSampleStream(lineStream);
        
        // Specifies the minimum number of times a feature must be seen
        int cutoff = 2;
        
        int trainingIterations = 30;
        
//TODO: is ther a better FeatureGenerator to use besides BagOfWords???????        
        FeatureGenerator[] def = { new BagOfWordsFeatureGenerator() };
//        FeatureGenerator[] def = { new NGramFeatureGenerator() };

//	WhitespaceTokenizer tokenizer = WhitespaceTokenizer.INSTANCE;

	DoccatFactory factory = new DoccatFactory(def);
  //	DoccatFactory factory = new DoccatFactory(tokenizer, def);
        
        TrainingParameters parameters = new TrainingParameters();//.defaultParams();
        parameters.put(TrainingParameters.CUTOFF_PARAM, cutoff);
        parameters.put(TrainingParameters.ITERATIONS_PARAM, trainingIterations);
        
        model = DocumentCategorizerME.train("en", sampleStream, 
//                                            cutoff, trainingIterations
                                            parameters, factory
                                            );
    }
    
    /**
     * @param text - this is the text to classify sentiment
     * @return sentiment classification of the text; 1 is positive and 0 is negative sentiment
     */
    public SentimentClassification classify(String text)
    {
        System.out.println("text = " + text);

        DocumentCategorizerME categorizer = new DocumentCategorizerME(model);
        
        String [] tokens = text.trim().split("\\s+");
        
        double[] outcomes = categorizer.categorize(tokens);
        
        SentimentClassification classification = new SentimentClassification();
        
        classification.setNegative( outcomes[1] );
        classification.setPositive( outcomes[0] );

        System.out.println("outcomes = " + Arrays.toString(outcomes) );

        int numberOfCategories = categorizer.getNumberOfCategories();
        
        System.out.println("numberOfCategories = " + numberOfCategories);

        System.out.print("categories: ");
        for(int c = 0; c < numberOfCategories; c++)
        {
            System.out.print(categorizer.getCategory(c) + " - ");
        }
        System.out.println();
        
        String category = categorizer.getBestCategory(outcomes);

        int categoryAsInt = Integer.valueOf(category);
        
        classification.setCategory(categoryAsInt);
        
        System.out.println("category = " + category);
        
        if (category.equalsIgnoreCase("1")) 
        {
            System.out.println("The tweet is positive :) ");
        } 
        else 
        {
            System.out.println("The tweet is negative :( ");
        }
        
        System.out.println("");

        return classification;
    }
}


package org.onebeartoe.deep.learning.color.predictor;

import com.google.common.io.Files;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.scene.paint.Color;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;

import org.nd4j.linalg.learning.config.Nesterovs;

import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;

import org.nd4j.linalg.factory.Nd4j;

/**
 * This class trains a feed forward neural network
 * to create text color predictor
 *  given rgb predict if light text or dark text should be used on top 
 * of the rgb color.
 */
public class ColorPredictor
{
    private boolean printPrediction;

    private MultiLayerNetwork model = null;

    public void working()
    {
//TODO: enable this
//        var w = 3;
        
        
    }
    

    public ColorPredictor() throws URISyntaxException, IOException
    {
        printPrediction = false;
        
        MultiLayerConfiguration dl4jNN = 
//        NeuralNetConfiguration dl4jNN = 
                new NeuralNetConfiguration.Builder()
                .weightInit(WeightInit.UNIFORM)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater( new Nesterovs(.006, .9) )
                .l2(1e-4)
                .list(
                        new DenseLayer.Builder().nIn(3).nOut(3).activation(Activation.RELU).build(),
                        new OutputLayer.Builder().nIn(3).nOut(2).activation(Activation.SOFTMAX).build()
                ).pretrain(false)
                .backprop(true)
                .build();

                model = new MultiLayerNetwork(dl4jNN);
                model.init() ;
//                .let(::MultiLayerNetwork).apply { init() }

                

//    val inputs = FXCollections.observableArrayList<CategorizedInput>()
    List<CategorizedInput> inputs = FXCollections.observableArrayList();
    
    URL resource = getClass().getResource("/color_training_set.csv");    
    Path inpath = Paths.get(resource.toURI() );
    List<String> readLines = Files.readLines(inpath.toFile(), Charset.defaultCharset());
    readLines.stream()
            .map( l -> l.split(",") )
            .map( s -> 
            {
                Integer [] ds = {Integer.valueOf(s[0]),
                                       Integer.valueOf(s[1]),
                                       Integer.valueOf(s[2])};
            
                return ds;    
            })
            .map(it -> Color.rgb(it[0], it[1], it[2])  )
            .map( it -> 
            { 
                CategorizedInput ci = new CategorizedInput();
                ci.color = it;
                ci.fontShade = FormulaicPredictor.predict(it);  
                
                return ci;
            })
            .collect( Collectors.toList() )
            .forEach(i -> inputs.add(i) );
//public List<CategorizedInput> inputs = FXCollections.observableArrayList();
//    fun preTrainData() {
//        PredictorModel::class.java.getResource("color_training_set.csv").readText().lines()
//                .asSequence()
//                .map { s -> s.split(",").map { it.toInt() } }
//                .map { Color.rgb(it[0], it[1], it[2]) }
//                .map { CategorizedInput(it, Predictor.FORMULAIC.predict(it))  }
//                .toList()
//                .forEach {
//                    inputs += it
//                }
//    }


List<INDArray> exampleList = new ArrayList();
inputs.forEach(input ->
{
    INDArray row = Nd4j.create(colorAttributes(input.color).data);
    
    exampleList.add(row);
});
int[] shape = { exampleList.size(), 3 };
        INDArray examples =  Nd4j.create(exampleList, shape);
//        val examples = inputs.asSequence()
//                .map { colorAttributes(it.color) }
//                .toList().toTypedArray()
//                .let { Nd4j.create(it) }
                
                
List<INDArray> outcomesList = new ArrayList();
inputs.forEach(input ->
{
    INDArray row = Nd4j.create(input.fontShade.shade.data);
    
    outcomesList.add(row);
});
int [] outcomesShape = {outcomesList.size(), 2};
        INDArray outcomes = Nd4j.create(outcomesList, outcomesShape);
//        val outcomes = inputs.asSequence()
//                .map { it.fontShade.outputValue }
//                .toList().toTypedArray()
//                .let { Nd4j.create(it) }


                

        // train for 1000 iterations (epochs)
        for(int e = 0; e < 1000; e++)        
//        repeat(1000) 
        {
            model.fit(examples, outcomes);
//            dl4jNN.fit(examples, outcomes)
        }
        
    }

//        override fun predict(color: Color): FontShade {    
    public FontShade predict(Color color)
    {       
        // Test the input color and predict it as LIGHT or DARK
        double [] result = 
                model.output(
                        Nd4j.create( colorAttributes(color).data          )
                ).toDoubleVector();
//        val result = dl4jNN.output(Nd4j.create(colorAttributes(color))).toDoubleVector()



        if(printPrediction)
        {
            System.out.print("result = ");// +  result.toString() );    
            for(Double d : result)
            {
                System.out.print(d + " - ");
            }
            System.out.println();
        }
                
                


        return  (result[0] > result[1]) ? FontShade.LIGHT : FontShade.DARK;
//        return if (result[0] > result[1]) FontShade.LIGHT else FontShade.DARK
        
    }
        
    
    public static DoubleArray doubleArrayOf(Double ... d)
//    private static DoubleArray doubleArrayOf(Double ... d)
    {
        DoubleArray array = new DoubleArray();
        
        array.addAll( Arrays.asList(d) );
        
        return array;
    }
    
    private DoubleArray colorAttributes(Color c)
//    private static DoubleArray colorAttributes(Color c)
    {
        return doubleArrayOf(
            c.getRed(),
            c.getGreen(),
            c.getBlue()
        );
    }


    class CategorizedInput
    {
        Color color;
        
        FontShade fontShade;
    }
}
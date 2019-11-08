
package org.onebeartoe.deep.learning.regression.linear.house.prices.hackerrank;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.encog.ConsoleStatusReportable;
import org.encog.ml.MLRegression;
import org.encog.ml.data.MLData;
import org.encog.ml.data.versatile.NormalizationHelper;
import org.encog.ml.data.versatile.VersatileMLDataSet;
import org.encog.ml.data.versatile.columns.ColumnDefinition;
import org.encog.ml.data.versatile.columns.ColumnType;
import org.encog.ml.data.versatile.sources.CSVDataSource;
import org.encog.ml.data.versatile.sources.VersatileDataSource;
import org.encog.ml.factory.MLMethodFactory;
import org.encog.ml.model.EncogModel;
import org.encog.util.csv.CSVFormat;
import org.onebeartoe.deep.learning.encog.MapResults;
import org.onebeartoe.deep.learning.encog.NormalizedModel;
import org.onebeartoe.deep.learning.encog.RegressionArtifacts;

/**
 * This class implements the solution to the house prices challenge presented at this
 * URL using the Encog machine learning framework.:
 * 
 *      https://www.hackerrank.com/challenges/predicting-house-prices/
 *
 * Call Encog.getInstance().shutdown() once done with this class.
 */
public class HackerRankHousePricesRegession
{
    private NormalizationHelper displayResults(NormalizedModel model, MLRegression bestMethod) 
    {
        // Display the training and validation errors.
        System.out.println( "Training error: " + model.model.calculateError(bestMethod, model.model.getTrainingDataset()));
        System.out.println( "Validation error: " + model.model.calculateError(bestMethod, model.model.getValidationDataset()));

        // Display our normalization parameters.
        NormalizationHelper helper = model.dataset.getNormHelper();
        System.out.println(helper.toString());

        // Display the final model.
        System.out.println("Final model: " + bestMethod);
        
        return helper;        
    }

    private MLRegression fitModel(NormalizedModel normalizedModel)
    {
        EncogModel model = normalizedModel.model;
        
        VersatileMLDataSet dataset = normalizedModel.dataset;
        
        // Hold back some data for a final validation.
        // Shuffle the data into a random ordering.
        // Use a seed of 1001 so that we always use the same holdback and will get more consistent results.
        model.holdBackValidation(0.2, true, 1001);
//        model.holdBackValidation(0.3, true, 1001);

        // Choose whatever is the default training type for this model.
        model.selectTrainingType(dataset);

        // Use a 5-fold cross-validated train.  Return the best method found.
        MLRegression bestMethod = (MLRegression)model.crossvalidate(5, true);

        return bestMethod;
    }
    private MapResults mapInputFile() throws URISyntaxException 
    {
        URL resource = getClass().getResource("/hacker-rank/training-house-prices.csv");
        
        Path inpath = Paths.get( resource.toURI() );

        File infile = inpath.toFile();

        boolean containsHeader = false;
        
        CSVFormat format = new CSVFormat('.',' '); // decimal point and space separated
        VersatileDataSource source = new CSVDataSource(infile, containsHeader, format);
        
        VersatileMLDataSet data = new VersatileMLDataSet(source);
        data.getNormHelper().setFormat(format);

        data.defineSourceColumn("feature 0", 0, ColumnType.continuous);
                
        data.defineSourceColumn("feature 1", 1, ColumnType.continuous);
        
        ColumnDefinition priceColumn = data.defineSourceColumn("price", 2, ColumnType.continuous);
        
        // Analyze the data, determine the min/max/mean/sd of every column.
        data.analyze();

        MapResults results = new MapResults();
        
        results.dataset = data;
        
        results.output = priceColumn;
        
        results.artifacts.infile = infile;
        
        results.artifacts.format = format;
        
        results.artifacts.containsHeader = containsHeader;
        
        return results;        
    }

    double predict(RegressionArtifacts artifacts, House house)
    {
        String[] line = house.toArray();
        
        MLData input = artifacts.helper.allocateInputVector();

        artifacts.helper.normalizeInputVector(line,input.getData(),false);

        MLData output = artifacts.bestMethod.compute(input);

        String predictedPrice = artifacts.helper.denormalizeOutputVectorToString(output)[0];

        return Double.valueOf(predictedPrice);        
    }

    private NormalizedModel specifyModelAndNormalize(MapResults results)
    {
        ColumnDefinition output = results.output;
        
        VersatileMLDataSet dataset = results.dataset;        
        
        // Map the prediction column to the output of the model, and all
        // other columns to the input.
        dataset.defineSingleOutputOthersInput(output);
        
        EncogModel model = new EncogModel(dataset);

//        model.selectMethod(dataset, MLMethodFactory.TYPE_SVM); // two fail  *
//        model.selectMethod(dataset, MLMethodFactory.TYPE_SOM); // errors with 'autoconfig error'
//        model.selectMethod(dataset, MLMethodFactory.TYPE_RBFNETWORK); // two fail  *
//        model.selectMethod(dataset, MLMethodFactory.TYPE_PNN); // null pointer error
        model.selectMethod(dataset, MLMethodFactory.TYPE_NEAT); // they all fail
//        model.selectMethod(dataset, MLMethodFactory.TYPE_EPL); // errors with 'autoconfig error'
//        model.selectMethod(dataset, MLMethodFactory.TYPE_BAYESIAN); // errors with 'autoconfig error'
//        model.selectMethod(dataset, MLMethodFactory.TYPE_FEEDFORWARD); // original *

        // Send any output to the console.
        model.setReport(new ConsoleStatusReportable());

        // Now normalize the data.  Encog will automatically determine the correct normalization
        // type based on the model you chose in the last step.
        dataset.normalize();

        NormalizedModel normalizedModel = new NormalizedModel();
        
        normalizedModel.dataset = dataset;
        
        normalizedModel.model = model;
        
        return normalizedModel;        
    }

    public RegressionArtifacts trainAndTest() throws URISyntaxException
    {
        MapResults results = mapInputFile();

        RegressionArtifacts artifacts = results.artifacts;
        
        NormalizedModel model = specifyModelAndNormalize(results);
        
        MLRegression regression = fitModel(model);
        
        artifacts.helper = displayResults(model, regression);
        
        artifacts.bestMethod = regression;
        
        return artifacts;
    }
}

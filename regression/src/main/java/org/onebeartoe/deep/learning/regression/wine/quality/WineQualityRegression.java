
package org.onebeartoe.deep.learning.regression.wine.quality;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.encog.ConsoleStatusReportable;
import org.encog.Encog;
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
import org.encog.util.csv.ReadCSV;

import org.onebeartoe.deep.learning.encog.MapResults;
import org.onebeartoe.deep.learning.encog.NormalizedModel;
import org.onebeartoe.deep.learning.encog.RegressionArtifacts;
import static org.onebeartoe.deep.learning.regression.wine.quality.Wine.FEATURE_COUNT;

/**
 * This class provides a solution to the wine quality challenge using supervised 
 * learning via a regression machine learning model.
 * 
 * The challenge is originally found here:
 * 
 *      https://elitedatascience.com/python-machine-learning-tutorial-scikit-learn
 * 
 */
public class WineQualityRegression
{    
    public static void main(String [] args) throws URISyntaxException
    {
        WineQualityRegression regression = new WineQualityRegression();
        
        RegressionArtifacts artifacts = regression.trainAndTest();
        
        regression.useModelAndNormalize(artifacts);
        
        Encog.getInstance().shutdown();
    }

    private MapResults mapInputFile() throws URISyntaxException
    {
        URL resource = getClass().getResource("/winequality-red.csv");
        Path inpath = Paths.get( resource.toURI() );
        File infile = inpath.toFile();

        boolean containsHeader = true;
        
        CSVFormat format = new CSVFormat('.',';'); // decimal point and semicolon separated
        VersatileDataSource source = new CSVDataSource(infile, containsHeader, format);

        VersatileMLDataSet data = new VersatileMLDataSet(source);
        data.getNormHelper().setFormat(format);

        data.defineSourceColumn("fixed acidity", 0, ColumnType.continuous);
                
        data.defineSourceColumn("volatile acidity", 1, ColumnType.continuous);

        data.defineSourceColumn("citric acid", 2, ColumnType.continuous);

        data.defineSourceColumn("residual sugar", 3, ColumnType.continuous);

        data.defineSourceColumn("chlorides", 4, ColumnType.continuous);

        data.defineSourceColumn("free sulfur dioxide", 5, ColumnType.continuous);

        data.defineSourceColumn("total sulfur dioxide", 6, ColumnType.continuous);

        data.defineSourceColumn("density", 7, ColumnType.continuous);

        data.defineSourceColumn("pH", 8, ColumnType.continuous);

        data.defineSourceColumn("sulphates", 9, ColumnType.continuous);

        data.defineSourceColumn("alcohol", 10, ColumnType.continuous);

        ColumnDefinition qualityColumn = data.defineSourceColumn("quality", 11, ColumnType.continuous);

        // Analyze the data, determine the min/max/mean/sd of every column.
        data.analyze();

        MapResults results = new MapResults();
        
        results.dataset = data;
        
        results.output = qualityColumn;
        
        results.artifacts.infile = infile;
        
        results.artifacts.format = format;
        
        results.artifacts.containsHeader = containsHeader;
        
        return results;
    }

    public double predict(RegressionArtifacts artifacts, Wine wine) 
    {
        String[] line = wine.toArray();

        MLData input = artifacts.helper.allocateInputVector();

        artifacts.helper.normalizeInputVector(line,input.getData(),false);

        MLData output = artifacts.bestMethod.compute(input);

        String predictedQuality = artifacts.helper.denormalizeOutputVectorToString(output)[0];

        return Double.valueOf(predictedQuality);
    }
    
    public RegressionArtifacts trainAndTest() throws URISyntaxException 
    {        
        MapResults results = mapInputFile();

        RegressionArtifacts artifacts = results.artifacts;
        
        NormalizedModel model = specifyModelAndNormalize(results);
        
        double holdBack = 0.3;

        MLRegression regression = model.fitModel(holdBack);
        
        artifacts.helper = model.displayResults(regression);
        
        artifacts.bestMethod = regression;
        
        return artifacts;
    }

    private NormalizedModel specifyModelAndNormalize(MapResults results)
    {
        ColumnDefinition output = results.output;
        
        VersatileMLDataSet dataset = results.dataset;        
        
        // Map the prediction column to the output of the model, and all
        // other columns to the input.
        dataset.defineSingleOutputOthersInput(output);
        
        EncogModel model = new EncogModel(dataset);
        model.selectMethod(dataset, MLMethodFactory.TYPE_FEEDFORWARD);

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

    private void useModelAndNormalize(RegressionArtifacts artifacts)
    {
        File infile = artifacts.infile;
        
        CSVFormat format = artifacts.format;
        
        NormalizationHelper helper = artifacts.helper;
        
        MLRegression bestMethod = artifacts.bestMethod;
        
        boolean containsHeader = artifacts.containsHeader;
        
        // Loop over the entire, original, dataset and feed it through the model.
        // This also shows how you would process new data, that was not part of your
        // training set.  You do not need to retrain, simply use the NormalizationHelper
        // class.  After you train, you can save the NormalizationHelper to later
        // normalize and denormalize your data.
        ReadCSV csv = new ReadCSV(infile, containsHeader, format);
        String[] line = new String[FEATURE_COUNT];
        MLData input = helper.allocateInputVector();

        while(csv.next()) 
        {
            StringBuilder result = new StringBuilder();

            line[0] = csv.get(0);
            line[1] = csv.get(1);
            line[2] = csv.get(2);
            line[3] = csv.get(3);
            line[4] = csv.get(4);
            line[5] = csv.get(5);
            line[6] = csv.get(6);
            line[7] = csv.get(7);
            line[8] = csv.get(8);
            line[9] = csv.get(9);
            line[10] = csv.get(10);

            String correct = csv.get(11);
            helper.normalizeInputVector(line,input.getData(),false);
            MLData output = bestMethod.compute(input);
            String predictedQuality = helper.denormalizeOutputVectorToString(output)[0];

            result.append(Arrays.toString(line));
            result.append(" -> predicted: ");
            result.append(predictedQuality);
            result.append("(correct: ");
            result.append(correct);
            result.append(")");

            System.out.println(result.toString());
        }
    }
}

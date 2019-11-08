
package org.onebeartoe.deep.learning.encog;

import org.encog.ml.MLRegression;
import org.encog.ml.data.versatile.NormalizationHelper;
import org.encog.ml.data.versatile.VersatileMLDataSet;
import org.encog.ml.model.EncogModel;


public class NormalizedModel
{
    public EncogModel model;

    public VersatileMLDataSet dataset;

    public NormalizationHelper displayResults(MLRegression bestMethod)
    {
        // Display the training and validation errors.
        System.out.println( "Training error: " + model.calculateError(bestMethod, model.getTrainingDataset()));
        System.out.println( "Validation error: " + model.calculateError(bestMethod, model.getValidationDataset()));

        // Display our normalization parameters.
        NormalizationHelper helper = dataset.getNormHelper();
        System.out.println(helper.toString());

        // Display the final model.
        System.out.println("Final model: " + bestMethod);
        
        return helper;
    }

    public MLRegression fitModel(double holdBack)
    {        
        // Hold back some data for a final validation.
        // Shuffle the data into a random ordering.
        // Use a seed of 1001 so that we always use the same holdback and will get more consistent results.
        model.holdBackValidation(holdBack, true, 1001);

        // Choose whatever is the default training type for this model.
        model.selectTrainingType(dataset);

        // Use a 5-fold cross-validated train.  Return the best method found.
        MLRegression bestMethod = (MLRegression)model.crossvalidate(5, true);

        return bestMethod;
    }
}

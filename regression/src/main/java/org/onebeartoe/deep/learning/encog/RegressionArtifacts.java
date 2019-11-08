package org.onebeartoe.deep.learning.encog;


import java.io.File;
import org.encog.ml.MLRegression;
import org.encog.ml.data.versatile.NormalizationHelper;
import org.encog.util.csv.CSVFormat;

public class RegressionArtifacts
{
    public File infile;

    public boolean containsHeader;

    public CSVFormat format;

    public NormalizationHelper helper;

    public MLRegression bestMethod;
}

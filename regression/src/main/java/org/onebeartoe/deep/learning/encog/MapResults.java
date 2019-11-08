package org.onebeartoe.deep.learning.encog;


import org.encog.ml.data.versatile.VersatileMLDataSet;
import org.encog.ml.data.versatile.columns.ColumnDefinition;

public class MapResults
{
    public ColumnDefinition output;

    public VersatileMLDataSet dataset;

    public RegressionArtifacts artifacts;

    public MapResults()
    {
        artifacts = new RegressionArtifacts();
    }
}

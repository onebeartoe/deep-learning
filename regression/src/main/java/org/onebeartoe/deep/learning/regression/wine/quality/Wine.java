
package org.onebeartoe.deep.learning.regression.wine.quality;

/**
 * This class is an abstraction of wine with the properties found in the 
 * 'winequality-red.csv' data file.
 */
public class Wine 
{
    public static final int FEATURE_COUNT = 12;

    double fixedAcidity;
    double volatileAcidity;
    double citricAcid;
    double residualSugar;
    double chlorides;
    double freeSulfurDioxide;
    double totalSulfurDioxide;
    double density;
    double pH;
    double sulphates;
    double alcohol;
    double quality;
    
    /**
     * See 'winequality-red.metrics' for an idea of how the value impact the 
     * value of the quality.
     * 
     * @param values
     * @return 
     */
    public static  Wine fromArray(double [] values)
    {
        Wine wine = new Wine();
        
        wine.fixedAcidity = values[0];
        wine.volatileAcidity = values[1];
        wine.citricAcid = values[2];
        wine.residualSugar = values[3];
        wine.chlorides = values[4];
        wine.freeSulfurDioxide = values[5];
        wine.totalSulfurDioxide = values[6];
        wine.density = values[7];
        wine.pH = values[8];
        wine.sulphates = values[9];
        wine.alcohol = values[10];
        wine.quality = values[11];        
        
        return wine;
    }
    
    public String [] toArray()
    {
        String[] line = new String[FEATURE_COUNT];
        
        line[0] = String.valueOf(fixedAcidity);
        line[1] = String.valueOf(volatileAcidity);
        line[2] = String.valueOf(citricAcid);
        line[3] = String.valueOf(residualSugar);
        line[4] = String.valueOf(chlorides);
        line[5] = String.valueOf(freeSulfurDioxide);
        line[6] = String.valueOf(totalSulfurDioxide);
        line[7] = String.valueOf(density);
        line[8] = String.valueOf(pH);
        line[9] = String.valueOf(sulphates);
        line[10] = String.valueOf(alcohol);
        line[11] = String.valueOf(quality);
        
        return line;
    }
}

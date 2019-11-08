
package org.onebeartoe.deep.learning.regression.linear.house.prices.hackerrank;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.encog.ml.data.MLData;
import org.encog.ml.data.versatile.NormalizationHelper;
import org.encog.util.csv.CSVFormat;
import org.encog.util.csv.ReadCSV;
import org.onebeartoe.deep.learning.encog.RegressionArtifacts;

import static org.onebeartoe.deep.learning.regression.wine.quality.Wine.FEATURE_COUNT;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * 
 */
public class HackerRankHousePricesRegessionSpecification
{
    private HackerRankHousePricesRegession implementation;
    
    private RegressionArtifacts artifacts;
    
    @BeforeClass
    public void initialize() throws Exception 
    {
        implementation = new HackerRankHousePricesRegession();
        
        artifacts = implementation.trainAndTest();        
    }
    
    @DataProvider(name="testHousePrices")
    public Object [][] testHousePrices() throws URISyntaxException
    {
        URL resource = getClass().getResource("/hacker-rank/testing-house-prices.csv");
        
        Path inpath = Paths.get( resource.toURI() );

        File infile = inpath.toFile();

        boolean containsHeader = artifacts.containsHeader;
        
        CSVFormat format = artifacts.format;
        
        NormalizationHelper helper = artifacts.helper;
        
        ReadCSV csv = new ReadCSV(infile, containsHeader, format);

        String[] line = new String[FEATURE_COUNT];

//TODO: do we need this?
        MLData input = helper.allocateInputVector();

        List<House> houseList = new ArrayList();
        
        while(csv.next()) 
        {
//            StringBuilder result = new StringBuilder();

            line[0] = csv.get(0);
            line[1] = csv.get(1);
            line[2] = csv.get(2);
            
            House house = House.fromLine(line);
            
            houseList.add(house);
        }
        
        int rowCount = houseList.size();

        int PARAMETER_COUNT = 1;

        Object[][] data = new Object[rowCount][PARAMETER_COUNT];
        
        for(int i = 0; i < rowCount; i++)
        {
            data[i][0] = houseList.get(i);
        }
        
        return data;
    }

    /**
     * This test allows a $20 plus or minus tolerance.
     * 
     * @param house 
     */
    @Test(dataProvider = "testHousePrices")
    public void predict(House house)
    {
        double price = implementation.predict(artifacts, house);

        assertTrue( withinTolerance(house.price, price) );
    }

    private boolean withinTolerance(double expected, double actual)
    {
        int tolerance = 20;
//        int tolerance = 10;
        
        System.out.println("tolerance = " + tolerance + " -> expected: " + expected + " - actual: " + actual);
        
        double low = expected - tolerance;
        
        double high = expected + tolerance;
        
        return low < actual && actual < high;
    }
}

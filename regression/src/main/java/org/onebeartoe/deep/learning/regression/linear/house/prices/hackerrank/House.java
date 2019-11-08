
package org.onebeartoe.deep.learning.regression.linear.house.prices.hackerrank;

/**
 * This a record class to hold house properties.
 */
class House
{
    public static final int FEATURE_COUNT = 3;

    double feature0;
    
    double feature1;
    
    double price;

    static House fromLine(String [] values)
    {
        House house = new House();
        
        house.feature0 = Double.parseDouble(values[0]);
        
        house.feature1 = Double.parseDouble(values[1]);
        
        house.price = Double.parseDouble(values[2]);
        
        return house;
    }
    
    String[] toArray()
    {
        String[] line = new String[FEATURE_COUNT];
        
        line[0] = String.valueOf(feature0);
        line[1] = String.valueOf(feature1);
        line[2] = String.valueOf(price);
        
        return line;
    }
    
}

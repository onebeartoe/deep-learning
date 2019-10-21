
package org.onebeartoe.deep.learning.color.predictor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lando
 */
public class DoubleArray
{
    List<Double> data;

    public DoubleArray() {
        data = new ArrayList();
    }

    public void addAll(List<Double> all) {
        data.addAll(all);
    }

    public List<Double> values() {
        return data;
    }
}

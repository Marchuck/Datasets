package pl.datasets.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JOHANNES on 5/29/2016.
 */
public class DatasetItem {
    private List<String> properties = new ArrayList<>();
    private long timestamp;
    private List<Double> values = new ArrayList<>();

    public DatasetItem(long timestamp, List<Double> values) {
        this.timestamp = timestamp;
        this.values = values;
    }
    public DatasetItem(long timestamp, Double value) {
        this.timestamp = timestamp;

        this.values.add(0, value);
    }

    public static double[] wrap(List<Double> doubles) {
        double[] doubles1 = new double[doubles.size()];
        for (int j = 0; j < doubles.size(); j++)
            doubles1[j] = doubles.get(j);
        return doubles1;
    }

    public List<String> getProperties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "timestamp: " + timestamp +
                ", " + Arrays.toString(wrap(values));
    }
}

package pl.datasets.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOHANNES on 5/29/2016.
 */
public class DatasetItem {

    private long timestamp;
    private List<Double> values = new ArrayList<>();

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

    public DatasetItem(long timestamp, List<Double> values) {
        this.timestamp = timestamp;
        this.values = values;
    }

    public DatasetItem(long timestamp, Double value) {
        this.timestamp = timestamp;

        this.values.add(0,value);
    }

}

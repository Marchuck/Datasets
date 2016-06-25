package pl.datasets.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOHANNES on 5/29/2016.
 */
public class BaseItem {
    private List<Double> values = new ArrayList<>();
    private List<String> columnNames = new ArrayList<>();

    public BaseItem() {
    }

    public BaseItem(List<Double> values) {
        this.values = values;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }

}

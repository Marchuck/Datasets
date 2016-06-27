package pl.datasets.interfaces;

import pl.datasets.model.DatasetItem;

import java.util.List;

/**
 * Created by JOHANNES on 6/25/2016.
 */
public abstract class TrendDetectingStrategy {
    private String name;

    public TrendDetectingStrategy(String name) {
        this.name = name;
    }

    public abstract boolean hasTrend(List<DatasetItem> candidate, Integer columnId);

    public abstract String getOptionalThreshold();
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}

package pl.datasets.interfaces;

import pl.datasets.model.DatasetItem;

import java.util.List;

/**
 * Created by JOHANNES on 6/25/2016.
 */
public interface TrendDetectingStrategy {
    boolean hasTrend(List<DatasetItem> candidate, Integer columnId);
    String getName();
}

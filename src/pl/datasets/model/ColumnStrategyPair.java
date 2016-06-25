package pl.datasets.model;

import pl.datasets.interfaces.TrendDetectingStrategy;

/**
 * Created by JOHANNES on 6/25/2016.
 */
public class ColumnStrategyPair {

    private TrendDetectingStrategy strategy;
    private int columnId;

    public ColumnStrategyPair(TrendDetectingStrategy strategy, int columnId) {
        this.strategy = strategy;
        this.columnId = columnId;
    }

    public TrendDetectingStrategy getStrategy() {
        return strategy;
    }

    public int getColumnId() {
        return columnId;
    }
}

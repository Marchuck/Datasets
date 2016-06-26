package pl.datasets.utils;

import pl.datasets.interfaces.TrendDetectingStrategy;

import java.io.Serializable;

/**
 * @author Lukasz
 * @since 22.06.2016.
 */
public class Event implements Serializable {
    private TrendDetectingStrategy strategy;
    private int columnIndex;

    public Event() {
    }

    public Event(TrendDetectingStrategy strategy, int columnIndex) {
        this.strategy = strategy;
        this.columnIndex = columnIndex;
    }

    @Override
    public String toString() {
        return "strategy: " + strategy + ", columnIndex: " + columnIndex;
    }

    public TrendDetectingStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(TrendDetectingStrategy strategy) {
        this.strategy = strategy;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }
}
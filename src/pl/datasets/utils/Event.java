package pl.datasets.utils;

import java.io.Serializable;

/**
 * @author Lukasz
 * @since 22.06.2016.
 */
public class Event implements Serializable {
    public Operation operation;
    public String operationName;
    public double value;
    public int columnIndex;

    public Event() {
    }

    public Event(Operation operation, double value, int columnIndex) {
        this.operation = operation;
        this.value = value;
        this.columnIndex = columnIndex;
    }

    @Override
    public String toString() {
        return "operation: " + operationName +
                ", value: " + value +
                ", columnIndex: " + columnIndex;
    }
}
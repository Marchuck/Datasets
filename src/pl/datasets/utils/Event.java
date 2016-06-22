package pl.datasets.utils;

/**
 * @author Lukasz
 * @since 22.06.2016.
 */
public class Event {
    public Operation operation;
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
        return "operation: " + operation +
                ", value: " + value +
                ", columnIndex: " + columnIndex;
    }
}
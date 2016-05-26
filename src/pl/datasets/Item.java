package pl.datasets;

import pl.datasets.utils.NullSafe;

import java.util.Arrays;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public class Item {
    String[] attrs;

    public Item() {
    }

    public Item(String line) {
        attrs = line.split(",");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        if (NullSafe.nonEmpty(attrs)) {
            for (String s : attrs) sb.append(s).append(",");
        }
        sb.append(']');

        return sb.toString();
    }
}

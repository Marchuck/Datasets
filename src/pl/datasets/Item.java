package pl.datasets;

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
        return Arrays.toString(attrs);
    }
}

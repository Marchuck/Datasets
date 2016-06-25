package pl.datasets.utils;

import java.io.Serializable;

/**
 * @author Lukasz
 * @since 22.06.2016.
 */
public interface Operation extends Serializable {
    boolean compute(double value, double valueToCompare);
}

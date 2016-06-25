package pl.datasets.utils;

import pl.datasets.model.DatasetItem;

import java.io.Serializable;

/**
 * @author Lukasz
 * @since 22.06.2016.
 */
public interface Operation extends Serializable {
    boolean compute(DatasetItem first, double valueToCompare, int columnIndex);
}
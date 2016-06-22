package pl.datasets.utils;

import pl.datasets.model.DatasetItem;

/**
 * @author Lukasz
 * @since 22.06.2016.
 */
public interface Operation {
    boolean compute(DatasetItem first, double valueToCompare, int columnIndex);
}

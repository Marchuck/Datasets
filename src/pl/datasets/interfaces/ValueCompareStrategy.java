package pl.datasets.interfaces;

import pl.datasets.model.DatasetItem;

import java.text.ParseException;

/**
 * Created by JOHANNES on 6/25/2016.
 */
public interface ValueCompareStrategy<T> {
    T getRowToCompare(DatasetItem item);
}

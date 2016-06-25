package pl.datasets.interfaces;

import pl.datasets.model.DatasetItem;


/**
 * Created by JOHANNES on 6/25/2016.
 */
public interface GetValueToCompareStrategy {
    Double getRowToCompare(DatasetItem item);


}

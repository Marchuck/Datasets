package pl.datasets.interfaces;

import pl.datasets.model.BaseItem;

/**
 * @author Lukasz
 * @since 25.06.2016.
 */
public interface SubsetStrategy extends UnSafeReadStrategy<BaseItem>, Propertable {
    //no-op: hold merge both interfaces together
}

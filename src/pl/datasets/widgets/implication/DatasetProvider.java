package pl.datasets.widgets.implication;

import pl.datasets.model.DatasetItem;

import java.util.List;

/**
 * @author Lukasz
 * @since 16.09.2016.
 */
public class DatasetProvider {
    public static DatasetProvider instance = new DatasetProvider();

    public List<DatasetItem> items;
    public String[] properties;

    public static void setDataset(List<DatasetItem> items) {
        instance.items = items;
    }

    public static void setProperties(String[] properties) {
        instance.properties = properties;
    }
}

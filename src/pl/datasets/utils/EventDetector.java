package pl.datasets.utils;

import pl.datasets.Application;
import pl.datasets.model.DatasetItem;

import java.util.List;

/**
 * @author Lukasz
 * @since 22.06.2016.
 */
public class EventDetector {


    /*public int detect(List<Event> events) {
        List<DatasetItem> items = get();
        int matches = 0;
        for (DatasetItem it : items) {
            boolean result = true;
            for (Event event : events) {
                if (!event.operation.compute(it, event.value, event.columnIndex)) {
                    result = false;
                    break;
                }
            }
            if (result) matches++;
        }
        return matches;

    }*/

    private List<DatasetItem> get() {
        return Application.getInstance().getDataset();
    }
}

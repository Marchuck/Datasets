package pl.datasets.model;

import javafx.util.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Lukasz
 * @since 09.09.2016.
 */
public class DatasetExtensions {
    private static HashMap<Integer, Pair<Double, Double>> rangesHashmap = new HashMap<>();

    /**
     * @param valueIndex
     * @param datasetItems
     * @return
     */
    public static Pair<Double, Double> getPropertyRange(int valueIndex, Collection<DatasetItem> datasetItems) {
        System.out.println("Getting property " + datasetItems.iterator().next().getProperties().get(valueIndex)
                + " at index " + valueIndex);

        //get cached result if exists
        if (rangesHashmap.containsKey(valueIndex)) {
            return rangesHashmap.get(valueIndex);
        }

        Iterator<DatasetItem> nextItem = datasetItems.iterator();
        double currentMinRange = nextItem.next().getValues().get(valueIndex);
        double currentMaxRange = currentMinRange;
        while (nextItem.hasNext()) {
            double nextValue = nextItem.next().getValues().get(valueIndex);

            if (nextValue < currentMinRange) {
                currentMinRange = nextValue;
            }
            if (nextValue > currentMaxRange) {
                currentMaxRange = nextValue;
            }
        }
        //cache result for future calls
        rangesHashmap.put(valueIndex, new Pair<>(currentMinRange, currentMaxRange));
        return new Pair<>(currentMinRange, currentMaxRange);
    }
}

package pl.datasets.widgets.event_search;

import pl.datasets.model.DatasetItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOHANNES on 9/10/2016.
 */
public class LogicalSolver {

    public static List<List<Long>> resolveAnd(List<List<Long>> firstSet, List<List<Long>> secondSet, int minTrendLength) {
        List<List<Long>> result = new ArrayList<>();

        List<Long> tempItem = new ArrayList<>();

        for (List<Long> firstSetItem : firstSet) {
            for (Long firstSetItemItem : firstSetItem) {
                secondItemsLoop:
                for (List<Long> secondSetItem : secondSet) {
                    for (Long secondSetItemItem : secondSetItem) {
                        if (firstSetItemItem.longValue() == secondSetItemItem.longValue()) {
                            tempItem.add(firstSetItemItem);
                            break secondItemsLoop;
                        } else {
                            if (tempItem.size() >= minTrendLength) {
                                result.add(tempItem);
                            }
                            tempItem.clear();
                        }
                    }
                }
            }
            if (tempItem.size() >= minTrendLength) {
                result.add(tempItem);
            }
            tempItem.clear();
        }

        return result;
    }

    public static List<List<Long>> resolveOr(List<List<Long>> firstSet, List<List<Long>> secondSet, int datasetLength) {


        List<List<Long>> result = new ArrayList<>();
        List<Boolean> orResults = new ArrayList<>(datasetLength);
        List<Long> tempOrSubset = new ArrayList<>();


        for (List<Long> firstSetItem : firstSet) {
            for (Long firstSetItemItem : firstSetItem)
                orResults.set(firstSetItemItem.intValue(), true);
        }

        for (List<Long> secondSetItem : secondSet) {
            for (Long secondSetItemItem : secondSetItem)
                orResults.set(secondSetItemItem.intValue(), true);
        }


        for (Boolean isPresent : orResults) {
            if (isPresent)
                tempOrSubset.add((long) orResults.indexOf(isPresent));
            else {
                if (tempOrSubset.size() > 0) {

                    result.add(tempOrSubset);
                    tempOrSubset.clear();
                }
            }
        }
        return result;
    }

    public static List<List<Long>> evaluate(String relationship, List<List<Long>> firstSet, List<List<Long>> secondSet, int minTrendLength, int datasetLength) {

        switch (relationship){
            case "AND":
                return resolveAnd(firstSet,secondSet,minTrendLength);
            case "OR":
                return resolveAnd(firstSet,secondSet,datasetLength);
            default:
                return resolveAnd(firstSet,secondSet,minTrendLength);
        }
    }


}

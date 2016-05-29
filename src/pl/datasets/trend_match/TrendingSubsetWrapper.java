package pl.datasets.trend_match;

import pl.datasets.model.DatasetItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOHANNES on 5/29/2016.
 * Wrapper for discovering trending subsets in dataset
 * To be implemented with formula for determine if subset is trending
 */
public abstract class TrendingSubsetWrapper {

    private List<DatasetItem> dataset;
    private List<List<Long>> trendsList = new ArrayList<>();
    private List<Long> singleTrendList = new ArrayList<>();
    private List<DatasetItem> tempWorkingList = new ArrayList<>();


    private int minTrendLength = 2;


    /**
     * Set minimal length of the searched trend
     *
     * @param minTrendLength
     */
    void setMinTrendLength(int minTrendLength) {
        this.minTrendLength = minTrendLength;
    }


    TrendingSubsetWrapper(List<DatasetItem> dataset) {
        this.dataset = dataset;

        printDatasetSummary(dataset);
    }

    private void printDatasetSummary(List<DatasetItem> dataset) {
        if (null != dataset && dataset.size() > 0) {

            System.out.println("\n\nInput dataset values:\n ");
            for (DatasetItem item : dataset) {
                System.out.print(String.valueOf(item.getValues().get(0)) + " ");
            }
            System.out.println();
        }
    }

    public List<List<Long>> getTrends() {
        findTrends();
        if (null != trendsList && trendsList.size() > 0) {

            System.out.print("\n");
            System.out.print("\n Items with given trend: ");
            for (List<Long> singleTrend : trendsList) {
                System.out.print("\n");
                if (null != singleTrend && singleTrend.size() > 0) {
                    for (Long item : singleTrend) {
                        System.out.print(String.valueOf(item) + " ");
                    }
                }
            }
        }
        return trendsList;
    }

    private void findTrends() {

        for (DatasetItem item : dataset) {

            tempWorkingList.add(item);

            if (tempWorkingList.size() >= minTrendLength) {

                if (!checkIfMatchesTrend(tempWorkingList)) {

                    tempWorkingList.clear();
                    tempWorkingList.add(item);
                    putTrendDelimiter();

                } else {
                    if (tempWorkingList.size() == minTrendLength) {

                        putDiscoveredTrend(tempWorkingList);
                    } else {

                        putDiscoveredTrend(item);
                    }
                }
            }
        }
        putTrendDelimiter();
    }


    abstract boolean checkIfMatchesTrend(List<DatasetItem> trendCandidate);



    private void putDiscoveredTrend(List<DatasetItem> trend) {

        for (DatasetItem item : trend) {

            singleTrendList.add(item.getTimestamp());
        }
    }


    private void putDiscoveredTrend(DatasetItem trendItem) {

        singleTrendList.add(trendItem.getTimestamp());
    }

    /**
     * put separate trends into subsequent lists
     */
    private void putTrendDelimiter() {

        if (singleTrendList.size() > 0) {
            List<Long> tempTrend = new ArrayList<>(singleTrendList);
            trendsList.add(tempTrend);
            singleTrendList.clear();
        }

    }


}


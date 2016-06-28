package pl.datasets.trend_match;

import pl.datasets.model.BeforeAfterPair;
import pl.datasets.model.DatasetItem;
import pl.datasets.utils.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOHANNES on 5/29/2016.
 * Wrapper for discovering trending subsets in dataset
 * To be implemented with formula for determine if subset is trending
 */
public class TrendingSubsetWrapper {

    public static final String TAG = TrendingSubsetWrapper.class.getSimpleName();
    private List<DatasetItem> dataset;
    private List<List<Long>> trendsList = new ArrayList<>();
    private List<Long> singleTrendList = new ArrayList<>();
    private List<DatasetItem> tempWorkingList = new ArrayList<>();


    private int minTrendLength = 2;

    private TrendingSubsetWrapper(List<DatasetItem> dataset) {
        this.dataset = dataset;

        printDatasetSummary(dataset);
    }

    public static TrendingSubsetWrapper getInstance(List<DatasetItem> dataset) {
        return new TrendingSubsetWrapper(dataset);
    }



    /**
     * Set minimal length of the searched trend
     *
     * @param minTrendLength
     */
    public void setMinTrendLength(int minTrendLength) {
        this.minTrendLength = minTrendLength;
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

    public List<List<Long>> getTrends(List<Event> columnStrategyPairs, boolean getAbsenceOfTrend) {

        clearLists();

        if (!getAbsenceOfTrend) {
            findTrends(columnStrategyPairs);
        } else {
            findAbsenceOfTrends(columnStrategyPairs);
        }
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

    private void clearLists() {
        trendsList.clear();
        singleTrendList.clear();
        tempWorkingList.clear();
    }

    /**
     *
     * @param eventBefore
     * @param eventAfter
     * @param allowedOffset between 1 and dataset length
     */
    public List<BeforeAfterPair> findAfter(Event eventBefore, Event eventAfter, int allowedOffset) {

        List<BeforeAfterPair> causationList = new ArrayList<>();
        DatasetItem beforeCandidate = null;

        for (DatasetItem item : dataset) {
            if (null != beforeCandidate) {
                if ((dataset.indexOf(item) - dataset.indexOf(beforeCandidate)) <= allowedOffset && eventAfter.hasTrend(item)) {
                    causationList.add(new BeforeAfterPair((int) beforeCandidate.getTimestamp(), (int) item.getTimestamp()));
                }

                if (eventBefore.hasTrend(item)) {
                    beforeCandidate = item;
                } else {
                    beforeCandidate = null;
                }
            } else {
                if (eventBefore.hasTrend(item)) {
                    beforeCandidate = item;
                }
            }
        }
        return causationList;
    }


    private void findTrends(List<Event> columnStrategyPairs) {

        setMinTrendLength(1);


        for (DatasetItem item : dataset) {

            tempWorkingList.add(item);

            if (tempWorkingList.size() >= minTrendLength) {

                if (!checkIfMatchesTrend(tempWorkingList, columnStrategyPairs)) {

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

    private void findAbsenceOfTrends(List<Event> columnStrategyPairs) {

        setMinTrendLength(1);

        for (DatasetItem item : dataset) {

            tempWorkingList.add(item);

            if (tempWorkingList.size() >= minTrendLength) {

                if (checkIfMatchesTrend(tempWorkingList, columnStrategyPairs)) {

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




    public boolean checkIfMatchesTrend(List<DatasetItem> trendCandidate, List<Event> columnStrategyPairs) {

        for (Event pair : columnStrategyPairs) {
            if (!pair.hasTrend(trendCandidate)) {
                return false;
            }
        }
        return true;

    }


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

    protected void printAttemptData(List<DatasetItem> trendCandidate) {
        if (null != trendCandidate && trendCandidate.size() > 0) {
            System.out.print("\n trend candidate: ");
            for (DatasetItem item : trendCandidate) {

                System.out.print(String.valueOf(item.getTimestamp()) + " ");

            }
        }


    }
}


package pl.datasets.trend_match;

import javafx.util.Pair;
import pl.datasets.model.BeforeAfterPair;
import pl.datasets.model.DatasetItem;
import pl.datasets.utils.Event;
import pl.datasets.utils.Strategies;
import pl.datasets.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static void main(String[] args) {
        new TestSuite().test();
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

    public List<List<Long>> getTrends(final Event columnStrategyPair, boolean getAbsenceOfTrend) {
        return getTrends(new ArrayList<Event>() {{
            add(columnStrategyPair);
        }}, getAbsenceOfTrend);

    }

    public List<Boolean> evaluate(final Event columnStrategyPair) {
        List<Boolean> trending = new ArrayList<>();
        int index = columnStrategyPair.getColumnIndex();
        for (DatasetItem d : dataset) {
            trending.add(columnStrategyPair.getStrategy().hasTrend(d, index));
        }
        return trending;
    }

    public Pair<List<List<Long>>, List<List<Long>>> getAllTrends(List<Event> columnStrategyPairs) {
        return new Pair<>(getTrends(columnStrategyPairs, false), getTrends(columnStrategyPairs, true));
    }

    public List<List<Long>> getTrends(List<Event> columnStrategyPairs, boolean getAbsenceOfTrend) {

        clearLists();

        if (!getAbsenceOfTrend) {
            findTrends(columnStrategyPairs);
        } else {
            findAbsenceOfTrends(columnStrategyPairs);
        }
        if (trendsList.size() > 0) {

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

            Utils.log("Returning list containing " + trendsList.size() + " elements");
        }
        return trendsList;
    }

    private void clearLists() {
        trendsList.clear();
        singleTrendList.clear();
        tempWorkingList.clear();
    }

    /**
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



    public List<List<Long>> findChainedEventsOccurences(List<Event> chainedEvents, int allowedOffset) {

        List<List<Long>> implicationCandidates = new ArrayList<>();

        for (DatasetItem item : dataset) {

            for (Event singleEvent : chainedEvents) {

                if (singleEvent.hasTrend(item)) {
                    if (chainedEvents.indexOf(singleEvent) == 0) {
                        implicationCandidates.add(initNewLongList(item));
                    } else {

                        for (List<Long> candidate : implicationCandidates) {

                            if (candidate.size() == chainedEvents.indexOf(singleEvent)
                                    && item.getTimestamp() - candidate.get(candidate.size() - 1) <= allowedOffset
                                    && item.getTimestamp() - candidate.get(candidate.size() - 1) > 0) {
                                Utils.log("Putting item into candidate list" + item.toString() + singleEvent.toString() + candidate.toString());
                                candidate.add(item.getTimestamp());
                                break;
                            }
                        }
                    }
                }
            }
        }

        return reduceCandidates(chainedEvents, implicationCandidates);
    }


    private List<List<Long>> reduceCandidates(List<Event> chainedEvents, List<List<Long>> implicationCandidates) {

        List<List<Long>> properImplications = new ArrayList<>();

        for (List<Long> candidate : implicationCandidates) {
            if (candidate.size() == chainedEvents.size()) {
                properImplications.add(candidate);
            }
        }
        return properImplications;
    }

    private List<Long> initNewLongList(DatasetItem item) {
        ArrayList<Long> result = new ArrayList<>();
        result.add(item.getTimestamp());
        return result;
    }

    private void findTrends(List<Event> columnStrategyPairs) {

        setMinTrendLength(2);


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

    private static class TestSuite {

        public void test() {
            System.out.println("Testing #1");
            Event event = new Event(new Strategies.LessThanStrategy(4), 4);
            TrendingSubsetWrapper wrapper = TrendingSubsetWrapper.getInstance(hardCodedSet());
            for (List<Long> singleTrend : wrapper.getTrends(event, true)) {
                System.out.print("\n");
                if (singleTrend.size() > 0) {
                    for (Long item : singleTrend) {
                        System.out.print(String.valueOf(item) + " ");
                    }
                }
            }
        }

        private List<DatasetItem> hardCodedSet() {
            List<DatasetItem> listOfDatasetItems = new ArrayList<>();
            listOfDatasetItems.add(new DatasetItem(1, Arrays.asList(1d, 100d, 3d, 4d, 0d)));
            listOfDatasetItems.add(new DatasetItem(2, Arrays.asList(2d, 90d, 3d, 4d, 0d)));
            listOfDatasetItems.add(new DatasetItem(3, Arrays.asList(3d, 80d, 3d, 4d, 0d)));
            listOfDatasetItems.add(new DatasetItem(4, Arrays.asList(4d, 70d, 3d, 4d, 0d)));
            listOfDatasetItems.add(new DatasetItem(5, Arrays.asList(5d, 60d, 0d, 4d, 0d)));
            listOfDatasetItems.add(new DatasetItem(6, Arrays.asList(6d, 50d, 0d, 4d, 0d)));
            listOfDatasetItems.add(new DatasetItem(7, Arrays.asList(7d, 40d, 0d, 4d, 0d)));
            listOfDatasetItems.add(new DatasetItem(8, Arrays.asList(8d, 30d, 0d, 4d, 0d)));
            listOfDatasetItems.add(new DatasetItem(9, Arrays.asList(9d, 20d, 0d, 4d, 0d)));
            return listOfDatasetItems;
        }
    }

    public List<DatasetItem> getDataset() {
        return dataset;
    }
}


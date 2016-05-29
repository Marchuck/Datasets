package pl.datasets.trend_match;

import pl.datasets.model.DatasetItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOHANNES on 5/29/2016.
 */
public abstract class TrendingPartsHelper {

    private List<Long> trendingPartsTimestamps = new ArrayList<>();
    private List<DatasetItem> dataset;

    private int minTrendLength = 1;


    public void setMinTrendLength(int minTrendLength) {
        this.minTrendLength = minTrendLength;
    }


    TrendingPartsHelper(List<DatasetItem> dataset) {
        this.dataset = dataset;
    }

    public List<Long> getTrendingPartsTimestamps() {
        findTrends();
        return trendingPartsTimestamps;
    }

    private void findTrends() {

        List<DatasetItem> workingList = new ArrayList<>();
        List<DatasetItem> tempWorkingList = new ArrayList<>();
        for (DatasetItem item : dataset) {

            tempWorkingList.add(item);
            if (!checkIfMatchesTrend(tempWorkingList)) {
                if (workingList.size() >= minTrendLength) {
                    putDiscoveredTrend(workingList);
                }
                workingList.clear();
                tempWorkingList.clear();
            } else {
                workingList = tempWorkingList;
            }
        }
    }

    abstract boolean checkIfMatchesTrend(List<DatasetItem> trendCandidate);

    private void putDiscoveredTrend(List<DatasetItem> candidate) {

        for (DatasetItem item : candidate) {
            trendingPartsTimestamps.add(item.getTimestamp());
        }
    }

}


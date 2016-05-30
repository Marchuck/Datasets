package pl.datasets.trend_match;

import pl.datasets.model.DatasetItem;

import java.util.List;

/**
 * Created by JOHANNES on 5/29/2016.
 */
public class AscendingTrend extends TrendingSubsetWrapper {


    public static AscendingTrend getInstance(List<DatasetItem> dataset) {
        return new AscendingTrend(dataset);
    }



    private AscendingTrend(List<DatasetItem> dataset) {
        super(dataset);
        setMinTrendLength(2);
    }




    @Override
    boolean checkIfMatchesTrend(List<DatasetItem> trendCandidate) {

        printAttemptData(trendCandidate);

        if (trendCandidate.size() == 1) {

            return true;
        } else if (trendCandidate.size() > 1) {

            DatasetItem firstItem;
            DatasetItem secondItem;

            for (int i = 0; i < trendCandidate.size()-1; i++) {

                firstItem = trendCandidate.get(i);
                secondItem = trendCandidate.get(i + 1);

                if (secondItem.getValues().get(0) <= firstItem.getValues().get(0)) {
                    return false;
                }
            }
        }
        return true;
    }


}

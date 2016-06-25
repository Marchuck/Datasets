package pl.datasets.trend_match;

import pl.datasets.interfaces.ValueCompareStrategy;
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
        AscendingAQIStrategy strategy = new AscendingAQIStrategy();
        if (trendCandidate.size() == 1) {
            return true;
        } else if (trendCandidate.size() > 1) {

            DatasetItem firstItem;
            DatasetItem secondItem;

            for (int i = 0; i < trendCandidate.size()-1; i++) {

                firstItem = trendCandidate.get(i);
                secondItem = trendCandidate.get(i + 1);

                if (strategy.getRowToCompare(secondItem) <= strategy.getRowToCompare(firstItem)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static class AscendingAQIStrategy implements ValueCompareStrategy<Double>{

        @Override
        public Double getRowToCompare(DatasetItem item) {
            return item.getValues().get(1);
        }
    }


}

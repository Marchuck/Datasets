package pl.datasets.widgets;

import java.util.List;

/**
 * Created by JOHANNES on 9/15/2016.
 */
public class ResultSummaryModel {

    public static int getNumberOfTrends(List<List<Long>> input){
        return input.size();
    }

    public static Double getAverageTrendLength(List<List<Long>> input){

        double noItems = 0;

        for (List<Long> item:input)
            noItems += item.size();

        return noItems/input.size();
    }

    public static int getLongestTrend(List<List<Long>> input){

        int longest = 0;

        for (List<Long> item:input)
            longest = longest>item.size() ? longest : item.size();


        return longest;
    }



}

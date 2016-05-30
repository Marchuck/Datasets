package pl.datasets.test;

import org.junit.Test;
import pl.datasets.model.DatasetItem;
import pl.datasets.trend_match.AscendingTrend;
import pl.datasets.trend_match.DescendingTrend;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by JOHANNES on 5/29/2016.
 */
public class TrendMatchingTest {




    @Test
    public void testAscendingTrend(){

        List<DatasetItem> dummyItems = new ArrayList<>();
        dummyItems.add(new DatasetItem(1,2d));
        dummyItems.add(new DatasetItem(2,3d));
        dummyItems.add(new DatasetItem(3,4d));
        dummyItems.add(new DatasetItem(4,2d));
        dummyItems.add(new DatasetItem(5,1d));
        dummyItems.add(new DatasetItem(6,0d));


        AscendingTrend ascendingTrend = AscendingTrend.getInstance(dummyItems);
        List<List<Long>> trends = ascendingTrend.getTrends();


        assertThat(trends.size(),is(1));
    }

    @Test
    public void testAscendingTrend2(){

        List<DatasetItem> dummyItems = new ArrayList<>();
        dummyItems.add(new DatasetItem(1,2d));
        dummyItems.add(new DatasetItem(2,3d));
        dummyItems.add(new DatasetItem(3,4d));
        dummyItems.add(new DatasetItem(4,2d));
        dummyItems.add(new DatasetItem(5,1d));
        dummyItems.add(new DatasetItem(6,0d));
        dummyItems.add(new DatasetItem(7,2d));
        dummyItems.add(new DatasetItem(8,1d));
        dummyItems.add(new DatasetItem(9,0d));
        dummyItems.add(new DatasetItem(10,2d));
        dummyItems.add(new DatasetItem(11,1d));
        dummyItems.add(new DatasetItem(12,0d));

        AscendingTrend ascendingTrend = AscendingTrend.getInstance(dummyItems);
        List<List<Long>> trends = ascendingTrend.getTrends();


        assertThat(trends.size(),is(3));
    }



    @Test
    public void testDescendingTrend(){

        List<DatasetItem> dummyItems = new ArrayList<>();
        dummyItems.add(new DatasetItem(1,2d));
        dummyItems.add(new DatasetItem(2,3d));
        dummyItems.add(new DatasetItem(3,4d));
        dummyItems.add(new DatasetItem(4,2d));
        dummyItems.add(new DatasetItem(5,1d));
        dummyItems.add(new DatasetItem(6,0d));


        DescendingTrend descendingTrend = DescendingTrend.getInstance(dummyItems);
        List<List<Long>> trends = descendingTrend.getTrends();


        assertThat(trends.size(),is(1));
    }

    @Test
    public void testDescendingTrend2(){

        List<DatasetItem> dummyItems = new ArrayList<>();
        dummyItems.add(new DatasetItem(1,2d));
        dummyItems.add(new DatasetItem(2,3d));
        dummyItems.add(new DatasetItem(3,4d));
        dummyItems.add(new DatasetItem(4,2d));
        dummyItems.add(new DatasetItem(5,1d));
        dummyItems.add(new DatasetItem(6,0d));
        dummyItems.add(new DatasetItem(7,2d));
        dummyItems.add(new DatasetItem(8,1d));
        dummyItems.add(new DatasetItem(9,0d));
        dummyItems.add(new DatasetItem(10,2d));
        dummyItems.add(new DatasetItem(11,1d));
        dummyItems.add(new DatasetItem(12,0d));

        DescendingTrend descendingTrend = DescendingTrend.getInstance(dummyItems);
        List<List<Long>> trends = descendingTrend.getTrends();


        assertThat(trends.size(),is(3));
    }

}

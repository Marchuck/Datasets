package pl.datasets;

import pl.datasets.model.DatasetItem;
import pl.datasets.trend_match.AscendingTrend;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public class Application {
    public JFrame currentUsedFrame;

    public static Application instance;

    public static Application getInstance() {
        return instance == null ? new Application() : instance;
    }

    public static void main(String[] args) {
        Application.getInstance();

        testGrowingTrend();
    }
    public static void testGrowingTrend(){

        List<DatasetItem> dummyItems = new ArrayList<>();
        dummyItems.add(new DatasetItem(0,1d));
        dummyItems.add(new DatasetItem(1,2d));
        dummyItems.add(new DatasetItem(2,3d));
        dummyItems.add(new DatasetItem(3,4d));
        dummyItems.add(new DatasetItem(4,2d));
        dummyItems.add(new DatasetItem(5,1d));
        dummyItems.add(new DatasetItem(6,2d));


        AscendingTrend ascendingTrend = AscendingTrend.getInstance(dummyItems);



    }


    private Application() {
        //currentUsedFrame = new MainGUIForm();
    }

}

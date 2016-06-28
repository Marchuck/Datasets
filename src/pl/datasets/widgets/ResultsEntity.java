package pl.datasets.widgets;

import pl.datasets.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Datasets
 *
 * @author Lukasz Marczak
 * @since 28 cze 2016.
 * 15 : 55
 */
public class ResultsEntity {



    public static void main(String[] args) {

    }

    @Deprecated
    private int getLastTimestamp(List<List<Long>> results) {
        int lastListIndex = results.size() - 1;
        List<Long> lastList = results.get(lastListIndex);

        while (lastList.isEmpty()) {
            --lastListIndex;
            lastList = results.get(lastListIndex);
        }
        int lastListLastIndex = lastList.size() - 1;
        return lastList.get(lastListLastIndex).intValue();
    }

    public void bind(List<List<Long>> results) {
        List<Boolean> data = new ArrayList<>();
        int threshold = 2;
        for (List<Long> aList : results) {
            for (Long l : aList) {
                if (aList.size() > threshold) {
                    data.add(true);
                    Utils.log("true: " + l);
                } else {
                    data.add(false);
                    Utils.log("false: " + l);
                }
            }
        }
        display(data, "TEXT XDXDXDXD");
    }

    public static void display(List<Boolean> data, String title) {
        JFrame window = new JFrame();
        window.setTitle(title);
        window.setLayout(new FlowLayout());
        //   window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 1000, 200);
        window.getContentPane().setLayout(new FlowLayout(5));
       // window.getContentPane().add(new ResultCanvas(new Point(20, 20), data));
       // window.getContentPane().add(new JLabel("ASCENDING"));
        window.add(new JLabel("ASCENDING"));
        window.add(new ResultCanvas(new Point(20, 20), data));

        window.setLocationByPlatform(true);
        window.setVisible(true);
    }
}

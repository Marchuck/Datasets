package pl.datasets.widgets;

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

    public static void displayMany(List<List<Boolean>> _data, String title) {
        JDialog window = new JDialog();
        window.setTitle(title);
        //window.setLayout(new FlowLayout());
        //   window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 800, 200);
        window.getContentPane().setLayout(new FlowLayout(FlowLayout.LEADING));
        // window.getContentPane().add(new ResultCanvas(new Point(20, 20), data));
        // window.getContentPane().add(new JLabel("ASCENDING"));
        window.add(new JLabel(labelForData(_data.get(0))));
        window.add(new ResultCanvas(new Point(20, 20), _data.get(0)).withExistanceColor(Color.MAGENTA));

        for (int j = 1; j < _data.size(); j++) {
            List<Boolean> data = _data.get(j);
            window.add(new JLabel(labelForData(data)));
            window.add(new ResultCanvas(new Point(20, 20 + 30 * j), data));
        }
        window.pack();
        window.setLocationByPlatform(true);
        window.setVisible(true);
    }

    public static void display(List<Boolean> data, String title) {
        JDialog window = new JDialog();
        window.setTitle(title);
        //window.setLayout(new FlowLayout());
        //   window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 800, 200);
        window.getContentPane().setLayout(new FlowLayout(FlowLayout.LEADING));
        // window.getContentPane().add(new ResultCanvas(new Point(20, 20), data));
        // window.getContentPane().add(new JLabel("ASCENDING"));
        window.add(new JLabel(labelForData(data)));
        window.add(new ResultCanvas(new Point(20, 20), data));
        window.pack();
        window.setLocationByPlatform(true);
        window.setVisible(true);
    }

    private static String labelForData(List<Boolean> data) {
        if (allTrue(data)) return "Invariance";
        else if (allFalse(data)) return "Absence";
        return "Undefined";
    }

    private static boolean allFalse(List<Boolean> data) {
        for (boolean b : data) if (b) return false;
        return true;
    }

    private static boolean allTrue(List<Boolean> data) {
        for (boolean b : data) if (!b) return false;
        return true;
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


    private List<Boolean> transformToBooleanList(List<List<Long>> results) {
        List<Boolean> data = new ArrayList<>();
        int threshold = 2;
        for (List<Long> aList : results) {
            for (Long l : aList) {
                if (aList.size() > threshold) {
                    data.add(true);
//                    Utils.log("true: " + l);
                } else {
                    data.add(false);
//                    Utils.log("false: " + l);
                }
            }
        }
        return data;
    }

    public void bind(List<List<Long>> results) {
        List<Boolean> data = transformToBooleanList(results);
        display(data, "Results");
    }

    public void bindAll(List<List<List<Long>>> res) {
        List<List<Boolean>> bls = new ArrayList<>();
        for (List<List<Long>> l : res) bls.add(transformToBooleanList(l));
        displayMany(bls, "Results");
    }
}


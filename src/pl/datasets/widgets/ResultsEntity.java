package pl.datasets.widgets;

import javafx.util.Pair;
import pl.datasets.utils.Event;
import pl.datasets.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Datasets
 *
 * @author Lukasz Marczak
 * @since 28 cze 2016.
 * 15 : 55
 */
public class ResultsEntity {

    private List<String> properties;


    public static void main(String[] args) {

    }

    public static void displayMany(List<List<Boolean>> _data, String title) {
        JDialog window = new JDialog();
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setTitle(title);
        //window.setLayout(new FlowLayout());
        //   window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 300, 400);
        window.getContentPane().setLayout(new FlowLayout(FlowLayout.LEADING));
        // window.getContentPane().add(new ResultCanvas(new Point(20, 20), data));
        // window.getContentPane().add(new JLabel("ASCENDING"));
        window.add(new JLabel(labelForData(_data.get(0))));
        window.add(new ResultCanvas(new Point(20, 20), _data.get(0)).withExistanceColor(Color.MAGENTA));
        //     new ArrayList<Event>(){{add(columnStrategyPair);}}
        for (int j = 1; j < _data.size(); j++) {
            List<Boolean> data = _data.get(j);
            window.add(new JLabel(labelForData(data)));
            window.add(new ResultCanvas(new Point(20, 20), data));
        }
        window.pack();
        window.setLocationByPlatform(true);
        window.setVisible(true);
    }

    public static void display(List<Boolean> data, String title) {
        JDialog window = new JDialog();
        window.setTitle(title);
        //window.setLayout(new FlowLayout());
        //   window.setDefaultCloseO peration(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 300, 400);
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
        if (allTrue(data)) return "(Invariance)";
        else if (allFalse(data)) return "(Absence)";
        return "";
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

    @Deprecated
    public void bindAll(List<List<List<Long>>> res) {
        List<List<Boolean>> bls = new ArrayList<>();
        List<List<Boolean>> bless = new ArrayList<>();
        for (List<List<Long>> l : res) {
            Utils.log("size:" + l.size());
            bls.add(transformToBooleanList(l));
        }
        for (int j = 0; j < 3; j++) {
            List<Boolean> list = generateBooleans(50);
            bless.add(list);
        }
//        displayMany(bless, "Results");
        displayMany(bls, "Results");
    }

    private List<Boolean> generateBooleans(int i) {
        Random r = new Random();
        List<Boolean> b = new ArrayList<>();
        for (int j = 0; j < i; j++) b.add(r.nextBoolean());
        return b;
    }

    public ResultsEntity withProperties(List<String> properties) {
        this.properties = properties;
        return this;
    }


    public void bindSeparated(List<Pair<Event, List<Boolean>>> sliced) {
        String title = "";
        JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setTitle(title);
        //window.setLayout(new FlowLayout());
        //   window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //dialog.setBounds(30, 30, 300, 400);
        JPanel rootPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        dialog.setContentPane(rootPanel);
        rootPanel.add(new Label("SUMMARY", Label.CENTER));

        String longest = findLongestDescription(sliced);
        for (Pair<Event, List<Boolean>> pair : sliced) {
            List<Boolean> timestampsWhichAgreesWithEvent = pair.getValue();
            Event nextEvent = pair.getKey();

            float[] existanceAndNonExistanceOfEvent = getPercentageStatistics(timestampsWhichAgreesWithEvent);
            int[] longestEventLengthAndOccurence = getLongestEventLengthAndOccurence(timestampsWhichAgreesWithEvent);

            int propertyId = nextEvent.getColumnIndex();
            String propertyName = properties.get(propertyId);
            String labelForStrip = nextEvent.eventNameFor(propertyName);

            String labelDescription = labelForStrip + optionalJudgement(timestampsWhichAgreesWithEvent);
            String statistics = String.format(" %.2f %%, %.2f %% %d, %d ",
                    existanceAndNonExistanceOfEvent[0], existanceAndNonExistanceOfEvent[1], longestEventLengthAndOccurence[0],
                    longestEventLengthAndOccurence[1]);
            labelDescription += statistics;
            int offset = longest.length();

            String descriptionPerLabel = withFixedOffset(offset, labelDescription);
            dialog.getContentPane().add(new JLabel(descriptionPerLabel));

            ResultCanvas resultCanvas = new ResultCanvas.Builder()
                    .setColor(Color.YELLOW)
                    .setData(pair.getValue())
                    .setPosition(new Point(20, 20))
                    .build();

            dialog.getContentPane().add(resultCanvas);
        }
        dialog.pack();
        dialog.setLocationByPlatform(true);
        dialog.setVisible(true);
        dialog.setSize(760, 512);
        dialog.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                System.out.println("componentResized: " + e.paramString());
            }
        });

    }

    private int[] getLongestEventLengthAndOccurence(List<Boolean> timestampsWhichAgreesWithEvent) {
        int longest = 0, tmpLength = 0, indexOfOccurence = 0;
        for (int j = 0; j < timestampsWhichAgreesWithEvent.size(); j++) {
            boolean b = timestampsWhichAgreesWithEvent.get(j);
            if (b) {
                ++tmpLength;
            } else {
                if (tmpLength > longest) {
                    longest = tmpLength;
                    indexOfOccurence = j - longest;
                }
                tmpLength = 0;
            }
        }
        if (tmpLength == timestampsWhichAgreesWithEvent.size()) {
            return new int[]{tmpLength, 0};
        } else return new int[]{
                longest, indexOfOccurence
        };
    }

    private float[] getPercentageStatistics(List<Boolean> timestampsWhichAgreesWithEvent) {
        int size = timestampsWhichAgreesWithEvent.size();
        int eventExistanceSize = calculateTimestampOccurrences(timestampsWhichAgreesWithEvent);
        int eventNonExistanceSize = size - eventExistanceSize;

        float percentOfExistance = (float) 100 * eventExistanceSize / size;
        float percentOfNonExistance = (float) 100 * eventNonExistanceSize / size;

        return new float[]{percentOfExistance, percentOfNonExistance};
    }

    private int calculateTimestampOccurrences(List<Boolean> timestampsWhichAgreesWithEvent) {
        return calculateTrues(timestampsWhichAgreesWithEvent);
    }

    private int calculateTrues(List<Boolean> list) {
        int size = 0;
        for (boolean value : list) if (value) ++size;
        return size;
    }

    private String findLongestDescription(List<Pair<Event, List<Boolean>>> sliced) {
        String longest = "";
        for (Pair<Event, List<Boolean>> pair : sliced) {

            Event nextEvent = pair.getKey();

            int propertyIndex = nextEvent.getColumnIndex();
            String propertyName = properties.get(propertyIndex);

            String labelForStrip = nextEvent.eventNameFor(propertyName);
            String out = labelForStrip + optionalJudgement(pair.getValue());
            longest = longest.length() < out.length() ? out : longest;
        }
        return longest;
    }

    private String withFixedOffset(int length, String s) {
        if (length == s.length()) return s;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length - s.length(); i++) {
            sb.append("_");
        }
        return sb.toString() + s;
    }

    private String optionalJudgement(List<Boolean> data) {
        return labelForData(data);
    }
}


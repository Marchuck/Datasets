package pl.datasets.widgets.event_search;

import javafx.util.Pair;
import pl.datasets.utils.Event;
import pl.datasets.utils.ThreeElements;
import pl.datasets.utils.Utils;
import pl.datasets.widgets.ResultCanvas;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Lukasz
 * @since 15.09.2016.
 */
public class EventSearchSummaryDialog extends JFrame {

    private JLabel titlehidden;
    private JPanel rootPanel;
    private JScrollPane scrollPane;
    private JList<ThreeElements> statisticsList;
    private JLabel theTitle;
    private JList<String> yellowBoxesList;
    private JLabel detailedText;

    //models!
    private DefaultListModel<ThreeElements> statisticsModel = new DefaultListModel<>();
    private DefaultListModel<String> yellowBoxesModel = new DefaultListModel<>();

    private EventSearchSummaryModel model;

    public EventSearchSummaryDialog(List<String> properties, List<Pair<Event, List<Boolean>>> sliced) {
        super("Summary");
        Utils.log("EventSearchSummaryDialog");
        setContentPane(rootPanel);

        //setPreferredSize(new Dimension(300, 200));

        model = new EventSearchSummaryModel(properties, sliced);
        fillWithdata();
        
        setMinimumSize(new Dimension(300, 200));
        setLocation(300, 300);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }


    Observable<DefaultListModel<String>> getModelForStrips() {
        return Observable.from(model.sliced).map(new Func1<Pair<Event, List<Boolean>>, String>() {
            @Override
            public String call(Pair<Event, List<Boolean>> pair) {
                Event nextEvent = pair.getKey();

                int propertyId = nextEvent.getColumnIndex();
                String propertyName = model.properties.get(propertyId);
                String labelForStrip = nextEvent.eventNameFor(propertyName);
                return labelForStrip;
            }
        }).toList().map(new Func1<List<String>, DefaultListModel<String>>() {
            @Override
            public DefaultListModel<String> call(List<String> strings) {
                DefaultListModel<String> listmodel = new DefaultListModel<String>();
                for (String s : strings) listmodel.addElement(s);
                return listmodel;
            }
        });
    }

    Observable<DefaultListModel<ThreeElements>> getModelForStatistics() {
        return Observable.from(model.sliced).
                map(new Func1<Pair<Event, List<Boolean>>, ThreeElements>() {
                    @Override
                    public ThreeElements call(Pair<Event, List<Boolean>> pair) {
                        List<Boolean> timestampsWhichAgreesWithEvent = pair.getValue();
                        Event nextEvent = pair.getKey();
                        Utils.log("processing");

                        float[] existanceAndNonExistanceOfEvent = model.getPercentageStatistics(timestampsWhichAgreesWithEvent);
                        int[] longestEventLengthAndOccurence = model.getLongestEventLengthAndOccurence(timestampsWhichAgreesWithEvent);

                        int propertyId = nextEvent.getColumnIndex();
                        String propertyName = model.properties.get(propertyId);
                        String labelForStrip = nextEvent.eventNameFor(propertyName);

                        //todo: add more statistical data here
                        String additionalData = model.optionalJudgement(timestampsWhichAgreesWithEvent);

                        String statistics = String.format(" %.2f %%, %.2f %% %d, %d ",
                                existanceAndNonExistanceOfEvent[0], existanceAndNonExistanceOfEvent[1], longestEventLengthAndOccurence[0],
                                longestEventLengthAndOccurence[1]);

                        ThreeElements threeElements = new ThreeElements(labelForStrip, statistics, additionalData);
                        return threeElements;

                    }
                }).toList().map(new Func1<List<ThreeElements>, DefaultListModel<ThreeElements>>() {
            @Override
            public DefaultListModel<ThreeElements> call(List<ThreeElements> threeElementses) {
                DefaultListModel<ThreeElements> defaultListModel = new DefaultListModel<ThreeElements>();
                for (ThreeElements e1 : threeElementses) defaultListModel.addElement(e1);
                return defaultListModel;
            }
        });
    }

    private void fillWithdata() {
        Utils.log("fillWithdata");

        getModelForStatistics().subscribeOn(Schedulers.computation())
                .subscribe(new Action1<DefaultListModel<ThreeElements>>() {
                    @Override
                    public void call(DefaultListModel<ThreeElements> statisticsModel) {
                        statisticsList.setModel(statisticsModel);

                        statisticsList.setCellRenderer(new ListCellRenderer<ThreeElements>() {
                            @Override
                            public Component getListCellRendererComponent(JList<? extends ThreeElements> list, ThreeElements three, int index, boolean isSelected, boolean cellHasFocus) {

                                ThreeElements nextListInARow = list.getModel().getElementAt(index);

                                return createComponent(nextListInARow);
                            }
                        });
                    }
                }, error());

//        for (Pair<Event, List<Boolean>> pair : model.sliced) {
//            List<Boolean> timestampsWhichAgreesWithEvent = pair.getValue();
//            Event nextEvent = pair.getKey();
//            Utils.log("processing");
//
//            float[] existanceAndNonExistanceOfEvent = model.getPercentageStatistics(timestampsWhichAgreesWithEvent);
//            int[] longestEventLengthAndOccurence = model.getLongestEventLengthAndOccurence(timestampsWhichAgreesWithEvent);
//
//            int propertyId = nextEvent.getColumnIndex();
//            String propertyName = model.properties.get(propertyId);
//            String labelForStrip = nextEvent.eventNameFor(propertyName);
//
//            //todo: add more statistical data here
//            String additionalData = model.optionalJudgement(timestampsWhichAgreesWithEvent);
//
//            String statistics = String.format(" %.2f %%, %.2f %% %d, %d ",
//                    existanceAndNonExistanceOfEvent[0], existanceAndNonExistanceOfEvent[1], longestEventLengthAndOccurence[0],
//                    longestEventLengthAndOccurence[1]);
//
//            ThreeElements threeElements = new ThreeElements(labelForStrip, statistics, additionalData);
//            statisticsModel.addElement(threeElements);
//            yellowBoxesModel.addElement(labelForStrip);
//
//        }
        getModelForStrips().subscribeOn(Schedulers.computation()).subscribe(new Action1<DefaultListModel<String>>() {
            @Override
            public void call(DefaultListModel<String> stringDefaultListModel) {
                yellowBoxesList.setModel(stringDefaultListModel);
                yellowBoxesList.setCellRenderer(new ListCellRenderer<String>() {
                    @Override
                    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
                        JPanel c = new JPanel(new FlowLayout(FlowLayout.LEADING));
                        c.add(new JLabel(value));
                        List<Boolean> stripData = model.sliced.get(index).getValue();
                        c.add(buildSingleStrip(stripData));
                        return c;
                    }
                });
            }
        }, error());
    }

    private Component createComponent(ThreeElements nextListInARow) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel a = new JLabel(nextListInARow.a, SwingConstants.LEFT);
        JLabel b = new JLabel(nextListInARow.b, SwingConstants.LEFT);
        JLabel c = new JLabel(nextListInARow.c, SwingConstants.RIGHT);

        b.setForeground(Color.gray);

        panel.add(a);
        panel.add(b);
        panel.add(c);

        return panel;
    }

    private Action1<Throwable> error() {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Utils.log("throwable: " + throwable.getMessage());
                throwable.printStackTrace();
            }
        };
    }

    private ResultCanvas buildSingleStrip(List<Boolean> data) {
        return buildSingleMaybeYellowStrip(data, Color.YELLOW);
    }

    private ResultCanvas buildSingleMaybeYellowStrip(List<Boolean> data, Color color) {
        ResultCanvas resultCanvas = new ResultCanvas.Builder()
                .setColor(color)
                .setData(data)
                .setPosition(new Point(20, 20))
                .build();
        return resultCanvas;
    }
}

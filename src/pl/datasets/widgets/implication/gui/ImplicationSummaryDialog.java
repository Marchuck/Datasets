package pl.datasets.widgets.implication.gui;

import pl.datasets.utils.ThreeElements;
import pl.datasets.utils.Utils;
import pl.datasets.widgets.ResultCanvas;
import pl.datasets.widgets.event_search.EventSearchSummaryDialog;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz
 * @since 15.09.2016.
 */
public class ImplicationSummaryDialog extends EventSearchSummaryDialog {


    // private EventSearchSummaryModel model;

    private List<List<Long>> implicationData;

    public ImplicationSummaryDialog(List<List<Long>> implicationData, int[] indexesThatShouldBeExposed) {
        super();
        Utils.log("ImplicationSummaryDialog");
        setContentPane(rootPanel);
        //setPreferredSize(new Dimension(300, 200));
        this.implicationData = implicationData;
        fillWithdata();
        setMinimumSize(new Dimension(300, 200));
        setLocation(300, 300);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }


    Observable<DefaultListModel<String>> getModelForStrips() {
        return Observable.from(implicationData)
                .map(new Func1<List<Long>, String>() {
                    @Override
                    public String call(List<Long> longs) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < longs.size() - 1; i++) {
                            sb.append(longs.get(i)).append(", ");
                        }
                        sb.append(longs.get(longs.size() - 1));
                        return sb.toString();
                    }
                }).toList().map(new Func1<List<String>, DefaultListModel<String>>() {
                    @Override
                    public DefaultListModel<String> call(List<String> strings) {
                        DefaultListModel<String> def = new DefaultListModel<String>();
                        for (String str : strings) def.addElement(str);
                        return def;
                    }
                });
    }

    Observable<DefaultListModel<ThreeElements>> getModelForStatistics() {
        return Observable.from(implicationData)
                .map(new Func1<List<Long>, ThreeElements>() {
                    @Override
                    public ThreeElements call(List<Long> longs) {
                        //todo: list long display as three values
                        String property1 = "";
                        String property2 = "";
                        String property3 = "";
                        return new ThreeElements(property1, property2, property3);
                    }
                }).toList().map(new Func1<List<ThreeElements>, DefaultListModel<ThreeElements>>() {
                    @Override
                    public DefaultListModel<ThreeElements> call(List<ThreeElements> threeElementses) {
                        DefaultListModel<ThreeElements> elementsDefaultListModel = new DefaultListModel<ThreeElements>();
                        for (ThreeElements e : threeElementses) elementsDefaultListModel.addElement(e);
                        return elementsDefaultListModel;
                    }
                });
    }

    private void fillWithdata() {
        Utils.log("fillWithdata");

        getModelForStatistics().subscribeOn(Schedulers.trampoline())
                .subscribe(new Action1<DefaultListModel<ThreeElements>>() {
                    @Override
                    public void call(DefaultListModel<ThreeElements> threeElementsDefaultListModel) {
                        statisticsList.setModel(threeElementsDefaultListModel);
                        statisticsList.setCellRenderer(new ListCellRenderer<ThreeElements>() {
                            @Override
                            public Component getListCellRendererComponent(JList<? extends ThreeElements> list, ThreeElements three, int index, boolean isSelected, boolean cellHasFocus) {

                                ThreeElements nextListInARow = list.getModel().getElementAt(index);

                                return createComponent(nextListInARow);
                            }
                        });
                    }
                }, error());

        getModelForStrips().subscribeOn(Schedulers.computation()).subscribe(new Action1<DefaultListModel<String>>() {
            @Override
            public void call(DefaultListModel<String> stringDefaultListModel) {
                yellowBoxesList.setModel(stringDefaultListModel);
                yellowBoxesList.setCellRenderer(new ListCellRenderer<String>() {
                    @Override
                    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
                        JPanel c = new JPanel(new FlowLayout(FlowLayout.LEADING));
                        c.add(new JLabel(value));
                        //List<Boolean> stripData = model.sliced.get(index).getValue();
                        for (Long l : implicationData.get(index)) {
                            System.out.println("," + l.intValue());
                        }
                        System.out.println();

                        List<Integer> stripData = getLongs(implicationData.get(index));

                        c.add(buildSingleStrip(stripData));
                        return c;
                    }
                });
            }
        }, error());

        setGeneralizedStatisticsText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, \n" +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad \n" +
                "minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea \n" +
                "commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit \n" +
                "esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat \n" +
                "non proident, sunt in culpa qui officia deserunt mollit anim id est laborum");
    }


    /**
     * code sucks but works.
     * given list of selected timestamps: a1, a2, ... , an
     * construct list 0, 0, ... , 0, b1, 0, ...0,  b2, 0, ...0 , b3, 0,... , ..., bn
     * where b_i is the equivalent of a1 and incremented one by one
     *
     * @param longs
     * @return
     */
    private List<Integer> getLongs(List<Long> longs) {
        List<Integer> inties = new ArrayList<>();

        int l = 0;
        int k = 0;
        for (int i = 0; i < longs.get(longs.size() - 1); i++) {
            if (longs.get(k) == i) {
                inties.add(++l);
                ++k;
            } else {
                inties.add(0);
            }
        }

        return inties;
    }
//
//    private List<List<Boolean>> getListListBooleans(List<Long> implicationData) {
//        List<List<Boolean>> b = new ArrayList<>();
//        for (List<Long> nextList : implicationData) {
//            List<Boolean> lb = new ArrayList<>();
//            if (nextList.size() == 0) {
//                continue;
//            }
//            int lastIndex = nextList.size() - 1;
//            Utils.log("lastIndex: " + lastIndex);
//            int lastElement = nextList.get(lastIndex).intValue();
//            Utils.log("lastElement: " + lastElement);
//            int k = 0;
//            for (int j = 0; j < lastElement; j++) {
//                if (nextList.get(k).intValue() == j) {
//                    lb.add(true);
//
//                    if (k < nextList.size()) ++k;
//                    else break;
//                } else lb.add(false);
//
//            }
//            b.add(lb);
//        }
//        Utils.log("returning " + b.size() + " elements");
//        return b;
//    }

    private void setGeneralizedStatisticsText(String s) {
        detailedTextArea.append(s);
//        additionalText.setText(s);
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


    private ResultCanvas buildSingleStrip(List<Integer> data) {
        ResultCanvas resultCanvas = new ResultCanvas.Builder()
                .setManyData(data)
                .setPreferredWidth(400)
                .setPosition(new Point(20, 20))
                .build();
        return resultCanvas;
    }
}

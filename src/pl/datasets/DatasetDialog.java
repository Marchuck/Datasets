package pl.datasets;

import com.sun.istack.internal.Nullable;
import javafx.util.Pair;
import pl.datasets.model.BeforeAfterPair;
import pl.datasets.model.DatasetItem;
import pl.datasets.trend_match.TrendingSubsetWrapper;
import pl.datasets.utils.Event;
import pl.datasets.utils.Utils;
import pl.datasets.widgets.*;
import pl.datasets.widgets.event_search.ComputeButtonBehaviour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public abstract class DatasetDialog extends JFrame implements ComputeButtonBehaviour {

    //    private List<Event> events = new ArrayList<>();
    private JPanel panel1;
    private JButton addButton;
    private JButton computeButton;
    private JList<Event> operationsList;
    private JButton beforeAfterButton;
    protected List<DatasetItem> datasetItems;
    private SelectOperationDialog selectOperationDialog;
    private String[] properties;
    private DefaultListModel<Event> model = new DefaultListModel<>();

    public DatasetDialog(String path, List<DatasetItem> datasetItems, String[] propertyNames, String[] operations) {
        super(path);
        setContentPane(panel1);
        this.datasetItems = datasetItems;
        this.properties = getAttributeNames(propertyNames);
        selectOperationDialog = new SelectOperationDialog(properties, operations);
        init();
        setMinimumSize(new Dimension(300, 200));
        setPreferredSize(new Dimension(300, 200));
        pack();
        setLocation(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * @return list of properties of this dataset
     * NOTE: first item SHOULD BE omitted because it's timestamp
     */
    private static String[] getAttributeNames(String[] _properties) {
//        String[] props = new String[_properties.length - 1];
//        System.arraycopy(_properties, 1, props, 0, props.length);
        /**HOT FIX*/
        return _properties;
    }

    private void init() {
        setupComputeButton();
        setupAddButton();
        setupBeforeAfterButton();
        setupList();
    }

    //todo: JOHANNES implement for n elements
    private void setupBeforeAfterButton() {
        beforeAfterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TwoEventsDialog().chooseTwo(beforeAfterButton, getEventsFromModel(), new ItemCallback<Pair<Event, Event>>() {
                    @Override
                    public void call(Pair<Event, Event> event) {
                        TrendingSubsetWrapper wrapper = TrendingSubsetWrapper.getInstance(datasetItems);
                        List<BeforeAfterPair> beforeAfterPairs = wrapper.findAfter(event.getKey(), event.getValue(), 8);
                        for (BeforeAfterPair a : beforeAfterPairs) {
                            Utils.log(a.toString());
                        }
                    }
                });
            }
        });
    }

    private void setupComputeButton() {


        //todo: JOHANNES
        /**
         * put implementation above to class extending from this class:
         * ({@link pl.datasets.widgets.event_search.EventBasedDatasetDialog})
         *              OR
         * ({@link pl.datasets.widgets.implication.ImplicationDatasetDialog})
         **/
        computeButton.addActionListener(computeButtonClickListener());
    }

    /**
     * @return events extracted from list's model
     */
    protected List<Event> getEventsFromModel() {
        List<Event> events = new ArrayList<>();
        for (int j = 0; j < model.size(); j++) events.add(model.getElementAt(j));
        return events;
    }

    private void setupAddButton() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectOperationDialog.displayAddEventDialog(datasetItems, addButton, new ItemCallback<Event>() {
                    @Override
                    public void call(@Nullable Event event) {
                        if (event == null) return;
                        Utils.log("Adding event: " + event.toString());
                        addElementToList(event);
                    }
                });
            }
        });
    }

    private void setupList() {
        Utils.log("setupList()");
        //bind list with data
        operationsList.setModel(model);
        //optional: change default appearance of list's cells
        operationsList.setCellRenderer(new EventRenderer(properties));
        operationsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        operationsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                int index = list.locationToIndex(evt.getPoint());
                if (index > -1) {

                    if (evt.getClickCount() == 2) {
                        Utils.log("double click");
                        /***DOUBLE CLICK - REMOVE {@link Event} from list*/
                        Utils.log("clicked twice on " + index);
                        removeElementFromList(index);
                    }
                    if (evt.getClickCount() == 1) {
                        Utils.log("click");

                        /***SINGLE CLICK - EDIT {@link Event} from list*/

                        Utils.log("clicked " + index);
                        buildEditEventDialog(index);
                    }
                }
            }
        });
    }

    private void addElementToList(Event event) {
        Utils.log("addElementToList(" + event.toString() + ")");
        model.addElement(event);
        operationsList.invalidate();
    }

    private void removeElementFromList(int indexOfItem) {
        Utils.log("removeElementFromList(" + indexOfItem + ")");
        model.remove(indexOfItem);
        operationsList.invalidate();
    }

    private void buildEditEventDialog(final int indexOfSelectedItem) {
        Utils.log("buildEditEventDialog(" + indexOfSelectedItem + ")");
        selectOperationDialog.displayEditEventDialog(datasetItems, addButton, new ItemCallback<Event>() {
            public void call(Event event) {
                if (event == null) {
                    model.remove(indexOfSelectedItem);
                    operationsList.invalidate();
                } else {
                    model.set(indexOfSelectedItem, event);
                    operationsList.invalidate();
                }
            }
        });
    }
}

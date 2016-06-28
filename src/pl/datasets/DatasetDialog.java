package pl.datasets;

import pl.datasets.model.DatasetItem;
import pl.datasets.trend_match.TrendingSubsetWrapper;
import pl.datasets.utils.Event;
import pl.datasets.utils.Utils;
import pl.datasets.widgets.EventRenderer;
import pl.datasets.widgets.ItemCallback;
import pl.datasets.widgets.ResultsEntity;
import pl.datasets.widgets.SelectOperationDialog;

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
public class DatasetDialog extends JFrame {

    //    private List<Event> events = new ArrayList<>();
    private JPanel panel1;
    private JButton addButton;
    private JButton computeButton;
    private JList<Event> operationsList;
    private List<DatasetItem> items;
    private SelectOperationDialog selectOperationDialog;
    private String[] properties;
    private DefaultListModel<Event> model = new DefaultListModel<>();

    public DatasetDialog(String path, List<DatasetItem> items, String[] propertyNames, String[] operations) {
        super(path);
        setContentPane(panel1);
        this.items = items;
        this.properties = getAttributeNames(propertyNames);
        selectOperationDialog = new SelectOperationDialog(properties, operations);
        init();
        setMinimumSize(new Dimension(300, 200));
        setPreferredSize(new Dimension(300, 200));
        pack();
        setLocation(300,300);
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
        setupList();
    }

    private void setupComputeButton() {
        computeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TrendingSubsetWrapper wrapper = TrendingSubsetWrapper.getInstance(items);
                wrapper.setMinTrendLength(2);
                List<List<Long>> results = wrapper.getTrends(getEventsFromModel(), false);
                new ResultsEntity().bind(results);
            }
        });
    }

    /**
     * @return events extracted from list's model
     */
    private List<Event> getEventsFromModel() {
        List<Event> events = new ArrayList<>();
        for (int j = 0; j < model.size(); j++) events.add(model.getElementAt(j));
        return events;
    }

    private void setupAddButton() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectOperationDialog.displayAddEventDialog(addButton, new ItemCallback<Event>() {
                    @Override
                    public void call(Event event) {
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
                if (evt.getClickCount() == 2) {

                    /***DOUBLE CLICK - REMOVE {@link Event} from list*/

                    int index = list.locationToIndex(evt.getPoint());
                    Utils.log("clicked twice on " + index);
                    removeElementFromList(index);
                } else if (evt.getClickCount() == 1) {

                    /***SINGLE CLICK - EDIT {@link Event} from list*/

                    int index = list.locationToIndex(evt.getPoint());
                    Utils.log("clicked " + index);
                    buildEditEventDialog(index);
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
        selectOperationDialog.displayEditEventDialog(addButton, new ItemCallback<Event>() {
            public void call(Event event) {
                model.set(indexOfSelectedItem, event);
                operationsList.invalidate();
            }
        });
    }
}

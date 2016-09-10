package pl.datasets.widgets;

/**
 * @author Lukasz
 * @since 22.06.2016.
 */

import com.sun.istack.internal.Nullable;
import javafx.util.Pair;
import pl.datasets.model.DatasetExtensions;
import pl.datasets.model.DatasetItem;
import pl.datasets.model.StringComboBoxModel;
import pl.datasets.utils.Event;
import pl.datasets.utils.Strategies;
import pl.datasets.utils.Utils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SelectOperationDialog extends JPanel {

    private Event currentEvent;
    private String currentStrategyName = "";
    private double currentThreshold = 40;
    private String[] propertiesToSelect, operations;

    public SelectOperationDialog() {
        //no-op
    }

    public SelectOperationDialog(String[] propertiesToSelect, String[] operations) {
        this.propertiesToSelect = propertiesToSelect;
        this.operations = operations;
    }

    private static Object[] addSpinner(String label, SpinnerModel model, JPanel root) {
        JPanel c = new JPanel(new GridLayout(2, 1));
        JLabel l = new JLabel(label);
        c.add(l);
        JSpinner spinner = new JSpinner(model);
        l.setLabelFor(spinner);
        c.add(spinner);
        root.add(c);
        return new Object[]{spinner, model};
    }

    public static JComboBox<String> addComboBox(String label, ComboBoxModel<String> model, JPanel root) {
        JPanel c = new JPanel(new GridLayout(2, 1));
        JLabel jLabel = new JLabel(label);
        c.add(jLabel);
        JComboBox<String> comboBox = new JComboBox<>(model);
        jLabel.setLabelFor(comboBox);
        c.add(comboBox);
        root.add(c);
//        layout.putConstraint(SpringLayout.WEST, comboBox, 5, SpringLayout.WEST, jLabel);
        return comboBox;
    }

    public void displayEditEventDialog(java.util.List<DatasetItem> datasetItemList, JComponent parent, final ItemCallback<Event> eventItemCallback) {

        displayAddEventDialog(datasetItemList, parent, "Edit", eventItemCallback);
    }

    public void displayAddEventDialog(java.util.List<DatasetItem> datasetItemList, JComponent parent, final ItemCallback<Event> eventCallable) {
        displayAddEventDialog(datasetItemList, parent, null, eventCallable);
    }

    public void displayAddEventDialog(final java.util.List<DatasetItem> datasetItemList, JComponent parent, @Nullable String additionalButton,
                                      final ItemCallback<Event> eventCallable) {
        //main dialog
        if (currentEvent == null) currentEvent = new Event();
        final JDialog mainDialog = new JDialog();
        //main layout
        FlowLayout layout = new FlowLayout();
        //sub-panel
        JPanel rootPanel = new JPanel(layout);
        //first combo-box: choose
        final ComboBoxModel<String> comboPropertiesModel = new StringComboBoxModel(propertiesToSelect);
        final JComboBox<String> comboProperty = addComboBox("Property", comboPropertiesModel, rootPanel);
        final Object[] spinnerData =
                addSpinner("", new SpinnerNumberModel(40f, 0f, 100f, 1f), rootPanel);
        final JSpinner valueRangeSpinner = (JSpinner) spinnerData[0];
        final SpinnerNumberModel spinnerModel = (SpinnerNumberModel) spinnerData[1];

        comboProperty.setMinimumSize(new Dimension(50, 15));
        comboProperty.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Utils.log("itemStateChanged(" + e.getItem() + ")");
                currentEvent.setColumnIndex(-1);
                for (int j = 0; j < comboProperty.getModel().getSize(); j++) {
                    String ss = comboProperty.getModel().getElementAt(j);
                    if (ss.equalsIgnoreCase((String) e.getItem())) {
                        currentEvent.setColumnIndex(j);

                        Pair<Double, Double> minAndMax = DatasetExtensions
                                .getPropertyRange(j, datasetItemList);
                        double min = minAndMax.getKey();
                        double max = minAndMax.getValue();
                        spinnerModel.setMinimum(min);
                        spinnerModel.setMaximum(max);
                        spinnerModel.setValue((min + max) / 2);
                    }
                }
            }
        });

        ComboBoxModel<String> comboOperationsModel = new StringComboBoxModel(operations);
        final JComboBox<String> comboOperation = addComboBox("Operation", comboOperationsModel, rootPanel);

        comboOperation.setMinimumSize(new Dimension(50, 15));
        comboOperation.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Utils.log("itemStateChanged(" + e.getItem() + ")");
                currentStrategyName = (String) e.getItem();
                currentEvent.setStrategy(Strategies.recognizeStrategy((String) e.getItem(), 22));
            }
        });


        valueRangeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Utils.log("spinnerValue onChanged " + valueRangeSpinner.getModel().getValue());
                if (valueRangeSpinner.getModel() instanceof SpinnerNumberModel) {
                    currentThreshold = ((Double) valueRangeSpinner.getModel().getValue());
                } else currentThreshold = -1;
            }
        });

        JButton jbutton;
        if (additionalButton == null) {
            jbutton = new JButton("Add");
        } else {
            JButton btn = new JButton("Remove");
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    eventCallable.call(null);
                    currentEvent = null;
                    mainDialog.dispose();
                    mainDialog.setVisible(false);
                }
            });
            rootPanel.add(btn);
            jbutton = new JButton("Edit");
        }
        jbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentEvent.setStrategy(Strategies.recognizeStrategy(currentStrategyName, currentThreshold));
                eventCallable.call(currentEvent);
                currentEvent = null;
                mainDialog.dispose();
                mainDialog.setVisible(false);
            }
        });

        rootPanel.add(jbutton);
        mainDialog.setMinimumSize(new Dimension(300, 200));
        mainDialog.setPreferredSize(new Dimension(300, 200));
        mainDialog.add(rootPanel);
        mainDialog.pack();
        mainDialog.setBounds(parent.getX() + 100, parent.getY() + 100, 450, 300);
        mainDialog.setVisible(true);
    }
}

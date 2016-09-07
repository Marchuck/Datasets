package pl.datasets.widgets;

import javafx.util.Pair;
import pl.datasets.model.StringComboBoxModel;
import pl.datasets.utils.Event;
import pl.datasets.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author Lukasz
 * @since 28.06.2016.
 */
public class TwoEventsDialog {

    private Event event1;

    private Event event2;

    public void chooseTwo(JComponent parent, final java.util.List<Event> eventList, final ItemCallback<Pair<Event, Event>> eventsCallback) {
        //main dialog
        final JDialog mainDialog = new JDialog();
        //main layout
        FlowLayout layout = new FlowLayout();
        //sub-panel
        JPanel rootPanel = new JPanel(layout);
        //first combo-box: choose
        String[] eventListStrings = new String[eventList.size()];
        for (int j = 0; j < eventList.size(); j++) {
            eventListStrings[j] = eventList.get(j).toString();
        }

        final ComboBoxModel<String> comboPropertiesModel = new StringComboBoxModel(eventListStrings);
        final JComboBox<String> comboProperty = SelectOperationDialog.addComboBox("First Event", comboPropertiesModel, rootPanel);
        comboProperty.setMinimumSize(new Dimension(50, 15));
        comboProperty.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Utils.log("itemStateChanged(" + e.getItem() + ")");
                event1 = null;
                for (int j = 0; j < comboProperty.getModel().getSize(); j++) {
                    String ss = comboProperty.getModel().getElementAt(j);
                    if (ss.equalsIgnoreCase((String) e.getItem())) {
                        event1 = eventList.get(j);
                    }
                }
            }
        });

        final JComboBox<String> comboProperty2 = SelectOperationDialog.addComboBox("Second Event", comboPropertiesModel, rootPanel);
        comboProperty2.setMinimumSize(new Dimension(50, 15));
        comboProperty2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Utils.log("itemStateChanged(" + e.getItem() + ")");
                event2 = null;
                for (int j = 0; j < comboProperty.getModel().getSize(); j++) {
                    String ss = comboProperty.getModel().getElementAt(j);
                    if (ss.equalsIgnoreCase((String) e.getItem())) {
                        event2 = eventList.get(j);
                    }
                }
            }
        });
        JButton jbutton = new JButton("Choose");
        final JLabel label = new JLabel("");
        label.setForeground(Color.red);
        jbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (event1 == null || event2 == null) {
                    label.setText("Choose 2 events!");
                    return;
                }
                eventsCallback.call(new Pair<>(event1, event2));
                event1 = null;
                event2 = null;
                mainDialog.dispose();
                mainDialog.setVisible(false);
            }
        });
        rootPanel.add(jbutton);
        rootPanel.add(label);

        mainDialog.setMinimumSize(new Dimension(300, 200));
        mainDialog.setPreferredSize(new Dimension(300, 200));
        mainDialog.add(rootPanel);
        mainDialog.pack();
        mainDialog.setBounds(parent.getX() + 100, parent.getY() + 100, 450, 300);
        mainDialog.setVisible(true);
    }
}

package pl.datasets.widgets;

/**
 * @author Lukasz
 * @since 22.06.2016.
 */

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

public class Spinners extends JPanel {

    private Event currentEvent = new Event();
    private String currentStrategyName = "";
    private double currentThreshold;

    /**
     * DateFormatSymbols returns an extra, empty value at the
     * end of the array of months.  Remove it.
     */
    static protected String[] getMonthStrings() {
        String[] months = new java.text.DateFormatSymbols().getMonths();
        int lastIndex = months.length - 1;

        if (months[lastIndex] == null
                || months[lastIndex].length() <= 0) { //last item empty
            String[] monthStrings = new String[lastIndex];
            System.arraycopy(months, 0,
                    monthStrings, 0, lastIndex);
            return monthStrings;
        } else { //last item not empty
            return months;
        }
    }

    static protected JSpinner addLabeledSpinner(Container c,
                                                String label,
                                                SpinnerModel model) {
        JLabel l = new JLabel(label);
        c.add(l);

        JSpinner spinner = new JSpinner(model);
        l.setLabelFor(spinner);
        c.add(spinner);

        return spinner;
    }

    private static JSpinner addSpinner(String label, SpinnerModel model, JPanel root) {
        JPanel c = new JPanel(new GridLayout(2, 1));
        JLabel l = new JLabel(label);
        c.add(l);
        JSpinner spinner = new JSpinner(model);
        l.setLabelFor(spinner);
        c.add(spinner);
        root.add(c);
        return spinner;
    }

    private static JComboBox<String> addCombobox(String label, ComboBoxModel<String> model, JPanel root) {
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

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Spinners");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
//        frame.add(new Spinners(false));

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }

    public void display(JComponent parent, String[] propertiesToSelect, String[] operations, final Caller<Event> eventCallable) {
        //main dialog
        final JDialog mainDialog = new JDialog();
        //main layout
        FlowLayout layout = new FlowLayout();
        //sub-panel
        JPanel rootPanel = new JPanel(layout);
        //first combo-box: choose
        final ComboBoxModel<String> comboPropertiesModel = new StringComboBoxModel(propertiesToSelect);
        final JComboBox<String> comboProperty = addCombobox("Property", comboPropertiesModel, rootPanel);
        comboProperty.setMinimumSize(new Dimension(50, 15));
        comboProperty.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Utils.log("itemStateChanged(" + e.getItem() + ")");
                currentEvent.setColumnIndex(-1);
                for (int j = 0; j < comboProperty.getModel().getSize(); j++) {
                    String ss = comboProperty.getModel().getElementAt(j);
                    if (ss.equalsIgnoreCase((String) e.getItem()))
                        currentEvent.setColumnIndex(j);
                }
            }
        });

        ComboBoxModel<String> comboOperationsModel = new StringComboBoxModel(operations);
        final JComboBox<String> comboOperation = addCombobox("Operation", comboOperationsModel, rootPanel);

        comboOperation.setMinimumSize(new Dimension(50, 15));
        comboOperation.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Utils.log("itemStateChanged(" + e.getItem() + ")");
                currentStrategyName = (String) e.getItem();
                currentEvent.setStrategy(Strategies.recognizeStrategy((String) e.getItem(), 22));
            }
        });

        final JSpinner spinnerValue = addSpinner("", new SpinnerNumberModel(40f, 0f, 100f, 1f), rootPanel);

        spinnerValue.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Utils.log("onChanged " + spinnerValue.getModel().getValue());
                if (spinnerValue.getModel() instanceof SpinnerNumberModel) {
                    currentThreshold = ((Double) spinnerValue.getModel().getValue());

                } else currentThreshold = -1;
            }
        });
        JButton jbutton = new JButton("Add");
        jbutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                currentEvent.setStrategy(Strategies.recognizeStrategy(currentStrategyName, currentThreshold));
                eventCallable.call(currentEvent);
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

package pl.datasets.widgets;

/**
 * @author Lukasz
 * @since 22.06.2016.
 */

import pl.datasets.utils.Event;
import pl.datasets.utils.OperationManager;
import pl.datasets.utils.Utils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

public class Spinners extends JPanel {


    public static void display(JComponent parent, String[] propertiesToSelect, final Caller<Event> eventCallable) {
        final JDialog jd = new JDialog();
        final Event currentEvent = new Event();
        JPanel rootPanel = new JPanel(new GridLayout(3, 2));
        SpinnerListModel propertiesModel = new SpinnerListModel(propertiesToSelect);

        final JSpinner spinnerProperty = addSpinner("Property", propertiesModel, rootPanel);
        spinnerProperty.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Utils.log("onChanged " + spinnerProperty.getModel().getValue());
                SpinnerModel model = spinnerProperty.getModel();
                if (model instanceof SpinnerListModel) {
                    java.util.List<?> list = ((SpinnerListModel) model).getList();
                    for (int j = 0; j < list.size(); j++) {
                        if (list.get(j).equals(model.getValue())) {
                            //todo: after change this value, the spinnerValue should be changed accordingly
                            currentEvent.columnIndex = j;
                        }
                    }
                }
            }
        });
        final JSpinner spinnerOperation = addSpinner("Operation", new SpinnerListModel(new String[]{"[]", "#", "~", "-", "<>"}), rootPanel);
        spinnerOperation.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Utils.log("onChanged " + spinnerOperation.getModel().getValue());
                SpinnerModel model = spinnerOperation.getModel();
                if (model instanceof SpinnerListModel) {
                    currentEvent.operation = OperationManager.create(model.getValue());
                }
            }
        });
        final JSpinner spinnerValue = addSpinner("", new SpinnerNumberModel(40, 0, 100, 1), rootPanel);
        spinnerValue.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Utils.log("onChanged " + spinnerValue.getModel().getValue());
                if (spinnerValue.getModel() instanceof SpinnerNumberModel) {
                    currentEvent.value = (int) spinnerValue.getModel().getValue();
                } else currentEvent.value = -1;
            }
        });
        JButton jbutton = new JButton("Add");
        jbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventCallable.call(currentEvent);
                jd.setVisible(false);
            }
        });
        rootPanel.add(jbutton);

        jd.add(rootPanel);
        jd.pack();
        jd.setBounds(parent.getX() + 100, parent.getY() + 100, 450, 300);
        jd.setVisible(true);
    }

    public Spinners(String[] propertiesToSelect, Caller<Event> selectedEvent) {
        super(new SpringLayout());

        String[] labels = {"Month: ", "Year: ", "Another Date: "};
        int numPairs = labels.length;
        JFormattedTextField ftf = null;

        //Add the first label-spinner pair.
//        String[] monthStrings = getMonthStrings(); //get month names
        SpinnerListModel monthModel = null;
//        if (cycleMonths) { //use custom model
//        monthModel = new CyclingSpinnerListModel(propertiesToSelect);
//        }
//        else { //use standard model
        monthModel = new SpinnerListModel(propertiesToSelect);
//        }
        JSpinner spinner = addLabeledSpinner(this,
                labels[0],
                monthModel);
        //Tweak the spinner's formatted text field.
        ftf = getTextField(spinner);
        if (ftf != null) {
            ftf.setColumns(8); //specify more width than we need
            ftf.setHorizontalAlignment(JTextField.RIGHT);
        }

        Calendar calendar = Calendar.getInstance();
        //Add second label-spinner pair.
        int currentYear = calendar.get(Calendar.YEAR);
        SpinnerModel yearModel = new SpinnerNumberModel(currentYear, //initial value
                currentYear - 100, //min
                currentYear + 100, //max
                1);                //step
        //If we're cycling, hook this model up to the month model.
        if (monthModel instanceof CyclingSpinnerListModel) {
            ((CyclingSpinnerListModel) monthModel).setLinkedModel(yearModel);
        }
        spinner = addLabeledSpinner(this, labels[1], yearModel);
        //Make the year be formatted without a thousands separator.
        spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));

        //Add the third label-spinner pair.
        Date initDate = calendar.getTime();
        calendar.add(Calendar.YEAR, -100);
        Date earliestDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 200);
        Date latestDate = calendar.getTime();
        SpinnerModel dateModel = new SpinnerDateModel(initDate,
                earliestDate,
                latestDate,
                Calendar.YEAR);//ignored for user input
        spinner = addLabeledSpinner(this, labels[2], dateModel);
        spinner.setEditor(new JSpinner.DateEditor(spinner, "MM/yyyy"));

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(this,
                numPairs, 2, //rows, cols
                10, 10,        //initX, initY
                6, 10);       //xPad, yPad
    }

    /**
     * Return the formatted text field used by the editor, or
     * null if the editor doesn't descend from JSpinner.DefaultEditor.
     */
    public JFormattedTextField getTextField(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            return ((JSpinner.DefaultEditor) editor).getTextField();
        } else {
            System.err.println("Unexpected editor type: "
                    + spinner.getEditor().getClass()
                    + " isn't a descendant of DefaultEditor");
            return null;
        }
    }

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

    static protected JSpinner addSpinner(String label, SpinnerModel model, JPanel root) {
        JPanel c = new JPanel(new GridLayout(2, 1));
        JLabel l = new JLabel(label);
        c.add(l);
        JSpinner spinner = new JSpinner(model);
        l.setLabelFor(spinner);
        c.add(spinner);
        root.add(c);
        return spinner;
    }


    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public static JPanel createSelectPropertyFrame(String[] options, Caller<Event> eventCaller) {
        return new Spinners(options, eventCaller);
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
}

package pl.datasets;

import pl.datasets.model.DatasetItem;
import pl.datasets.trend_match.TrendingSubsetWrapper;
import pl.datasets.utils.Event;
import pl.datasets.utils.Utils;
import pl.datasets.widgets.Caller;
import pl.datasets.widgets.Spinners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public class DatasetDialog extends JFrame {

    private static int index = 0;
    List<Event> events = new ArrayList<>();
    private JPanel panel1;
    private JButton addButton;
    private JPanel current_entry;
    private JButton computeButton;
    private String[] properties;
    private String[] operations;
    private JLabel jlabel;
    private List<DatasetItem> items;
    private Spinners spinnersDialog = new Spinners();
    private Color[] colors = new Color[]{Color.cyan, Color.yellow, Color.green, Color.orange, Color.pink, Color.lightGray};

    public DatasetDialog(String path, List<DatasetItem> items, String[] properyNames, String[] operations) {
        super(path);
        setContentPane(panel1);
        this.items = items;
        this.operations = operations;
        properties = getAttributeNames(properyNames);
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(300, 200));
        setPreferredSize(new Dimension(300, 200));
        pack();
        setVisible(true);
        init();
    }

    private void init() {
        computeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TrendingSubsetWrapper wrapper = TrendingSubsetWrapper.getInstance(items);
                wrapper.setMinTrendLength(1);
                wrapper.getTrends(TrendingSubsetWrapper.wrap(events));
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spinnersDialog.display(addButton, properties, operations, new Caller<Event>() {
                    @Override
                    public void call(Event event) {

                        Utils.log(event.toString());

                        events.add(event);

                        if (jlabel == null) {
                            jlabel = new JLabel(event.toString());
                            jlabel.setBackground(colorIndex());
                            current_entry.add(jlabel);
                        } else {
                            jlabel.setText(jlabel.getText() + "\n" + event.toString());
                        }
                        current_entry.revalidate();
                        /*final JButton button = new JButton(event.operationName);
                        button.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                current_entry.remove(jlabel);
                                current_entry.remove(button);
                                current_entry.revalidate();
                                current_entry.repaint();
                            }
                        });
                        current_entry.add(button);
                        current_entry.revalidate();
                        current_entry.repaint();*/
                    }
                });
            }
        });
    }

    private Color colorIndex() {
        index = (index++) % colors.length;
        return colors[index];
    }

    private String[] getAttributeNames(String[] _properties) {
        String[] props = new String[_properties.length - 1];
        System.arraycopy(_properties, 1, props, 0, props.length);
        return props;
    }
}

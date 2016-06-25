package pl.datasets;

import pl.datasets.model.DatasetItem;
import pl.datasets.utils.Event;
import pl.datasets.utils.Utils;
import pl.datasets.widgets.Caller;
import pl.datasets.widgets.Spinners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public class DatasetDialog extends JFrame {

    private static int index = 0;
    private JPanel panel1;
    private JButton addButton;
    private JPanel current_entry;
    private String[] properties;
    private List<DatasetItem> items;
    private Spinners spinnersDialog = new Spinners();
    private Color[] colors = new Color[]{Color.cyan, Color.yellow, Color.green, Color.orange, Color.pink, Color.lightGray};

    public DatasetDialog(String path, List<DatasetItem> items, String[] properyNames) {
        super(path);
        setContentPane(panel1);
        this.items = items;
        properties = getAttributeNames(properyNames);
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        init();
    }

    private void init() {

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spinnersDialog.display(addButton, properties, new Caller<Event>() {
                    @Override
                    public void call(Event event) {
                        Utils.log(event.toString());
                        final JLabel jlabel = new JLabel(event.toString());
                        jlabel.setBackground(colorIndex());
                        current_entry.add(jlabel);
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

package pl.datasets;

import pl.datasets.utils.Event;
import pl.datasets.utils.Utils;
import pl.datasets.widgets.Caller;
import pl.datasets.widgets.Spinners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public class DatasetDialog extends JFrame {

    private JPanel panel1;
    private JButton addButton;
    private JList<?> list1;

    public DatasetDialog(String path) {
        super(path);
        setContentPane(panel1);
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        init();
    }

    private void init() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Spinners.display(addButton,new String[]{"rain", "pressure", "temperature", "humidity"}, new Caller<Event>() {
                    @Override
                    public void call(Event event) {

                        Utils.log(event.toString());
                    }
                });
            }
        });
    }
}

package pl.datasets;

import javax.swing.*;
import java.awt.*;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public class DatasetDialog extends JFrame {

    private JCheckBox checkBox1;
    private JPanel panel1;

    public DatasetDialog(String path) throws HeadlessException {
        super(path);
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}

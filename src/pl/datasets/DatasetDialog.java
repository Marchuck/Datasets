package pl.datasets;

import javax.swing.*;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public class DatasetDialog extends JFrame {

    private JPanel panel1;
    private JButton absenceButton;
    private JButton invarianceButton;
    private JButton existanceButton;
    private JButton responseButton;
    private JButton button1;

    public DatasetDialog(String path)  {
        super(path);
        setContentPane(panel1);
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}

package pl.datasets.widgets.the_very_main_dialog;

import pl.datasets.MainDatasetOperationFrame;
import pl.datasets.widgets.event_search.Behaviour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Lukasz
 * @since 07.09.2016.
 */
public class ChooseAlgorithmDialog extends JFrame {

    private JButton implicationButton;
    private JButton eventSearchButton;
    private JPanel rootPanel;
    private ChooseAlgorithmDialog self;
    private MainDatasetOperationFrame mainDatasetOperationFrame;

    public ChooseAlgorithmDialog() {
        super();
        self = this;
        mainDatasetOperationFrame = new MainDatasetOperationFrame();
        setContentPane(rootPanel);
        initViews();
        initDefaultConfig();
    }

    private void initViews() {
        implicationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainDatasetOperationFrame.getDialog(Behaviour.IMPLICATION);
                self.setVisible(false);
            }
        });
        eventSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainDatasetOperationFrame.getDialog(Behaviour.EVENT_BASED);
                self.setVisible(false);
            }
        });
    }

    public void initDefaultConfig() {
        setMinimumSize(new Dimension(300, 200));
        setPreferredSize(new Dimension(300, 200));
        pack();
        setLocation(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

}

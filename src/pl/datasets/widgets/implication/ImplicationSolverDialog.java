package pl.datasets.widgets.implication;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Lukasz
 * @since 07.09.2016.
 */
public class ImplicationSolverDialog extends JFrame {

    private JPanel rootPanel;
    private JList<List<String>> swingList;

    private DefaultListModel<List<String>> resultedDatasetModel = new DefaultListModel<>();

    public ImplicationSolverDialog(List<List<String>> outputDataset) {
        super();
        setContentPane(rootPanel);

        initViews(outputDataset);
        initDefaultConfig();
    }

    private void initViews(List<List<String>> ouptputDataset) {
        for (List<String> list : ouptputDataset) {
            resultedDatasetModel.addElement(list);
        }
        swingList.setModel(resultedDatasetModel);
        //optional: change default appearance of list's cells
        swingList.setCellRenderer(new ImplicationCellRenderer());
        //swingList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
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

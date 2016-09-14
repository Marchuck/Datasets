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
    private JList<List<Long>> swingList;

    private DefaultListModel<List<Long>> resultedDatasetModel = new DefaultListModel<>();


    public ImplicationSolverDialog(List<List<Long>> outputDataset, List<?> anotherThing, int[] indexes) {
        //todo: JOHANNES put here as many parameters as you to express full raport per item

    }

    public ImplicationSolverDialog(List<List<Long>> outputDataset, int[] indexes) {
        super();
        setContentPane(rootPanel);

        initViews(outputDataset, indexes);
        initDefaultConfig();
    }

    private void initViews(List<List<Long>> ouptputDataset, int[] indexes) {
        for (List<Long> list : ouptputDataset) {
            resultedDatasetModel.addElement(list);
        }
        swingList.setModel(resultedDatasetModel);
        //optional: change default appearance of list's cells
        swingList.setCellRenderer(new ImplicationCellRenderer(indexes));
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

    /**
     * put extra parameters here to display rich-information report
     */
    public static class Builder {
        private List<List<Long>> longList;
        private List<?> iDontKnowList;
        private int[] indexes;
        //todo: JOHANNES DO NOT HESTITATE EDITING THESE PARAMS

        public Builder withIndexes(int[] indexes) {
            this.indexes = indexes;
            return this;
        }

        public Builder withLongList(List<List<Long>> longList) {
            this.longList = longList;
            return this;
        }

        public Builder withIDontKnowList(List<?> iDontKnowList) {
            this.iDontKnowList = iDontKnowList;
            return this;
        }

        public ImplicationSolverDialog build() {
            return new ImplicationSolverDialog(longList, iDontKnowList, indexes);
        }
    }

}

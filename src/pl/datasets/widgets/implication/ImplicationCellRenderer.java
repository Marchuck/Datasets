package pl.datasets.widgets.implication;

import pl.datasets.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author Lukasz
 * @since 07.09.2016.
 */
//todo: change visibility to expose important indexes
public class ImplicationCellRenderer implements ListCellRenderer<List<Long>> {
    private int[] indexesWhichShouldBeExposed;

    public ImplicationCellRenderer() {
    }

    public ImplicationCellRenderer(int[] indexesWhichShouldBeExposed) {
        this.indexesWhichShouldBeExposed = indexesWhichShouldBeExposed;
    }

    private static <T> String printedCollection(Collection<T> collection) {
        if (collection == null)
            return "null";

        int iMax = collection.size() - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        Iterator<T> iterator = collection.iterator();
        for (int i = 0; ; i++) {
            b.append(iterator.next().toString());
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends List<Long>> list,
                                                  List<Long> value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        List<Long> nextListInARow = list.getModel().getElementAt(index);
        int size = nextListInARow.size();
        Utils.log("this row has " + size + " items");
        JPanel panel = new JPanel(new FlowLayout());

        for (int i = 0; i < size; i++) {
            Long item = nextListInARow.get(i);
            String text = String.valueOf(item);
            JLabel label = new JLabel();
            if (shouldBeExposed(i)) {
                label.setText("[" + text + "],");
            } else {
                label.setText(text + ",");
            }
            panel.add(label);
        }
        return panel;
    }

    private boolean shouldBeExposed(int i) {
        for (int x : indexesWhichShouldBeExposed) {
            if (x == i) return true;
        }
        return false;
    }
}

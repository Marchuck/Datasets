package pl.datasets.widgets.implication;

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
public class ImplicationCellRenderer implements ListCellRenderer<List<String>> {
    private int[] indexesWhichShouldBeExposed;

    public ImplicationCellRenderer() {
    }

    public ImplicationCellRenderer(int... indexesWhichShouldBeExposed) {
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
    public Component getListCellRendererComponent(JList<? extends List<String>> list,
                                                  List<String> value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        List<String> nextListInARow = list.getModel().getElementAt(index);

        return new JLabel(printedCollection(nextListInARow));
    }
}

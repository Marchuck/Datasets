package pl.datasets.model;

import pl.datasets.utils.Utils;

import javax.swing.*;
import javax.swing.event.ListDataListener;

/**
 * @author Lukasz
 * @since 23.06.2016.
 */
public class StringComboBoxModel implements ComboBoxModel<String> {
    private String[] data;
    private String current;

    public StringComboBoxModel(String[] data) {
        this.data = data;
    }

    @Override
    public Object getSelectedItem() {
        return current;
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if (anItem instanceof String)
            for (String s : data)
                if (s.equalsIgnoreCase((String) anItem)) {
                    current = s;
                    Utils.log("selected " + s);
                }
    }

    @Override
    public int getSize() {
        return data.length;
    }

    @Override
    public String getElementAt(int index) {
        return data[index];
    }

    @Override
    public void addListDataListener(ListDataListener l) {

    }

    @Override
    public void removeListDataListener(ListDataListener l) {

    }
}

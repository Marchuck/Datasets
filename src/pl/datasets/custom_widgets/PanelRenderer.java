package pl.datasets.custom_widgets;

import pl.datasets.interfaces.Nameable;
import pl.datasets.utils.Pallete;

import javax.swing.*;
import java.awt.*;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public class PanelRenderer<T extends Nameable> implements ListCellRenderer<T> {
    @Override
    public Component getListCellRendererComponent(JList<? extends T> list, T value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = new JLabel(value.getName(), JLabel.LEFT);

        if (isSelected) label.setForeground(Pallete.primaryColor());
        else label.setForeground(Pallete.accentColor());
        return label;
    }

}
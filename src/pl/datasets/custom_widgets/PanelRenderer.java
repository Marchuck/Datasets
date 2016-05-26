package pl.datasets.custom_widgets;

import pl.datasets.interfaces.Nameable;

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
        if (isSelected) label.setForeground(hex2Rgb("#FFC107"));
        else label.setForeground(hex2Rgb("#3f51b5"));
        return label;
    }
    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }
}
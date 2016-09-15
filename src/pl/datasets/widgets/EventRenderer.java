package pl.datasets.widgets;

import pl.datasets.utils.Event;

import javax.swing.*;
import java.awt.*;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public class EventRenderer implements ListCellRenderer<Event> {


    private final JLabel label = new JLabel();
    private String[] properties;

    public EventRenderer() {
    }

    public EventRenderer(String[] properties) {
        this.properties = properties;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Event> list, Event selectedEvent,
                                                  int index, boolean isSelected, boolean cellHasFocus) {

        label.setText(selectedEvent.eventNameFor(properties));
//        if (selectedEvent.isSelected == 0) label.setForeground(classBackground(selectedEvent));
//        else if (selectedEvent.isSelected == 1) label.setForeground(Color.RED);
//        else if (selectedEvent.isSelected == 2) label.setForeground(Color.GREEN);
        label.setForeground(isSelected ? Color.RED : Color.BLUE);
        return label;
    }

    /***private Color classBackground(Event value) {
     //  Utils.log("classBackground");
     if (value.recordNode.getClassNode().getName().contains("versicolor")) {
     //  Utils.log("versicolor");
     return Color.GRAY;
     } else if (value.recordNode.getClassNode().getName().contains("setosa")) {
     //  Utils.log("setosa");
     return Color.BLACK;
     } else {
     //  Utils.log("virginica");
     return Color.LIGHT_GRAY;
     }
     }
     */
}
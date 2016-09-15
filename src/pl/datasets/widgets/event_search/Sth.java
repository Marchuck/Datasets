package pl.datasets.widgets.event_search;

import javafx.util.Pair;
import pl.datasets.utils.Event;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Lukasz
 * @since 15.09.2016.
 */
public class Sth extends JFrame {


    public Sth(List<String> properties, List<Pair<Event, List<Boolean>>> sliced) {
        super("Summary");
        setContentPane(null);
        setMinimumSize(new Dimension(300, 200));
//        setPreferredSize(new Dimension(300, 200));
        pack();
        setLocation(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}

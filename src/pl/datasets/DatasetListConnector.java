package pl.datasets;

import pl.datasets.interfaces.Connector;
import pl.datasets.interfaces.OnFileChosenListener;
import pl.datasets.model.NamedFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public class DatasetListConnector implements Connector {
    public DatasetListConnector(final JList list, final OnFileChosenListener onchosen) {

        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    NamedFile fileChosen = (NamedFile) list.getModel().getElementAt(index);
                    System.out.println("Selected file: " + fileChosen.file.getAbsolutePath());
                    if (index >= 0) onchosen.onChosen(index);
                } else if (evt.getClickCount() == 3) {
                    // Double-click detected
                    int index = list.locationToIndex(evt.getPoint());
                    if (index >= 0) onchosen.onRemoved(index);

                }
            }
        });
    }
}

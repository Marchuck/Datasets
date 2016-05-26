package pl.datasets.load;

import com.sun.javafx.beans.annotations.NonNull;
import pl.datasets.interfaces.Connector;
import pl.datasets.interfaces.OnFileLoadedListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public class LoadFileConnector implements Connector {

    public LoadFileConnector(JButton loadButton, @NonNull final OnFileLoadedListener onFileLoadedListener) {
        final JFileChooser fc = new JFileChooser();
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog((Component) e.getSource());

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println("JFileChooser.APPROVE_OPTION");
                    File file = fc.getSelectedFile();
                    onFileLoadedListener.onFileLoaded(file);
                    //This is where a real application would open the file.
                } else {
                    System.out.println("JFileChooser.CANCEL_OPTION");
                    System.out.println("Open command cancelled by user.\n");
                    onFileLoadedListener.onFileLoaded(null);
                }
            }
        });
    }
}

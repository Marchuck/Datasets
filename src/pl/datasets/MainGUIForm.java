package pl.datasets;


import com.sun.istack.internal.Nullable;
import pl.datasets.custom_widgets.PanelRenderer;
import pl.datasets.interfaces.Connector;
import pl.datasets.interfaces.OnFileChosenListener;
import pl.datasets.interfaces.OnFileLoadedListener;
import pl.datasets.load.LoadFileConnector;
import pl.datasets.model.NamedFile;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public class MainGUIForm extends JFrame implements OnFileLoadedListener, OnFileChosenListener {

    private JPanel rootPanel;
    private JTabbedPane tabbedPane1;
    private JTextField textField1;
    private JButton loadButton;
    private JList<NamedFile> list1;
    private DefaultListModel<NamedFile> listModel;

    public MainGUIForm() throws HeadlessException {
        super("Datasets");
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        listModel = new DefaultListModel<>();
        list1.setModel(listModel);
        list1.setCellRenderer(new PanelRenderer<>());
        list1.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        //   list1.setBackground(Color.cyan);
        // list1.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        //JScrollPane listScroller = new JScrollPane(list1);
        //listScroller.setPreferredSize(new Dimension(250, 80));
        Connector connector = new LoadFileConnector(loadButton, this);
        Connector connector1 = new DatasetListConnector(list1, this);
        pack();
        setVisible(true);
    }

    @Override
    public void onFileLoaded(@Nullable File file) {
        if (file != null) {
            System.out.println("Loaded file: " + file.getAbsolutePath());
            listModel.addElement(new NamedFile(file));

        } else {
            System.out.println("Nullable file ;O");
        }
    }

    @Override
    public void onChosen(int fileIndex) {
        System.out.println("onChosen()");
        File file = listModel.get(fileIndex).file;

        new DatasetOperationDialog(file);
     //     dispose(); uncoment to kill previous JFrame
    }

    /**
     * on removed
     *
     * @param fileIndex
     */
    @Override
    public void onRemoved(int fileIndex) {
        //double click event: remove dataset
        listModel.remove(fileIndex);
    }
}

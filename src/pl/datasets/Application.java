package pl.datasets;

import pl.datasets.model.DatasetItem;
import pl.datasets.widgets.the_very_main_dialog.ChooseAlgorithmDialog;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz
 * @since 26.05.2016
 */
public class Application {
    public static Application instance;
    public JFrame currentUsedFrame;

    private Application() {
//        currentUsedFrame =
//        new MainDatasetOperationFrame().getDialog();
        new ChooseAlgorithmDialog();


    }

    public static Application getInstance() {
        if (instance == null) instance = new Application();
        return instance;
    }

    public static void main(String[] args) {
        Application.getInstance();

    }

    public List<DatasetItem> getDataset() {
        return new ArrayList<>();
    }
}

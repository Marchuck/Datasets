package pl.datasets;

import java.io.File;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public class DatasetOperationDialog {
    DatasetDialog dialog;

    public DatasetOperationDialog(File file) {
        dialog = new DatasetDialog(file.getAbsolutePath());

    }
}

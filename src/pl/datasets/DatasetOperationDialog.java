package pl.datasets;

import pl.datasets.load.CSVReader;
import pl.datasets.model.DatasetItem;
import pl.datasets.trend_match.AscendingTrend;

import java.io.File;
import java.text.ParseException;
import java.util.List;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public class DatasetOperationDialog {

    DatasetDialog dialog;

    public DatasetOperationDialog() {
        this(new File("data/ad_viz_tile_data.csv"));
    }

    public DatasetOperationDialog(File file) {
        CSVReader<DatasetItem> csvReader = new CSVReader<>();
        List<DatasetItem> dataset;
        try {
            dataset = csvReader.skipFirstLine().readFile(file, new CSVReader.DataSetOnlyDoubleItemStrategy());

            initAscendingTrendRecognition(dataset);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Data set reading fatal error");
        }

        dialog = new DatasetDialog(file.getAbsolutePath(), dataset, csvReader.properties());
    }

    private void initAscendingTrendRecognition(List<DatasetItem> dataset) {

        AscendingTrend trend = AscendingTrend.getInstance(dataset);
        trend.getTrends();

    }

    public static void main(String[] args) {
        new DatasetOperationDialog();
    }
}

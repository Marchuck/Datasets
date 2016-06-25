package pl.datasets;

import pl.datasets.interfaces.SubsetStrategy;
import pl.datasets.load.CSVReader;
import pl.datasets.model.BaseItem;
import pl.datasets.model.DatasetItem;
import pl.datasets.trend_match.AscendingTrend;
import pl.datasets.utils.Utils;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    public DatasetOperationDialog(boolean b) {
        //no-op
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

    public static void main(String[] args) {
        List<DatasetItem> set = new DatasetOperationDialog(true).getFulldataset(true);

    }

    private static <X> int getMinSize(List<List<X>> sets) {
        List<Integer> sizes = new ArrayList<>();
        for (List<X> l : sets) sizes.add(l.size());
        return Collections.min(sizes);
    }

    public List<List<BaseItem>> createAllSublists() {
        Utils.log("createAllSublists");
        List<List<BaseItem>> readyToMerge = new ArrayList<>();

        readyToMerge.add(subsetOf("data/weather/SF04.csv", new CSVReader.DataSetWeatherStrategy("SF04")));
        readyToMerge.add(subsetOf("data/stock_exchange/istambul.csv", new CSVReader.IslandStrategy()));
        readyToMerge.add(subsetOf("data/island/reykjavik.csv", new CSVReader.IslandStrategy()));
        readyToMerge.add(subsetOf("data/ad_viz_tile_data.csv", new CSVReader.PollutantStrategy()));

        return readyToMerge;
    }

    private List<BaseItem> subsetOf(String fileName, SubsetStrategy strategy) {
        CSVReader<BaseItem> csvReader = new CSVReader<>();
        List<BaseItem> dataSet;

        File file = new File(fileName);

        dataSet = csvReader.skipFirstLine().readDataset(file, strategy);
        List<String> properties = strategy.getProperties();

        for (BaseItem baseItem : dataSet) {
            baseItem.setColumnNames(properties);
        }
        return dataSet;

    }

    private List<BaseItem> createNflWeatherSubset() {
        CSVReader<BaseItem> csvReader = new CSVReader<>();
        List<BaseItem> dataSet;

        File file = new File("data/nfl/nfl_weather.csv");
        CSVReader.NFLWeatherStrategy strategy = new CSVReader.NFLWeatherStrategy();

        dataSet = csvReader.skipFirstLine().readDataset(file, strategy);
        List<String> properties = strategy.getProperties();

        for (BaseItem baseItem : dataSet) {
            baseItem.setColumnNames(properties);
        }
        return dataSet;
    }

    private List<BaseItem> createReykjavikSubset() {
        CSVReader<BaseItem> csvReader = new CSVReader<>();
        List<BaseItem> dataSet;

        File file = new File("data/island/reykjavik.csv");
        CSVReader.IslandStrategy strategy = new CSVReader.IslandStrategy();

        dataSet = csvReader.skipFirstLine().readDataset(file, strategy);
        List<String> properties = strategy.getProperties();

        for (BaseItem baseItem : dataSet) {
            baseItem.setColumnNames(properties);
        }
        return dataSet;
    }

    public List<BaseItem> createWeatherSubset() {
        CSVReader<BaseItem> csvReader = new CSVReader<>();
        List<BaseItem> dataSet;
        File file = new File("data/weather/SF04.csv");
        CSVReader.DataSetWeatherStrategy strategy = new CSVReader.DataSetWeatherStrategy();

        dataSet = csvReader.skipFirstLine().readDataset(file, strategy);

        for (BaseItem baseItem : dataSet) {
            baseItem.setColumnNames((strategy.getProperties()));
        }
        return dataSet;
    }

    public List<BaseItem> createIstambuleSubset() {
        CSVReader<BaseItem> csvReader = new CSVReader<>();
        List<BaseItem> dataSet;

        File file = new File("data/stock_exchange/istambul.csv");
        CSVReader.IstambulStrategy strategy = new CSVReader.IstambulStrategy();

        dataSet = csvReader.skipFirstLine().readDataset(file, strategy);
        List<String> columnNames = strategy.getProperties();
        for (BaseItem baseItem : dataSet) {
            baseItem.setColumnNames(columnNames);
        }
        return dataSet;
    }

    private void initAscendingTrendRecognition(List<DatasetItem> dataset) {

        AscendingTrend trend = AscendingTrend.getInstance(dataset);
        trend.getTrends();

    }

    public List<DatasetItem> getFulldataset(boolean verbose) {
        List<DatasetItem> dataset = new ArrayList<>();
        List<List<BaseItem>> sets = new DatasetOperationDialog(true).createAllSublists();
        int minSize = getMinSize(sets);
        for (int j = 0; j < minSize; j++) {
            List<Double> doubles = new ArrayList<>();
            for (List<BaseItem> baseItem : sets) {

                doubles.addAll(baseItem.get(j).getValues());
            }
            dataset.add(new DatasetItem(j, doubles));
        }

        List<String> propertyNames = new ArrayList<>();
        propertyNames.add("Timestamp");
        for (List<BaseItem> differentSource : sets) {
            propertyNames.addAll(differentSource.get(0).getColumnNames());
        }

        for (DatasetItem aDataset : dataset) aDataset.setProperties(propertyNames);

        if (verbose) {
            Utils.log("DONE");
            Utils.log(Arrays.toString(CSVReader.wrapStrings(dataset.get(0).getProperties())));
            for (DatasetItem it : dataset) {
                Utils.log(it.toString());
            }
        }
        return dataset;
    }
}

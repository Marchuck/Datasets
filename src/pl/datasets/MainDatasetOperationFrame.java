package pl.datasets;

import pl.datasets.interfaces.SubsetStrategy;
import pl.datasets.load.CSVReader;
import pl.datasets.model.BaseItem;
import pl.datasets.model.DatasetItem;
import pl.datasets.utils.CsvSave;
import pl.datasets.utils.Event;
import pl.datasets.utils.Utils;
import pl.datasets.widgets.event_search.Behaviour;
import pl.datasets.widgets.event_search.EventBasedDatasetDialog;
import pl.datasets.widgets.implication.ImplicationDatasetDialog;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public class MainDatasetOperationFrame {
    private List<Event> columnStrategyPairs = new ArrayList<>();

    private DatasetDialog dialog;


    public MainDatasetOperationFrame(boolean b) {
        //no-op
    }

    public MainDatasetOperationFrame() {
        //dialog= getDialog();
    }


    public static void main(String[] args) {
        new MainDatasetOperationFrame().getDialog();
//        List<DatasetItem> data = new MainDatasetOperationFrame(true).getFulldataSet(true);
//        TrendingSubsetWrapper trend = TrendingSubsetWrapper.getInstance(data);
//        List<ColumnStrategyPair> columnStrategyPairs = new ArrayList<>();
//        columnStrategyPairs.add(new ColumnStrategyPair(Strategies.recognizeStrategy("++"), 0));
//        columnStrategyPairs.add(new ColumnStrategyPair(Strategies.recognizeStrategy("--"), 2));
//        columnStrategyPairs.add(new ColumnStrategyPair(Strategies.recognizeStrategy(">", 20), 1));
//        trend.getTrends(columnStrategyPairs);

    }

    private static <X> int getMinSize(List<List<X>> sets) {
        List<Integer> sizes = new ArrayList<>();
        for (List<X> l : sets) sizes.add(l.size());
        return Collections.min(sizes);
    }


    public DatasetDialog getDialog(Behaviour behaviour) {
        List<DatasetItem> dataset = getFulldataSet(true);
//        save(dataset,"dataset");
        String[] properties = CSVReader.listToArray(dataset.get(0).getProperties());
        String[] strategies = new String[]{"++", "--", ">", "<", "==", "<=", ">="};

        if (behaviour == Behaviour.EVENT_BASED) {
            return new EventBasedDatasetDialog("Event-Chained behaviour", dataset, properties, strategies);
        } else if (behaviour == Behaviour.IMPLICATION) {
            return new ImplicationDatasetDialog("Implication behaviour", dataset, properties, strategies);
        } else throw new RuntimeException("UNSUPPORTED enum type: " + behaviour.name());
    }

    @Deprecated
    public DatasetDialog getDialog() {
        List<DatasetItem> dataset = getFulldataSet(true);
//        save(dataset,"dataset");
        String[] properties = CSVReader.listToArray(dataset.get(0).getProperties());
        return new DatasetDialog("Datasets", dataset, properties, new String[]{"++", "--", ">", "<", "==", "<=", ">="}) {
            @Override
            public ActionListener computeButtonClickListener() {
                throw new UnsupportedOperationException("NOT ALLOWED HERE, use getDialog(Behaviour) instead");
            }
        };
    }

    public void save(List<DatasetItem> dataset, String filename) {
        new CsvSave<>(dataset).withStrategy(new CsvSave.Strategy<DatasetItem>() {
            @Override
            public String saveLine(DatasetItem datasetItem) {
                return datasetItem.asCsv();
            }
        }).saveAs(filename);
    }

    public List<List<BaseItem>> createAllSublists() {
        Utils.log("createAllSublists");
        List<List<BaseItem>> subsetToMerge = new ArrayList<>();

        subsetToMerge.add(subsetOf("data/weather/SF04.csv", new CSVReader.DataSetWeatherStrategy("SF04")));
        subsetToMerge.add(subsetOf("data/weather/SF15.csv", new CSVReader.DataSetWeatherStrategy("SF15")));
        subsetToMerge.add(subsetOf("data/weather/SF17.csv", new CSVReader.DataSetWeatherStrategy("SF17")));
        subsetToMerge.add(subsetOf("data/weather/SF18.csv", new CSVReader.DataSetWeatherStrategy("SF18")));
        subsetToMerge.add(subsetOf("data/stock_exchange/istambul.csv", new CSVReader.IstambulStrategy()));
        subsetToMerge.add(subsetOf("data/ad_viz_tile_data.csv", new CSVReader.PollutantStrategy()));
//        subsetToMerge.add(subsetOf("data/island/reykjavik.csv", new CSVReader.IslandStrategy())); !!!!
        subsetToMerge.add(subsetOf("data/nfl/nfl_weather.csv", new CSVReader.NFLWeatherStrategy())); //!!!!

        return subsetToMerge;
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

    private void initAscendingTrendRecognition(List<DatasetItem> dataset) {

    }

    public List<DatasetItem> getFulldataSet() {
        return getFulldataSet(true);
    }

    public List<DatasetItem> getFulldataSet(boolean logsVerbosity) {
        List<DatasetItem> dataset = new ArrayList<>();
        List<List<BaseItem>> sets = new MainDatasetOperationFrame(true).createAllSublists();

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

        if (logsVerbosity) {
            Utils.log("DONE");
            Utils.log(Arrays.toString(CSVReader.listToArray(dataset.get(0).getProperties())));
            for (DatasetItem it : dataset) {
                Utils.log(it.toString());
            }
        }
        return dataset;
    }


}

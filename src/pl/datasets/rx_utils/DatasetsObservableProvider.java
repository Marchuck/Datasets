package pl.datasets.rx_utils;

import javafx.util.Pair;
import pl.datasets.MainDatasetOperationFrame;
import pl.datasets.interfaces.SubsetStrategy;
import pl.datasets.load.CSVReader;
import pl.datasets.model.BaseItem;
import pl.datasets.model.DatasetItem;
import pl.datasets.utils.Utils;
import rx.Observable;
import rx.annotations.Experimental;
import rx.functions.Func0;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz
 * @since 09.09.2016.
 */
@Experimental
public class DatasetsObservableProvider {


    public static rx.Observable<List<DatasetItem>> provide() {
        return Observable.defer(new Func0<Observable<List<DatasetItem>>>() {
            @Override
            public Observable<List<DatasetItem>> call() {
                return _provide();
            }
        });
    }

    private static rx.Observable<List<DatasetItem>> _provide() {
        Utils.log("provide invoked");
        List<Pair<String, SubsetStrategy>> subsetToMerge = new ArrayList<>();

        subsetToMerge.add(new Pair<String, SubsetStrategy>("data/weather/SF04.csv", new CSVReader.DataSetWeatherStrategy("SF04")));
        subsetToMerge.add(new Pair<String, SubsetStrategy>("data/weather/SF15.csv", new CSVReader.DataSetWeatherStrategy("SF15")));
        subsetToMerge.add(new Pair<String, SubsetStrategy>("data/weather/SF17.csv", new CSVReader.DataSetWeatherStrategy("SF17")));
        subsetToMerge.add(new Pair<String, SubsetStrategy>("data/weather/SF18.csv", new CSVReader.DataSetWeatherStrategy("SF18")));
        subsetToMerge.add(new Pair<String, SubsetStrategy>("data/stock_exchange/istambul.csv", new CSVReader.IstambulStrategy()));
        subsetToMerge.add(new Pair<String, SubsetStrategy>("data/ad_viz_tile_data.csv", new CSVReader.PollutantStrategy()));
        subsetToMerge.add(new Pair<String, SubsetStrategy>("data/nfl/nfl_weather.csv", new CSVReader.NFLWeatherStrategy())); //!!!!
//        subsetToMerge.add(subsetOf("data/island/reykjavik.csv", new CSVReader.IslandStrategy())); !!!!

        return Observable.from(subsetToMerge).map(new Func1<Pair<String, SubsetStrategy>, List<BaseItem>>() {
            @Override
            public List<BaseItem> call(Pair<String, SubsetStrategy> pair) {
                return MainDatasetOperationFrame.subsetOf(pair.getKey(), pair.getValue());
            }
        }).toList().map(new Func1<List<List<BaseItem>>, List<DatasetItem>>() {
            @Override
            public List<DatasetItem> call(List<List<BaseItem>> baseItemsLists) {
                List<DatasetItem> dataset = new ArrayList<>();
                int minSize = MainDatasetOperationFrame.getShortestCollectionSize(baseItemsLists);
                for (int j = 0; j < minSize; j++) {
                    List<Double> doubles = new ArrayList<>();
                    for (List<BaseItem> baseItem : baseItemsLists) {
                        doubles.addAll(baseItem.get(j).getValues());
                    }
                    dataset.add(new DatasetItem(j, doubles));
                }
                List<String> propertyNames = new ArrayList<>();
                propertyNames.add("Timestamp");
                for (List<BaseItem> differentSource : baseItemsLists) {
                    propertyNames.addAll(differentSource.get(0).getColumnNames());
                }
                for (DatasetItem set : dataset) {
                    set.setProperties(propertyNames);
                }
                return dataset;
            }
        });
    }
}

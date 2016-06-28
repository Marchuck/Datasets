package pl.datasets.weka;


import weka.associations.Apriori;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.util.ArrayList;
import java.util.List;

import static pl.datasets.weka.ToNominal.prepared;

/**
 * @author Lukasz
 * @since 20.06.2016.
 */
public class AprioriTest {

    public static void testAll() {
        List<String> datasets = new ArrayList<>();
//        datasets.add("aaaa.csv");
//        datasets.add("ad_viz_tile_data.csv");
//        datasets.add("island/reykjavik.csv");
//        datasets.add("nfl/nfl_weather.csv");
//        datasets.add("stock_exchange/istambul.csv");
//        datasets.add("weather/SF04.csv");
//        datasets.add("weather/SF15.csv");
//        datasets.add("weather/SF17.csv");
//        datasets.add("weather/SF18.csv");
//        datasets.add("weather/SF36.csv");
        datasets.add("weather/SF37.csv");
        for (String s : datasets) {
            try {
                System.out.println(perform(s));
            } catch (Exception l) {
            }
        }
   /*     rx.Observable.from(datasets).map(new Func1<String, Apriori>() {
            @Override
            public Apriori call(String dataset) {
                try {
                    return perform(dataset);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new Apriori();
            }
        }).subscribe(new Action1<Apriori>() {
            @Override
            public void call(Apriori apriori) {
                if (apriori != null)
                    System.out.println(apriori);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.err.println(throwable.getMessage());
                throwable.printStackTrace();
            }
        });
        */
    }

    /**
     * Expects a dataset saveAs first parameter. The last attribute is used
     * saveAs class attribute.
     *
     * @param args the command-line parameters
     * @throws Exception if something goes wrong
     */
    public static void main(String[] args) throws Exception {
        // load data

        Instances data = ConverterUtils.DataSource.read("data/ad_viz_tile_data.csv");
        data = prepared(data);
//        ConverterUtils.DataSource source = new ConverterUtils.DataSource("aaaa.csv");
//        ConverterUtils.DataSource source = new ConverterUtils.DataSource("precipitation_on_few_places_1949.csv");

//        Instances data = prepared(source.getDataSet(),"1-18");

        //prepared data(numeric into nominal values
        data.setClassIndex(data.numAttributes() - 1);
        // build associator
        Apriori apriori = new Apriori();
        apriori.setClassIndex(data.classIndex());
        apriori.buildAssociations(data);
        // output associator
        System.out.println(apriori);
    }

    public static Apriori perform(Instances instances) throws Exception {
        Instances data = new Instances(instances);
        data.setClassIndex(data.numAttributes() - 1);
        // build associator
        Apriori apriori = new Apriori();
        apriori.setClassIndex(data.classIndex());
        apriori.buildAssociations(data);
        return apriori;
    }

    public static Apriori perform(String file) throws Exception {
        Instances data = ConverterUtils.DataSource.read(file);
        return perform(prepared(data));
    }
}

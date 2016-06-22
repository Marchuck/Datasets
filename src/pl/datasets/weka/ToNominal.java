package pl.datasets.weka;

import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

/**
 * @author Lukasz
 * @since 20.06.2016.
 * <p>
 * Wraps numeric to nominal classes
 */
public class ToNominal {
    public static final String DATASET1 = "data/ad_viz_tile_data.csv";
    public static final String POLLUTANTS = "data/aaaa.csv";

    public static Instances defaultInstances() throws Exception {
        Instances data = ConverterUtils.DataSource.read(DATASET1);
        return wrap(data);
    }

    public static Instances get(String path) throws Exception {
        Instances data = ConverterUtils.DataSource.read(path);
        return wrap(data);
    }

    public static Instances getPollutants() throws Exception {
        return get(POLLUTANTS);
    }

    public static Instances wrap(Instances instances) throws Exception {

        NumericToNominal convert = new NumericToNominal();
        String[] options = new String[2];
        options[0] = "-R";
        options[1] = "1-" + instances.numAttributes();  //range of variables to make numeric

        convert.setOptions(options);
        convert.setInputFormat(instances);

        return Filter.useFilter(instances, convert);
    }
}

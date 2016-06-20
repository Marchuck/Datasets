package pl.datasets.weka;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

/**
 * @author Lukasz
 * @since 20.06.2016.
 */
public class NumericToNominalWrapper {
    public static final String DATASET1 = "ad_viz_tile_data.csv";

    public static Instances wrap(Instances instances, String selectedColumns) throws Exception {

        NumericToNominal convert = new NumericToNominal();
        String[] options = new String[2];
        options[0] = "-R";
        options[1] = selectedColumns;  //range of variables to make numeric

        convert.setOptions(options);
        convert.setInputFormat(instances);

        return Filter.useFilter(instances, convert);
    }
}

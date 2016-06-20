package pl.datasets.weka;

import weka.associations.Apriori;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import static pl.datasets.weka.NumericToNominalWrapper.wrap;

/**
 * @author Lukasz
 * @since 20.06.2016.
 */
public class AprioriOutput {

    /**
     * Expects a dataset as first parameter. The last attribute is used
     * as class attribute.
     *
     * @param args the command-line parameters
     * @throws Exception if something goes wrong
     */
    public static void main(String[] args) throws Exception {
        // load data
        Instances data = ConverterUtils.DataSource.read("ad_viz_tile_data.csv");
        data = wrap(data, "1-5");
        //wrap data(numeric into nominal values
        data.setClassIndex(data.numAttributes() - 1);
        // build associator
        Apriori apriori = new Apriori();
        apriori.setClassIndex(data.classIndex());
        apriori.buildAssociations(data);
        // output associator
        System.out.println(apriori);
    }
}

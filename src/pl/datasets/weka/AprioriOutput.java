package pl.datasets.weka;


import weka.associations.Apriori;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import static pl.datasets.weka.ToNominal.prepared;

/**
 * @author Lukasz
 * @since 20.06.2016.
 */
public class AprioriOutput {


    /**
     * Expects a dataset saveAs first parameter. The last attribute is used
     * saveAs class attribute.
     *
     * @param args the command-line parameters
     * @throws Exception if something goes wrong
     */
    public static void main(String[] args) throws Exception {
        // load data
        Instances data = ConverterUtils.DataSource.read("data/denmark.csv");
        data = prepared(ToNominal.defaultInstances());
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

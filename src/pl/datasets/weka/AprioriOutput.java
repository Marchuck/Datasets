package pl.datasets.weka;


import weka.associations.Apriori;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import static pl.datasets.weka.ToNominal.wrap;

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
        Instances data = ConverterUtils.DataSource.read("data/denmark.csv");
        data = wrap(data);
//        ConverterUtils.DataSource source = new ConverterUtils.DataSource("aaaa.csv");
//        ConverterUtils.DataSource source = new ConverterUtils.DataSource("precipitation_on_few_places_1949.csv");

//        Instances data = wrap(source.getDataSet(),"1-18");


        //wrap data(numeric into nominal values
        data.setClassIndex(data.numAttributes() - 1);
        // build associator
        Apriori apriori = new Apriori();
        apriori.setClassIndex(data.classIndex());
        apriori.buildAssociations(data);
        // output associator
        System.out.println(apriori);
    }

    public static Apriori performApriori(Instances instances) throws Exception {
        Instances data = new Instances(instances);
        data.setClassIndex(data.numAttributes() - 1);
        // build associator
        Apriori apriori = new Apriori();
        apriori.setClassIndex(data.classIndex());
        apriori.buildAssociations(data);
        return apriori;
    }
}

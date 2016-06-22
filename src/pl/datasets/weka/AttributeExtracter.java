package pl.datasets.weka;

import pl.datasets.utils.Utils;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import static pl.datasets.weka.ToNominal.wrap;

/**
 * @author Lukasz
 * @since 20.06.2016.
 */
public class AttributeExtracter {

    public static void main(String[] args) {
        try {
            new AttributeExtracter().extract();

        } catch (Exception igonred) {
            Utils.loge(igonred.getMessage());
            igonred.printStackTrace();
        }
    }

    public void extract() throws Exception {

        Instances instances = ConverterUtils.DataSource.read("data/ad_viz_tile_data.csv");
        instances = wrap(instances );

        if (instances.classIndex() == -1)
            instances.setClassIndex(instances.numAttributes() - 1);

        String[] options = new String[1];
        options[0] = "-U";            // unpruned tree
        J48 tree = new J48();         // new instance of tree
        tree.setOptions(options);     // set the options
        tree.buildClassifier(instances);   // build classifier

        NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
        nb.buildClassifier(instances);
        for (Instance current : instances)
            nb.updateClassifier(current);
        // output generated model
        System.out.println(nb);
    }


}

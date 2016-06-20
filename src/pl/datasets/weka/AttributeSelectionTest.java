package pl.datasets.weka;

import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

import java.util.Random;

/**
 * performs attribute selection using CfsSubsetEval and GreedyStepwise
 * (backwards) and trains J48 with that. Needs 3.5.5 or higher to compile.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class AttributeSelectionTest {

    /**
     * uses the meta-classifier
     */
    protected static void useClassifier(Instances data) throws Exception {
        System.out.println("\n1. Meta-classfier");
        AttributeSelectedClassifier classifier = new AttributeSelectedClassifier();
        CfsSubsetEval eval = new CfsSubsetEval();
        GreedyStepwise search = new GreedyStepwise();
        search.setSearchBackwards(true);
        J48 base = new J48();
        classifier.setClassifier(base);
        classifier.setEvaluator(eval);
        classifier.setSearch(search);
        Evaluation evaluation = new Evaluation(data);
        evaluation.crossValidateModel(classifier, data, 10, new Random(1));
        System.out.println(evaluation.toSummaryString());
    }

    /**
     * uses the filter
     */
    protected static void useFilter(Instances data) throws Exception {
        System.out.println("\n2. Filter");
        weka.filters.supervised.attribute.AttributeSelection filter = new weka.filters.supervised.attribute.AttributeSelection();
        CfsSubsetEval eval = new CfsSubsetEval();
        GreedyStepwise search = new GreedyStepwise();
        search.setSearchBackwards(true);
        filter.setEvaluator(eval);
        filter.setSearch(search);
        filter.setInputFormat(data);
        Instances newData = Filter.useFilter(data, filter);
        System.out.println(newData);
    }

    /**
     * uses the low level approach
     */
    protected static void useLowLevel(Instances data) throws Exception {
        System.out.println("\n3. Low-level");
        AttributeSelection attsel = new AttributeSelection();
        CfsSubsetEval eval = new CfsSubsetEval();
        GreedyStepwise search = new GreedyStepwise();
        search.setSearchBackwards(true);
        attsel.setEvaluator(eval);
        attsel.setSearch(search);
        attsel.SelectAttributes(data);
        int[] indices = attsel.selectedAttributes();
        System.out.println("selected attribute indices (starting with 0):\n" + Utils.arrayToString(indices));
    }

    /**
     * takes a dataset as first argument
     *
     * @param args the commandline arguments
     * @throws Exception if something goes wrong
     */
    public static void main(String[] args) throws Exception {
        // load data
        System.out.println("\n0. Loading data");
        DataSource source = new DataSource(NumericToNominalWrapper.DATASET1);
        Instances data = NumericToNominalWrapper.wrap(source.getDataSet(),"1-5");
        if (data.classIndex() == -1)
            data.setClassIndex(data.numAttributes() - 1);

        // 1. meta-classifier
        useClassifier(data);

        // 2. filter
        useFilter(data);

        // 3. low-level
        useLowLevel(data);
    }



    public static void kurde() throws Exception {
        //load training instances
//    Instances originalTrain= //...load data with numeric attributes
        DataSource source = new DataSource("aaaa.csv");
        Instances originalTrain = source.getDataSet();
        NumericToNominal convert = new NumericToNominal();
        String[] options = new String[2];
        options[0] = "-R";
        options[1] = "1-2";  //range of variables to make numeric

        convert.setOptions(options);
        convert.setInputFormat(originalTrain);

        Instances newData = Filter.useFilter(originalTrain, convert);
    }
}

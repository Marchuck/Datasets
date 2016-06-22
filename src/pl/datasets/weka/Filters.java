package pl.datasets.weka;

import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

/**
 * @author Lukasz
 * @since 21.06.2016.
 *
 * Filters out data that does not change: Leaves only these columns, where data change
 */
public class Filters {
    public static void main(String[] args) throws Exception {
        new Filters().run();
    }

    void run() throws Exception {
        Instances data = ToNominal.defaultInstances();
        AttributeSelection filter = new AttributeSelection();  // package weka.filters.supervised.attribute!
        CfsSubsetEval eval = new CfsSubsetEval();
        GreedyStepwise search = new GreedyStepwise();
        search.setSearchBackwards(true);
        filter.setEvaluator(eval);
        filter.setSearch(search);
        filter.setInputFormat(data);
        // generate new data
        Instances newData = Filter.useFilter(data, filter);
        System.out.println(newData);
    }

    public static Instances filterImmutable(Instances instances) throws Exception{
        AttributeSelection filter = new AttributeSelection();  // package weka.filters.supervised.attribute!
        CfsSubsetEval eval = new CfsSubsetEval();
        GreedyStepwise search = new GreedyStepwise();
        search.setSearchBackwards(true);
        filter.setEvaluator(eval);
        filter.setSearch(search);
        filter.setInputFormat(instances);
        return Filter.useFilter(instances,filter);
    }

}

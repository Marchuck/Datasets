package pl.datasets.weka;

import weka.associations.Apriori;
import weka.core.Instances;

/**
 * @author Lukasz
 * @since 21.06.2016.
 */
public class Testo {
    public static void main(String[] args) throws Exception {
        new Testo().apriori();
    }

    private void apriori() {
        AprioriTest.testAll();
    }

    private void run() throws Exception {

        Instances instances = ToNominal.get("data/aaaa.csv");
        instances = Filters.filterImmutable(instances);
        Apriori apriori = AprioriTest.perform(instances);
        System.out.println(apriori);
    }

}

package pl.datasets.widgets.implication;

import pl.datasets.DatasetDialog;
import pl.datasets.model.DatasetItem;
import pl.datasets.trend_match.TrendingSubsetWrapper;
import pl.datasets.utils.Event;
import pl.datasets.widgets.event_search.ComputeButtonBehaviour;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author Lukasz
 * @since 07.09.2016.
 */
public class ImplicationDatasetDialog extends DatasetDialog implements ComputeButtonBehaviour {

    public ImplicationDatasetDialog(String path, List<DatasetItem> items, String[] propertyNames, String[] operations) {
        super(path, items, propertyNames, operations);
    }

    @Override
    public ActionListener computeButtonClickListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                TrendingSubsetWrapper wrapper = TrendingSubsetWrapper.getInstance(datasetItems);
                List<Event> events1 = getEventsFromModel();
                ImplicationSolver solver1 = new ImplicationSolver(datasetItems,events1);

                List<List<Long>> result1 = wrapper.findChainedEventsOccurences(events1, 8);

                int[] indexesThatShouldBeExposed = new int[events1.size()];
                for (int i = 0; i < events1.size(); i++) {
                    indexesThatShouldBeExposed[i] = events1.get(i).getColumnIndex();
                }
                new ImplicationSolverDialog(result1, indexesThatShouldBeExposed);


                //todo Lukasz : modify implicationSolverDialog to accept such data, you can get indexes from that object, there is a method

               /* List<Event> events = getEventsFromModel();
                ImplicationSolver solver = new ImplicationSolver(datasetItems,events);

                ImplicationEventResult result = solver.compute();

                new ImplicationSolverDialog(result);*/

            }
        };
    }
}

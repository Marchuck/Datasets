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
                List<Event> events = getEventsFromModel();

                List<List<Long>> result = wrapper.findChainedEventsOccurences(events, 8);

                int[] indexesThatShouldBeExposed = new int[events.size()];
                for (int i = 0; i < events.size(); i++) {
                    indexesThatShouldBeExposed[i] = events.get(i).getColumnIndex();
                }
                new ImplicationSolverDialog.Builder()
                        .withLongList(result)
                        .withIndexes(indexesThatShouldBeExposed)
                        .build();
            }
        };
    }
}

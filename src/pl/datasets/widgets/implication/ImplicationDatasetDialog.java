package pl.datasets.widgets.implication;

import pl.datasets.DatasetDialog;
import pl.datasets.model.DatasetItem;
import pl.datasets.trend_match.TrendingSubsetWrapper;
import pl.datasets.utils.Event;
import pl.datasets.widgets.event_search.ComputeButtonBehaviour;
import pl.datasets.widgets.implication.gui.ImplicationSummaryDialog;

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


                List<Event> events1 = getEventsFromModel();
                ImplicationSolver solver = new ImplicationSolver(datasetItems,events1);
                ImplicationEventResult result = solver.compute();

                new ImplicationSummaryDialog(datasetItems, result,events1);
            }
        };
    }
}

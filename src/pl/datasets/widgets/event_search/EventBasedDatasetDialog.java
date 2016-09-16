package pl.datasets.widgets.event_search;

import javafx.util.Pair;
import pl.datasets.DatasetDialog;
import pl.datasets.model.DatasetItem;
import pl.datasets.trend_match.TrendingSubsetWrapper;
import pl.datasets.utils.Event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz
 * @since 07.09.2016.
 */
public class EventBasedDatasetDialog extends DatasetDialog implements ComputeButtonBehaviour {

    public EventBasedDatasetDialog(String path, List<DatasetItem> items, String[] propertyNames, String[] operations) {
        super(path, items, propertyNames, operations);
    }

    /**
     * note:
     *
     * @return compute button uses this implementation to perform calculations
     */
    @Override
    public ActionListener computeButtonClickListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                TrendingSubsetWrapper wrapper = TrendingSubsetWrapper.getInstance(datasetItems);
                wrapper.setMinTrendLength(2);
                List<List<List<Long>>> resultsOfAllSingleWrapperOutput = new ArrayList<>();
                List<List<Boolean>> bols = new ArrayList<>();
                List<Event> events = getEventsFromModel();
                List<Pair<Event, List<Boolean>>> sliced = new ArrayList<Pair<Event, List<Boolean>>>();


                for (Event event : events) {
                    sliced.add(new Pair<Event, List<Boolean>>(event, wrapper.evaluate(event)));
                }

                if (events.size() > 1) {
                    List<List<Long>> results = wrapper.getTrends(events, false);
                    //result combined from all outputs
                    resultsOfAllSingleWrapperOutput.add(results);
                }

                for (Event ev : events) {
                    bols.add(wrapper.evaluate(ev));
                    List<List<Long>> trends = wrapper.getTrends(ev, false);
                    resultsOfAllSingleWrapperOutput.add(trends);
                }
                List<String> properties = datasetItems.get(0).getProperties();
//                new Sth(properties,sliced);
                new EventSearchSummaryDialog(datasetItems, properties, sliced, resultsOfAllSingleWrapperOutput);
//                new ResultsEntity().withProperties(properties).bindSeparated(sliced);
            }
        };
    }
}

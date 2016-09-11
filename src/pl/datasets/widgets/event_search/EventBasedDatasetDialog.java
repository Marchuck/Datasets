package pl.datasets.widgets.event_search;

import javafx.util.Pair;
import pl.datasets.DatasetDialog;
import pl.datasets.model.DatasetItem;
import pl.datasets.trend_match.TrendingSubsetWrapper;
import pl.datasets.utils.Event;
import pl.datasets.widgets.ResultsEntity;

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
                //todo: Lukasz create view  for displaying results

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
                    for (List<Long> l : trends)
//                        Utils.log(Arrays.toString(CSVReader.genericlistToArray(l, new CSVReader.Bie<Long>() {
//                            @Override
//                            public Long[] create(int capacity) {
//                                return new Long[capacity];
//                            }
//                        })));
                        resultsOfAllSingleWrapperOutput.add(trends);
                }
                for (int j = 0; j < resultsOfAllSingleWrapperOutput.size(); j++) {

                }
//                List<List<Long>> results1 = wrapper.getTrends(, false);
//                res.add(results);
//                new ResultsEntity().bind(results);


//                new ResultsEntity().bindAll(resultsOfAllSingleWrapperOutput);
                new ResultsEntity().bindSeparated(sliced);
            }



                //List<?> out = yourLogicImpl.compute(this); or sth better

                //compute here and pass result when you will be creating
                // ImplicationDatasetDialog
                //just invoke:
                //note: break private->protected to get access to data from super class

        };
    }
}

package pl.datasets.widgets.implication;

import pl.datasets.model.DatasetItem;
import pl.datasets.trend_match.TrendingSubsetWrapper;
import pl.datasets.utils.Event;
import pl.datasets.utils.Utils;
import pl.datasets.widgets.event_search.EventResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOHANNES on 9/15/2016.
 */
public class ImplicationSolver {

    private List<DatasetItem> dataset;

    private List<Event> chainedEvents;

    private TrendingSubsetWrapper trendSolver;


    public void setAllowedOffset(int allowedOffset) {
        this.allowedOffset = allowedOffset;
    }

    private int allowedOffset = 8;





    public ImplicationSolver(List<DatasetItem> dataset, List<Event> chainedEvents) {

        this.dataset = dataset;
        this.chainedEvents = chainedEvents;
        trendSolver = TrendingSubsetWrapper.getInstance(this.dataset);
    }



    private List<List<Long>> findChainedEventsOccurences() {

        List<List<Long>> implicationCandidates = new ArrayList<>();

        for (DatasetItem item : dataset) {

            for (Event singleEvent : chainedEvents) {

                if (singleEvent.hasTrend(item)) {
                    if (chainedEvents.indexOf(singleEvent) == 0) {
                        implicationCandidates.add(initNewLongList(item));
                    } else {

                        for (List<Long> candidate : implicationCandidates) {

                            if (candidate.size() == chainedEvents.indexOf(singleEvent)
                                    && item.getTimestamp() - candidate.get(candidate.size() - 1) <= allowedOffset
                                    && item.getTimestamp() - candidate.get(candidate.size() - 1) > 0) {
                                candidate.add(item.getTimestamp());
                                break;
                            }
                        }
                    }
                }
            }
        }

        return reduceCandidates(chainedEvents, implicationCandidates);
    }


    public ImplicationEventResult compute(){

        List<EventResult> results = new ArrayList<>();

        for (Event event:chainedEvents){
            results.add(new EventResult(event,trendSolver.getTrends(event,false)));
        }

        return new ImplicationEventResult(results,findChainedEventsOccurences());
    }




    private List<List<Long>> reduceCandidates(List<Event> chainedEvents, List<List<Long>> implicationCandidates) {

        List<List<Long>> properImplications = new ArrayList<>();

        for (List<Long> candidate : implicationCandidates) {
            if (candidate.size() == chainedEvents.size()) {
                properImplications.add(candidate);
            }
        }
        return properImplications;
    }

    private List<Long> initNewLongList(DatasetItem item) {
        ArrayList<Long> result = new ArrayList<>();
        result.add(item.getTimestamp());
        return result;
    }

}

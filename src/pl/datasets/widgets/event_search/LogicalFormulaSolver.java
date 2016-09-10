package pl.datasets.widgets.event_search;

import pl.datasets.model.DatasetItem;
import pl.datasets.trend_match.TrendingSubsetWrapper;
import pl.datasets.utils.Event;

import java.util.List;

/**
 * Created by JOHANNES on 9/11/2016.
 */
public class LogicalFormulaSolver {


    private TrendingSubsetWrapper wrapper;
    private List<Event> chosenEvents;
    private List<String> chosenRelationships;

    public LogicalFormulaSolver(TrendingSubsetWrapper wrapper, List<Event> chosenEvents, List<String> chosenRelationships) {
        this.wrapper = wrapper;
        this.chosenEvents = chosenEvents;
        this.chosenRelationships = chosenRelationships;

        if (chosenEvents.size()!=chosenRelationships.size()+1)
            throw new IllegalArgumentException("Wrong number of items in lists passed as argument. Make sure that chosenEvents has one item more than chosenRelationships");
    }

    public List<List<Long>> evaluate(List<Event> events, List<String> relationships){
        if (events.size()>2&&relationships.size()>0){
            //TODO test implementation of recurring algorithm
            return LogicalSolver.evaluate(relationships.get(relationships.size()-1),
                    wrapper.getTrends(events.get(events.size()-1),false),
                    evaluate(events.subList(0,events.size()-1),
                            relationships.subList(0,
                                    relationships.size()-1)),
                    2,
                    wrapper.getDataset().size());
        }else
            return wrapper.getTrends(events.get(events.size()-1),false);
    }


}

package pl.datasets.widgets.event_search;

import pl.datasets.utils.Event;

import java.util.List;

/**
 * Created by JOHANNES on 9/15/2016.
 */
public class EventResult {

    private Event event;
    private List<List<Long>> results;

    public EventResult(Event event, List<List<Long>> results) {

        this.event = event;
        this.results = results;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<List<Long>> getResults() {
        return results;
    }

    public void setResults(List<List<Long>> results) {
        this.results = results;
    }
}

package pl.datasets.widgets.implication;

import pl.datasets.utils.Event;
import pl.datasets.widgets.event_search.EventResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOHANNES on 9/15/2016.
 */
public class ImplicationEventResult {

    private List<EventResult> listOfSingleEventResults;
    private List<List<Long>> listOfFullImplication;

    public List<EventResult> getListOfSingleEventResults() {
        return listOfSingleEventResults;
    }
    public List<List<Long>> getListOfFullImplication() {
        return listOfFullImplication;
    }

    public List<Integer> getEventsIndexes(){
        List<Integer> indexes = new ArrayList<>();

        for (EventResult result:listOfSingleEventResults)
            indexes.add(result.getEvent().getColumnIndex());

        return indexes;
    }


    public ImplicationEventResult(List<EventResult> listOfSingleEventResults, List<List<Long>> listOfFullImplication) {

        this.listOfSingleEventResults = listOfSingleEventResults;
        this.listOfFullImplication = listOfFullImplication;
    }

}

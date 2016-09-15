package pl.datasets.widgets.event_search;

import javafx.util.Pair;
import pl.datasets.utils.Event;

import java.util.List;

/**
 * @author Lukasz
 * @since 15.09.2016.
 */
public class EventSearchSummaryModel {


    public List<String> properties;
    public List<Pair<Event, List<Boolean>>> sliced;

    public EventSearchSummaryModel(List<String> properties, List<Pair<Event, List<Boolean>>> sliced) {
        this.properties = properties;
        this.sliced = sliced;
    }

    public static String labelForData(List<Boolean> data) {
        if (allTrue(data)) return "(Invariance)";
        else if (allFalse(data)) return "(Absence)";
        return "";
    }

    public static boolean allFalse(List<Boolean> data) {
        for (boolean b : data) if (b) return false;
        return true;
    }

    public static boolean allTrue(List<Boolean> data) {
        for (boolean b : data) if (!b) return false;
        return true;
    }

    public int[] getLongestEventLengthAndOccurence(List<Boolean> timestampsWhichAgreesWithEvent) {
        int longest = 0, tmpLength = 0, indexOfOccurence = 0;
        for (int j = 0; j < timestampsWhichAgreesWithEvent.size(); j++) {
            boolean b = timestampsWhichAgreesWithEvent.get(j);
            if (b) {
                ++tmpLength;
            } else {
                if (tmpLength > longest) {
                    longest = tmpLength;
                    indexOfOccurence = j - longest;
                }
                tmpLength = 0;
            }
        }
        if (tmpLength == timestampsWhichAgreesWithEvent.size()) {
            return new int[]{tmpLength, 0};
        } else return new int[]{
                longest, indexOfOccurence
        };
    }

    public float[] getPercentageStatistics(List<Boolean> timestampsWhichAgreesWithEvent) {
        int size = timestampsWhichAgreesWithEvent.size();
        int eventExistanceSize = calculateTimestampOccurrences(timestampsWhichAgreesWithEvent);
        int eventNonExistanceSize = size - eventExistanceSize;

        float percentOfExistance = (float) 100 * eventExistanceSize / size;
        float percentOfNonExistance = (float) 100 * eventNonExistanceSize / size;

        return new float[]{percentOfExistance, percentOfNonExistance};
    }

    public int calculateTimestampOccurrences(List<Boolean> timestampsWhichAgreesWithEvent) {
        return calculateTrues(timestampsWhichAgreesWithEvent);
    }

    public int calculateTrues(List<Boolean> list) {
        int size = 0;
        for (boolean value : list) if (value) ++size;
        return size;
    }

    public String findLongestDescription(List<Pair<Event, List<Boolean>>> sliced) {
        String longest = "";
        for (Pair<Event, List<Boolean>> pair : sliced) {

            Event nextEvent = pair.getKey();

            int propertyIndex = nextEvent.getColumnIndex();
            String propertyName = properties.get(propertyIndex);

            String labelForStrip = nextEvent.eventNameFor(propertyName);
            String out = labelForStrip + optionalJudgement(pair.getValue());
            longest = longest.length() < out.length() ? out : longest;
        }
        return longest;
    }

    public String withFixedOffset(int length, String s) {
        if (length == s.length()) return s;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length - s.length(); i++) {
            sb.append("_");
        }
        return sb.toString() + s;
    }

    public String optionalJudgement(List<Boolean> data) {
        return labelForData(data);
    }
}

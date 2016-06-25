package pl.datasets.utils;

import pl.datasets.interfaces.TrendDetectingStrategy;
import pl.datasets.model.DatasetItem;

import java.util.List;

/**
 * Created by JOHANNES on 6/25/2016.
 */
public class Strategies {

    public static TrendDetectingStrategy recognizeStrategy(String pattern) throws IllegalArgumentException  {
        switch (pattern) {

            case "++":
                return new AscendingTrendStrategy();
            case "--":
                return new DescendingTrendStrategy();
            case ">":
            case "<":
            case "==":
                throw new IllegalArgumentException("Treshold not set");
        }
        throw new IllegalArgumentException("Invalid operator");
    }


    public static TrendDetectingStrategy recognizeStrategy(String pattern, double treshold) throws IllegalArgumentException {
        switch (pattern) {

            case ">":
                return new GreaterThanStrategy(treshold);
            case "<":
                return new LessThanStrategy(treshold);
            case "==":
                return new EqualsStrategy(treshold);
        }
        throw new IllegalArgumentException();
    }


    public static class AscendingTrendStrategy implements TrendDetectingStrategy {

        @Override
        public boolean hasTrend(List<DatasetItem> candidate, Integer columnId) {

            if (candidate.size() == 1) {
                return true;
            } else if (candidate.size() > 1) {

                DatasetItem firstItem;
                DatasetItem secondItem;

                for (int i = 0; i < candidate.size() - 1; i++) {

                    firstItem = candidate.get(i);
                    secondItem = candidate.get(i + 1);

                    if (secondItem.getValues().get(columnId) <= firstItem.getValues().get(columnId)) {
                        return false;
                    }
                }
            }
            return true;

        }

        @Override
        public String getName() {
            return "Ascending";
        }
    }

    public static class DescendingTrendStrategy implements TrendDetectingStrategy {

        @Override
        public boolean hasTrend(List<DatasetItem> candidate, Integer columnId) {

            if (candidate.size() == 1) {
                return true;
            } else if (candidate.size() > 1) {

                DatasetItem firstItem;
                DatasetItem secondItem;

                for (int i = 0; i < candidate.size() - 1; i++) {

                    firstItem = candidate.get(i);
                    secondItem = candidate.get(i + 1);

                    if (secondItem.getValues().get(columnId) <= firstItem.getValues().get(columnId)) {
                        return false;
                    }
                }
            }
            return true;

        }

        @Override
        public String getName() {
            return "Ascending";
        }
    }


    public static class GreaterThanStrategy implements TrendDetectingStrategy {

        double treshold;

        GreaterThanStrategy(double treshold) {
            this.treshold = treshold;
        }

        @Override
        public boolean hasTrend(List<DatasetItem> candidate, Integer columnId) {


            if (candidate.size() == 1) {
                return true;
            } else if (candidate.size() > 1) {

                DatasetItem item;

                for (int i = 0; i < candidate.size() - 1; i++) {

                    item = candidate.get(i);

                    if (Double.compare(item.getValues().get(columnId), treshold) != 1) {
                        return false;
                    }
                }
            }
            return true;

        }

        @Override
        public String getName() {
            return "GreaterThan";
        }
    }

    public static class LessThanStrategy implements TrendDetectingStrategy {

        double treshold;

        LessThanStrategy(double treshold) {
            this.treshold = treshold;
        }

        @Override
        public boolean hasTrend(List<DatasetItem> candidate, Integer columnId) {


            if (candidate.size() == 1) {
                return true;
            } else if (candidate.size() > 1) {

                DatasetItem item;

                for (int i = 0; i < candidate.size() - 1; i++) {

                    item = candidate.get(i);

                    if (Double.compare(item.getValues().get(columnId), treshold) != -1) {
                        return false;
                    }
                }
            }
            return true;

        }

        @Override
        public String getName() {
            return "GreaterThan";
        }
    }

    public static class EqualsStrategy implements TrendDetectingStrategy {

        double treshold;

        EqualsStrategy(double treshold) {
            this.treshold = treshold;
        }

        @Override
        public boolean hasTrend(List<DatasetItem> candidate, Integer columnId) {


            if (candidate.size() == 1) {
                return true;
            } else if (candidate.size() > 1) {

                DatasetItem item;

                for (int i = 0; i < candidate.size() - 1; i++) {

                    item = candidate.get(i);

                    if (Double.compare(item.getValues().get(columnId), treshold) != 0) {
                        return false;
                    }
                }
            }
            return true;

        }

        @Override
        public String getName() {
            return "GreaterThan";
        }
    }

}

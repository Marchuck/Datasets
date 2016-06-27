package pl.datasets.utils;

import pl.datasets.interfaces.TrendDetectingStrategy;
import pl.datasets.model.DatasetItem;

import java.util.List;

/**
 * Created by JOHANNES on 6/25/2016.
 */
public class Strategies {


    public static TrendDetectingStrategy recognizeStrategy(String pattern) throws IllegalArgumentException {
        return recognizeStrategy(pattern, -1);
    }

    public static TrendDetectingStrategy recognizeStrategy(String pattern, double treshold) throws IllegalArgumentException {
        switch (pattern) {

            case "++":
                return new AscendingTrendStrategy();
            case "--":
                return new DescendingTrendStrategy();

            case ">":
                return new GreaterThanStrategy(treshold);
            case "<":
                return new LessThanStrategy(treshold);
            case "==":
                return new EqualsStrategy(treshold);
        }
        throw new IllegalArgumentException();
    }


    public static class AscendingTrendStrategy extends TrendDetectingStrategy {
        public AscendingTrendStrategy() {
            super("Ascending");
        }
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
    }

    public static class DescendingTrendStrategy extends TrendDetectingStrategy {
        public DescendingTrendStrategy() {
            super("Descending");
        }

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
    }

    public static class InvariantStrategy extends TrendDetectingStrategy{

        public InvariantStrategy(String name) {
            super(name);
        }

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

                    if (Double.compare(firstItem.getValues().get(columnId),secondItem.getValues().get(columnId))!=0) {
                        return false;
                    }
                }
            }
            return true;
        }
    }


    public static class GreaterThanStrategy extends TrendDetectingStrategy {

        double treshold;

        GreaterThanStrategy(double treshold) {
            super("Greater than");
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
    }

    public static class LessThanStrategy extends TrendDetectingStrategy {

        double treshold;

        LessThanStrategy(double treshold) {
            super("Less than");
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
    }

    public static class EqualsStrategy extends TrendDetectingStrategy {

        double treshold;

        EqualsStrategy(double treshold) {
            super("Equals");
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
    }

}

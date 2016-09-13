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
            case ">=":
                return new GreaterThanStrategy(treshold);
            case "<=":
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

        @Override
        public String getOptionalThreshold() {
            return "";
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

        @Override
        public String getOptionalThreshold() {
            return "";
        }
    }

    public static class InvariantStrategy extends TrendDetectingStrategy {

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

                    if (Double.compare(firstItem.getValues().get(columnId), secondItem.getValues().get(columnId)) != 0) {
                        return false;
                    }
                }
            }
            return true;
        }

        @Override
        public String getOptionalThreshold() {
            return "";
        }
    }


    public static class GreaterThanStrategy extends TrendDetectingStrategy {

        double threshold;

        public GreaterThanStrategy(double threshold) {
            super("Greater than");
            this.threshold = threshold;
        }

        @Override
        public String getOptionalThreshold() {
            return String.format("%." + ACCURACY + "f", threshold);
        }

        @Override
        public boolean hasTrend(List<DatasetItem> candidate, Integer columnId) {

            if (candidate.size() == 1) {
                return Double.compare(candidate.get(0).getValues().get(columnId), threshold) == 1;
            } else if (candidate.size() > 1) {

                DatasetItem item;

                for (int i = 0; i < candidate.size() - 1; i++) {

                    item = candidate.get(i);

                    if (Double.compare(item.getValues().get(columnId), threshold) != 1) {
                        return false;
                    }
                }
            }
            return true;

        }
    }

    public static class LessThanStrategy extends TrendDetectingStrategy {

        double threshold;

        public LessThanStrategy(double threshold) {
            super("Less than");
            this.threshold = threshold;
        }

        @Override
        public boolean hasTrend(List<DatasetItem> candidate, Integer columnId) {

            if (candidate.size() == 1) {
                return Double.compare(candidate.get(0).getValues().get(columnId), threshold) == -1;
            } else if (candidate.size() > 1) {

                for (int i = 0; i < candidate.size() - 1; i++) {

                    DatasetItem item = candidate.get(i);

                    if (Double.compare(item.getValues().get(columnId), threshold) != -1) {
                        return false;
                    }
                }
            }
            return true;
        }

        @Override
        public String getOptionalThreshold() {
            return String.format("%." + ACCURACY + "f", threshold);
        }
    }

    public static class EqualsStrategy extends TrendDetectingStrategy {

        double threshold;

        public EqualsStrategy(double threshold) {
            super("Equals");
            this.threshold = threshold;
        }

        @Override
        public String getOptionalThreshold() {
            return String.format("%." + ACCURACY + "f", threshold);
        }

        @Override
        public boolean hasTrend(List<DatasetItem> candidate, Integer columnId) {


            if (candidate.size() == 1) {
                return Double.compare(candidate.get(0).getValues().get(columnId), threshold) == 0;
            } else if (candidate.size() > 1) {

                DatasetItem item;

                for (int i = 0; i < candidate.size() - 1; i++) {

                    item = candidate.get(i);

                    if (Double.compare(item.getValues().get(columnId), threshold) != 0) {
                        return false;
                    }
                }
            }
            return true;

        }
    }

}

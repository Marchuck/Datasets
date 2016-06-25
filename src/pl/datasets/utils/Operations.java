package pl.datasets.utils;

import pl.datasets.model.DatasetItem;

/**
 * @author Lukasz
 * @since 23.06.2016.
 */
public class Operations {
    public static Operation match(Object item) {
        //todo: implement all possible operators
        if (item instanceof String) {
            String asString = (String) item;
            if (asString.equalsIgnoreCase("<="))
                return LEQ.create();
            else if (asString.equalsIgnoreCase(">="))
                return GEQ.create();
        }
        return null;
    }



    /**
     * Less or equal
     */
    public static class LEQ implements Operation {
        public static LEQ create() {
            return new LEQ();
        }

        @Override
        public boolean compute(double value, double valueToCompare) {
            return value <= valueToCompare;
        }
    }

    /**
     * Greater or equal
     */
    public static class GEQ implements Operation {
        public static GEQ create() {
            return new GEQ();
        }

        @Override
        public boolean compute(double value, double valueToCompare) {
            return value >= valueToCompare;
        }
    }
}

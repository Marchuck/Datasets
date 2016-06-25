package pl.datasets.utils;

/**
 * @author Lukasz
 * @since 23.06.2016.
 */
public class OperationManager {
    public static Operation create(final Object value) {
       /* if (value instanceof String) {
            return new Operation() {
                @Override
                public boolean compute(DatasetItem first, double valueToCompare, int columnIndex) {
                    if (((String) value).equalsIgnoreCase("<>"))
                        return Double.compare(first.getValues().get(columnIndex), valueToCompare) != 0;
                    else return false;
                }
            };
        }*/
        return null;
    }
}

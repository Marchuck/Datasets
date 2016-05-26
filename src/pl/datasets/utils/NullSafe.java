package pl.datasets.utils;

import java.util.Collection;
import java.util.List;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public class NullSafe {

    public static <T> boolean nonEmpty(Collection<T> items) {
        return items != null && items.size() > 0;
    }
    public static <T> boolean nonEmpty(T[] items) {
        return items != null && items.length > 0;
    }

}

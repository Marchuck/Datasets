package pl.datasets.interfaces;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public interface UnSafeReadStrategy<T> {
    T createNewRow(String line);
}

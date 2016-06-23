package pl.datasets.interfaces;

import java.text.ParseException;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public interface ReadStrategy<T> {
    T createNewRow(String line) throws ParseException;
}

package pl.datasets.load;

import com.sun.javafx.beans.annotations.NonNull;
import pl.datasets.Item;
import pl.datasets.interfaces.ReadStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public class CSVReader<T> {
    private boolean shouldSkipFirstLine;
    private String firstLine;

    public CSVReader<T> skipFirstLine() {
        shouldSkipFirstLine = true;
        return this;
    }

    public String getFirstLine() {
        return firstLine;
    }

    public final static ReadStrategy<Item> DEFAULT = new ReadStrategy<Item>() {
        @Override
        public Item createNewRow(String line) {
            return new Item(line);
        }
    };


    public List<T> readFile(File file, @NonNull ReadStrategy<T> strategy) {
        List<T> list = new ArrayList<>();
        Scanner input = null;
        try {
            input = new Scanner(file);
            while (input.hasNextLine()) {
                //read next line
                String nextLine = input.nextLine();
                //save record as four variables
                if (shouldSkipFirstLine) {
                    firstLine = nextLine;
                    shouldSkipFirstLine = false;
                    continue;
                }
                list.add(strategy.createNewRow(nextLine));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error! " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (input != null) input.close();
        }
        return list;
    }
}

package pl.datasets.load;

import com.sun.javafx.beans.annotations.NonNull;
import pl.datasets.Item;
import pl.datasets.interfaces.ReadStrategy;
import pl.datasets.model.DatasetItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public class CSVReader<T> {
    public final static ReadStrategy<Item> DEFAULT = new ReadStrategy<Item>() {
        @Override
        public Item createNewRow(String line) {
            return new Item(line);
        }
    };
    private boolean shouldSkipFirstLine;
    private String firstLine;

    public CSVReader<T> skipFirstLine() {
        shouldSkipFirstLine = true;
        return this;
    }

    public String getFirstLine() {
        return firstLine;
    }

    public List<T> readFile(File file, @NonNull ReadStrategy<T> strategy) throws ParseException {
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


    public String[] properties() {
        return firstLine.split(",");
    }

    public String[] properties(String expression) {
        return firstLine.split(expression);
    }

    public static class DataSetItemStrategy implements ReadStrategy<DatasetItem> {
        @Override
        public DatasetItem createNewRow(String line) throws ParseException {
            String[] all = line.split(",");
            SimpleDateFormat dateFormat = new SimpleDateFormat("\"dd/MM/yyyy\"");
            Date date = dateFormat.parse(all[0]);
            List<Double> doubles = new ArrayList<>();
            for (int j = 1; j < all.length; j++) doubles.add((double) all[j].hashCode());

            return new DatasetItem(date.getTime(), doubles);
        }
    }
}

package pl.datasets.load;

import com.sun.javafx.beans.annotations.NonNull;
import pl.datasets.Item;
import pl.datasets.interfaces.Rangeable;
import pl.datasets.interfaces.ReadStrategy;
import pl.datasets.interfaces.UnSafeReadStrategy;
import pl.datasets.model.BaseItem;
import pl.datasets.model.DatasetItem;
import pl.datasets.utils.Utils;

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


    public List<T> readDataset(File file, @NonNull UnSafeReadStrategy<T> strategy) {
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

                if (!nextLine.trim().isEmpty())
                    try {
                        list.add(strategy.createNewRow(nextLine));
                    } catch (IndexOutOfBoundsException ex) {
                        Utils.loge(ex.getMessage());
                        ex.printStackTrace();
                    }
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


    public static class DataSetOnlyDoubleItemStrategy implements ReadStrategy<DatasetItem> {
        @Override
        public DatasetItem createNewRow(String line) throws ParseException {
            String[] all = line.split(",");
            SimpleDateFormat dateFormat = new SimpleDateFormat("\"dd/MM/yyyy\"");
            Date date = dateFormat.parse(all[0]);
            List<Double> doubles = new ArrayList<>();


            doubles.add((double) all[1].hashCode());
            doubles.add(Double.parseDouble(all[4].replaceAll("\"", "")));

            return new DatasetItem(date.getTime(), doubles);
        }
    }

    public static class DataSetWeatherStrategy implements UnSafeReadStrategy<BaseItem>, Rangeable {

        @Override
        public BaseItem createNewRow(String line) {
            String[] all = line.split(",");
            //SF15,"Warnerville Switchyard, Oakdale",
            // "Warnerville",5,2016-01-6? 01:45,
            // 0,0,0,0,0.046

            List<Double> doubles = new ArrayList<>();
            doubles.add(Double.parseDouble(all[6]));
            doubles.add(Double.parseDouble(all[7]));
            doubles.add(Double.parseDouble(all[8]));
            doubles.add(Double.parseDouble(all[9]));
//            doubles.add(Double.parseDouble(all[10]));
            return new BaseItem(doubles);
        }

        @Override
        public int getFromIndex() {
            return 6;
        }

        @Override
        public int getToIndex() {
            return 9 + 1;//must be 10 +1(subList works exclusive for last element)
        }
    }

    public static class IstambulStrategy implements UnSafeReadStrategy<BaseItem>, Rangeable {
        private int index = 0;

        @Override
        public int getFromIndex() {
            return 1;
        }

        @Override
        public int getToIndex() {
            return 9;//must be 9 +1(subList works exclusive for last element)
        }

        @Override
        public BaseItem createNewRow(String line) {
            String[] items = line.split(";");
            List<Double> doubles = new ArrayList<>();
            for (int j = 1; j < items.length; j++) {
                doubles.add(Double.parseDouble(items[j].replace(",", ".")));
            }
            ++index;
            return new BaseItem(doubles);
        }
    }

    public static class IslandStrategy implements UnSafeReadStrategy<BaseItem>, Rangeable {

        @Override
        public int getFromIndex() {
            return 2;
        }

        @Override
        public int getToIndex() {
            return 9;
        }

        @Override
        public BaseItem createNewRow(String line) {
//            "Month";"??ey";"Akureyri";"Dalatangi";"Gr?mssta?ir";"Keflav?k airport";"Kirkjub?jarklaustur";"Reykjav?k";"St?rh?f?i Westman Islands";"Stykkish?lmur"
//            "1949-01"; ;5.2;   53.2;2.5;;19.4;9.6;15;12.9
            String[] strings = line.split(";");

            List<Double> doubles = new ArrayList<>();
            int index = 1;
            while (index < strings.length) {
                if (!strings[index].trim().isEmpty()) doubles.add(Double.parseDouble(strings[index]));
                index++;
            }

            return new BaseItem(doubles);
        }
    }
}

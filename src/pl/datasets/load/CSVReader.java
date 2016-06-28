package pl.datasets.load;

import com.sun.javafx.beans.annotations.NonNull;
import pl.datasets.Item;
import pl.datasets.interfaces.ReadStrategy;
import pl.datasets.interfaces.SubsetStrategy;
import pl.datasets.interfaces.UnSafeReadStrategy;
import pl.datasets.model.BaseItem;
import pl.datasets.model.DatasetItem;
import pl.datasets.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public static double[] wrap(List<Double> doubles) {
        double[] doubles1 = new double[doubles.size()];
        for (int j = 0; j < doubles.size(); j++)
            doubles1[j] = doubles.get(j);
        return doubles1;
    }


    public static String[] listToArray(List<String> strings) {
        String[] strings1 = new String[strings.size()];
        for (int j = 0; j < strings.size(); j++)
            strings1[j] = strings.get(j);
        return strings1;
    }

    public static <T> T[] genericlistToArray(List<T> listItems, @NonNull Bie<T> newbie) {
        T[] arrayItems = newbie.create(listItems.size());//[strings.size()];
        for (int j = 0; j < listItems.size(); j++)
            arrayItems[j] = listItems.get(j);
        return arrayItems;
    }

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
                //save record saveAs four variables
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
                //save record saveAs four variables
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

    public interface Bie<T> {
        T[] create(int capacity);
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

    public static class PollutantStrategy implements SubsetStrategy {
        @Override
        public BaseItem createNewRow(String line) {
            String[] all = line.split(",");
            List<Double> doubles = new ArrayList<>();

            doubles.add((double) all[1].hashCode());
            doubles.add(Double.parseDouble(all[4].replaceAll("\"", "")));

            return new BaseItem(doubles);
        }

        @Override
        public List<String> getProperties() {
            List<String> l = new ArrayList<>();
//            "Date","Main Pollutant","Site Name","Site ID"," AQI Value"
            l.add("Main_Pollutant");
            l.add("AQI_Value");
            return l;
        }
    }

    public static class DataSetWeatherStrategy implements SubsetStrategy {
        private String base;

        public DataSetWeatherStrategy(String base) {
            this.base = base;
        }

        public DataSetWeatherStrategy() {
            this("");
        }

        @Override
        public BaseItem createNewRow(String line) {
            String[] all = line.split(",");
            List<Double> doubles = new ArrayList<>();
            doubles.add(Double.parseDouble(all[6]));
            doubles.add(Double.parseDouble(all[7]));
            doubles.add(Double.parseDouble(all[8]));
            doubles.add(Double.parseDouble(all[9]));
//            doubles.add(Double.parseDouble(all[10]));
            return new BaseItem(doubles);
        }

        @Override
        public List<String> getProperties() {
            List<String> list = new ArrayList<>();
            list.add(base.concat("_Wind_Velocity_Mtr_Sec"));
            list.add(base.concat("_Wind_Direction_Variance_Deg"));
            list.add(base.concat("_Wind_Direction_Deg"));
            list.add(base.concat("_Ambient_Temperature_Deg_C"));
            return list;
        }
    }

    public static class IstambulStrategy implements SubsetStrategy {

        @Override
        public BaseItem createNewRow(String line) {
            String[] items = line.split(";");
            List<Double> doubles = new ArrayList<>();
            for (int j = 1; j < items.length; j++) {
                doubles.add(Double.parseDouble(items[j].replace(",", ".")));
            }
            return new BaseItem(doubles);
        }

        @Override
        public List<String> getProperties() {
            List<String> l = new ArrayList<>();
            l.addAll(Arrays.asList(("ISE1;ISE2;SP;DAX;FTSE;NIKKEI;BOVESPA;EU;EM").split(";")));
            return l;
        }
    }

    public static class NFLWeatherStrategy implements SubsetStrategy {

        @Override
        public BaseItem createNewRow(String line) {
            //id,home_team,home_score,away_team,away_score,temperature,wind_chill,humidity,wind_mph,weather,date
//            196009230ram,Los Angeles Rams,21,St. Louis Cardinals,43,66,,78%,8,66 degrees- relative humidity 78%- wind 8 mph,9/23/1960
//            196009240dal,Dallas Cowboys,28,Pittsburgh Steelers,35,72,,80%,16,72 degrees- relative humidity 80%- wind 16 mph,9/24/1960
            String[] s = line.split(",");
            List<Double> doubles = new ArrayList<>();
            doubles.add(Double.parseDouble(s[2]));
            doubles.add(Double.parseDouble(s[4]));
            doubles.add(Double.parseDouble(s[5]));
            if (!s[6].isEmpty()) doubles.add(Double.parseDouble(s[6]));
            else if (!s[7].isEmpty()) doubles.add(Double.parseDouble(s[7].replace("%", "")));
            else if (!s[8].isEmpty()) doubles.add(Double.parseDouble(s[8]));
            doubles.add(Double.parseDouble(s[9].substring(0, 2).trim()));
            doubles.add(Double.parseDouble(s[9].split("humidity ")[0].substring(0, 2)));
            doubles.add(Double.parseDouble(s[9].split("wind ")[0].substring(0, 2)));
            return new BaseItem(doubles);
        }

        @Override
        public List<String> getProperties() {
            List<String> l = new ArrayList<>();
            l.add("home_score");
            l.add("away_score");
            l.add("temperature");
            l.add("wind_chill");

//            l.add("wind_mph");
            l.add("weather_temp");
            l.add("weather_humidity");
            l.add("weather_wind");

            return l;
        }
    }

    public static class IslandStrategy implements SubsetStrategy {

        @Override
        public BaseItem createNewRow(String line) {
            String[] strings = line.split(";");

            List<Double> doubles = new ArrayList<>();
            int index = 1;
            while (index < strings.length) {
                if (!strings[index].trim().isEmpty()) doubles.add(Double.parseDouble(strings[index].replace(",", ".")));
                index++;
            }
            return new BaseItem(doubles);
        }

        @Override
        public List<String> getProperties() {
            String[] s = "\"??ey\";\"Akureyri\";\"Dalatangi\";\"Gr?mssta?ir\";\"Keflav?k airport\";\"Kirkjub?jarklaustur\";\"Reykjav?k\";\"St?rh?f?i Westman Islands\";\"Stykkish?lmur\"".split(";");
            List<String> l = new ArrayList<>();
            for (String x : s) l.add(x.replaceAll("\"", ""));
            return l;
        }
    }

}

package pl.datasets.utils;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz
 * @since 27.06.2016.
 */
public class CsvSave<DATA> {
    private List<DATA> list = new ArrayList<>();
    private Strategy<DATA> strategy;

    public CsvSave(List<DATA> list) {
        this.list = list;
    }

    public CsvSave<DATA> withStrategy(Strategy<DATA> strategy) {
        this.strategy = strategy;
        return this;
    }

    public boolean saveAs(String nameWithoutExtension) {
        Utils.log("saveAs " + nameWithoutExtension + ".csv");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(nameWithoutExtension + ".csv", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException x) {
            Utils.log(x.getMessage());
            x.printStackTrace();
            return false;
        } finally {
            if (writer != null) {
                for (DATA item : list) {
                    writer.println(strategy.saveLine(item));
                }
                writer.close();
            }
        }
        return true;
    }

    public interface Strategy<DATA> {
        String saveLine(DATA data);
    }
}

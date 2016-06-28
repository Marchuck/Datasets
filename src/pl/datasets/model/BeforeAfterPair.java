package pl.datasets.model;

/**
 * Created by JOHANNES on 6/27/2016.
 */
public class BeforeAfterPair {

    int beforeTimestamp;
    int afterTimestamp;

    public BeforeAfterPair(int beforeTimestamp, int afterTimestamp) {

        this.beforeTimestamp = beforeTimestamp;
        this.afterTimestamp = afterTimestamp;
    }

    public int getBeforeTimestamp() {
        return beforeTimestamp;
    }

    public void setBeforeTimestamp(int beforeTimestamp) {
        this.beforeTimestamp = beforeTimestamp;
    }

    public int getAfterTimestamp() {
        return afterTimestamp;
    }

    public void setAfterTimestamp(int afterTimestamp) {
        this.afterTimestamp = afterTimestamp;
    }

    @Override
    public String toString() {
        return "[" + beforeTimestamp +
                ", " + afterTimestamp + "]";
    }
}

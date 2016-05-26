package pl.datasets.interfaces;


/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public interface OnFileChosenListener {
    void onChosen(int fileIndex);
    void onRemoved(int fileIndex);
}

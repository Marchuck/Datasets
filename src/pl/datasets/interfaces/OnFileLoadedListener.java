package pl.datasets.interfaces;

import com.sun.istack.internal.Nullable;

import java.io.File;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public interface OnFileLoadedListener {
    void onFileLoaded(@Nullable File file);
}

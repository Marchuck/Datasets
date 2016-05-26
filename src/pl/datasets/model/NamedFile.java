package pl.datasets.model;

import pl.datasets.interfaces.Nameable;

import java.io.File;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public class NamedFile implements Nameable {
    public File file;

    public NamedFile() {
    }

    public NamedFile(File file) {
        this.file = file;
    }

    @Override
    public String getName() {

        return file != null ? file.getName() : "null";
    }
}

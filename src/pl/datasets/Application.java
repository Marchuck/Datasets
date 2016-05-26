package pl.datasets;

import javax.swing.*;

/**
 * @author Lukasz
 * @since 26.05.2016.
 */
public class Application {
    public JFrame currentUsedFrame;

    public static Application instance;

    public static Application getInstance() {
        return instance == null ? new Application() : instance;
    }

    public static void main(String[] args) {
        Application.getInstance();
    }


    private Application() {
        currentUsedFrame = new MainGUIForm();
    }

}

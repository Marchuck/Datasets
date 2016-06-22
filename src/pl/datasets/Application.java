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
        if (instance == null) instance = new Application();
        return instance;
    }

    public static void main(String[] args) {
        Application.getInstance();

    }


    private Application() {
        currentUsedFrame = new MainGUIForm();
    }

}

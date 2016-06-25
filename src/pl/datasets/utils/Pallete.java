package pl.datasets.utils;

import java.awt.*;

/**
 * @author Lukasz
 * @since 26.05.2016.
 * Defines the app colors here
 */
public class Pallete {


    private Pallete() {
    }

    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }

    public static Color primaryColor() {
        return hex2Rgb("#3F51B5");
    }

    public static Color primaryDarkColor() {
        return hex2Rgb("#FFC107");
    }

    public static Color accentColor() {
        return hex2Rgb("#3f51b5");
    }

    public static Color primaryTextColor() {
        return hex2Rgb("#212121");
    }

    public static Color secondaryTextColor() {
        return hex2Rgb("#727272");
    }
}

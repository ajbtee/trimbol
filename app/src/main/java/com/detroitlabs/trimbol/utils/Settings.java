package com.detroitlabs.trimbol.utils;

import android.widget.TextView;

/**
 * Created by andrewjb on 12/4/14.
 */
public class Settings {

    public static enum GameTheme {
        TRIMBOL,
        DARK,
        CLASSIC,
        LITERALLY,
        SMORES
    }

    // Default settings
    public static GameTheme gameTheme = GameTheme.CLASSIC;
    public static int selectedTheme = 2;

    public static void useTheme(TextView theme) {
        if (selectedTheme > GameTheme.values().length-1)
            selectedTheme = 0;
        if (selectedTheme < 0)
            selectedTheme = GameTheme.values().length-1;
        gameTheme = gameTheme.values()[selectedTheme];
        switch (gameTheme) {
            case TRIMBOL:
                theme.setText("TRIMBOL");
                break;
            case DARK:
                theme.setText("DARK");
                break;
            case CLASSIC:
                theme.setText("CLASSIC");
                break;
            case LITERALLY:
                theme.setText("LITERALLY");
                break;
            case SMORES:
                theme.setText("SMORES");
                break;
        }
    }
}

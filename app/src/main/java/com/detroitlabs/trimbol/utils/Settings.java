package com.detroitlabs.trimbol.utils;

/**
 * Created by andrewjb on 12/4/14.
 */
public class Settings {
    public static enum GameMode {
        TUTORIAL,
        EASY,
        NORMAL,
        HARD,
        ESCALATE
    }
    public static enum GameTheme {
        TRIMBOL,
        CLASSIC,
        LITERALLY
    }

    // Default settings
    public static GameMode gameMode = GameMode.ESCALATE;
    public static GameTheme gameTheme = GameTheme.TRIMBOL;
    public static boolean gameSound = true;
}

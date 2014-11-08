package com.detroitlabs.trimbol;

import java.lang.reflect.Array;

/**
 * Created by andrewjb on 11/8/14.
 */
public class LevelGrid {
    // constants
    final int NIL = 0;
    final int ROC = 1;
    final int PAP = 2;
    final int SCI = 3;

    // grid size
    final int gridX = 3;
    final int gridY = 3;
    int[][] grid = new int[gridX][gridY];

    // random start location
    int gridCursorX = (int) Math.ceil(Math.random() * gridX);
    int gridCursorY = (int) Math.ceil(Math.random() * gridY);
    int symbol = (int) Math.ceil(Math.random() * 3);
}

package com.detroitlabs.trimbol.utils;

import com.detroitlabs.trimbol.objects.Grid;
import java.util.Stack;

public class SaveGame {
    private final Stack<Grid> gridHistory = new Stack<Grid>();
    public Settings.GameTheme currentTheme;
    public int currentLevel;
    public int currentStreak;
    public int currentRank;
    public int currentRankXP;
    public int maxStreak;
    public int maxLevel;
    public int maxPuzzlesSolved;
    public int maxSymbolsTaken;
}

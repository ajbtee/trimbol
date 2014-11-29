package com.detroitlabs.trimbol.objects;


public class Loc {
    final public static int X = 0;
    final public static int Y = 1;
    int locX;
    int locY;

    public Loc(int x,int y) {
        locX = x;
        locY = y;
    }

    public int getX() {
        return locX;
    }
    public int getY() {
        return locY;
    }
}

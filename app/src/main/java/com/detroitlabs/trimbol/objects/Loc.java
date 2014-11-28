package com.detroitlabs.trimbol.objects;


public class Loc {
    final static int X = 0;
    final static int Y = 1;
    int[] xy;

    public Loc(int x,int y) {
        this.xy[X] = x;
        this.xy[Y] = y;
    }

    public int[] getXy() {
        return xy;
    }

    public void setXy(int[] xy) {
        this.xy = xy;
    }
}

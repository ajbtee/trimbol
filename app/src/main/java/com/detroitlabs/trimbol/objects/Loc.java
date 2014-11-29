package com.detroitlabs.trimbol.objects;


import java.util.ArrayList;
import java.util.List;

public class Loc {
    final static int X = 0;
    final static int Y = 1;
    List xy = new ArrayList();

    public Loc(int x,int y) {
        xy.add(x);
        xy.add(y);
    }
}

package com.detroitlabs.trimbol.objects;

/**
 * Created by andrewjb on 11/14/14.
 */
public class Symbol extends Grid{

    // Constants
    public final static int NIL = 0;
    public final static int ROC = 1;
    public final static int PAP = 2;
    public final static int SCI = 3;
    public final static int STATE_BORN = 0;
    public final static int STATE_GONE = 1;

    int symbol;
    int state;
    Loc location;

    public Symbol(int symbol, int state, Loc loc) {
        this.symbol = symbol;
        this.state = state;
        this.location = loc;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Loc getLocation() {
        return location;
    }

    public void setLocation(Loc location) {
        this.location = location;
    }
}

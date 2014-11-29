package com.detroitlabs.trimbol.objects;

/**
 * Created by andrewjb on 11/14/14.
 */
public class Symbol{

    // Constants
    public final static int NIL = 0;
    public final static int ROC = 1;
    public final static int PAP = 2;
    public final static int SCI = 3;
    public final static int STATE_GONE = 0;
    public final static int STATE_BORN = 1;
    public final static int STATE_SELECTED = 2;

    int type;
    int state;
    int posX;
    int posY;

    public Symbol(int symbol, int state, int posY, int posX) {
        this.type = symbol;
        this.state = state;
        this.posX = posX;
        this.posY = posY;
    }

    public int getType() {
        return type;
    }

    public int getState() {
        return state;
    }
}

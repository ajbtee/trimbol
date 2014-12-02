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

    public static enum State {
        GONE,
        EXIST,
        SELECT,
        CONVERT,
        HISTORY

    }

    State state;
    int type;
    int posX;
    int posY;

    public Symbol(int symbol, State state, int posY, int posX) {
        this.type = symbol;
        this.state = state;
        this.posX = posX;
        this.posY = posY;
    }

    public int getX(){
        return posX;
    }

    public int getY(){
        return posY;
    }

    public int getType() {
        return type;
    }

    public State getState() {
        return state;
    }
}

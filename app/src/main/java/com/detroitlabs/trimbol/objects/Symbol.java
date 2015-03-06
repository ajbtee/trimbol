package com.detroitlabs.trimbol.objects;

/**
 * Created by andrewjb on 11/14/14.
 */
public class Symbol{

    // Constants
    public static enum Type {
        NIL,
        ROC,
        PAP,
        SCI
    }

    public static enum State {
        GONE,
        EXIST,
        CONVERT,
    }

    State state;
    Type type;
    int posX;
    int posY;
    public int xCoord, yCoord;

    public Symbol(Type symbol, State state, int posY, int posX) {
        this.type = symbol;
        this.state = state;
        this.posX = posX;
        this.posY = posY;
    }

    public Symbol(Symbol symbol) {
        this(symbol.type, symbol.state, symbol.posY, symbol.posX);
    }

    public Type getType() {
        return type;
    }

    public State getState() {
        return state;
    }

    public static Type counterOf(Type s) {
        switch (s) {
            case ROC: return Type.SCI;
            case SCI: return Type.PAP;
            case PAP: return Type.ROC;
        }
        return Type.NIL;
    }
}

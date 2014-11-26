package com.detroitlabs.trimbol.objects;

/**
 * Created by andrewjb on 11/14/14.
 */
public class Symbol {

    // Constants
    public final static int NIL = 0;
    public final static int ROC = 1;
    public final static int PAP = 2;
    public final static int SCI = 3;

    int posX;
    int posY;

    boolean nilN;
    boolean nilS;
    boolean nilE;
    boolean nilW;

    int symbol;
    int state;

    public Symbol(int posX, int posY, boolean nilN, boolean nilS, boolean nilE, boolean nilW, int symbol, int state) {
        this.posX = posX;
        this.posY = posY;
        this.nilN = nilN;
        this.nilS = nilS;
        this.nilE = nilE;
        this.nilW = nilW;
        this.symbol = symbol;
        this.state = state;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public boolean isNilN() {
        return nilN;
    }

    public void setNilN(boolean nilN) {
        this.nilN = nilN;
    }

    public boolean isNilS() {
        return nilS;
    }

    public void setNilS(boolean nilS) {
        this.nilS = nilS;
    }

    public boolean isNilE() {
        return nilE;
    }

    public void setNilE(boolean nilE) {
        this.nilE = nilE;
    }

    public boolean isNilW() {
        return nilW;
    }

    public void setNilW(boolean nilW) {
        this.nilW = nilW;
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
}

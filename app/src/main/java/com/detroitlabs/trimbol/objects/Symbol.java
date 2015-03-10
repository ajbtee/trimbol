package com.detroitlabs.trimbol.objects;

import java.util.Random;

import static com.detroitlabs.trimbol.objects.Symbol.Type.PAP;
import static com.detroitlabs.trimbol.objects.Symbol.Type.ROC;
import static com.detroitlabs.trimbol.objects.Symbol.Type.SCI;

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

    private static final Random random = new Random();

    State state;
    Type type;

    public Symbol(Type symbol, State state) {
        this.type = symbol;
        this.state = state;
    }

    public Symbol(Symbol symbol) {
        this(symbol.type, symbol.state);
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

    public static Symbol random() {
        Type[] types = {ROC, PAP, SCI};
        Type randomType = types[random.nextInt(types.length)];
        return new Symbol(randomType, State.EXIST);
    }
}

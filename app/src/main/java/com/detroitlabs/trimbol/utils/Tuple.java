package com.detroitlabs.trimbol.utils;

public class Tuple<A, B> {
    public final A fst;
    public final B snd;
    public Tuple(A fst, B snd) {
        this.fst = fst;
        this.snd = snd;
    }
}

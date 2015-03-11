package com.detroitlabs.trimbol.utils;

public class Tuple<A, B> {
    public final A fst;
    public final B snd;
    public Tuple(final A fst, final B snd) {
        this.fst = fst;
        this.snd = snd;
    }
}

package com.scratchgame;

public class BonusSymbol extends Symbol {
    private final int extra;

    public BonusSymbol(String symbol, int extra) {
        super(symbol, "bonus");
        this.extra = extra;
    }

    public int getExtra() {
        return extra;
    }
}

package com.scratchgame;

public class Symbol {
    private final String symbol;
    private final String type;

    public Symbol(String symbol, String type) {
        this.symbol = symbol;
        this.type = type;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getType() {
        return type;
    }
}

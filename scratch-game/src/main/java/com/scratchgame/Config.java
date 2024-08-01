package com.scratchgame;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class Config {
    private final int rows;
    private final int columns;
    private final JsonNode probabilities;
    private final JsonNode symbols;
    private final JsonNode winCombinations;

    public Config(
            @JsonProperty("rows") int rows,
            @JsonProperty("columns") int columns,
            @JsonProperty("probabilities") JsonNode probabilities,
            @JsonProperty("symbols") JsonNode symbols,
            @JsonProperty("win_combinations") JsonNode winCombinations) {
        this.rows = rows;
        this.columns = columns;
        this.probabilities = probabilities;
        this.symbols = symbols;
        this.winCombinations = winCombinations;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public JsonNode getProbabilities() {
        return probabilities;
    }

    public JsonNode getSymbols() {
        return symbols;
    }

    public JsonNode getWinCombinations() {
        return winCombinations;
    }
}

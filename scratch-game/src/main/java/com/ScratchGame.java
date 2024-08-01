package com;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scratchgame.Config;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ScratchGame {
    private final Config config;
    private final int bettingAmount;
    private final Random random;

    // Constructor updated to take a file path as a string
    public ScratchGame(String configFilePath, int bettingAmount) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode configNode = mapper.readTree(new File(configFilePath)); // Read the config file
        this.config = new Config(
                configNode.get("rows").asInt(),
                configNode.get("columns").asInt(),
                configNode.get("probabilities"),
                configNode.get("symbols"),
                configNode.get("win_combinations")
        );
        this.bettingAmount = bettingAmount;
        this.random = new Random();
    }

    public Config getConfig() {
        return config;
    }

    public void play() {
        // Generate matrix
        String[][] matrix = new String[config.getRows()][config.getColumns()];
        for (int i = 0; i < config.getRows(); i++) {
            for (int j = 0; j < config.getColumns(); j++) {
                matrix[i][j] = getSymbol();
            }
        }

        // Check for winning combinations
        Map<String, List<String>> winningCombinations = new HashMap<>();
        for (int i = 0; i < config.getRows(); i++) {
            for (int j = 0; j < config.getColumns(); j++) {
                String symbol = matrix[i][j];
                if (isBonusSymbol(symbol)) {
                    continue;
                }
                for (JsonNode combination : config.getWinCombinations()) {
                    if (matchesCombination(matrix, combination, i, j)) {
                        winningCombinations.computeIfAbsent(symbol, k -> new ArrayList<>()).add(combination.get("name").asText());
                    }
                }
            }
        }

        // Apply bonus symbols
        Map<String, String> appliedBonusSymbols = new HashMap<>();
        for (int i = 0; i < config.getRows(); i++) {
            for (int j = 0; j < config.getColumns(); j++) {
                String symbol = matrix[i][j];
                if (isBonusSymbol(symbol)) {
                    appliedBonusSymbols.put(symbol, symbol);
                }
            }
        }

        // Calculate reward
        int reward = 0;
        for (Map.Entry<String, List<String>> entry : winningCombinations.entrySet()) {
            String symbol = entry.getKey();
            List<String> combinations = entry.getValue();
            int multiplier = getMultiplier(symbol);
            for (String combination : combinations) {
                multiplier *= getMultiplier(combination);
            }
            reward += bettingAmount * multiplier;
        }
        for (Map.Entry<String, String> entry : appliedBonusSymbols.entrySet()) {
            String bonusSymbol = entry.getKey();
            int extra = getExtra(bonusSymbol);
            reward += extra;
        }

        // Print output
        System.out.println("Matrix:");
        for (String[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("Reward: " + reward);
        System.out.println("Applied winning combinations: " + winningCombinations);
        System.out.println("Applied bonus symbols: " + appliedBonusSymbols);
    }

    private String getSymbol() {
        JsonNode probabilities = config.getProbabilities();
        JsonNode standardSymbols = probabilities.get("standard_symbols");
        JsonNode bonusSymbols = probabilities.get("bonus_symbols");
        double randomValue = random.nextDouble();
        double cumulativeProbability = 0;

        Iterator<String> standardSymbolIterator = standardSymbols.fieldNames();
        while (standardSymbolIterator.hasNext()) {
            String symbol = standardSymbolIterator.next();
            cumulativeProbability += standardSymbols.get(symbol).asDouble();
            if (randomValue < cumulativeProbability) {
                return symbol;
            }
        }

        Iterator<String> bonusSymbolIterator = bonusSymbols.fieldNames();
        while (bonusSymbolIterator.hasNext()) {
            String symbol = bonusSymbolIterator.next();
            cumulativeProbability += bonusSymbols.get(symbol).asDouble();
            if (randomValue < cumulativeProbability) {
                return symbol;
            }
        }

        return null;
    }

    private boolean isBonusSymbol(String symbol) {
        JsonNode symbols = config.getSymbols();
        return symbols.get(symbol).get("type").asText().equals("bonus");
    }

    private boolean matchesCombination(String[][] matrix, JsonNode combination, int row, int col) {
        String symbol = matrix[row][col];
        int count = combination.get("count").asInt();
        JsonNode coveredAreas = combination.get("covered_areas");
        for (JsonNode area : coveredAreas) {
            int r = area.get(0).asInt();
            int c = area.get(1).asInt();
            if (r >= 0 && r < matrix.length && c >= 0 && c < matrix[0].length && matrix[r][c].equals(symbol)) {
                count--;
                if (count == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getMultiplier(String symbol) {
        JsonNode symbols = config.getSymbols();
        return symbols.get(symbol).get("multiplier").asInt();
    }

    private int getExtra(String bonusSymbol) {
        JsonNode symbols = config.getSymbols();
        return symbols.get(bonusSymbol).get("extra").asInt();
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 4 || !args[0].equals("--config") || !args[2].equals("--betting-amount")) {
            System.out.println("Usage: java -jar target/scratch-game-1.0-SNAPSHOT.jar --config <path_to_config.json> --betting-amount <amount>");
            return;
        }

        String configFilePath = args[1];
        int bettingAmount = Integer.parseInt(args[3]);

        ScratchGame game = new ScratchGame(configFilePath, bettingAmount);
        game.play();
    }
}

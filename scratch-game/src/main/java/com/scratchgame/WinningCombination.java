package com.scratchgame;

import java.util.List;

public class WinningCombination {
    private final String name;
    private final int count;
    private final List<String[]> coveredAreas;

    public WinningCombination(String name, int count, List<String[]> coveredAreas) {
        this.name = name;
        this.count = count;
        this.coveredAreas = coveredAreas;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public List<String[]> getCoveredAreas() {
        return coveredAreas;
    }
}

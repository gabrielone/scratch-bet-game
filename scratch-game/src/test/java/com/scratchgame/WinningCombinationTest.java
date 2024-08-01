package com.scratchgame;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WinningCombinationTest {

    @Test
    public void testWinningCombinationCreation() {
        List<String[]> coveredAreas = Arrays.asList(
                new String[]{"0:0", "0:1"},
                new String[]{"1:0", "1:1"}
        );
        WinningCombination winningCombination = new WinningCombination("Line", 2, coveredAreas);

        assertEquals("Line", winningCombination.getName());
        assertEquals(2, winningCombination.getCount());
        assertEquals(coveredAreas, winningCombination.getCoveredAreas());
    }
}


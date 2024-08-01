package com.scratchgame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SymbolTest {

    @Test
    public void testSymbolCreation() {
        Symbol symbol = new Symbol("A", "standard");
        assertEquals("A", symbol.getSymbol());
        assertEquals("standard", symbol.getType());
    }
}


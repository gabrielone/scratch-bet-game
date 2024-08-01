package com.scratchgame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BonusSymbolTest {

    @Test
    public void testBonusSymbolCreation() {
        BonusSymbol bonusSymbol = new BonusSymbol("Bonus", 100);
        assertEquals("Bonus", bonusSymbol.getSymbol());
        assertEquals("bonus", bonusSymbol.getType());
        assertEquals(100, bonusSymbol.getExtra());
    }
}


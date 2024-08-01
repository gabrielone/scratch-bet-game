package com;

import com.scratchgame.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ScratchGameTest {

    private ScratchGame game;

    @BeforeEach
    public void setUp() throws IOException {
        game = new ScratchGame("src/main/resources/config.json", 100);
    }

    @Test
    public void testGameInitialization() {
        Config config = game.getConfig();
        assertNotNull(config);
        assertEquals(3, config.getRows());
        assertEquals(3, config.getColumns());
    }

    @Test
    public void testPlay() {
        game.play();
        // Add assertions based on expected behavior
    }
}

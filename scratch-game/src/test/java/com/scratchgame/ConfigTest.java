package com.scratchgame;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigTest {

    @Test
    public void testConfigCreation() throws IOException {
        String jsonString = "{\"rows\": 3, \"columns\": 3, \"probabilities\": {}, \"symbols\": {}, \"win_combinations\": {}}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode configNode = mapper.readTree(jsonString);

        Config config = new Config(
                configNode.get("rows").asInt(),
                configNode.get("columns").asInt(),
                configNode.get("probabilities"),
                configNode.get("symbols"),
                configNode.get("win_combinations")
        );

        assertEquals(3, config.getRows());
        assertEquals(3, config.getColumns());
        assertEquals("{}", config.getProbabilities().toString());
        assertEquals("{}", config.getSymbols().toString());
        assertEquals("{}", config.getWinCombinations().toString());
    }
}


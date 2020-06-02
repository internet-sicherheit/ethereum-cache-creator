package de.internetsicherheit.brl.bloxberg.cache;

import de.internetsicherheit.brl.bloxberg.cache.gui.BlockDataExtractor;

import java.io.IOException;

public class LogicController {

    public LogicController() {
    }

    public void createJson(String[] input) throws IOException {
        BlockDataExtractor blockDataExtractor = new BlockDataExtractor(input);
        blockDataExtractor.generateJsonFile();
    }
}
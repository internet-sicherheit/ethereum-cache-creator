package de.internetsicherheit.brl.bloxberg.cache;

import java.io.IOException;

public class LogicController {

    public LogicController() {
    }

    public void createJson(String[] input) throws IOException {
        BlockDataExtractor blockDataExtractor = new BlockDataExtractor(input);
        blockDataExtractor.generateJsonFile();
        blockDataExtractor.printOutWssTestData();
    }
}
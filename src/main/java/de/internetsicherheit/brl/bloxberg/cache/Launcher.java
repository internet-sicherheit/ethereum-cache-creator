package de.internetsicherheit.brl.bloxberg.cache;

import de.internetsicherheit.brl.bloxberg.cache.gui.MainGUI;

import java.io.IOException;

public class Launcher {

    public static void main(String[] args) throws IOException {
        ArgParser parser = new ArgParser();
        String[] parsedArgs = parser.parseArgs(args);
        if (parsedArgs[4].equals("gui")) {
            MainGUI.main(args);
        } else {
            LogicController logicController = new LogicController();
            logicController.createJson(parsedArgs);
        }

    }


}

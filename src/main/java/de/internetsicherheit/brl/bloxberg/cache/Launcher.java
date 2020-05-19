package de.internetsicherheit.brl.bloxberg.cache;

import de.internetsicherheit.brl.bloxberg.cache.gui.MainGUI;

import java.io.IOException;

public class Launcher {

    public static void main(String[] args) throws IOException {
        String[] parsedArgs = parseArgs(args);
        if (parsedArgs[4].equals("gui")) {
            MainGUI.main(args);
        } else {
            LogicController logicController = new LogicController();
            logicController.createJson(parsedArgs);
        }

    }


    private static String[] parseArgs(String[] args) {
        String[] parsedArgs = new String[5];
        // defaults
        parsedArgs[0] = "https://core.bloxberg.org";
        parsedArgs[1] = "transactions_from_to";
        parsedArgs[2] = "0";
        parsedArgs[3] = "1000";
        parsedArgs[4] = "cli";
        for (int i = 0; i < args.length; i++) {
            String[] parts;
            if(args[i].contains("=")) {
                parts = args[i].split("=");
                switch (parts[0]) {
                    case "--ui":
                        parsedArgs[4] = parts[parts.length -1];
                        break;
                    case "--url":
                        parsedArgs[0] = parts[parts.length -1];
                        break;
                    case "--filename":
                        parsedArgs[1] = parts[parts.length -1];
                        break;
                    case "--start":
                        parsedArgs[2] = parts[parts.length -1];
                        break;
                    case "--stop":
                        parsedArgs[3] = parts[parts.length -1];
                        break;
                }
            }

        }
        return parsedArgs;


    }
}

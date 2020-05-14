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
            if (args[i].contains("--ui=")) {
                parts = args[i].split("=");
                parsedArgs[4] = parts[parts.length - 1];
            }
            if (args[i].contains("--url=")) {
                parts = args[i].split("=");
                parsedArgs[0] = parts[parts.length - 1];
            }
            if (args[i].contains("--filename=")) {
                parts = args[i].split("=");
                parsedArgs[1] = parts[parts.length - 1];
            }
            if (args[i].contains("--start=")) {
                parts = args[i].split("=");
                parsedArgs[2] = parts[parts.length - 1];
            }
            if (args[i].contains("--stop=")) {
                parts = args[i].split("=");
                parsedArgs[3] = parts[parts.length - 1];
            }


        }
        return parsedArgs;


    }
}

package de.internetsicherheit.brl.bloxberg.cache.gui;

import de.internetsicherheit.brl.bloxberg.cache.LogicController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * this class is supposed to become the new main class once the other classes are changed accordingly.
 * NOT USED ATM
 * delete and rename historicDataViszualizer accordingly
 */
public class MainGUI extends Application {
    Stage window;
    Scene sceneOne, sceneTwo;


    public static void main(String[] args) throws IOException {
        String[] parsedArgs = parseArgs(args);
        if (parsedArgs[4].equals("gui")) {
            launch(args);
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
                parsedArgs[4] = parts[parts.length -1];
            }
            if (args[i].contains("--url=")) {
                parts = args[i].split("=");
                parsedArgs[0] = parts[parts.length -1];
            }
            if (args[i].contains("--filename=")) {
                parts = args[i].split("=");
                parsedArgs[1] = parts[parts.length -1];
            }
            if (args[i].contains("--start=")) {
                parts = args[i].split("=");
                parsedArgs[2] = parts[parts.length -1];
            }
            if (args[i].contains("--stop=")) {
                parts = args[i].split("=");
                parsedArgs[3] = parts[parts.length -1];
            }


        }
        return parsedArgs;

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        //HistoricDataVisualizer visualizer = new HistoricDataVisualizer();
        primaryStage.setTitle("Bloxberg Cache Creator");

        VBox root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml_main.fxml"));

        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}

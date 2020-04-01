package de.internetsicherheit.brl.bloxberg.cache.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * this class is supposed to become the new main class once the other classes are changed accordingly.
 * NOT USED ATM
 * delete and rename historicDataViszualizer accordingly
 */
public class MainGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //HistoricDataVisualizer visualizer = new HistoricDataVisualizer();
        primaryStage.setTitle("Bloxberg Cache Creator");

        VBox root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml_main.fxml"));

        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}

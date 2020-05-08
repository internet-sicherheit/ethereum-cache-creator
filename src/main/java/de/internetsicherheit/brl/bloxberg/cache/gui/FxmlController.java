package de.internetsicherheit.brl.bloxberg.cache.gui;

/**
 * this class is supposed to contain the logic that controlls the fxml layout. contains stuff like key-listeners and so on.
 */

import de.internetsicherheit.brl.bloxberg.cache.LogicController;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FxmlController {

    @FXML
    //
    private Label label;

    @FXML
    // The reference of inputText will be injected by the FXML loader
    private TextField fileNameInput;
    @FXML
    private TextField startInput;
    @FXML
    private TextField endInput;
    @FXML
    private ComboBox urlbox;

    private LogicController logicController;

    // The reference of outputText will be injected by the FXML loader
    @FXML
    private TextArea outputText;

    // location and resources will be automatically injected by the FXML loader
    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    // Add a public no-args constructor
    public FxmlController() {
        this.logicController = new LogicController();
    }

    @FXML
    private void initialize() {
    }

    @FXML
    private void transferInput() throws IOException {
        String urlboxString = urlbox.getSelectionModel().getSelectedItem().toString();
        int startInt = Integer.parseInt(startInput.getText());
        int endInt = Integer.parseInt(endInput.getText());
        String fileName = fileNameInput.getText() + ".json";
        System.out.println("filename: " + fileName + "\n"
                            + "starting blocknumber: "  + startInt + "\n"
                            + "ending blocknumber: " + endInt + "\n"
                            + "url: " + urlboxString);
        String[] input = new String[4];
        input[0] = urlboxString;
        input[1] = fileName;
        input[2] = startInput.getText();
        input[3] = endInput.getText();

        logicController.createJson(input);
    }
}
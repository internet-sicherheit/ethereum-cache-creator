package de.internetsicherheit.brl.bloxberg.cache.gui;

/**
 * this class is supposed to contain the logic that controlls the fxml layout. contains stuff like key-listeners and so on.
 */

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FxmlController {
    @FXML
    // The reference of inputText will be injected by the FXML loader
    private TextField inputText;

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
    }

    @FXML
    private void initialize() {
    }

    @FXML
    private void printOutput() {
        outputText.setText(inputText.getText());
    }
}
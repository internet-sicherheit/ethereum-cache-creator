package de.internetsicherheit.brl.bloxberg.cache.gui;

/**
 * this class is supposed to contain the logic that controlls the fxml layout. contains stuff like key-listeners and so on.
 */

import de.internetsicherheit.brl.bloxberg.cache.ethereum.BloxbergClient;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;
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
    private void createJson() throws IOException {
        String urlboxString = urlbox.getSelectionModel().getSelectedItem().toString();
        int startInt = Integer.parseInt(startInput.getText());
        int endInt = Integer.parseInt(endInput.getText());
        String fileName = fileNameInput.getText() + ".json";
        System.out.println("filename: " + fileName + "\n"
                            + "starting blocknumber: "  + startInt + "\n"
                            + "ending blocknumber: " + endInt + "\n"
                            + "url: " + urlboxString);
        BloxbergClient client = new BloxbergClient(urlboxString);
        String OUTPUTDIRECTORY = System.getProperty("user.dir") + "/output/";
        TestCenter testCenter = new TestCenter(client, new File(OUTPUTDIRECTORY + fileName));
        testCenter.generateJsonFile(startInt, endInt);

    }
}
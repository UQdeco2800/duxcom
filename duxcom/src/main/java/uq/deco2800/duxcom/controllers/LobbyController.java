package uq.deco2800.duxcom.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import uq.deco2800.duxcom.interfaces.InterfaceManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for the lobby screen.
 *
 * Created by liamdm on 27/09/2016.
 */
public class LobbyController  implements Initializable {

    @FXML
    private ListView<String> mpLobbyList;
    @FXML
    private Label mpServerNameLabel;
    @FXML
    private Label mpMapNameLabel;
    @FXML
    private TitledPane acPanel;
    @FXML
    private Button acStartGame;
    @FXML
    private Button acDitchGame;
    @FXML
    private Button acOpenServer;
    @FXML
    private Label acServerStateLabel;
    @FXML
    private TitledPane ucPanel;
    @FXML
    private Button ucLeaveServer;
    @FXML
    private Label mpLoadStatusLabel;
    /**
     * Enables the user panel
     */
    public void enableUserPanel(){
        this.ucPanel.setDisable(false);
    }

    /**
     * Enables the admin panel
     */
    public void enableAdminPanel(){
        this.acPanel.setDisable(false);
    }

    /**
     * Set the server name
     * @param serverName the new name
     */
    public void setServerName(String serverName){
        this.mpServerNameLabel.setText(serverName);
    }

    /**
     * Show the could not join text
     */
    public void showCouldNotJoin(String message){
        mpLoadStatusLabel.setWrapText(true);
        mpLoadStatusLabel.setText(message);
    }

    protected InterfaceManager interfaceManager;
    /**
     * Sets the interfaceManager class variable such that the class is able to call InterfaceManager
     * methods.
     *
     * @param interfaceManager The interfaceManager which is controlling the Interface that called
     *                         the load screen fxml file.
     */
    public void setInterfaceManager(InterfaceManager interfaceManager) {
        this.interfaceManager = interfaceManager;
    }

    /**
     * This method exists such that DuxComController can be an implementation of
     * Initializable
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // See javadoc
    }


    public ListView<String> getMpLobbyList() {
        return mpLobbyList;
    }

    public Label getMpServerNameLabel() {
        return mpServerNameLabel;
    }

    public Label getMpMapNameLabel() {
        return mpMapNameLabel;
    }

    public TitledPane getAcPanel() {
        return acPanel;
    }

    public Button getAcStartGame() {
        return acStartGame;
    }

    public Button getAcDitchGame() {
        return acDitchGame;
    }

    public Button getAcOpenServer() {
        return acOpenServer;
    }

    public Label getAcServerStateLabel() {
        return acServerStateLabel;
    }

    public TitledPane getUcPanel() {
        return ucPanel;
    }

    public Button getUcLeaveServer() {
        return ucLeaveServer;
    }

    public Label getMpLoadStatusLabel() {
        return mpLoadStatusLabel;
    }

    public void setServerStateText(String serverStateText) {
        this.acServerStateLabel.setText(serverStateText);
    }

    public void setServerNameText(String mpServerNameLabel) {
        this.mpServerNameLabel.setText(mpServerNameLabel);
    }
}

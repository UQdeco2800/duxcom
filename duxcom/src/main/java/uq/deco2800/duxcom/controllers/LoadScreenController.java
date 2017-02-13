package uq.deco2800.duxcom.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import uq.deco2800.duxcom.achievements.AchievementLoader;
import uq.deco2800.duxcom.achievements.AchievementManager;
import uq.deco2800.duxcom.handlers.keyboard.LoadScreenKeyHandler;
import uq.deco2800.duxcom.interfaces.InterfaceManager;
import uq.deco2800.duxcom.interfaces.InterfaceSegmentType;
import uq.deco2800.duxcom.interfaces.LoadScreenInterface;

/**
 * LoadScreenController is the controller used for the game's load screen. It controls the user
 * interaction with the load screen and processes all requests made by the user. The main functions
 * of the login screen is attempting to initiate different game modes.
 *
 * This class is initialized through the fxml file used for the load screen. The fxml file is
 * initially called from LoadScreenInterface.
 *
 * @author Alex McLean
 * @see LoadScreenInterface
 */
public class LoadScreenController implements Initializable {

    private static Logger logger = LoggerFactory.getLogger(LoadScreenController.class);

    @FXML
    public TextField serverNameBox;
    @FXML
    public Button hostServerButton;
    @FXML
    public Button joinServerButton;

    // Declaring the class instances to interact with
    private InterfaceManager interfaceManager;
    private Stage stage;

    // Declaring the fx objects that will be modified
    @FXML
    private Button loadGameButton;
    @FXML
    private Button mapCreatorButton;
    @FXML
    private Button heroSelectButton;
    @FXML
    private Button overworldButton;
    @FXML
    private TitledPane multiplayerBox;
    @FXML
    private Button faladorButton;
    @FXML
    private Button betaButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button playerRewardsButton;
    @FXML
    private Button leaderboardButton;
    @FXML
    private AnchorPane anchorPane;

    /**
     * Called upon Class instantiation, sets the Stage of the Interface.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if
     *                  the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object
     *                  was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventHandler<KeyEvent> keyHandler = e -> {
            if (e.getCode() == KeyCode.BACK_SPACE) {
                this.startBackToLoginScreen();
            }
        };

        AchievementLoader.addAll();

        faladorButton.requestFocus();
        loadGameButton.setOnAction(e -> this.loadGame());
        mapCreatorButton.setOnAction(e -> this.loadMapCreator());
        heroSelectButton.setOnAction(e -> this.loadHeroSelect());
        overworldButton.setOnAction(e -> this.loadOverworld());
        playerRewardsButton.setOnAction(e -> this.loadPlayerRewards());
        faladorButton.setOnAction(e -> this.loadFalador());
        betaButton.setOnAction(e -> this.loadBeta());
        logoutButton.setOnAction(e ->this.startBackToLoginScreen());
        hostServerButton.setOnAction(e ->this.switchServer(true));
        joinServerButton.setOnAction(e ->this.switchServer(false));
        leaderboardButton.setOnAction(e ->this.loadLeaderboard());
        
        /* Add back button to all keys. */
        mapCreatorButton.setOnKeyPressed(keyHandler);
        heroSelectButton.setOnKeyPressed(keyHandler);
        overworldButton.setOnKeyPressed(keyHandler);
        playerRewardsButton.setOnKeyPressed(keyHandler);
        faladorButton.setOnKeyPressed(keyHandler);
        logoutButton.setOnKeyPressed(keyHandler);
        hostServerButton.setOnKeyPressed(keyHandler);
        joinServerButton.setOnKeyPressed(keyHandler);
        anchorPane.setOnKeyPressed(keyHandler);
    }

    /**
     * Switches to server mode
     * @param host if should host
     */
    private void switchServer(boolean host){
        String target = (host ? "H" : "J") + String.valueOf(serverNameBox.getText());
        logger.info("--------> " + target);
        Platform.runLater(() -> interfaceManager.loadSegmentImmediate(InterfaceSegmentType.LOBBY, stage, target));
    }

    /**
     * Creates a new thread to perform the back to login screen process
     */
    private void startBackToLoginScreen() {
        Runnable back = this::runBackToLoginScreen;

        Thread backThread = new Thread(back);
        backThread.setDaemon(true);
        backThread.start();
    }

    /**
     * Changes to the login interface.
     * Should be run only through startBackToLoginScreen in order to be run in a background thread.
     */
    private void runBackToLoginScreen() {
        Platform.runLater(() -> interfaceManager.loadSegmentImmediate(InterfaceSegmentType.LOGIN_SCREEN, stage, "login"));
    }

    /**
     * Start the player rewards shop.
     *
     */
    @FXML
    private void loadPlayerRewards() {
        interfaceManager.loadSegmentImmediate(InterfaceSegmentType.PLAYER_REWARDS, stage, "rewards");
    }

    /**
     * Starts the map creator
     */
    @FXML
    private void loadMapCreator() {
        interfaceManager.loadSegmentImmediate(InterfaceSegmentType.MAP_CREATOR, stage, "creator");
    }

    /**
     * Starts the hero select screen
     */
    @FXML
    private void loadHeroSelect() {
        interfaceManager.loadSegmentImmediate(InterfaceSegmentType.HERO_SELECT, stage, "heroes");
    }

    /**
     * Goes to the overworld interface
     */
    @FXML
    private void loadOverworld() {
        interfaceManager.loadSegmentImmediate(InterfaceSegmentType.OVERWORLD, stage, "overworld");
    }

    /**
     * Goes to the falador game
     */
    @FXML
    private void loadFalador() {
        interfaceManager.loadSegmentImmediate(InterfaceSegmentType.GAME, stage, "falador");
    }

    /**
     * Opens test environment
     */
    @FXML
    private void loadBeta() {
        interfaceManager.loadSegmentImmediate(InterfaceSegmentType.GAME, stage, "beta");
    }
    
    /**
     * Goes to leader board
     */
    @FXML
    private void loadLeaderboard() {
        interfaceManager.loadSegmentImmediate(InterfaceSegmentType.LEADER_BOARD, stage, "leaderboard");
    }

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
     * Sets the Stage variable such that the class is able to render to the Stage.
     *
     * @param stage The Stage which the interfaces are rendered on.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets up a key press event for the whole interface
     */
    public void setKeyEvent() {
        anchorPane.setOnKeyPressed(new LoadScreenKeyHandler(interfaceManager, anchorPane));
    }
    

    /**
     * Starts the map creator
     */
    @FXML
    private void loadGame() {
    	interfaceManager.loadSegmentImmediate(InterfaceSegmentType.GAME, stage, "saved");
    }
}

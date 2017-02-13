package uq.deco2800.duxcom.controllers.esccontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler.GlossaryGraphicsHandler;
import uq.deco2800.duxcom.interfaces.overlaymaker.popup.OverlayMakerPopUp;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * EscMenu Controller
 *
 * @author The_Magic_Karps
 */
public class EscMenuController extends ESCOverlay implements Initializable {

    private boolean initialised = false;

    private static final String RETURN_TEXT = "Return to Game";
    private static final String LOGOUT_TEXT = "Log Out";
    private static final String QUIT_TEXT = "Quit Game";
    private static final String RETURN_LOAD_TEXT = "Back to Load Screen";

    private static Logger logger = LoggerFactory.getLogger(EscMenuController.class);
    @FXML
    private AnchorPane basePane;

    /**
     * A button to mute and unmute the the sound
     */
    @FXML
    private Button soundToggleButton;

    /**
     * A button to hide and show the minimap
     */
    @FXML
    private Button mapToggleButton;

    @FXML
    private VBox buttonBox;

    @FXML
    private HBox iconBox;

    /**
     * Determine which type of escape menu to render
     *
     * @param type the type of segment for the esc menu to render
     */
    public void setSegment(EscSegmentType type) {
        if (!initialised) {
            switch (type) {
                case MAIN_GAME:
                    setBaseDimension(265, 291);
                    addMainGameButtons();

                    break;
                case OVERWORLD:
                    setBaseDimension(265, 201);
                    addOverworldButtons();
                    hideIcons();
                    break;
                case LOADSCREEN:
                    setBaseDimension(265, 150);
                    addLoadScreenButtons();
                    hideIcons();
                    break;
                default:
                    destroyOverlay();
                    return;
            }
            initialised = true;
        }
    }

    /**
     * Method handler to add the buttons
     *
     * For Main Game
     */
    private void addMainGameButtons() {
        add(RETURN_TEXT, ButtonUse.CLOSE_OVERLAY);
        add("Save Game", ButtonUse.SAVE_GAME);
        add("Load Game", ButtonUse.LOAD_GAME);
        add("Key Shortcut Help", ButtonUse.SHORTCUT_HELP);
        add(RETURN_LOAD_TEXT, ButtonUse.RETURN_LOAD);
        add(LOGOUT_TEXT, ButtonUse.RETURN_LOG);
        add(QUIT_TEXT, ButtonUse.CLOSE_GAME);
    }

    /**
     * Method handler to add the buttons
     *
     * For Overworld
     */
    private void addOverworldButtons() {
        add(RETURN_TEXT, ButtonUse.CLOSE_OVERLAY);
        add(RETURN_LOAD_TEXT, ButtonUse.RETURN_LOAD);
        add(LOGOUT_TEXT, ButtonUse.RETURN_LOG);
        add(QUIT_TEXT, ButtonUse.CLOSE_GAME);
    }

    /**
     * Method handler to add the buttons
     *
     * For Load Screen
     */
    private void addLoadScreenButtons() {
        add(RETURN_TEXT, ButtonUse.CLOSE_OVERLAY);
        add(LOGOUT_TEXT, ButtonUse.RETURN_LOG);
        add(QUIT_TEXT, ButtonUse.CLOSE_GAME);
    }

    /**
     * Set up eventhandlers for the button and add into the pane
     */
    private void add(String name, ButtonUse usage) {
        Button button = addButtons(name);
        button.setOnAction(usage.getEventHandler(this));
        buttonBox.getChildren().add(button);
    }

    /**
     * Creates the buttons
     *
     * @param name name of the button to be added
     * @return Button object of the button to be added
     */
    private Button addButtons(String name) {
        Button button = new Button(name);
        button.getStyleClass().add("menuButtons");
        button.prefWidthProperty().bind(buttonBox.widthProperty());
        return button;
    }

    /**
     * Set the base dimension of the EscMenu background for the buttons
     *
     * @param x width of the base
     * @param y height of the base
     */
    private void setBaseDimension(double x, double y) {
        basePane.setPrefWidth(x);
        basePane.setPrefHeight(y);
    }

    /**
     * Opens the glossary GUI
     */
    @FXML
    private void openGlossary() {
        try {
            OverlayMakerPopUp
                    .makeWithGameManager(gameManager.getController().getGamePane(),
                            "/ui/fxml/Glossary.fxml", gameManager)
                    .showOverlay();
            hide();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (!GlossaryGraphicsHandler.getInitialised()) {
            GlossaryGraphicsHandler.setInitialised(true);
        } else {
            GlossaryGraphicsHandler.setInitialised(false);
        }

    }

    /**
     * Toggles sound
     */
    public void toggleMute() {
        if (gameManager.toggleMute()) {
            soundToggleButton.setText("");
        } else {
            soundToggleButton.setText("");
        }
    }

    /**
     * Toggle Minimap visibility and change button label
     */
    public void toggleMap() {
        if (gameManager.toggleMiniMap()) {
            mapToggleButton.setText("");
        } else {
            mapToggleButton.setText("");
        }
    }

    public void hideIcons(){
        basePane.getChildren().removeAll(iconBox);
        AnchorPane.setTopAnchor(buttonBox,30.0);
    }



    /**
     * Refreshes
     */
    @Override
    public void refresh() {
        deleteIcon();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // for initializable implementation
    }
}

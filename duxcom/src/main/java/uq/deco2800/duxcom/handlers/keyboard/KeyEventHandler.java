/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uq.deco2800.duxcom.handlers.keyboard;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.handlers.GameEventHandler;
import uq.deco2800.duxcom.interfaces.InterfaceManager;
import uq.deco2800.duxcom.interfaces.overlaymaker.OverlayMaker;
import uq.deco2800.duxcom.interfaces.overlaymaker.popup.OverlayMakerPopUp;
import uq.deco2800.duxcom.interfaces.overlaymaker.popup.OverlayMakerSuperPopUp;

import java.io.IOException;
import javafx.scene.input.KeyCombination;

/**
 *
 * @author TROLL
 */
public abstract class KeyEventHandler extends GameEventHandler<KeyEvent> {

    // logger
    private static Logger logger = LoggerFactory.getLogger(KeyEventHandler.class);

    /**
     * Overlay maker handler (super popup)
     */
    protected OverlayMakerSuperPopUp superPopUp;

    /**
     * Overlay maker handler
     */
    protected OverlayMakerPopUp popUp;
    /**
     * The base pane being used for display
     */
    protected Pane basePane;

    /**
     * Controller of the overlay created
     */
    protected OverlayMaker overlayController;

    /**
     * Constructor for KeyEventHandler
     *
     * @param interfaceManager InterfaceManager of the stage
     * @param gameManager GameManager of the current game
     * @param controller controller of the game
     */
    public KeyEventHandler(InterfaceManager interfaceManager, GameManager gameManager, Object controller) {
        super(interfaceManager, gameManager, controller);
    }

    /**
     * Constructor for KeyEventHandler
     *
     * @param interfaceManager InterfaceManager of the stage
     * @param parent Parent pane for the overlay to reside in
     */
    public KeyEventHandler(InterfaceManager interfaceManager, Pane parent) {
        super(interfaceManager, parent);
    }

    /**
     * Abstract method to handle events
     *
     * @param event KeyEvent to be handled
     */
    @Override
    public abstract void handleEvent(KeyEvent event);

    /**
     * Helper method to make overlays
     *
     * @param fxml location of the overlay file
     * @param superPop whether a superpopupp will be made
     * @return true if success, false if failure
     */
    protected boolean makeOverlay(String fxml, boolean superPop) {
        Pane parent;
        if (gameManager != null) {
            parent = gameManager.getController().getGamePane();
        } else {
            parent = parentPane;
        }
        try {
            if (superPop) {
                superPopUp = OverlayMakerSuperPopUp.makeSuperPopUpGM(parent, fxml, gameManager);
                overlayController = superPopUp.getController();
            } else {
                popUp = OverlayMakerPopUp.makeWithGameManager(parent, fxml, gameManager);
                overlayController = popUp.getController();
            }
            overlayController.show();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * Toggles Full Screen
     */
    protected void toggleFullScreen() {
        interfaceManager.getStage().setFullScreenExitKeyCombination(
                KeyCombination.NO_MATCH);
        if (!interfaceManager.getStage().isFullScreen()) {
            interfaceManager.getStage().setFullScreen(true);
        } else {
            interfaceManager.getStage().setFullScreen(false);
        }
    }
}

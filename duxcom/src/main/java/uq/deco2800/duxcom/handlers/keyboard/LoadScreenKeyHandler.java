package uq.deco2800.duxcom.handlers.keyboard;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import uq.deco2800.duxcom.controllers.esccontrollers.ESCOverlay;
import uq.deco2800.duxcom.controllers.esccontrollers.EscMenuController;
import uq.deco2800.duxcom.controllers.esccontrollers.EscSegmentType;
import uq.deco2800.duxcom.interfaces.InterfaceManager;

/**
 * Ticket 52 - Keyboard Shortcuts Keyboard Handler for loading screen
 *
 * @author The_Magic_Karps
 */
public class LoadScreenKeyHandler extends KeyEventHandler {

    /**
     * Constructor of LoadScreenKeyHandler
     *
     * @param interfaceManager InterfaceManager for the stage
     * @param parent parent pane where overlays will reside in
     */
    public LoadScreenKeyHandler(InterfaceManager interfaceManager, Pane parent) {
        super(interfaceManager, parent);
    }

    /**
     * Handles the key event
     *
     * @param event KeyEvent to be handled
     */
    @Override
    public void handleEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            makeOverlay("/ui/fxml/EscMenu.fxml", true);
            ((EscMenuController) superPopUp.getController()).setSegment(EscSegmentType.LOADSCREEN);
            ((ESCOverlay) overlayController).addInterface(interfaceManager);
        }
    }
}

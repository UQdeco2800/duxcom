package uq.deco2800.duxcom.handlers.keyboard;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import uq.deco2800.duxcom.controllers.esccontrollers.ESCOverlay;
import uq.deco2800.duxcom.controllers.esccontrollers.EscMenuController;
import uq.deco2800.duxcom.controllers.esccontrollers.EscSegmentType;
import uq.deco2800.duxcom.interfaces.InterfaceManager;

/**
 * Keyboard Event Handler for overworld screen
 *
 * @author The_Magic_Karps
 */
public class OverworldScreenKeyHandler extends KeyEventHandler {

    /**
     * Constructor for overworld keyevents
     * @param interfaceManager InterfaceManager of the stage
     * @param parent parent pane where overlays will reside in
     */
    public OverworldScreenKeyHandler(InterfaceManager interfaceManager, Pane parent) {
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
            ((EscMenuController) superPopUp.getController()).setSegment(EscSegmentType.OVERWORLD);
            ((ESCOverlay) overlayController).addInterface(interfaceManager);
        }
    }
}

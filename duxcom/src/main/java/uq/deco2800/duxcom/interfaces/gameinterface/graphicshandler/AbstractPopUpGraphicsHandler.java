package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;
import uq.deco2800.duxcom.interfaces.overlaymaker.OverlayMaker;
import uq.deco2800.duxcom.interfaces.overlaymaker.popup.OverlayMakerPopUp;

import java.io.IOException;

/**
 * Used to implement static pop up overlays in the game render window
 *
 * @author Alex McLean
 */
public class AbstractPopUpGraphicsHandler extends GraphicsHandler {

    // Whether the overlay has started
    protected static boolean initialised = false;

    protected boolean visible = false;

    // Controller of the overlay class instance
    protected OverlayMaker controller;

    protected OverlayMakerPopUp popUp;

    protected String fxmlFileName;

    @Override
    public void updateGraphics(GraphicsContext graphicsContext) {
        // Child should override
    }

    @Override
    public boolean needsUpdating() {
        return false;
    }

    /**
     * Process the updating of the handler graphics
     *
     * @return true if successfully initialised
     *
     * @throws IOException if fxml cannot be found
     */
    public boolean processGraphicsUpdate() throws IOException {
        if (!visible && initialised) {
            AnchorPane gamePane = currentGameManager.getController().getGamePane();
            popUp = OverlayMakerPopUp
                    .makeWithGameManager(gamePane, fxmlFileName, currentGameManager);
            controller = popUp.getController();
            controller.show();
            visible = true;
        } else if (visible && initialised && controller != null) {
            controller.refresh();
        } else if (!initialised && visible && controller != null) {
            controller.destroyOverlay();
            visible = false;
            controller = null;
        } else {
            return false;
        }
        return true;
    }
}

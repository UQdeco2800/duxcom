package uq.deco2800.duxcom.handlers;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import uq.deco2800.duxcom.GameManager;

/**
 * Created by woody on 01-Aug-16.
 */
public class MouseDraggedHandler extends GameEventHandler<MouseEvent> {

    /**
     * Creates a mouse dragged handler for the given game manager
     *
     * @param gameManager the corresponding game manager
     */
    public MouseDraggedHandler(GameManager gameManager) {
        super(gameManager);
    }

    public void handleEvent(MouseEvent event) {
        if (event.getButton().equals(MouseButton.SECONDARY)) {
            double xOffset = event.getX();
            double yOffset = event.getY();
            gameManager.setDragged(xOffset, yOffset);
        }
    }
}

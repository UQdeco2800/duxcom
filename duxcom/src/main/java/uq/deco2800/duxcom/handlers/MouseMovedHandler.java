package uq.deco2800.duxcom.handlers;

import javafx.scene.input.MouseEvent;
import uq.deco2800.duxcom.GameManager;

/**
 * Used to receive mouse hover events.
 *
 * Created by liamdm on 30-Jul-16.
 */
public class MouseMovedHandler extends GameEventHandler<MouseEvent>  {

    /**
     * Creates a mouse moved handler for the given game manager
     *
     * @param gameManager the corresponding game manager
     */
    public MouseMovedHandler(GameManager gameManager) {
        super(gameManager);
    }

    /**
     * Handles a mouse event in game
     */
    public void handleEvent(MouseEvent event) {
        setMouseLocationToEvent(event.getX(), event.getY());

    }
}

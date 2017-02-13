package uq.deco2800.duxcom.handlers;

import javafx.scene.input.MouseEvent;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.graphics.AnimationManager;

/**
 * Created by woody on 30-Jul-16.
 */
public class MouseReleasedHandler extends GameEventHandler<MouseEvent>  {

    /**
     * Creates a mouse released handler for the given game manager
     *
     * @param gameManager the corresponding game manager
     */
    public MouseReleasedHandler(GameManager gameManager) {
        super(gameManager);
    }

    public void handleEvent(MouseEvent event) {
        AnimationManager.unpause();
        gameManager.setReleased();
    }
}

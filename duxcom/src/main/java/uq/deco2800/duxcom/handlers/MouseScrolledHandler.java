package uq.deco2800.duxcom.handlers;

import javafx.scene.input.ScrollEvent;
import uq.deco2800.duxcom.GameManager;

/**
 * Created by woody on 01-Aug-16.
 */
public class MouseScrolledHandler extends GameEventHandler<ScrollEvent> {

	/**
	 * Creates a mouse scrolled handler for the given game manager
	 *
	 * @param gameManager the corresponding game manager
	 */
	public MouseScrolledHandler(GameManager gameManager) {
		super(gameManager);
	}

	public void handleEvent(ScrollEvent event) {
		if (event.getDeltaY() > 0) {
			gameManager.zoomIn();
		} else {
			gameManager.zoomOut();
		}

		setMouseLocationToEvent(event.getX(), event.getY());
	}

}

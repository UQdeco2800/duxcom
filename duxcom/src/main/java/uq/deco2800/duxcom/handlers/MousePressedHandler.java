package uq.deco2800.duxcom.handlers;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.dataregisters.TileDataRegister;
import uq.deco2800.duxcom.graphics.AnimationManager;

/**
 * Created by woody on 30-Jul-16.
 */
public class MousePressedHandler extends GameEventHandler<MouseEvent> {

	/**
	 * Creates a mouse pressed handler for the given game manager
	 *
	 * @param gameManager the corresponding game manager
	 */
	public MousePressedHandler(GameManager gameManager) {
		super(gameManager);
	}

    public void handleEvent(MouseEvent event) {
        if (event.getButton().equals(MouseButton.SECONDARY)) {
			AnimationManager.pause();
        	//get position of mouse on screen to calculate offset for panning
            double x = event.getX();
            double y = event.getY();
			gameManager.setRightPressed(x, y);
        } else if (event.getButton().equals(MouseButton.PRIMARY)) {
        	//calculate the tile position that was clicked
			double offsetX = gameManager.getxOffset();
			double offsetY = gameManager.getyOffset();
			double scale = gameManager.getScale();
			double scaledWidth = TileDataRegister.TILE_WIDTH * scale;
			double scaledHeight = TileDataRegister.TILE_HEIGHT * scale;
			double basex =  (gameManager.getMap().getWidth() * scaledWidth) / 2.0
					+ scaledWidth/4.0;
			double basey = scaledHeight/4.0;

			//reverse the math logic from the GameRenderer
			int i = (int) (((event.getY() - offsetY + basey) / scaledHeight)
					- ((event.getX() - offsetX) - basex) /  scaledWidth);

			int j = (int) ((2 * ((event.getX() - offsetX) - basex) / scaledWidth)  + i);

			gameManager.setLeftPressed(i, j);
        }

    }
}

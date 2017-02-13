package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.canvas.GraphicsContext;

/**
 * The graphics handler for displaying a map in the game interface.
 * <p>
 * @author Alex McLean
 */
public class GameMapGraphicsHandler extends MapGraphicsHandler {

    @Override
    public void updateGraphics(GraphicsContext graphicsContext) {
        graphicsImages.clear();
        this.renderTiles(graphicsImages, currentGameManager, false);
    }

    @Override
    public boolean needsUpdating() {
        return currentGameManager.isGameChanged() || currentGameManager.isSelectionChanged();
    }
}

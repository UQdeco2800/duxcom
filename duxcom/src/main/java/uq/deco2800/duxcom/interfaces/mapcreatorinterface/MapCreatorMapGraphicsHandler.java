package uq.deco2800.duxcom.interfaces.mapcreatorinterface;

import javafx.scene.canvas.GraphicsContext;
import uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler.MapGraphicsHandler;

/**
 * Handles the actual display of the map. It is rendered on top of the weather background
 * and displays the tiles in an isometric view.
 *
 * Game manager (Will be changed to View Manager) alters the way the tiles are viewed.
 * Scale/X/Y values alter the view the developer has of the map
 *
 * @author Lucas Reher
 */

public class MapCreatorMapGraphicsHandler extends MapGraphicsHandler {

    @Override
    public void updateGraphics(GraphicsContext graphicsContext) {
        graphicsImages.clear();
        this.renderTiles(graphicsImages, currentGameManager, true);
    }

    @Override
    public boolean needsUpdating() {
        return true;
    }
}

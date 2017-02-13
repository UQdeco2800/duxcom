package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.canvas.GraphicsContext;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;
import uq.deco2800.duxcom.interfaces.gameinterface.minimap.MiniMapController;

/**
 * The graphics handler for displaying the minimap in fullscreen
 * <p>
 * Created by liamdm on 16/08/2016.
 */
public class MiniMapGraphicsHandler extends GraphicsHandler {

    @Override
    public void updateGraphics(GraphicsContext graphicsContext) {
        graphicsPolygons.clear();
        graphicsOvals.clear();
        MiniMapController.refreshMiniMap(graphicsContext, currentGameManager, graphicsPolygons, graphicsOvals);
        currentGameManager.setMiniMapChanged(false);
    }

    @Override
    public void render(GraphicsContext graphicsContext) {
        if (GameLoop.getCurrentGameManager().isMiniMapVisible()) {
            super.render(graphicsContext);
        }
    }

    @Override
    public boolean needsUpdating() {
        return currentGameManager.isMiniMapChanged() && currentGameManager.isMiniMapVisible();
    }
}

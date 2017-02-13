/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.canvas.GraphicsContext;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;

/**
 *
 * @author TROLL
 */
public abstract class TileRangeGraphicsHandler extends GraphicsHandler {

    protected int mapWidth;
    protected int mapHeight;

    public void setDimensions() {
        double scale = currentGameManager.getScale();
        scaledWidth = TILE_WIDTH * scale;
        scaledHeight = TILE_HEIGHT * scale;
        baseX = currentGameManager.getMap().getWidth() * TILE_WIDTH * scale / 2;
        baseY = 0;
        mapWidth = currentGameManager.getMap().getWidth();
        mapHeight = currentGameManager.getMap().getHeight();
    }

    @Override
    public abstract void updateGraphics(GraphicsContext graphicsContext);

    @Override
    public abstract boolean needsUpdating();
}

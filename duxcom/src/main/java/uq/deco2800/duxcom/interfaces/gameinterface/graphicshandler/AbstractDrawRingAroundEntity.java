package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.canvas.GraphicsContext;
import uq.deco2800.duxcom.graphics.TextureRegister;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;

/**
 * Created by charliegrc, ducksters
 */
public class AbstractDrawRingAroundEntity extends TileRangeGraphicsHandler {

    /**
     * Process tiles to determine which ones need the range drawn on them.
     *
     * @param xLoot the x position of the loot
     * @param yLoot the y position of the loot
     */
    protected void processLootRange(int xLoot, int yLoot) {
        for (int i = xLoot - 1; i <= (xLoot + 1); i++) {
            for (int j = yLoot - 1; j <= (yLoot + 1); j++) {
                if (!currentGameManager.getMap().canSelectPoint(i, j)) {
                    continue;
                }
                drawLootRangeGraphics(i, j, xLoot, yLoot);
            }
        }
    }

    /**
     * overriden by child class
     * @return false
     */
    public boolean needsUpdating() {
        return false;
    }

    /**
     * overriden
     * @param graphicsContext ...
     */
    public void updateGraphics(GraphicsContext graphicsContext) {

    }

    /**
     * Draw the loot range graphics on the graphics context.
     *
     * @param i     the x coordinate of the tile to check for drawing on
     * @param j     the y coordinate of the tile to check for drawing on
     * @param xLoot the x position of the loot
     * @param yLoot the y position of the loot
     */
    protected void drawLootRangeGraphics(int i, int j, int xLoot, int yLoot) {

        double x = baseX + (j - i) * scaledWidth / 2;
        double y = baseY + (j + i) * scaledHeight / 2;

        // Top right border
        if (i == xLoot - 1 || i == 0) {
            graphicsImages.add(new GraphicsHandler.ImageToAdd(TextureRegister.getTexture("green_range_1"),
                    x + currentGameManager.getxOffset(), y + currentGameManager.getyOffset(),
                    scaledWidth, scaledHeight));
        }

        // Bottom left border
        if (i == xLoot + 1 || i == (mapWidth - 1)) {
            graphicsImages.add(new GraphicsHandler.ImageToAdd(TextureRegister.getTexture("green_range_3"),
                    x + currentGameManager.getxOffset(), y + currentGameManager.getyOffset(),
                    scaledWidth, scaledHeight));
        }

        // Top left border
        if (j == yLoot - 1 || j == 0) {
            graphicsImages.add(new GraphicsHandler.ImageToAdd(TextureRegister.getTexture("green_range_4"),
                    x + currentGameManager.getxOffset(), y + currentGameManager.getyOffset(),
                    scaledWidth, scaledHeight));
        }

        // Bottom right border
        if (j == yLoot + 1 || j == (mapHeight - 1)) {
            graphicsImages.add(new GraphicsHandler.ImageToAdd(TextureRegister.getTexture("green_range_2"),
                    x + currentGameManager.getxOffset(), y + currentGameManager.getyOffset(),
                    scaledWidth, scaledHeight));
        }
    }

}

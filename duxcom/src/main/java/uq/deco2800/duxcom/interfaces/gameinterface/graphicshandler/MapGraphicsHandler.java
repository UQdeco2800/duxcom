package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.image.Image;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.annotation.MethodContract;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.TileDataRegister;
import uq.deco2800.duxcom.graphics.TextureRegister;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.tiles.Tile;

import java.util.ArrayList;

/**
 * The graphics handler for displaying a map in a generic interface.
 *
 * Created by liamdm on 16/08/2016.
 */
public abstract class MapGraphicsHandler extends GraphicsHandler {

    /**
     * Renders the tiles of a map
     * @param graphicsImages the graphics list
     */
    @MethodContract(precondition = {"gameManager != null", "map != null", "graphicsContext != null",
                                    "Double.isFinite(xOffset)", "Double.isFinite(yOffset)"})
    protected void renderTiles(ArrayList<ImageToAdd> graphicsImages, GameManager gameManager, boolean mapCreator) {

        MapAssembly map = gameManager.getMap();

        // The scale determines how far we are from map (zoom)
        scale = gameManager.getScale();

        scaledWidth = TILE_WIDTH * scale;
        scaledHeight = TILE_HEIGHT * scale;
        double halfScaledWidth = scaledWidth / 2;
        double halfScaledHeight = scaledHeight / 2;
        baseX = map.getWidth() * halfScaledWidth;

        TileDataRegister tileDataRegister = DataRegisterManager.getTileDataRegister();

        // render the tiles
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                if (!map.canSelectPoint(i, j) || (!gameManager.onScreen(i, j) && !mapCreator)) {
                    continue;
                }

                Tile tile = map.getTile(i, j);

                x = baseX + (j - i) * halfScaledWidth;
                y = (j + i) * halfScaledHeight;

                String resource = tileDataRegister.getData(tile.getTileType()).getTileTextureName();

                Image image;
                if (tile.isHidden()) {
                    image = TextureRegister.getFadedTexture(resource);
                } else {
                    image = TextureRegister.getTexture(resource);
                }
                graphicsImages.add(new ImageToAdd(image, x + gameManager.getxOffset(), y + gameManager.getyOffset(), scaledWidth, scaledHeight));
            }
        }
    }

}

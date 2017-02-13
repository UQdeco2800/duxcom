package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uq.deco2800.duxcom.buffs.AbstractBuff;
import uq.deco2800.duxcom.graphics.TextureRegister;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.tiles.LiveTile;

import java.util.List;

/**
 * Created by jay-grant on 14/10/2016.
 */
public class LiveTileGraphicsHandler extends GraphicsHandler {

    private boolean floor;

    /**
     * Creates a love tile graphics handler either for the floor level or the entity level
     *
     * @param floor is floor level
     */
    public LiveTileGraphicsHandler(boolean floor) {
        this.floor = floor;
    }

    @Override
    public void updateGraphics(GraphicsContext graphicsContext) {

        graphicsImages.clear();
        MapAssembly map = currentGameManager.getMap();

        //List of all entities to render
        List<LiveTile> liveTiles;

        //scale that entities should be drawn at to give zoom effect
        scale = currentGameManager.getScale();

        //scaled width of each tile
        scaledWidth = TILE_WIDTH * scale;

        //scaled height of each tile
        scaledHeight = TILE_HEIGHT * scale;

        //x position for the first tile to be drawn
        baseX = map.getWidth() * TILE_WIDTH * scale / 2;
        //y position for first tile to be drawn
        baseY = 0;

        liveTiles = map.getLiveTiles();

        for (LiveTile liveTile :
                liveTiles) {

            drawLiveTile(liveTile, floor, map);

            if (liveTile.hasAppliedEffect()) {
                for (LiveTile buff :
                        liveTile.getEffectLiveTiles()) {
                    drawLiveTile(buff, false, map);
                }
            }
        }
    }

    @Override
    public boolean needsUpdating() {
        return currentGameManager.isGameChanged();
    }
}

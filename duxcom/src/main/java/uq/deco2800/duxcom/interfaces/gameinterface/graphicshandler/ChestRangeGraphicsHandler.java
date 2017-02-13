package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import uq.deco2800.duxcom.entities.Chest;
import uq.deco2800.duxcom.inventory.ChestManager;

/**
 * Created by Ducksters
 *
 * A class that renders a ring around chests so users know how close they have to be to the chest to
 * open it.
 */
public class ChestRangeGraphicsHandler extends AbstractDrawRingAroundEntity {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(
            LootRangeGraphicsHandler.class);

    /**
     * Update the graphics when needsUpdating() is set to true
     *
     * @param graphicsContext the graphics context of the game canvas
     */
    @Override
    public void updateGraphics(GraphicsContext graphicsContext) {
        graphicsImages.clear();
        super.setDimensions();
        if (ChestManager.getChestManager() == null) {
            return;
        }
        ArrayList<Chest> chests = ChestManager.getChestManager().getChests();
        if (chests == null) {
            return;
        }
        for (Chest chest : chests) {
            processLootRange(chest.getX(), chest.getY());
        }
    }

    /**
     * since chests are static, they will never need to be updated
     *
     * @return true if it needs updating, false if it doesn't
     */
    @Override
    public boolean needsUpdating() {
        return currentGameManager.isGameChanged() ||
                currentGameManager.getMap().haveChestsBeenRendered();
    }


}

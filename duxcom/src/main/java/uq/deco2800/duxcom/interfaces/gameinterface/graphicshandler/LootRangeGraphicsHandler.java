package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import java.io.IOException;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.controllers.LootPopUpController;
import uq.deco2800.duxcom.entities.PickableEntities;
import uq.deco2800.duxcom.graphics.TextureRegister;
import uq.deco2800.duxcom.interfaces.overlaymaker.popup.OverlayMakerPopUp;

/**
 * Loot Range Graphics Handler
 *
 * @author The_Magic_Karps
 */
public class LootRangeGraphicsHandler extends AbstractDrawRingAroundEntity {

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
        List<PickableEntities> unlootedEntities
                = currentGameManager.getMap().getUnlootedDeath();
        for (PickableEntities unlootedEntity : unlootedEntities) {
            if (!currentGameManager.getMap().getTile(unlootedEntity.getX(),
                    unlootedEntity.getY()).isHidden()) {
                processLootRange(unlootedEntity.getX(),
                        unlootedEntity.getY());
            }
        }
        if (currentGameManager.getMap().getLootManager().getInvInLootArea(
                currentGameManager.getMap().getCurrentTurnHero()).size() > 0) {
            currentGameManager.getController().getUIController()
                    .setLootButtonVisibility(true);
        } else {
            currentGameManager.getController().getUIController()
                    .setLootButtonVisibility(false);
            try {
                LootPopUpController controller = (LootPopUpController) OverlayMakerPopUp
                        .makeWithGameManager(currentGameManager.getController()
                                .getGamePane(), "/ui/fxml/lootPopUp.fxml",
                                currentGameManager).getController();
                controller.hide();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        currentGameManager.getMap().setLootStatusChanged(false);

    }



    /**
     * Check whether the game canvas need updating
     *
     * @return true if it needs updating, false if it doesn't
     */
    @Override
    public boolean needsUpdating() {
        return currentGameManager.isGameChanged()
                || currentGameManager.getMap().isLootStatusChanged();
    }

}

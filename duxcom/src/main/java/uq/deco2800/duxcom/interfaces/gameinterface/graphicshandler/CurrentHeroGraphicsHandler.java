package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.canvas.GraphicsContext;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.graphics.TextureRegister;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;

/**
 * Renders an effect around the current hero
 *
 * Created by liamdm on 17/08/2016.
 */
public class CurrentHeroGraphicsHandler extends GraphicsHandler {

    @Override
    public void updateGraphics(GraphicsContext graphicsContext) {

        graphicsImages.clear();
        scale = currentGameManager.getScale();
        scaledWidth = TILE_WIDTH * scale;
        scaledHeight = TILE_HEIGHT * scale;
        baseX = currentGameManager.getMap().getWidth() * TILE_WIDTH * scale / 2;
        baseY = 0;
        boolean playerTurn = currentGameManager.isPlayerTurn();

        /**
         * Render the selection
         */
        if (currentGameManager.getMap().getCurrentTurnEntity(playerTurn) != null) {
            int i = currentGameManager.getMap().getCurrentTurnEntity(playerTurn).getX();
            int j = currentGameManager.getMap().getCurrentTurnEntity(playerTurn).getY();
            x = baseX + (j - i) * scaledWidth / 2;
            y = baseY + (j + i) * scaledHeight / 2;

            if (currentGameManager.getMap().getCurrentTurnEntity(playerTurn) instanceof AbstractHero) {
                // Draw box around CurrentHero
                graphicsImages.add(new ImageToAdd(
                        TextureRegister.getTexture("real_selection_special"), x + currentGameManager.getxOffset(), y
                                + currentGameManager.getyOffset(), scaledWidth, scaledHeight));
            } else {
                // Draw box around CurrentEnemy
                graphicsImages.add(new ImageToAdd(
                        TextureRegister.getTexture("enemy_selection"), x + currentGameManager.getxOffset(), y
                                + currentGameManager.getyOffset(), scaledWidth, scaledHeight));
            }
        }
    }

    @Override
    public boolean needsUpdating() {
        return currentGameManager.isGameChanged() || currentGameManager.isSelectionChanged() || currentGameManager.isAbilitySelectedChanged();
    }
}

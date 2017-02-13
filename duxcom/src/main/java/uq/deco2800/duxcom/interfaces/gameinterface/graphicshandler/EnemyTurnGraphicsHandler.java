package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.canvas.GraphicsContext;
import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;

/**
 * Graphics handler for all overlays that are displayed during the enemy turn
 * procedure.
 *
 * NOTE: some floor based graphics have been integrated with the CurrentHero and
 * CurrentSelection Graphics Handlers.
 *
 * Created by jay-grant on 21/09/2016.
 */
public class EnemyTurnGraphicsHandler extends GraphicsHandler {

    @Override
    public void updateGraphics(GraphicsContext graphicsContext) {

        graphicsImages.clear();

        scale = currentGameManager.getScale();
        scaledWidth = (int) (TILE_WIDTH * scale);
        scaledHeight = (int) (TILE_HEIGHT * scale);
        baseX = (int) (currentGameManager.getMap().getWidth() * TILE_WIDTH * scale) / 2;
        int ownerX = currentGameManager.getCurrentAbilityOwnerX();
        int ownerY = currentGameManager.getCurrentAbilityOwnerY();
        int targetX = currentGameManager.getCurrentAbilityTargetX();
        int targetY = currentGameManager.getCurrentAbilityTargetY();
        boolean hit = currentGameManager.getHitOrSplat();
        AbstractAbility ability = currentGameManager.getCurrentAbilityCast();


        if (ownerX != -1) {
            /**
             * Render the cast indicator above owner
             */
            x = baseX + (ownerY - ownerX) * scaledWidth / 2;
            y = baseY + (ownerY + ownerX) * scaledHeight / 2;
            graphicName = ability.getCastIcon().toString();
            addImage(graphicName);
        }
        if (targetX != -1 && hit) {
            /**
             * Render the attack indicator above target
             */
            x = baseX + (targetY - targetX) * scaledWidth / 2;
            y = baseY + (targetY + targetX) * scaledHeight / 2;
            graphicName = ability.getHitIcon().toString();
            addImage(graphicName);
        } else if (targetX != -1) {
            /**
             * Render the hit splat on the target
             */
            x = baseX + (targetY - targetX) * scaledWidth / 2;
            y = baseY + (targetY + targetX) * scaledHeight / 2;
            graphicName = ability.getSplatIcon().toString();
            addImage(graphicName);
        }
        currentGameManager.setTurnGraphicsChanged(true);
    }

    @Override
    public boolean needsUpdating() {
        return currentGameManager.isTurnGraphicsChanged();
    }
}

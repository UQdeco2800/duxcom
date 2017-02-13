package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.canvas.GraphicsContext;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.SelectionType;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.graphics.TextureRegister;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;

/**
 * The current selection graphics handler.
 * <p>
 * Created by liamdm on 17/08/2016.
 */
public class CurrentSelectionGraphicsHandler extends GraphicsHandler {

    private int selectionX;
    private int selectionY;
    private double scaledWidth;
    private double scaledHeight;
    private double baseX;
    private double baseY;
    private int enemyMoveIntentX;
    private int enemyMoveIntentY;


    @Override
    public void updateGraphics(GraphicsContext graphicsContext) {

        graphicsImages.clear();

        scale = currentGameManager.getScale();

        selectionX = currentGameManager.getSelectionX();
        selectionY = currentGameManager.getSelectionY();
        scaledWidth = TILE_WIDTH * scale;
        scaledHeight = TILE_HEIGHT * scale;
        baseX = (currentGameManager.getMap().getWidth() * TILE_WIDTH * scale) / 2;
        baseY = 0;
        enemyMoveIntentX = currentGameManager.getEnemyMoveIntentX();
        enemyMoveIntentY = currentGameManager.getEnemyMoveIntentY();

        boolean playerTurn = currentGameManager.isPlayerTurn();

        if (currentGameManager.getMap().getCurrentTurnEntity(playerTurn) instanceof AbstractHero) {
            renderHeroTurn();
        } else {
            renderEnemyTurn();
        }
        currentGameManager.setSelectionChanged(false);
    }

    /**
     * Render the movement tile for the current Enemy
     */
    private void renderEnemyTurn() {
        if (enemyMoveIntentX != -1) {
            x = baseX + (enemyMoveIntentY - enemyMoveIntentX) * scaledWidth / 2;
            y = baseY + (enemyMoveIntentY + enemyMoveIntentX) * scaledHeight / 2;
            // Enemy Turn
            graphicsImages.add(new ImageToAdd(
                    TextureRegister.getTexture("enemy_move_indicator"), x + currentGameManager.getxOffset(),
                    y + currentGameManager.getyOffset(), scaledWidth, scaledHeight));
        }
    }

    private void renderHeroTurn() {
        // Render the selection
        if (currentGameManager.getSelectionX() != -1) {
            x = baseX + (currentGameManager.getSelectionY() - currentGameManager.getSelectionX()) * scaledWidth / 2;
            y = baseY + (selectionY + selectionX) * scaledHeight / 2;

            graphicsImages.add(new ImageToAdd(TextureRegister.getTexture("selection"), x + currentGameManager.getxOffset(), y
                    + currentGameManager.getyOffset(), scaledWidth, scaledHeight));
        }

        // Render the hover
        int hoverX = currentGameManager.getHoverX();
        int hoverY = currentGameManager.getHoverY();

        if (hoverX == -1 && !currentGameManager.isGameChanged()) {
            return;
        }

        if (hoverX < 0 || hoverX > currentGameManager.getMap().getWidth()) {
            // no render
            return;
        }

        if (hoverY < 0 || hoverY > currentGameManager.getMap().getHeight()) {
            // no render
            return;
        }

        x = baseX + (hoverY - hoverX) * scaledWidth / 2;
        y = baseY + (hoverY + hoverX) * scaledHeight / 2;

        if (currentGameManager.getMap().getMovableEntity(hoverX, hoverY) != null) {
            // Can't move here, there's an entity
            String resource = DataRegisterManager.getSelectionDataRegister().getData(SelectionType.SELECTION_FAIL).getSelectionTextureName();
            graphicsImages.add(new ImageToAdd(
                    TextureRegister.getTexture(resource), x + currentGameManager.getxOffset(), y
                    + currentGameManager.getyOffset(), scaledWidth, scaledHeight));
            return;
        }

        String resource = DataRegisterManager.getSelectionDataRegister().getData(SelectionType.SELECTION_STANDARD).getSelectionTextureName();
        graphicsImages.add(new ImageToAdd(
                TextureRegister.getTexture(resource), x + currentGameManager.getxOffset(), y
                + currentGameManager.getyOffset(), scaledWidth, scaledHeight));

    }

    @Override
    public boolean needsUpdating() {
        return currentGameManager.isSelectionChanged() || currentGameManager.isGameChanged();
    }

}

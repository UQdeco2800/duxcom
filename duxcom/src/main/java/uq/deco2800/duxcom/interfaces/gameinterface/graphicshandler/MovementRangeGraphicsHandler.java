package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.canvas.GraphicsContext;
import uq.deco2800.duxcom.abilities.AbilitySelected;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.graphics.TextureRegister;

/**
 * Graphics handler for drawing the movement range of the current turn hero onto the screen.
 *
 * @author Hayden Pike on 06/09/2016.
 */
public class MovementRangeGraphicsHandler extends TileRangeGraphicsHandler {

    /**
     * Draw the current turn hero's movement range onto the map.
     *
     * @param graphicsContext for drawing the movement range onto the canvas
     * @return true if the movement range was drawn, false otherwise (occurs if current hero's movement range has not
     * been initialised.
     */
    @Override
    public void updateGraphics(GraphicsContext graphicsContext) {

    	graphicsImages.clear();

        boolean playerTurn = currentGameManager.isPlayerTurn();

    	if (!(currentGameManager.getMap().getCurrentTurnEntity(playerTurn)
    			instanceof AbstractHero)
    			|| currentGameManager.getAbilitySelected() !=
    			AbilitySelected.MOVE) {
            return;
        }

        super.setDimensions();

        processMovementRange();
    }

    private void processMovementRange() {
        Float[][] tileAPCosts = currentGameManager.getMap().getCurrentTurnHero().getMovementCost();

        // If the movement range hasn't been initialised yet, nothing to map
        if (tileAPCosts == null) {
            return;
        }

        // Render the valid tiles
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                if (tileAPCosts[i][j] == null || !currentGameManager.onScreen(i, j)) {
                    continue;
                }
                drawMovementRangeGraphics(i, j, tileAPCosts);
            }
        }
        currentGameManager.setMovementChanged(false);
        currentGameManager.setAbilitySelectedChanged(false);
    }

    private void drawMovementRangeGraphics(int i, int j, Float[][] tileAPCosts) {
        double x = baseX + (j - i) * scaledWidth / 2;
        double y = baseY + (j + i) * scaledHeight / 2;

        // Top right border
        if (i == 0 || tileAPCosts[i - 1][j] == null) {
            graphicsImages.add(new ImageToAdd(TextureRegister.getTexture("movement_range_1"),
                    x + currentGameManager.getxOffset(), y + currentGameManager.getyOffset(),
                    scaledWidth, scaledHeight));
        }

        // Bottom left border
        if (i == (mapWidth - 1) || tileAPCosts[i + 1][j] == null) {
            graphicsImages.add(new ImageToAdd(TextureRegister.getTexture("movement_range_3"),
                    x + currentGameManager.getxOffset(), y + currentGameManager.getyOffset(),
                    scaledWidth, scaledHeight));
        }

        // Top left border
        if (j == 0 || tileAPCosts[i][j - 1] == null) {
            graphicsImages.add(new ImageToAdd(TextureRegister.getTexture("movement_range_4"),
                    x + currentGameManager.getxOffset(), y + currentGameManager.getyOffset(),
                    scaledWidth, scaledHeight));
        }
        
        // Bottom right border
        if (j == (mapHeight - 1) || tileAPCosts[i][j + 1] == null) {
            graphicsImages.add(new ImageToAdd(TextureRegister.getTexture("movement_range_2"),
                    x + currentGameManager.getxOffset(), y + currentGameManager.getyOffset(),
                    scaledWidth, scaledHeight));
        }
    }

    @Override
    public boolean needsUpdating() {
        return currentGameManager.isGameChanged()
        		|| currentGameManager.isMovementChanged()
        		|| currentGameManager.isAbilitySelectedChanged();
    }
}
package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.canvas.GraphicsContext;

import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.graphics.TextureRegister;


/**
 * Graphics handler for drawing the movement range of a selected enemy onto the screen.
 *
 * @author Hayden Pike on 11/10/2016.
 */
public class EnemyMovementRangeGraphicsHandler extends TileRangeGraphicsHandler {

    private int selectionX;
    private int selectionY;

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

        if (!(currentGameManager.getMap().getCurrentTurnEntity(playerTurn) instanceof AbstractHero)) {
            return;
        }
        super.setDimensions();
        selectionX = currentGameManager.getSelectionX();
        selectionY = currentGameManager.getSelectionY();
        
        // If enemy not selected, nothing to map
        if ((selectionX == -1 && selectionY == -1) || !(currentGameManager.getMap().getTile(selectionX, selectionY).getMovableEntity() instanceof AbstractEnemy)) {
            return;
        }

        AbstractEnemy selectedEnemy = (AbstractEnemy)currentGameManager.getMap().getTile(selectionX, selectionY).getMovableEntity();
        int enemyMoveRange = selectedEnemy.getMoveRange();

        // Render the valid tiles
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {

            	if (!currentGameManager.onScreen(i, j)) {
                    continue;
                }

                drawMovementRange(enemyMoveRange, i, j);

			}
            currentGameManager.setMovementChanged(false);
		}

    }

    private void drawMovementRange(int enemyMoveRange, int i, int j) {
        double x = baseX + (j - i) * scaledWidth / 2;
        double y = baseY + (j + i) * scaledHeight / 2;


        if (Math.abs(selectionY - j) <= enemyMoveRange) {
            // Top right border
            if (selectionX - i == enemyMoveRange || (i == 0 && selectionX - i <= enemyMoveRange)) {
                graphicsImages.add(new ImageToAdd(TextureRegister.getTexture("enemy_movement_range_1"),
                        x + currentGameManager.getxOffset(), y + currentGameManager.getyOffset(),
                        scaledWidth, scaledHeight));
            }

            // Bottom left border
            if (i - selectionX == enemyMoveRange || (i == (mapWidth - 1) && i - selectionX <= enemyMoveRange)) {
                graphicsImages.add(new ImageToAdd(TextureRegister.getTexture("enemy_movement_range_3"),
                        x + currentGameManager.getxOffset(), y + currentGameManager.getyOffset(),
                        scaledWidth, scaledHeight));
            }
        }

        if (Math.abs(selectionX - i) <= enemyMoveRange) {
            // Top left border
            if (selectionY - j == enemyMoveRange || (j == 0 && selectionY - j <= enemyMoveRange)) {
                graphicsImages.add(new ImageToAdd(TextureRegister.getTexture("enemy_movement_range_4"),
                        x + currentGameManager.getxOffset(), y + currentGameManager.getyOffset(),
                        scaledWidth, scaledHeight));
            }

            // Bottom left border
            if (j - selectionY == enemyMoveRange || (j == (mapHeight - 1) && j - selectionY <= enemyMoveRange)) {
                graphicsImages.add(new ImageToAdd(TextureRegister.getTexture("enemy_movement_range_2"),
                        x + currentGameManager.getxOffset(), y + currentGameManager.getyOffset(),
                        scaledWidth, scaledHeight));
            }
        }
    }

    @Override
    public boolean needsUpdating() {
        return currentGameManager.isGameChanged() || currentGameManager.isMovementChanged();
    }	
}

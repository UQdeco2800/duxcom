package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.canvas.GraphicsContext;
import uq.deco2800.duxcom.abilities.AbilitySelected;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.graphics.TextureRegister;

/**
 * Graphics handler for drawing the optimal path to a selected tile on the map.
 *
 * @author Hayden Pike on 21/10/2016.
 */
public class MovementPathGraphicsHandler extends TileRangeGraphicsHandler {

    private int selectionX;
    private int selectionY;
    private Integer[][] currentMovementPath;
    
    /**
     * Draw the current turn hero's movement path to a selected tile.
     *
     * @param graphicsContext for drawing the movement path onto the canvas
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
        
        selectionX = currentGameManager.getSelectionX();
        selectionY = currentGameManager.getSelectionY();
        currentMovementPath = currentGameManager.getMap().getCurrentTurnHero().getMovementPath();
        
        drawMovementPath(selectionX, selectionY);
	}
	
	private void drawMovementPath(int x, int y) {
		
		// If selected tile within movement range i.e. tileAPCosts[selected tile] != null
		// || if there is no path		
		if (x == -1 || y == -1) {
			return;
		} else if (currentMovementPath[x][y] == null) {
			return;
		} else {
			
	        double currentX = baseX + (y - x) * scaledWidth / 2;
	        double currentY = baseY + (y + x) * scaledHeight / 2;
	        double nextX, nextY;
	        
	        switch (currentMovementPath[x][y]) {
		        case 0:
		            graphicsImages.add(new ImageToAdd(TextureRegister.getTexture("movement_path_1"),
		                    currentX + currentGameManager.getxOffset(), currentY + currentGameManager.getyOffset(),
		                    scaledWidth, scaledHeight));
		            nextX = baseX + (y - (x - 1)) * scaledWidth / 2;
		            nextY = baseY + (y + (x - 1)) * scaledHeight / 2;
		            graphicsImages.add(new ImageToAdd(TextureRegister.getTexture("movement_path_3"),
		                    nextX + currentGameManager.getxOffset(), nextY + currentGameManager.getyOffset(),
		                    scaledWidth, scaledHeight));
		            drawMovementPath(x - 1, y);
		        	break;
		        case 1:
		            graphicsImages.add(new ImageToAdd(TextureRegister.getTexture("movement_path_3"),
		                    currentX + currentGameManager.getxOffset(), currentY + currentGameManager.getyOffset(),
		                    scaledWidth, scaledHeight));
		            nextX = baseX + (y - (x + 1)) * scaledWidth / 2;
		            nextY = baseY + (y + (x + 1)) * scaledHeight / 2;
		            graphicsImages.add(new ImageToAdd(TextureRegister.getTexture("movement_path_1"),
		                    nextX + currentGameManager.getxOffset(), nextY + currentGameManager.getyOffset(),
		                    scaledWidth, scaledHeight));
		            drawMovementPath(x + 1, y);
		        	break;
		        case 2:
		            graphicsImages.add(new ImageToAdd(TextureRegister.getTexture("movement_path_4"),
		                    currentX + currentGameManager.getxOffset(), currentY + currentGameManager.getyOffset(),
		                    scaledWidth, scaledHeight));
		            nextX = baseX + ((y - 1) - x) * scaledWidth / 2;
		            nextY = baseY + ((y - 1) + x) * scaledHeight / 2;
		            graphicsImages.add(new ImageToAdd(TextureRegister.getTexture("movement_path_2"),
		                    nextX + currentGameManager.getxOffset(), nextY + currentGameManager.getyOffset(),
		                    scaledWidth, scaledHeight));
		            drawMovementPath(x, y - 1);
		        	break;
		        case 3:
		            graphicsImages.add(new ImageToAdd(TextureRegister.getTexture("movement_path_2"),
		                    currentX + currentGameManager.getxOffset(), currentY + currentGameManager.getyOffset(),
		                    scaledWidth, scaledHeight));
		            nextX = baseX + ((y + 1) - x) * scaledWidth / 2;
		            nextY = baseY + ((y + 1) + x) * scaledHeight / 2;
		            graphicsImages.add(new ImageToAdd(TextureRegister.getTexture("movement_path_4"),
		                    nextX + currentGameManager.getxOffset(), nextY + currentGameManager.getyOffset(),
		                    scaledWidth, scaledHeight));
		            drawMovementPath(x, y + 1);
		        	break;
	        }			
		}
	}
	
    @Override
    public boolean needsUpdating() {
        return currentGameManager.isGameChanged()
        		|| currentGameManager.isMovementChanged()
        		|| currentGameManager.isAbilitySelectedChanged();
    }
	
}

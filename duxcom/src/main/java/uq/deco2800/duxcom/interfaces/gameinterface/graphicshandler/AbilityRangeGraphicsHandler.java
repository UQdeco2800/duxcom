package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import static java.awt.geom.Point2D.distance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uq.deco2800.duxcom.abilities.AbilitySelected;
import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.graphics.TextureRegister;

/**
 * Graphics handler for drawing the range of the currently selected hero's
 * ability.
 *
 * @author jake-stevenson
 */
public class AbilityRangeGraphicsHandler extends TileRangeGraphicsHandler {

	private static Logger logger = LoggerFactory.getLogger(AbilityRangeGraphicsHandler.class);
    /**
     * Draw the current turn hero's selected ability range onto the map.
     */
    @Override
    public void updateGraphics(GraphicsContext graphicsContext) {

    	graphicsImages.clear();

        boolean playerTurn = currentGameManager.isPlayerTurn();

    	if (!(currentGameManager.getMap().getCurrentTurnEntity(playerTurn)
    			instanceof AbstractHero)
    			|| currentGameManager.getAbilitySelected() == AbilitySelected.MOVE) {
            return;
        }

        super.setDimensions();

		AbstractHero owner = currentGameManager.getMap().getCurrentTurnHero();
		AbstractAbility	ability;
		try {
		ability = owner.getSelectedAbility(currentGameManager.getAbilitySelected());
		}
		catch (NullPointerException e){
			logger.error("Ability could not be selected.", e);
			return;
		}

		if (ability != null) {

	        // Set range graphics to red
			Image image1 = TextureRegister.getTexture("enemy_movement_range_1");
			Image image2 = TextureRegister.getTexture("enemy_movement_range_2");
			Image image3 = TextureRegister.getTexture("enemy_movement_range_3");
			Image image4 = TextureRegister.getTexture("enemy_movement_range_4");

			// Set range graphics to green if ability is used on friend
			if(ability.canUseOnFriend()) {
				image1 = TextureRegister.getTexture("green_range_1");
				image2 = TextureRegister.getTexture("green_range_2");
				image3 = TextureRegister.getTexture("green_range_3");
				image4 = TextureRegister.getTexture("green_range_4");
			}

			for (int i = 0; i < mapWidth; i++) {
				for (int j = 0; j < mapHeight; j++) {
			        drawAbilityRangeGraphics(i, j, owner, ability, image1,
			        		image2, image3, image4);
				}
			}
		}
        currentGameManager.setAbilitySelectedChanged(false);
    }

    private void drawAbilityRangeGraphics(int i, int j, AbstractHero owner,
    		AbstractAbility ability, Image image1, Image image2,
    		Image image3, Image image4) {

    	double x = baseX + (j - i) * scaledWidth / 2;
        double y = baseY + (j + i) * scaledHeight / 2;

        if (ability.inRange(owner.getX(), owner.getY(), i, j)) {
			if (Math.round(distance(owner.getX(), owner.getY(), i - 1, j)) >
				ability.getRange() || i == 0) {
				graphicsImages.add(new ImageToAdd(
						image1, x
						+ currentGameManager.getxOffset(), y 
						+ currentGameManager.getyOffset(), scaledWidth,
						scaledHeight));
			}
			if (Math.round(distance(owner.getX(), owner.getY(), i, j + 1)) >
				ability.getRange() || j == mapHeight - 1) {
				graphicsImages.add(new ImageToAdd(
						image2, x
						+ currentGameManager.getxOffset(), y 
						+ currentGameManager.getyOffset(), scaledWidth,
						scaledHeight));
			}
			if (Math.round(distance(owner.getX(), owner.getY(), i + 1, j)) >
				ability.getRange() || i == mapWidth - 1) {
				graphicsImages.add(new ImageToAdd(
						image3, x
						+ currentGameManager.getxOffset(), y 
						+ currentGameManager.getyOffset(), scaledWidth,
						scaledHeight));
			}
			if (Math.round(distance(owner.getX(), owner.getY(), i, j - 1)) >
				ability.getRange() || j == 0) {
				graphicsImages.add(new ImageToAdd(
						image4, x
						+ currentGameManager.getxOffset(), y 
						+ currentGameManager.getyOffset(), scaledWidth,
						scaledHeight));
			}
        }
    }
    @Override
    public boolean needsUpdating() {
        return currentGameManager.isGameChanged()
        		|| currentGameManager.isAbilitySelectedChanged();
    }
}
package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.EntityDataRegister;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.dynamics.DynamicEntity;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.scenery.AbstractScenery;
import uq.deco2800.duxcom.graphics.TextureRegister;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;
import uq.deco2800.duxcom.interfaces.gameinterface.statusbars.MiniHealthBar;
import uq.deco2800.duxcom.items.weapon.Weapon;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.util.List;

/**
 * Created by liamdm on 17/08/2016.
 * Created by Woody Hill, Lachlan Healey.
 * <p>
 * The main job of the renderer is to get points [i,j] from a2d array
 * and convert them to X, Y points to draw an image.
 * <p>
 * In the 2d array, the origin [0,0] is the very top corner.
 * i extends down the left side, and j extends down the right.
 * <p>
 * The X and Y points correspond to the position where the image will be drawn from (from the bottom left)
 */
public class EntityGraphicsHandler extends GraphicsHandler {

    @Override
    public void updateGraphics(GraphicsContext graphicsContext) {

        double entityScale = 1;

        graphicsImages.clear();
        graphicsRects.clear();

        MapAssembly map = currentGameManager.getMap();

        MiniHealthBar miniHealthBar = new MiniHealthBar(15, 150, 70);

        scale = currentGameManager.getScale();
        baseX = map.getWidth() * TILE_WIDTH * scale / 2;

        //List of all entities to render
        List<Entity> entities;

        //scale that entities should be drawn at to give zoom effect
        scale = currentGameManager.getScale();
        entities = map.getEntities();

        //scaled width of each tile
        scaledWidth = TILE_WIDTH * scale;

        //scaled height of each tile
        scaledHeight = TILE_HEIGHT * scale;

        EntityDataRegister entityDataRegister = DataRegisterManager.getEntityDataRegister();

        /*
          Render all entities in order, assume list is presorted.
          this runs in E time where E is number of entities. E < T where
		  T = number of tiles.
         */
        for (Entity entity : entities) {

            if (!currentGameManager.onScreen(entity) ||
                    (entity.isHidden() && (entity instanceof AbstractCharacter))) {
                continue;
            }

            //the position of the entity in the 2D array
            double i = entity.getX() - entity.getElevation();
            double j = entity.getY() - entity.getElevation();

            if (entity instanceof AbstractScenery) {
                entityScale = ((AbstractScenery) entity).getScale();
            } else {
                entityScale = 1;
            }

            String resource;
            if (entity instanceof AbstractScenery || entity instanceof AbstractCharacter) {
                resource = entity.getImageName();
            } else {
                resource = entityDataRegister.getData(entity.getEntityType()).getEntityTextureName();
            }

            Image image;
            if (entity.isHidden()) {
                image = TextureRegister.getFadedTexture(resource);
            } else {
                image = TextureRegister.getTexture(resource);
            }

            //math transformation logic
            x = baseX + (j - i) * scaledWidth / 2;
            y = baseY + (j + i) * scaledHeight / 2;

            //considers the size of the image to ensure it is rendered from the correct point
            anchorX = (image.getWidth() * entityScale - TILE_WIDTH) / 2.0 * scale;
            anchorY = (image.getHeight() * entityScale - TILE_HEIGHT) / 2.0 * scale;

            offsetX = x - anchorX + currentGameManager.getxOffset();
            offsetY = y - anchorY + currentGameManager.getyOffset();

            graphicsImages.add(new ImageToAdd(image, offsetX, offsetY, scaledWidth * image.getWidth() * entityScale / TILE_WIDTH,
                    scaledHeight * image.getHeight() * entityScale / TILE_HEIGHT));

            if (currentGameManager.isMiniHealthBarVisible() && entity instanceof AbstractHero) {
                miniHealthBar.draw((AbstractCharacter) entity, graphicsRects, offsetX + 80 * scale, offsetY, scale, Color.BLACK, 15, 150);
            } else if (currentGameManager.isMiniHealthBarVisible() && entity instanceof AbstractEnemy) {
                miniHealthBar.draw((AbstractCharacter) entity, graphicsRects, offsetX + 80 * scale, offsetY, scale, Color.RED, 10, 100);
            }
        }
    }

    @Override
    public boolean needsUpdating() {
        return currentGameManager.isGameChanged() || dynamicOnScreen();
    }

    private boolean dynamicOnScreen() {
        for (Entity entity : currentGameManager.getMap().getEntities()) {
            if (entity instanceof DynamicEntity && currentGameManager.onScreen(entity.getX(), entity.getY())) {
                return true;
            }
        }
        return false;
    }
}

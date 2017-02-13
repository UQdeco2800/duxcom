package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.scenery.AbstractScenery;
import uq.deco2800.duxcom.graphics.TextureRegister;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.util.List;

/**
 * Displays all required shadows
 *
 * @author Alex McLean
 */
public class ShadowGraphicsHandler extends GraphicsHandler {

    @Override
    public void updateGraphics(GraphicsContext graphicsContext) {

        double entityScale = 1;

        graphicsImages.clear();
        MapAssembly map = currentGameManager.getMap();

        //List of all entities to render
        List<Entity> entities;

        //scale that entities should be drawn at to give zoom effect
        scale = currentGameManager.getScale();

        //scaled width of each tile
        scaledWidth = TILE_WIDTH * scale;

        //scaled height of each tile
        scaledHeight = TILE_HEIGHT * scale;

        //x position for the first tile to be drawn
        baseX = map.getWidth() * TILE_WIDTH * scale / 2;
        //y position for first tile to be drawn
        baseY = 0;

        /**
         * Render all entities in order, assume list is presorted.
         * this runs in E time where E is number of entities. E < T where
         * T = number of tiles.
         */
        entities = map.getEntities();
        for (Entity entity : entities) {

            if (!currentGameManager.onScreen(entity.getX(), entity.getY())) {
                continue;
            }

            if (!(entity.isHidden() && entity instanceof AbstractCharacter)) {

                //the position of the entity in the 2D array
                double i = entity.getX();
                double j = entity.getY() + entity.getElevation();

                if (entity instanceof AbstractScenery) {
                    entityScale = ((AbstractScenery) entity).getScale();
                } else {
                    entityScale = 1;
                }

                String resource;
                if (entity instanceof AbstractCharacter) {
                    resource = entity.getImageName();
                } else if (entity instanceof AbstractScenery) {
                    resource = ((AbstractScenery) entity).getSceneryString();
                } else {
                    resource = DataRegisterManager.getEntityDataRegister().getData(entity.getEntityType()).getEntityTextureName();
                }
                Image shadowImage;
                shadowImage = TextureRegister.getShadowTexture(resource);

                //math transformation logic
                x = baseX + (j - i) * scaledWidth / 2;
                y = baseY + (j + i) * scaledHeight / 2;

                //considers the size of the image to ensure it is rendered from the correct point
                anchorY = (shadowImage.getHeight() * entityScale - TILE_HEIGHT) / 2.0 * scale;
                anchorX = (shadowImage.getWidth() * entityScale - TILE_WIDTH) / 2.0 * scale;

                offsetX = x - anchorX + currentGameManager.getxOffset();
                offsetY = y - anchorY + currentGameManager.getyOffset();

                graphicsImages.add(new ImageToAdd(shadowImage, offsetX, offsetY, scaledWidth * shadowImage.getWidth() * entityScale / TILE_WIDTH,
                        scaledHeight * shadowImage.getHeight() * entityScale / TILE_HEIGHT));

            }

        }
    }

    @Override
    public boolean needsUpdating() {
        return currentGameManager.isGameChanged();
    }
}

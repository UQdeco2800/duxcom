package uq.deco2800.duxcom;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import uq.deco2800.duxcom.interfaces.mapcreatorinterface.MapCreatorDisplayManager;
import uq.deco2800.duxcom.interfaces.mapcreatorinterface.MapCreatorDisplays;

/**
 * Manages the Rendering for the Map Creator
 *
 * @author Lucas Reher
 * @author Sam Thomas
 */
public class MapCreatorRenderer extends AnimationTimer {

    private GraphicsContext graphicsContext;
    private GameManager gameManager;
    private MapCreatorDisplayManager displayManager;

    /**
     * Loads the FXML into MapCreatorController.
     * Sets Interface to primary stage
     *
     * @param graphicsContext     The graphics context that has the MapCreator canvas bound to it
     * @param gameManager         The manager which handles the connection between MapCreatorController
     *                            and the rendering
     */
    public MapCreatorRenderer(GraphicsContext graphicsContext, GameManager gameManager) {
        super();
        this.graphicsContext = graphicsContext;
        this.gameManager = gameManager;
        displayManager = new MapCreatorDisplayManager(MapCreatorDisplays.MAP_CREATOR);
    }


    @Override
    public void handle(long arg0) {
        displayManager.updateGraphicsHandlers(graphicsContext);
        displayManager.render(graphicsContext);
    }

}

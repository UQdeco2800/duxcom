package uq.deco2800.duxcom.interfaces.gameinterface.minimap;

import javafx.scene.canvas.GraphicsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.util.List;

import static uq.deco2800.duxcom.dataregisters.TileDataRegister.TILE_HEIGHT;
import static uq.deco2800.duxcom.dataregisters.TileDataRegister.TILE_WIDTH;

/**
 * Designs a 'mini map' of the current state of the game to be rendered.
 *
 * @author mtimmo
 */
public class MiniMapController {

    private static Logger logger = LoggerFactory.getLogger(MiniMapController.class);

    // The instance of GraphicsContext passed from the graphics handler
    private static GraphicsContext graphicsContext;

    // The instance of AbstractGameMap passed from the graphics handler
    static MapAssembly map;

    // The list of ovals
    static List<GraphicsHandler.OvalToAdd> graphicsOvals;

    // The list of polygons
    static List<GraphicsHandler.PolygonToAdd> graphicsPolygons;

    // The height (in pixels) of the mini map (calculated separately - don't assign!)
    static float mapHeight;

    // The width (in pixels) of the mini map (the height will be scaled from this)
    static float mapWidth = 400;

    // Horizontal offset of the mini map from the left of the window
    static int left = 20;

    // Vertical offset of the mini map from the top of the window
    static int top = 20;

    // The number of tiles the hero can see in each direction (less tiles = bigger scale)
    static int zoom = 20;

    // The coordinates to center the mini map around (coordinates of current hero)
    static int centerX;
    static int centerY;

    // The current mini map
    private static MiniMap minimap;

    // Setting to false will hide the mini map feature
    private static boolean visible = true;

    /**
     * Smells get smelly if this isn't here.
     */
    private MiniMapController() {
    }

    /**
     * Checks core variables haven't changed, and that a instance of mini map exists,
     * and then requests the mini map to render
     *
     * @param gc The instance of GraphicsContext passed from the graphics handler
     * @param gm The instance of AbstractGameMap passed from the graphics handler
     * @param graphicsPolygons
     * @param graphicsOvals
     * @return true once complete, or false if bad arguments
     */
    public static boolean refreshMiniMap(GraphicsContext gc, GameManager gm,
                                         List<GraphicsHandler.PolygonToAdd> graphicsPolygons,
                                         List<GraphicsHandler.OvalToAdd> graphicsOvals) {

        if (!checkArguments(gc, gm)) {
            return false;
        }

        MiniMapController.graphicsPolygons = graphicsPolygons;
        MiniMapController.graphicsOvals = graphicsOvals;

        // Check is mini map is hidden
        if (!visible) {
            return true;
        }

        // If mini map doesn't exist, create it
        if (minimap == null) {
            calculateHeight();
            minimap = new MiniMap();
        }

        // Find the coordinates of the current hero
        centerX = map.getCurrentTurnHero().getX();
        centerY = map.getCurrentTurnHero().getY();

        // Render the mini map
        return minimap.render();
    }

    private static boolean checkArguments(GraphicsContext gc, GameManager gm) {
        // Check for bad arguments
        if (gc == null || gm == null) {
            logger.error("Invalid minimap arguments> GraphicsContext: [{}], GameManager: [{}]", gc, gm);
            return false;
        }

        // Check the GraphicsContext hasn't changed
        if (graphicsContext == null || !graphicsContext.equals(gc)) {
            graphicsContext = gc;
        }

        // Check the GameManager hasn't changed
        if (map == null || !map.equals(gm.getMap())) {
            map = gm.getMap();
        }

        return true;
    }

    /**
     * Method to calculate the height of a terrain tile, scaled by the size of the actual tile
     */
    private static void calculateHeight() {
        // Set map dimensions (height ratio based on the tile height ratio)
        mapHeight = mapWidth * TILE_HEIGHT / TILE_WIDTH;
    }

    /**
     * Get the scale (width) of the mini map in pixels
     *
     * @return the width of the mini map in pixels
     */
    public static float getScale() {
        return mapWidth;
    }

    /**
     * Set the scale (width) of the mini map in pixels
     * The height of the map is also set from here.
     *
     * @param scale the width of the mini map in pixels
     */
    public static void setScale(float scale) {
        MiniMapController.mapWidth = scale;
        calculateHeight();
    }

    /**
     * Get the distance from the left of the mini map in pixels
     *
     * @return the distance from the left of the mini map in pixels
     */
    public static int getLeft() {
        return left;
    }

    /**
     * Set the distance from the left of the mini map in pixels
     *
     * @param left the distance from the left of the mini map in pixels
     */
    public static void setLeft(int left) {
        MiniMapController.left = left;
    }

    /**
     * Get the distance from the top of the mini map in pixels
     *
     * @return the distance from the top of the mini map in pixels
     */
    public static int getTop() {
        return top;
    }

    /**
     * Set the distance from the top of the mini map in pixels
     *
     * @param top the distance from the top of the mini map in pixels
     */
    public static void setTop(int top) {
        MiniMapController.top = top;
    }

    /**
     * Get the number of tiles the hero can see in each direction
     *
     * @return the number of tiles the hero can see in each direction
     */
    public static int getZoom() {
        return zoom;
    }

    /**
     * Set the number of tiles the hero can see in each direction
     *
     * @param zoom the number of tiles the hero can see in each direction
     */
    public static void setZoom(int zoom) {
        MiniMapController.zoom = zoom;
    }

    /**
     * Get the visibility of the mini map in the game
     *
     * @return the visibility of the mini map in the game (true is visible)
     */
    public static boolean isVisible() {
        return visible;
    }

    /**
     * Set the visibility of the mini map in the game
     *
     * @param visible the visibility of the mini map in the game (true is visible)
     */
    public static void setVisible(boolean visible) {
        MiniMapController.visible = visible;
    }
}
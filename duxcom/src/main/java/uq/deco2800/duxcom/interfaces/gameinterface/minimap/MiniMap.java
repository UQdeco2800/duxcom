package uq.deco2800.duxcom.interfaces.gameinterface.minimap;

import javafx.scene.paint.Color;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;

import static javafx.scene.paint.Color.web;
import static uq.deco2800.duxcom.interfaces.gameinterface.minimap.MiniMapController.*;


/**
 * Renders a 'mini map' of the current state of the game.
 *
 * @author mtimmo
 */
class MiniMap {

	/**
	 * A semi-transparent yellow for highlighting the movement range of the current turn hero.
	 */
	private static final Color MOVEMENT_RANGE_HIGHLIGHT = new Color(1.0, 1.0, 0, 0.5);

    /**
     * Renders the background box for the mini map with a border
     */
    private boolean renderBox() {
        double[] xPoints;
        double[] yPoints;

        // Render border of rectangle
        xPoints = new double[]{
                left - 2,
                mapWidth / 2 + left,
                mapWidth + 2 + left,
                mapWidth / 2 + left,

        };
        yPoints = new double[]{
                mapHeight / 2 + top,
                top - 2,
                mapHeight / 2 + top,
                mapHeight + 2 + top,
        };
        graphicsPolygons.add(new GraphicsHandler.PolygonToAdd(Color.WHITE, xPoints, yPoints, 4));

        // Render background rectangle
        xPoints = new double[]{
                left,
                mapWidth / 2 + left,
                mapWidth + left,
                mapWidth / 2 + left,

        };
        yPoints = new double[]{
                mapHeight / 2 + top,
                top,
                mapHeight / 2 + top,
                mapHeight + top,
        };
        graphicsPolygons.add(new GraphicsHandler.PolygonToAdd(Color.BLACK, xPoints, yPoints, 4));

        return true;
    }

    /**
     * Renders an oval (if entity) or polygon (if terrain) on the mini map relative to its position from the current
     * hero
     *
     * @param x the x coordinate for the dot
     * @param y the y coordinate for the dot
     * @return true once completed, false if dot is too far away from the current hero
     */
    private boolean place(Color color, float x, float y, boolean isEntity) {

        // The X and Y scale of the mini map compared to the actual map
        float scaleX;
        float scaleY;

        // Determine entity scaling
        double scale = 1;
        if (isEntity) {
            scale = 0.8;
        }

        // Make sure the object is close enough to the active hero
        if (Math.abs(centerX - x) > zoom || Math.abs(centerY - y) > zoom) {
            return false;
        }

        // Determine the pixel scaling for x and y
        scaleX = (mapWidth) / 2 / (float) (zoom + 0.5);
        scaleY = (mapHeight) / 2 / (float) (zoom + 0.5);

        // Determine the diameter of the dot
        float dotSize = Math.min(scaleX, scaleY) * (float) scale;

        // Place the dot in the perfect position
        if (isEntity) {
            graphicsOvals.add(new GraphicsHandler.OvalToAdd(color,
                    (left + (mapWidth - dotSize) / 2) + (centerX - x + y - centerY) * scaleX / 2,
                    (top + (mapHeight - dotSize) / 2) + (x - centerX + y - centerY) * scaleY / 2,
                    dotSize, dotSize));
        } else {
            double[] xPoints;
            double[] yPoints;

            // Design tile size and position
            xPoints = new double[]{
                    (left + (mapWidth - scaleX) / 2) + (centerX - x + y - centerY) * scaleX / 2,
                    (left + (mapWidth) / 2) + (centerX - x + y - centerY) * scaleX / 2,
                    (left + (mapWidth + scaleX) / 2) + (centerX - x + y - centerY) * scaleX / 2,
                    (left + (mapWidth) / 2) + (centerX - x + y - centerY) * scaleX / 2
            };
            yPoints = new double[]{
                    (top + (mapHeight) / 2) + (x - centerX + y - centerY) * scaleY / 2,
                    (top + (mapHeight - scaleY) / 2) + (x - centerX + y - centerY) * scaleY / 2,
                    (top + (mapHeight) / 2) + (x - centerX + y - centerY) * scaleY / 2,
                    (top + (mapHeight + scaleY) / 2) + (x - centerX + y - centerY) * scaleY / 2
            };

            // Place tile
            graphicsPolygons.add(new GraphicsHandler.PolygonToAdd(color, xPoints, yPoints, 4));
        }

        return true;
    }

    /**
     * Iterates through each entity on the map, retrieving its coordinates and requests a "dot" on the mini map
     */
    private boolean renderEntities() {

        // Find each entity and display it on the mini map
        for (Entity entity : map.getEntities()) {
            // Only draw entities if they are not hidden
            if (!entity.isHidden()) {
                Color entityColor = web(DataRegisterManager
                        .getEntityDataRegister()
                        .getData(entity.getEntityType())
                        .getMinimapColorString());
                place(entityColor, entity.getX(), entity.getY(), true);
            }
        }

        // Place current hero as center of mini map
        place(Color.WHITE, centerX, centerY, true);

        return true;
    }

    /**
     * Iterates through tiles within range of the 'zoom' variable and requests a coloured box on the mini map based on
     * the tile type.
     */
    private boolean renderTerrain() {
        Tile tile;
        // Iterate through each tile within range of current user
        for (int x = centerX - zoom; x <= centerX + zoom; x++) {
            for (int y = centerY - zoom; y <= centerY + zoom; y++) {
                // Checks the tile exists
                if (map.canSelectPoint(x, y)) {
                    // Determine the colour for this tile type
                    tile = map.getTile(x, y);
                } else {
                    // not a tile, try next co-ordinates
                    continue;
                }
                Color color;
                if (tile.isHidden()) {
                    // If tile is hidden by fog-of-war, set it to be grey.
                    color = Color.GRAY;
                } else {
                    color = web(DataRegisterManager
                            .getTileDataRegister()
                            .getData(tile.getTileType())
                            .getMinimapColorString());
                }
                // Place tile on the map
                place(color, x, y, false);
            }

        }
        return true;
    }


    /**
     * Renders the entire mini-map for the active player
     *
     * @return true once completed
     */
    protected boolean render() {
        // Render the mini map
        renderBox();
        renderTerrain();
        renderEntities();

        return true;
    }
}
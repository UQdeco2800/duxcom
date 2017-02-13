package uq.deco2800.duxcom.maps;

import javafx.scene.Scene;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.WoodStack;
import uq.deco2800.duxcom.entities.dynamics.Water;
import uq.deco2800.duxcom.entities.scenery.AbstractScenery;
import uq.deco2800.duxcom.entities.scenery.StackableScenery;
import uq.deco2800.duxcom.graphics.scenery.SceneryType;
import uq.deco2800.duxcom.tiles.LiveTile;
import uq.deco2800.duxcom.tiles.LiveTileType;
import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.util.Array2D;

import java.util.*;

import static uq.deco2800.duxcom.graphics.scenery.SceneryType.*;

/**
 * Created by josh on 10/8/16.
 */
class MapHelper {

    private int mapHeight;
    private int mapWidth;
    public final Array2D<Tile> tiles;

    /**
     * Creates a new helper class for the given map size and array of tiles
     *
     * @param mapHeight height of the map to help with
     * @param mapWidth  width of the map to help with
     * @param tiles     tile array
     */
    MapHelper(int mapHeight, int mapWidth, Array2D<Tile> tiles) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.tiles = tiles;
    }

    /**
     * Draws a line into the helper array using the given specification and tile type
     *
     * @param startX    x start of line
     * @param startY    y start of line
     * @param length    length of line
     * @param direction direction of line
     * @param tile      type of tile for line
     */
    void drawLine(int startX, int startY, int length, char direction, TileType tile) {
        switch (direction) {
            case 'N':
                drawNorthLine(startX, startY, length, tile);
                break;
            case 'E':
                drawEastLine(startX, startY, length, tile);
                break;
            case 'S':
                drawSouthLine(startX, startY, length, tile);
                break;
            case 'W':
                drawWestLine(startX, startY, length, tile);
                break;
            default:
                break;
        }
    }

    private void drawNorthLine(int startX, int startY, int length, TileType tile) {
        for (int i = 0; i < length; i++) {
            if (!onMap(startX, i + startY)) {
                return;
            }
            tiles.set(startX, i + startY, new Tile(tile));
        }
    }

    private void drawEastLine(int startX, int startY, int length, TileType tile) {
        for (int i = 0; i < length; i++) {
            if (!onMap(i + startX, startY)) {
                return;
            }
            tiles.set(i + startX, startY, new Tile(tile));
        }
    }

    private void drawSouthLine(int startX, int startY, int length, TileType tile) {
        for (int i = 0; i < length; i++) {
            if (!onMap(startX, startY - i)) {
                return;
            }
            tiles.set(startX, startY - i, new Tile(tile));
        }
    }

    private void drawWestLine(int startX, int startY, int length, TileType tile) {
        for (int i = 0; i < length; i++) {
            if (!onMap(startX - i, startY)) {
                return;
            }
            tiles.set(startX - i, startY, new Tile(tile));
        }
    }

    /**
     * Draws a rectangle into the helper array using the given specification and tile type
     *
     * @param startX x start of line
     * @param startY y start of line
     * @param width  width of rectangle
     * @param height height of rectangle
     * @param tile   type of tile for line
     * @param fill   whether or not the rectangle should be filled
     */
    void drawRectangle(int startX, int startY, int width, int height, TileType tile, boolean fill) {
        // North from start
        drawLine(startX, startY, height, 'N', tile);

        // West from start
        drawLine(startX - 1, startY, width - 1, 'W', tile);

        // West from end of start
        drawLine(startX - 1, startY + height - 1, width - 1, 'W', tile);

        // South from end of prev
        drawLine(startX - width + 1, startY + height - 1, height, 'S', tile);

        if ((width - 2) >= 0 && (height - 2) >= 0 && fill) {
            drawRectangle(startX - 1, startY + 1, width - 2, height - 2, tile, true);
        }
    }

    /**
     * Determines whether a given set of coordinates is on the map
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return true iff on the map
     */
    private boolean onMap(int x, int y) {
        if (x < 0 || x > mapHeight) {
            return false;
        } else if (y < 0 || y > mapWidth) {
            return false;
        }
        return true;
    }

    /**
     * Gets a set of entities that will make a 2x2 fountain
     *
     * @param bottomX bottom x coordinate
     * @param bottomY bottom y coordinate
     * @return set of entities
     */
    static Set<Entity> getFountain(int bottomX, int bottomY) {
        HashSet<Entity> ret = new HashSet<>();

        for (int i = bottomX - 3; i < bottomX; i++) {
            ret.add(new WoodStack(i, bottomY - 4));
        }
        for (int i = bottomX - 3; i < bottomX; i++) {
            ret.add(new WoodStack(i, bottomY - 1));
        }
        for (int i = bottomY - 4; i < bottomY; i++) {
            ret.add(new WoodStack(bottomX - 3, i));
        }
        for (int i = bottomY - 4; i < bottomY; i++) {
            ret.add(new WoodStack(bottomX, i));
        }

        ret.add(new Water(bottomX - 2, bottomY - 3, -1));

        return ret;
    }

    /**
     * Gets a set of entities that will make a bridge of a given size
     *
     * @param bottomX bottom x coordinate
     * @param bottomY bottom y coordinate
     * @param width x dimension of bridge
     * @param length y dimension of bridge
     * @return set of entities
     */
    static Set<Entity> getBridge(int bottomX, int bottomY, int width, int length) {
        HashSet<Entity> ret = new HashSet<>();

        for (int i = bottomY; i > bottomY - width; i--) {
            ret.add(new StackableScenery(BRIDGE_NEAR_END, bottomX, i));
            ret.add(new StackableScenery(BRIDGE_NEAR_RAMP, bottomX - 1, i));
            for (int j = bottomX - 2; j > bottomX - length + 2; j--) {
                ret.add(new StackableScenery(BRIDGE_MIDDLE, j, i));
            }
            ret.add(new StackableScenery(BRIDGE_FAR_RAMP, bottomX - length + 2, i));
            ret.add(new StackableScenery(BRIDGE_FAR_END, bottomX - length + 1, i));
        }

        return ret;
    }


    /**
     * Generates a little old 2x2 block of whatever LiveTile you specify.
     *
     * @param liveTileType type of live tile
     * @param bottomX bottom x coordinate
     * @param bottomY bottom y coordinate
     * @return set of LiveTiles
     */
    public static Set<LiveTile> getLiveTileChunk(LiveTileType liveTileType, int bottomX, int bottomY) {
        HashSet<LiveTile> ret = new HashSet<>();

        ret.add(LiveTile.getNewLiveTile(liveTileType, bottomX, bottomY));
        ret.add(LiveTile.getNewLiveTile(liveTileType, bottomX, bottomY - 1));
        ret.add(LiveTile.getNewLiveTile(liveTileType, bottomX - 1, bottomY));
        ret.add(LiveTile.getNewLiveTile(liveTileType, bottomX - 1, bottomY - 1));

        return ret;
    }

    /**
     * Method for setting individual regions of the map to checkered tiles.
     *
     * Please note that the start X,Y must be less than or equal to the end
     * X,Y for this to work properly.
     *
     * @param tileType1 checker tile type 1
     * @param tileType2 checker tile type 2
     * @param startX starting x
     * @param startY starting y
     * @param endX finishing x
     * @param endY finishing y
     */
    public void checkerTile(TileType tileType1, TileType tileType2, int startX, int startY, int endX, int endY) {
        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                if ((x + y) % 2 == 0) {
                    tiles.set(x, y, new Tile(tileType1));
                } else {
                    tiles.set(x, y, new Tile(tileType2));
                }
            }
        }
    }

    /**
     * Generates a random forest at the given location.
     *
     * @param startX the starting x-coord
     * @param startY the starting y-coord
     * @param height the height of the forest
     * @param width the width of the forst
     * @param density how close the trees are together. 0 = no trees, 1 = everything is green
     */
    public static Set<AbstractScenery> generateRandomForest(int startX, int startY, int height, int width, double density) {
        HashSet<AbstractScenery> ret = new HashSet<>();

        ArrayList<SceneryType> trees = new ArrayList<>();
        trees.add(SceneryType.RS_TREE_BASIC);
        trees.add(SceneryType.RS_TREE_BRANCH);
        trees.add(SceneryType.RS_TREE_PINE);
        trees.add(SceneryType.RS_BUSH);
        trees.add(SceneryType.RS_TREE_BASIC);
        trees.add(SceneryType.RS_TREE_BRANCH);
        trees.add(SceneryType.RS_TREE_PINE);
        trees.add(SceneryType.RS_BUSH);
        trees.add(SceneryType.RS_TREE_BASIC);
        trees.add(SceneryType.RS_TREE_BRANCH);
        trees.add(SceneryType.RS_TREE_PINE);
        trees.add(SceneryType.RS_BUSH);
        trees.add(SceneryType.DESERT_PLANT);
        trees.add(SceneryType.RS_TREE_BASIC);
        trees.add(SceneryType.RS_TREE_BRANCH);
        trees.add(SceneryType.RS_TREE_PINE);
        trees.add(SceneryType.RS_BUSH);
        trees.add(SceneryType.RS_TREE_BASIC);
        trees.add(SceneryType.RS_TREE_BRANCH);
        trees.add(SceneryType.RS_TREE_PINE);
        trees.add(SceneryType.RS_BUSH);
        trees.add(SceneryType.RS_TREE_BASIC);
        trees.add(SceneryType.RS_TREE_BRANCH);
        trees.add(SceneryType.RS_TREE_PINE);
        trees.add(SceneryType.RS_BUSH);
        trees.add(SceneryType.DESERT_PLANT);
        trees.add(SceneryType.RS_ROCK_LOW);
        trees.add(SceneryType.RS_ROCK_HIGH);

        Random randomGen = new Random();

        for (int i = startX; i < startX + width; i++) {
            for (int j = startY; j < startY + height; j++) {
                if (Math.random() < 0.4) {
                    SceneryType tree = trees.get(randomGen.nextInt(trees.size()));
                    ret.add(new AbstractScenery(tree, i, j));
                }

            }
        }

        return ret;
    }

    public static Set<AbstractScenery> generateRandomSingedForest(int startX, int startY, int height, int width, double density) {
        HashSet<AbstractScenery> ret = new HashSet<>();

        ArrayList<SceneryType> trees = new ArrayList<>();
        trees.add(SceneryType.RS_TREE_BASIC_BURNED);
        trees.add(SceneryType.RS_TREE_BRANCH_BURNED);
        trees.add(SceneryType.RS_TREE_PINE_BURNED);

        Random randomGen = new Random();

        for (int i = startX; i < startX + width; i++) {
            for (int j = startY; j < startY + height; j++) {
                if (Math.random() < density) {
                    SceneryType tree = trees.get(randomGen.nextInt(trees.size()));
                    ret.add(new AbstractScenery(tree, i, j));
                }

            }
        }

        return ret;
    }

    public static Set<AbstractScenery> generateRandomMixedForest(int startX, int startY, int height, int width, double density) {
        HashSet<AbstractScenery> ret = new HashSet<>();

        ArrayList<SceneryType> trees = new ArrayList<>();
        trees.add(SceneryType.RS_TREE_BASIC);
        trees.add(SceneryType.RS_TREE_BRANCH);
        trees.add(SceneryType.RS_TREE_PINE);
        trees.add(SceneryType.RS_BUSH);
        trees.add(SceneryType.RS_TREE_BASIC_BURNED);
        trees.add(SceneryType.RS_TREE_BRANCH_BURNED);
        trees.add(SceneryType.RS_TREE_PINE_BURNED);

        Random randomGen = new Random();

        for (int i = startX; i < startX + width; i++) {
            for (int j = startY; j < startY + height; j++) {
                if (Math.random() < density) {
                    SceneryType tree = trees.get(randomGen.nextInt(trees.size()));
                    ret.add(new AbstractScenery(tree, i, j));
                }

            }
        }

        return ret;
    }

    public static Set<AbstractScenery> generateEastWall(int startX, int startY, int length) {
        HashSet<AbstractScenery> ret = new HashSet<>();

        for (int i = 0; i < length; i++) {

            ret.add(new AbstractScenery(SceneryType.WALL_STONE_LEFT, startX + i, startY));
        }

        return ret;
    }

    public static Set<AbstractScenery> generateNorthWall(int startX, int startY, int length) {
        HashSet<AbstractScenery> ret = new HashSet<>();

        for (int i = 0; i < length; i++) {
            ret.add(new AbstractScenery(SceneryType.WALL_STONE_RIGHT, startX, startY + i));
        }

        return ret;
    }



    /**
     * Adds a textured tile of a specified type at the given location.
     *
     * @param base the type to add
     * @param x the x coord
     * @param y the y coord
     */
    public void addTexturedTile(TileType base, int x, int y) {
        /* will probably split this up into helpers at some point.
        *  will also cache it for performance gainz as this implementation
        *  uses a lot of cycles for no proper reason other than ease\
        */
        ArrayList<TileType> availableTiles = new ArrayList<>();

        String[] entityName = base.toString().split("_");
        ArrayList<String> entityNameArrayList = new ArrayList<>(Arrays.asList(entityName));
        entityNameArrayList.remove(entityNameArrayList.size() - 1);

        for (TileType tile : TileType.values()) {
            String[] enumTileName = tile.toString().split("_");

            ArrayList<String> enumTileNameArrayList = new ArrayList<>(Arrays.asList(enumTileName));
            String lastElement = enumTileNameArrayList.get(enumTileNameArrayList.size() - 1);

            if (lastElement.matches("^-?\\d+$")) {
                enumTileNameArrayList.remove(enumTileNameArrayList.size() - 1);

                if (entityNameArrayList.toString().equals(enumTileNameArrayList.toString())) {
                    availableTiles.add(tile);
                }
            }
        }

        Random randomGen = new Random();
        // now choose a random tile
        TileType tile = availableTiles.get(randomGen.nextInt(availableTiles.size()));

        tiles.set(x, y, new Tile(tile));
    }

    /**
     * Takes an array of co-ordinates and fills them with the specified TileType.
     *
     * @param tileLocations integer array of {x, y} co-ordinates
     * @param tileType the type of tile to set each co-ordinate
     */
    public void SetTileArray(int[][]tileLocations, TileType tileType) {
        Tile tile = new Tile(tileType);

        for (int[] tileLocation : tileLocations) {
            tiles.set(tileLocation[0], tileLocation[1], tile);
        }
    }
}

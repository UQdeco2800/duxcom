package uq.deco2800.duxcom.maps;

import uq.deco2800.duxcom.entities.heros.Knight;
import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.util.TurnTickable;

/**
 * Created by jay-grant on 5/09/2016.
 *
 * An empty 25x25 map for testing
 */
public class EmptyMap extends AbstractGameMap implements TurnTickable {

    private static final int MAP_HEIGHT = 25;
    private static final int MAP_WIDTH = 25;

    /**
     * Creates an empty checkered grass map
     */
    public EmptyMap(String name) {
        super.name = name;
        super.mapType = MapType.EMPTY;
        initialiseEmptyCheckeredMap(TileType.GRASS_1, TileType.GRASS_2, MAP_WIDTH, MAP_HEIGHT);
    }
}

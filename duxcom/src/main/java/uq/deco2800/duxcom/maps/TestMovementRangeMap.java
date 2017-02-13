package uq.deco2800.duxcom.maps;

import uq.deco2800.duxcom.entities.heros.*;
import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.util.TurnTickable;

/**
 * A map used for tests of the movement range calculations performed in MapAssembly.
 */
public class TestMovementRangeMap extends AbstractGameMap implements TurnTickable {

	private static final int MAP_HEIGHT = 40;
	private static final int MAP_WIDTH = 40;

	/**
	 * Create a new map for the tests.
	 *
	 * This map contains two ducks and no other obstacles.
	 * 
	 * @param name name of the map (not used)
	 */
	public TestMovementRangeMap(String name) {
		super.name = name;
		super.mapType = MapType.TEST_MOVEMENT_RANGE;
		super.setSize(MAP_WIDTH, MAP_HEIGHT);

		for (int y = 0; y < MAP_WIDTH; y++) {
			for (int x = 0; x < MAP_HEIGHT; x++) {
				tiles.set(x, y, new Tile(TileType.DT_GRASS_DARK_1));
			}
		}

		addHero(new Duck(10, 8));
		addHero(new Duck(10, 9));
	}
}

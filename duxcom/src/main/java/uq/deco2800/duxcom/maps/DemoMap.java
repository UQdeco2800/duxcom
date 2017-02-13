package uq.deco2800.duxcom.maps;

import uq.deco2800.duxcom.entities.heros.*;
import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.util.TurnTickable;

/**
 * Models the game's physical environment.
 * 
 * @author Leggy
 * @author woody
 *
 */
public class DemoMap extends AbstractGameMap implements TurnTickable {

	private static final int MAP_HEIGHT = 40;
	private static final int MAP_WIDTH = 40;
	/**
	 * Instantiates a DemoMap object with the specified parameters.
	 * 
	 * @param name
	 *            The name of the DemoMap
	 */
	public DemoMap(String name) {
		super.name = name;
		super.mapType = MapType.DEMO;
		super.setSize(MAP_WIDTH, MAP_HEIGHT);

		for (int y = 0; y < MAP_WIDTH; y++) {
			for (int x = 0; x < MAP_HEIGHT; x++) {
				tiles.set(x, y, new Tile(TileType.DT_GRASS_DARK_1));
			}
		}


		addHero(new Priest(10, 8));
		addHero(new Warlock(10, 10));
		addHero(new Knight(10, 12));
		addHero(new Archer(10, 14));
		addHero(new Cavalier(10,16));
		addHero(new Rogue(10, 18));
        addHero(new Ibis(20,20));
                
		heroManager.setHero(0);
	}
}

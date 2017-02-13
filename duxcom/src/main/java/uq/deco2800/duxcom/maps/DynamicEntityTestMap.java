package uq.deco2800.duxcom.maps;

import uq.deco2800.duxcom.entities.WoodStack;
import uq.deco2800.duxcom.entities.dynamics.BreakableWoodStack;
import uq.deco2800.duxcom.entities.dynamics.DiscoDoggo;
import uq.deco2800.duxcom.entities.heros.BetaTester;
import uq.deco2800.duxcom.tiles.TileType;

/**
 * Created by tiget on 28/08/2016.
 *
 * An empty map for testing all things related to Enemy entities
 */
public class DynamicEntityTestMap extends AbstractGameMap {

    private static final int MAP_HEIGHT = 100;
    private static final int MAP_WIDTH = 100;

    /**
     * Creates the benchmark map
     *
     * @param name name of benchmark map
     */
    public DynamicEntityTestMap(String name) {
        super.name = name;
        super.mapType = MapType.DYNAMIC_ENTITY_TEST;
        super.setSize(MAP_WIDTH, MAP_HEIGHT);

        initialiseEmptyCheckeredMap(TileType.RS_GRASS_1, TileType.GRASS_1, MAP_WIDTH, MAP_HEIGHT);

		addEntity(new DiscoDoggo(51, 51));

		addEntity(new WoodStack(44, 44));
		addEntity(new WoodStack(43, 44));
		addEntity(new WoodStack(46, 45));

        addHero(new BetaTester(50, 50));
		addEntity(new BreakableWoodStack(52, 52));
    }
}

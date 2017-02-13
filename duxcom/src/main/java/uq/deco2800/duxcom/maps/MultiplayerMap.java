package uq.deco2800.duxcom.maps;

import uq.deco2800.duxcom.tiles.TileType;

/**
 * Map specialised for playing multiplayer.
 *
 * Created by liamdm on 16/10/2016.
 */
public class MultiplayerMap extends AbstractGameMap {

    private org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MultiplayerMap.class);


    /**
     * The size of the multiplayer map
     */
    public static final int MULTIPLAYER_SIZE = 200;

    public MultiplayerMap() {
        super.setSize(MULTIPLAYER_SIZE, MULTIPLAYER_SIZE);
        super.name = "";
        super.mapType = MapType.MULTIPLAYER;

        initialiseEmptyCheckeredMap(TileType.RS_GRASS_1, TileType.GRASS_1, MULTIPLAYER_SIZE, MULTIPLAYER_SIZE);

    }

}

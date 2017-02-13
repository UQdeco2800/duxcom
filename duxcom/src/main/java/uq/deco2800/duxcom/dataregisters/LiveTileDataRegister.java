package uq.deco2800.duxcom.dataregisters;

import uq.deco2800.duxcom.tiles.LiveTileType;

/**
 * Here is where you register your LiveTile data boys and girls
 * <p>
 * Created by jay-grant on 14/10/2016.
 */
public class LiveTileDataRegister extends AbstractDataRegister<LiveTileType, LiveTileDataClass> {

    /**
     * Package private constructor
     * <p>
     * Should only be called by DataRegisterManager
     */
    LiveTileDataRegister() {
        super();

        // Register passive effect LiveTiles
        linkDataToType(LiveTileType.LAVA,
                new LiveTileDataClass("live_lava", "lava",
                        TileDataRegister.CRIMSON, 8, true, 2, true, 500L, false, 10));

        linkDataToType(LiveTileType.FLAT_LAVA,
                new LiveTileDataClass("live_lava", "lava",
                        TileDataRegister.CRIMSON, 0, true, 2, true, 500L, false, 10));

        linkDataToType(LiveTileType.LONG_GRASS,
                new LiveTileDataClass("live_grass", "longgrass",
                        TileDataRegister.GREEN, 0, false, 3, false, 700L, true, 2));

        linkDataToType(LiveTileType.PUDDLE,
                new LiveTileDataClass("live_puddle", "puddle",
                        TileDataRegister.BLUE, 0, true, 2, true, 1500L, false));

        linkDataToType(LiveTileType.BLEED_TRAP,
                new LiveTileDataClass("live_bleed", "bleed",
                        TileDataRegister.BLUE, 0, true, 2, true, 2000L, true, 2));

        linkDataToType(LiveTileType.POISON_TRAP,
                new LiveTileDataClass("live_poison", "poison",
                        TileDataRegister.BLUE, 0, true, 8, true, 500L, false, 2));

        linkDataToType(LiveTileType.SWAMP,
                new LiveTileDataClass("live_swamp", "swamp",
                        TileDataRegister.BLUE, 0, true, 5, true, 750L, false, 3));

        linkDataToType(LiveTileType.FROST,
                new LiveTileDataClass("live_frost", "frost",
                        TileDataRegister.BLUE, 0, true, 6, true, 500L, false));

        linkDataToType(LiveTileType.FLAME,
                new LiveTileDataClass("live_flame", "flame",
                        TileDataRegister.BLUE, 4, true, 0, true, 400L, true, 2));

        // Register scenic LiveTiles
        linkDataToType(LiveTileType.CAVE_TWO,
                new LiveTileDataClass("cave_two", "cave2",
                        TileDataRegister.WHEAT, 1, true, 0, true, 500L, false));

        linkDataToType(LiveTileType.CAVE_THREE,
                new LiveTileDataClass("cave_three", "cave3",
                        TileDataRegister.WHEAT, 1, true, 0, true, 500L, false));

        linkDataToType(LiveTileType.CAVE_FOUR,
                new LiveTileDataClass("cave_four", "cave4",
                        TileDataRegister.WHEAT, 4, true, 1, true, 500L, false));

        linkDataToType(LiveTileType.OIL,
                new LiveTileDataClass("live_oil", "oil_tile",
                        TileDataRegister.BLACK, 0, true, 5, true, 500L, true));
    }

}

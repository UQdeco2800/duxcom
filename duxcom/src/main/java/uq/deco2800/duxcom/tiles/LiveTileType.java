package uq.deco2800.duxcom.tiles;

/**
 * Register list of all Live Tile Types.
 * <p>
 * Each LiveTileType must be a unique value and should correspond to an entry
 * in the LiveTileDataRegister and a unique class.
 * <p>
 * Please note that every new addition should also result in the following
 * - a new LiveTileDataRegister entry being created
 * - all appropriate frames added to TextureRegister with the name linking to
 * the DataRegister argument
 * - a new Class being created to contain the specific buffs
 * - a new entry added to the LiveTile.getNewLiveTile(...) static method
 * <p>
 * Created by jay-grant on 14/10/2016.
 */
public enum LiveTileType {
    LAVA,
    FLAT_LAVA,
    LONG_GRASS,
    PUDDLE,
    CAVE_TWO,
    CAVE_THREE,
    CAVE_FOUR,
    FLAME,
    BLEED_TRAP,
    POISON_TRAP,
    SWAMP,
    FROST,
    OIL,
    FACTORY_DEFAULT_TESTER

}

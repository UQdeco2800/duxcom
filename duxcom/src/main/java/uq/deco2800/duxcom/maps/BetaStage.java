package uq.deco2800.duxcom.maps;

import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyKnight;
import uq.deco2800.duxcom.entities.heros.*;
import uq.deco2800.duxcom.tiles.LiveTileType;
import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.util.TurnTickable;

/**
 * Beta testing environment
 *
 * Created by jay-grant on 31/10/2016.
 */
public class BetaStage extends AbstractGameMap implements TurnTickable {

    private static final int MAP_HEIGHT = 25;
    private static final int MAP_WIDTH = 25;

    /**
     * Creates an empty checkered grass map
     */
    public BetaStage(String name) {
        super.name = name;
        super.mapType = MapType.ENEMY_TEST;
        initialiseEmptyCheckeredMap(TileType.RS_GRASS_1, TileType.RS_GRASS_2, MAP_WIDTH, MAP_HEIGHT);

//        addHero(new Knight(0, 0));
        addHero(new Warlock(0, 2));
//        addHero(new Cavalier(0, 4));
        addHero(new Rogue(0, 6));
//        addHero(new Priest(0, 8));
//        addHero(new Archer(0, 10));

        MapHelper.getLiveTileChunk(LiveTileType.LAVA, 2, 2).forEach(this::addLiveTile);


        MapHelper.getBridge(16, 16, 5, 10).forEach(this::addEntity);
        addEnemy(new EnemyKnight(0, 0));
        addEnemy(new EnemyKnight(0, 4));
        addEnemy(new EnemyKnight(0, 8));
//        addEnemy(new EnemyKnight(4, 4));
//        addEnemy(new EnemyKnight(0, 16));
//        addEnemy(new EnemyKnight(0, 20));


//        addEnemyKillObjective(EnemyType.ENEMY_KNIGHT, 1);

//        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 1, 1).forEach(this::addLiveTile);
//        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 1, 3).forEach(this::addLiveTile);
//        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 1, 5).forEach(this::addLiveTile);
//        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 1, 7).forEach(this::addLiveTile);
//        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 3, 1).forEach(this::addLiveTile);
//        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 3, 3).forEach(this::addLiveTile);
//        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 3, 5).forEach(this::addLiveTile);
//        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 3, 7).forEach(this::addLiveTile);
//        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 5, 1).forEach(this::addLiveTile);
//        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 5, 3).forEach(this::addLiveTile);
//        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 5, 5).forEach(this::addLiveTile);
//        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 5, 7).forEach(this::addLiveTile);

//        MapHelper.getLiveTileChunk(LiveTileType.OIL, 1, 1).forEach(this::addLiveTile);
//        MapHelper.getLiveTileChunk(LiveTileType.OIL, 1, 3).forEach(this::addLiveTile);
//        MapHelper.getLiveTileChunk(LiveTileType.OIL, 1, 5).forEach(this::addLiveTile);
//        MapHelper.getLiveTileChunk(LiveTileType.OIL, 1, 7).forEach(this::addLiveTile);
//        MapHelper.getLiveTileChunk(LiveTileType.OIL, 3, 1).forEach(this::addLiveTile);
//        MapHelper.getLiveTileChunk(LiveTileType.OIL, 3, 5).forEach(this::addLiveTile);
//        MapHelper.getLiveTileChunk(LiveTileType.OIL, 3, 7).forEach(this::addLiveTile);
//        MapHelper.getLiveTileChunk(LiveTileType.OIL, 5, 1).forEach(this::addLiveTile);
//        MapHelper.getLiveTileChunk(LiveTileType.OIL, 5, 3).forEach(this::addLiveTile);
//        MapHelper.getLiveTileChunk(LiveTileType.OIL, 5, 7).forEach(this::addLiveTile);
    }
}

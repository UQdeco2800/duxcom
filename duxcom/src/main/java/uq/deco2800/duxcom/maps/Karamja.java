package uq.deco2800.duxcom.maps;

import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyKnight;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemySupport;
import uq.deco2800.duxcom.entities.heros.Knight;
import uq.deco2800.duxcom.tiles.TileType;

/**
 * Created by jay-grant on 16/10/2016.
 */
public class Karamja extends AbstractGameMap {

    private static final int MAP_HEIGHT = 50;
    private static final int MAP_WIDTH = 50;

    public Karamja(String name) {
        super.name = name;
        super.mapType = MapType.KARAMJA;
        super.setSize(MAP_WIDTH, MAP_HEIGHT);
        super.initialiseEmptyCheckeredMap(TileType.RS_SNOW1, TileType.RS_SNOW1, MAP_WIDTH, MAP_HEIGHT);
        addMapHelperPatterns();
        addCharacters();
    }

    private void addMapHelperPatterns() {
        MapHelper memes = new MapHelper(MAP_HEIGHT, MAP_WIDTH, tiles);
        drawFloor(memes);
        addObjectives();
    }

    private void drawFloor(MapHelper memes) {
        // Floor for volcano level
        memes.checkerTile(TileType.RS_SAND1, TileType.RS_SAND2, 0, 0, 40, 50);
        memes.checkerTile(TileType.RS_SAND1, TileType.RS_SAND2, 40, 0, 41, 39);
        memes.checkerTile(TileType.RS_SAND1, TileType.RS_SAND2, 41, 0, 42, 37);
        memes.checkerTile(TileType.RS_SAND1, TileType.RS_SAND2, 42, 0, 43, 4);
        memes.checkerTile(TileType.RS_SAND1, TileType.RS_SAND2, 42, 31, 43, 34);

        memes.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 38, 34, 50);
        memes.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 36, 33, 38);
        memes.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 34, 32, 36);
        memes.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 32, 30, 34);
        memes.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 30, 28, 32);
        memes.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 28, 26, 30);
        memes.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 27, 17, 28);
        memes.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 26, 12, 27);
        memes.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 25, 11, 28);

        memes.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 0, 0, 27, 8);
        memes.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 27, 0, 31, 6);
        memes.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 31, 0, 32, 5);
        memes.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 32, 0, 33, 5);
        memes.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 33, 0, 36, 3);
        memes.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 36, 0, 37, 2);
        memes.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 37, 0, 38, 1);
        memes.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 0, 0, 8, 30);
        memes.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 0, 30, 7, 31);
        memes.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 0, 31, 6, 32);
        memes.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 0, 32, 4, 33);
        memes.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 0, 33, 3, 34);
        memes.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 8, 8, 9, 9);
        memes.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 21, 8, 26, 9);
        memes.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 8, 25, 9, 29);
    }

    private void addCharacters() {
        addHero(new Knight(25, 25));
        addEnemy(new EnemyKnight(6,6));
        addEnemy(new EnemySupport(5,5));
    }

    private void addObjectives() {
        addEnemyKillObjective(EnemyType.ENEMY_KNIGHT, 3);
        addScoreObjective(50000);
        addEnemyKillObjective(EnemyType.ENEMY_BEAR, 1);
        addEnemyHitObjective(EnemyType.ENEMY_WOLF);
    }
}

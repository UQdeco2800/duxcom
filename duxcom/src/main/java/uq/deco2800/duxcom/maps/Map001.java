package uq.deco2800.duxcom.maps;

import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyKnight;
import uq.deco2800.duxcom.graphics.scenery.SceneryType;
import uq.deco2800.duxcom.sound.SoundPlayer;
import uq.deco2800.duxcom.tiles.LavaTile;
import uq.deco2800.duxcom.tiles.TileType;

/**
 * Created by winstonf6 on 17/10/2016
 */

public class Map001 extends AbstractGameMap {
    private static final int MAP_HEIGHT = 36;
    private static final int MAP_WIDTH = 36;

    public Map001(String name, boolean beta) {
        super.name = name;
        super.mapType = MapType.MAP001;
        super.setSize(MAP_WIDTH, MAP_HEIGHT);
        super.initialiseEmptyCheckeredMap(TileType.DT_SAND_DARK_3,
                TileType.DT_SAND_DARK_4, MAP_WIDTH, MAP_HEIGHT);

        addCharacters();
        addLava();
        addFountain();
        addWalls();
        addGrass();
        addTrees();
        addRocks();
        addObjectives();

        SoundPlayer.stopMusic();
        SoundPlayer.playGameMusic();
    }

    private void addCharacters() {
        super.spawnPoints = new int[][]{{29, 31}, {30, 30}, {31, 29}, {32, 28}};

        addSelectedHeroes();


        addEnemy(new EnemyKnight(25, 7));
        addEnemy(new EnemyKnight(26, 8));
        addEnemy(new EnemyKnight(27, 9));

        addEnemy(new EnemyKnight(13, 6));
        addEnemy(new EnemyKnight(16, 8));
        addEnemy(new EnemyKnight(6, 16));
        addEnemy(new EnemyKnight(11, 16));

        addEnemy(new EnemyKnight(14, 22));
        addEnemy(new EnemyKnight(15, 21));
        addEnemy(new EnemyKnight(16, 23));

        addEnemy(new EnemyKnight(21, 15));
        addEnemy(new EnemyKnight(21, 16));
        addEnemy(new EnemyKnight(21, 17));
        addEnemy(new EnemyKnight(21, 18));

        addEnemy(new EnemyKnight(28, 22));
        addEnemy(new EnemyKnight(29, 22));

        addEnemy(new EnemyKnight(17, 33));
        addEnemy(new EnemyKnight(24, 32));
    }

    private void addObjectives() {
        addEnemyKillObjective(EnemyType.ENEMY_KNIGHT, 2);
        addEnemyHitObjective(EnemyType.ENEMY_KNIGHT);
        addMovementObjective(25, 25);
        addScoreObjective(4000);
    }

    private void addLava() {
        for (int i = 1; i <= 35; i++) {
            addLiveTile(new LavaTile(0, i));
        }

        for (int i = 2; i <= 35; i++) {
            addLiveTile(new LavaTile(i, 0));
        }

        addLiveTile(new LavaTile(28, 35));
        addLiveTile(new LavaTile(29, 34));
        addLiveTile(new LavaTile(29, 35));
        addLiveTile(new LavaTile(30, 34));
        addLiveTile(new LavaTile(30, 35));
        addLiveTile(new LavaTile(31, 34));
        addLiveTile(new LavaTile(31, 35));
        addLiveTile(new LavaTile(32, 33));
        addLiveTile(new LavaTile(32, 34));
        addLiveTile(new LavaTile(32, 35));
        addLiveTile(new LavaTile(33, 32));
        addLiveTile(new LavaTile(33, 33));
        addLiveTile(new LavaTile(33, 34));
        addLiveTile(new LavaTile(33, 35));
        addLiveTile(new LavaTile(34, 29));
        addLiveTile(new LavaTile(34, 30));
        addLiveTile(new LavaTile(34, 31));
        addLiveTile(new LavaTile(34, 32));
        addLiveTile(new LavaTile(34, 33));
        addLiveTile(new LavaTile(34, 34));
        addLiveTile(new LavaTile(34, 35));
        addLiveTile(new LavaTile(35, 28));
        addLiveTile(new LavaTile(35, 29));
        addLiveTile(new LavaTile(35, 30));
        addLiveTile(new LavaTile(35, 31));
        addLiveTile(new LavaTile(35, 32));
        addLiveTile(new LavaTile(35, 33));
        addLiveTile(new LavaTile(35, 34));
        addLiveTile(new LavaTile(35, 35));
    }

    private void addFountain() {
        MapHelper.getFountain(12, 13).forEach(this::addEntity);
    }

    private void addGrass() {
        for (int i = 3; i <= 31; i++) {
            for (int j = 3; j <= 31; j++) {
                setTile(j, i, TileType.DT_GRASS_DARK_1);
            }
        }
    }

    private void addWalls() {
        addScenery(SceneryType.WALL_STONE_TOWER, 3, 3);
        addScenery(SceneryType.WALL_STONE_TOWER, 3, 11);
        addScenery(SceneryType.WALL_STONE_TOWER, 11, 3);
        addScenery(SceneryType.WALL_STONE_TOWER, 3, 19);
        addScenery(SceneryType.WALL_STONE_TOWER, 10, 19);
        addScenery(SceneryType.WALL_STONE_TOWER, 19, 3);
        addScenery(SceneryType.WALL_STONE_TOWER, 19, 11);

        for (int i = 4; i <= 10; i++) {
            addScenery(SceneryType.WALL_STONE_RIGHT, 3, i);
            addScenery(SceneryType.WALL_STONE_RIGHT, 19, i);
            addScenery(SceneryType.WALL_STONE_LEFT, i, 3);

        }

        for (int i = 12; i <= 18; i++) {
            addScenery(SceneryType.WALL_STONE_RIGHT, 3, i);
            addScenery(SceneryType.WALL_STONE_LEFT, i, 3);
        }

        addScenery(SceneryType.WALL_STONE_LEFT, 6, 19);
        addScenery(SceneryType.WALL_STONE_LEFT, 7, 19);
        addScenery(SceneryType.WALL_STONE_LEFT, 9, 19);
        addScenery(SceneryType.WALL_STONE_LEFT, 10, 19);
        addScenery(SceneryType.WALL_STONE_LEFT, 11, 19);
        addScenery(SceneryType.WALL_STONE_LEFT, 12, 19);
        addScenery(SceneryType.WALL_STONE_LEFT, 13, 19);
        addScenery(SceneryType.WALL_STONE_LEFT, 14, 19);
        addScenery(SceneryType.WALL_STONE_LEFT, 15, 19);

        addScenery(SceneryType.WALL_STONE_RIGHT, 19, 12);
        addScenery(SceneryType.WALL_STONE_RIGHT, 19, 13);
        addScenery(SceneryType.WALL_STONE_RIGHT, 19, 14);
        addScenery(SceneryType.WALL_STONE_RIGHT, 19, 15);


    }

    private void addTrees() {
        //right side trees
        addScenery(SceneryType.RS_TREE_PINE, 3, 13);
        addScenery(SceneryType.RS_TREE_PINE, 3, 28);
        addScenery(SceneryType.RS_TREE_PINE, 4, 25);
        addScenery(SceneryType.RS_TREE_PINE, 5, 26);
        addScenery(SceneryType.RS_TREE_PINE, 6, 27);
        addScenery(SceneryType.RS_TREE_PINE, 7, 22);
        addScenery(SceneryType.RS_TREE_PINE, 9, 27);
        addScenery(SceneryType.RS_TREE_PINE, 9, 25);

        addScenery(SceneryType.RS_TREE_BRANCH, 2, 21);
        addScenery(SceneryType.RS_TREE_BRANCH, 2, 25);
        addScenery(SceneryType.RS_TREE_BRANCH, 3, 23);
        addScenery(SceneryType.RS_TREE_BRANCH, 3, 26);
        addScenery(SceneryType.RS_TREE_BRANCH, 4, 20);
        addScenery(SceneryType.RS_TREE_BRANCH, 4, 22);
        addScenery(SceneryType.RS_TREE_BRANCH, 4, 26);
        addScenery(SceneryType.RS_TREE_BRANCH, 5, 22);
        addScenery(SceneryType.RS_TREE_BRANCH, 5, 28);
        addScenery(SceneryType.RS_TREE_BRANCH, 5, 23);
        addScenery(SceneryType.RS_TREE_BRANCH, 6, 23);
        addScenery(SceneryType.RS_TREE_BRANCH, 7, 20);
        addScenery(SceneryType.RS_TREE_BRANCH, 7, 29);
        addScenery(SceneryType.RS_TREE_BRANCH, 8, 25);
        addScenery(SceneryType.RS_TREE_BRANCH, 8, 30);
        addScenery(SceneryType.RS_TREE_BRANCH, 9, 20);
        addScenery(SceneryType.RS_TREE_BRANCH, 9, 23);
        addScenery(SceneryType.RS_TREE_BRANCH, 10, 22);
        addScenery(SceneryType.RS_TREE_BRANCH, 11, 25);
        addScenery(SceneryType.RS_TREE_BRANCH, 11, 27);
        addScenery(SceneryType.RS_TREE_BRANCH, 11, 29);
        addScenery(SceneryType.RS_TREE_BRANCH, 11, 21);
        addScenery(SceneryType.RS_TREE_BRANCH, 12, 24);
        addScenery(SceneryType.RS_TREE_BRANCH, 12, 30);
        addScenery(SceneryType.RS_TREE_BRANCH, 13, 27);
        addScenery(SceneryType.RS_TREE_BRANCH, 13, 29);
        addScenery(SceneryType.RS_TREE_BRANCH, 14, 24);
        addScenery(SceneryType.RS_TREE_BRANCH, 14, 31);
        addScenery(SceneryType.RS_TREE_BRANCH, 15, 28);
        addScenery(SceneryType.RS_TREE_BRANCH, 16, 27);
        addScenery(SceneryType.DESERT_PLANT, 17, 25);

        //left side trees
        addScenery(SceneryType.RS_TREE_PINE, 26, 14);
        addScenery(SceneryType.RS_TREE_PINE, 27, 4);
        addScenery(SceneryType.RS_TREE_PINE, 28, 7);
        addScenery(SceneryType.RS_TREE_PINE, 29, 4);
        addScenery(SceneryType.RS_TREE_PINE, 29, 9);
        addScenery(SceneryType.RS_TREE_PINE, 30, 6);
        addScenery(SceneryType.RS_TREE_BRANCH, 22, 5);
        addScenery(SceneryType.RS_TREE_BRANCH, 22, 7);
        addScenery(SceneryType.RS_TREE_BRANCH, 23, 8);
        addScenery(SceneryType.RS_TREE_BRANCH, 25, 9);
        addScenery(SceneryType.RS_TREE_BRANCH, 26, 6);


        //random trees
        addScenery(SceneryType.RS_TREE_BASIC, 5, 7);

    }


    private void addRocks() {
        addScenery(SceneryType.RS_ROCK_HIGH, 8, 5);
        addScenery(SceneryType.RS_ROCK_HIGH, 22, 14);
        addScenery(SceneryType.RS_ROCK_HIGH, 27, 20);

        addScenery(SceneryType.RS_ROCK_LOW, 13, 22);
        addScenery(SceneryType.RS_ROCK_LOW, 32, 22);
        addScenery(SceneryType.RS_ROCK_LOW, 16, 24);

        addScenery(SceneryType.RS_ROCK_VANTAGE, 12, 20);

        addScenery(SceneryType.RS_CAMPFIRE, 6, 8);

        addScenery(SceneryType.RS_CASTLE, 19, 19, 4, 4);

    }


}
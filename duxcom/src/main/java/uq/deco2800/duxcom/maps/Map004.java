package uq.deco2800.duxcom.maps;

import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyBrute;
import uq.deco2800.duxcom.graphics.scenery.SceneryType;
import uq.deco2800.duxcom.sound.SoundPlayer;
import uq.deco2800.duxcom.tiles.TileType;

/**
 * Created by winstonf6 on 19/10/2016
 */

public class Map004 extends AbstractGameMap {
    private static final int MAP_HEIGHT = 36;
    private static final int MAP_WIDTH = 36;

    public Map004(String name, boolean beta) {
        super.name = name;
        super.mapType = MapType.MAP004;
        super.setSize(MAP_WIDTH, MAP_HEIGHT);

        //grass terrain
        super.initialiseEmptyCheckeredMap(TileType.DT_SAND_LIGHT_1,
                TileType.DT_SAND_LIGHT_3, MAP_WIDTH, MAP_HEIGHT);

        addCharacters();
        addWalls();
        addScenery();
        addObjectives();

        SoundPlayer.stopMusic();
        SoundPlayer.playGameMusic();
    }

    private void addCharacters() {

        super.spawnPoints = new int[][]{{29, 31}, {30, 30}, {31, 29}, {32, 28}};

        addSelectedHeroes();
        int[][] enemyLocations = {
                {7, 2},
                {14, 4},
                {21, 3},
                {33, 8},
                {28, 15},
                {28, 16},
                {28, 17},
                {28, 18},
                {28, 19},
                {33, 23},
                {24, 12},
                {24, 24},
                {17, 13},
                {17, 17},
                {17, 25},
                {15, 16},
                {12, 13},
                {11, 18},
                {4, 12},
                {4, 18},
                {2, 17},
                {6, 32},
                {17, 29},
                {22, 30},
                {23, 34}
        };

        for (int[] i : enemyLocations) {
            addEnemy(new EnemyBrute(i[0], i[1]));
        }
    }

    private void addObjectives() {
        addEnemyKillObjective(EnemyType.ENEMY_BRUTE, 8);
        addScoreObjective(20000);
        addMovementObjective(17, 17);
    }

    private void addWalls() {
        int[][] castleTowerLocations = {
                {8, 8},
                {8, 26},
                {26, 8},
                {26, 26},
                {26, 15},
                {26, 19}
        };

        for (int[] i : castleTowerLocations) {
            addScenery(SceneryType.WALL_STONE_TOWER, i[0], i[1]);

        }

        for (int i = 9; i <= 25; i++) {
            addScenery(SceneryType.WALL_STONE_RIGHT, 8, i);
            addScenery(SceneryType.WALL_STONE_LEFT, i, 8);
            addScenery(SceneryType.WALL_STONE_LEFT, i, 26);
        }

        for (int i = 9; i <= 14; i++) {
            addScenery(SceneryType.WALL_STONE_RIGHT, 26, i);
        }

        for (int i = 20; i <= 25; i++) {
            addScenery(SceneryType.WALL_STONE_RIGHT, 26, i);
        }
    }

    private void addScenery() {

        int[][] tombstoneLocations = {
                {10, 10},
                {10, 11},
                {10, 12},
                {10, 13},
                {10, 14},
                {10, 20},
                {10, 21},
                {10, 22},
                {10, 23},
                {10, 24},

                {13, 10},
                {13, 11},
                {13, 12},
                {13, 13},
                {13, 14},
                {13, 20},
                {13, 21},
                {13, 22},
                {13, 23},
                {13, 24},

                {16, 10},
                {16, 11},
                {16, 12},
                {16, 13},
                {16, 14},
                {16, 20},
                {16, 21},
                {16, 22},
                {16, 23},
                {16, 24},

                {19, 10},
                {19, 11},
                {19, 12},
                {19, 13},
                {19, 14},
                {19, 20},
                {19, 21},
                {19, 22},
                {19, 23},
                {19, 24},

                {22, 10},
                {22, 11},
                {22, 12},
                {22, 13},
                {22, 14},
                {22, 20},
                {22, 21},
                {22, 22},
                {22, 23},
                {22, 24},
        };

        for (int[] i : tombstoneLocations) {
            addScenery(SceneryType.TOMBSTONE, i[0], i[1]);
        }

    }
}
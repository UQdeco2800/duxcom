package uq.deco2800.duxcom.maps;

import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyKnight;
import uq.deco2800.duxcom.graphics.scenery.SceneryType;
import uq.deco2800.duxcom.sound.SoundPlayer;
import uq.deco2800.duxcom.tiles.TileType;

/**
 * Created by winstonf6 on 18/10/2016
 */

public class Map002 extends AbstractGameMap {
    private static final int MAP_HEIGHT = 36;
    private static final int MAP_WIDTH = 36;

    public Map002(String name, boolean beta) {
        super.name = name;
        super.mapType = MapType.MAP002;
        super.setSize(MAP_WIDTH, MAP_HEIGHT);
        super.initialiseEmptyCheckeredMap(TileType.DT_GRASS_LIGHT_3, TileType.DT_GRASS_LIGHT_4, MAP_WIDTH, MAP_HEIGHT);

        addCharacters();
        addRocks();
        addWater();
        addTrees();
        addCastle();
        addObjectives();

        SoundPlayer.stopMusic();
        SoundPlayer.playGameMusic();
    }

    private void addCharacters() {

        super.spawnPoints = new int[][]{{29, 31}, {30, 30}, {31, 29}, {32, 28}};

        addSelectedHeroes();
        //Enemies
        int[][] knightLocations = {
                {3, 11},
                {8, 13},
                {5, 15},
                {24, 15},
                {1, 18},
                {8, 18},
                {28, 18},
                {31, 18},
                {21, 21},
                {32, 22},
                {28, 23},
                {24, 25},
                {26, 25},
                {9, 26},
                {31, 28},
                {3, 32},
                {29, 31}
        };

        for (int[] knightLocation : knightLocations) {
            addEnemy(new EnemyKnight(knightLocation[0], knightLocation[1]));
        }
    }

    private void addRocks() {
        int[][] lowRockLocations = {
                {20, 3},
                {21, 1},
                {23, 1},
                {23, 7},
                {24, 4},
                {26, 1},
                {26, 6},
                {26, 12},
                {27, 5},
                {27, 9},
                {29, 1},
                {29, 5},
                {29, 10},
                {29, 11},
                {30, 5},
                {30, 9},
                {32, 11},
                {33, 1},
                {33, 6},
                {33, 9},
                {12, 0},
                {9, 5},
                {18, 5},
                {18, 8},
                {12, 11},
                {11, 13},
                {13, 13},
                {13, 15},
                {20, 15},
                {18, 18},
                {12, 22},
                {14, 23},
                {12, 28},
                {11, 29},
                {11, 30},
                {12, 26},
                {23, 29},
                {9, 32},
                {23, 32},
                {25, 33}
        };

        int[][] highRockLocations = {
                {21, 4},
                {22, 10},
                {25, 8},
                {29, 4},
                {30, 2},
                {30, 4},
                {31, 14},
                {33, 5},
                {11, 3},
                {10, 9},
                {24, 18},
                {20, 23},
                {21, 26},
                {11, 31},
                {8, 34}
        };

        for (int[] lowRockLocation : lowRockLocations) {
            addScenery(SceneryType.RS_ROCK_LOW, lowRockLocation[0], lowRockLocation[1]);
        }

        for (int[] highRockLocation : highRockLocations) {
            addScenery(SceneryType.RS_ROCK_HIGH, highRockLocation[0], highRockLocation[1]);
        }

    }

    private void addObjectives() {
        addScoreObjective(8000);
        addEnemyKillObjective(EnemyType.ENEMY_KNIGHT, 7);
        addMovementObjective(20, 20);
    }

    private void addWater() {
        for (int i = 0; i <= 35; i++) {
            for (int j = 15; j <= 16; j++) {
                setTile(j, i, TileType.DT_WATER_MID_2);
                setTile(j, i, TileType.DT_WATER_MID_2);
            }
        }

        int[][] waterLocations = {
                {17, 0},
                {18, 0},
                {17, 1},
                {18, 1},
                {14, 2},
                {17, 2},
                {18, 2},
                {13, 3},
                {14, 3},
                {17, 3},
                {13, 4},
                {14, 4},
                {13, 5},
                {14, 5},
                {11, 6},
                {12, 6},
                {13, 6},
                {14, 6},
                {11, 7},
                {12, 7},
                {13, 7},
                {14, 7},
                {11, 8},
                {12, 8},
                {13, 8},
                {14, 8},
                {12, 9},
                {13, 9},
                {14, 9},
                {13, 10},
                {14, 10},
                {17, 10},
                {17, 11},
                {17, 12},
                {18, 12},
                {17, 13},
                {18, 13},
                {19, 13},
                {17, 14},
                {18, 14},
                {17, 15},
                {14, 16},
                {14, 17},
                {17, 25},
                {18, 25},
                {17, 26},
                {18, 26},
                {19, 26},
                {17, 27},
                {18, 27},
                {19, 27},
                {20, 27},
                {17, 28},
                {18, 28},
                {19, 28},
                {20, 28},
                {14, 29},
                {17, 29},
                {18, 29},
                {19, 29},
                {20, 29},
                {21, 29},
                {13, 30},
                {14, 30},
                {17, 30},
                {18, 30},
                {19, 30},
                {20, 30},
                {21, 30},
                {13, 31},
                {14, 31},
                {17, 31},
                {18, 31},
                {19, 31},
                {20, 31},
                {21, 31},
                {12, 32},
                {13, 32},
                {14, 32},
                {17, 32},
                {18, 32},
                {19, 32},
                {20, 32},
                {21, 32},
                {11, 33},
                {12, 33},
                {13, 33},
                {14, 33},
                {17, 33},
                {18, 33},
                {19, 33},
                {20, 33},
                {21, 33},
                {10, 34},
                {11, 34},
                {12, 34},
                {13, 34},
                {14, 34},
                {17, 34},
                {18, 34},
                {19, 34},
                {20, 34},
                {21, 34},
                {10, 35},
                {11, 35},
                {12, 35},
                {13, 35},
                {14, 35},
                {17, 35},
                {18, 35},
                {19, 35},
                {20, 35},
                {21, 35},
        };

        for (int[] waterLocation : waterLocations) {
            setTile(waterLocation[0], waterLocation[1], TileType.DT_WATER_MID_2);
        }

        addStackableScenery(SceneryType.RS_BRIDGE, 19, 21, 8, 2);
    }

    private void addGrass() {
        for (int i = 3; i <= 31; i++) {
            for (int j = 3; j <= 31; j++) {
                setTile(j, i, TileType.DT_GRASS_DARK_1);
            }
        }
    }

    private void addTrees() {
        int[][] pineLocations = {
                {5, 0},
                {9, 0},
                {2, 1},
                {6, 2},
                {5, 3},
                {7, 4},
                {4, 5},
                {2, 7},
                {4, 7},
                {0, 9},
                {8, 9},
                {5, 11},
                {1, 12}
        };

        int[][] branchLocations = {
                {0, 1},
                {0, 6},
                {2, 9}
        };

        int[][] basicLocations = {
                {1, 4},
                {7, 7},
                {5, 7}
        };

        for (int[] pineLocation : pineLocations) {
            addScenery(SceneryType.RS_TREE_PINE, pineLocation[0], pineLocation[1]);
        }

        for (int[] branchLocation : branchLocations) {
            addScenery(SceneryType.RS_TREE_BRANCH, branchLocation[0], branchLocation[1]);
        }

        for (int[] basicLocation : basicLocations) {
            addScenery(SceneryType.RS_TREE_BASIC, basicLocation[0], basicLocation[1]);
        }

    }

    private void addCastle() {
        addScenery(SceneryType.RS_CASTLE, 5, 27, 4, 4);
    }


}
package uq.deco2800.duxcom.maps;

import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyArcher;
import uq.deco2800.duxcom.graphics.scenery.SceneryType;
import uq.deco2800.duxcom.sound.SoundPlayer;
import uq.deco2800.duxcom.tiles.TileType;


/**
 * Created by winstonf6 on 21/10/2016
 * Designed by nicquek.
 */

public class Map006 extends AbstractGameMap {
    private static final int MAP_HEIGHT = 36;
    private static final int MAP_WIDTH = 36;

    public Map006(String name, boolean beta) {
        super.name = name;
        super.mapType = MapType.MAP006;
        super.setSize(MAP_WIDTH, MAP_HEIGHT);
        super.initialiseEmptyCheckeredMap(TileType.DT_GRASS_MID_3,
                TileType.DT_GRASS_MID_5, MAP_WIDTH, MAP_HEIGHT);

        addCharacters();
        addSandPath();
        addMudPath();
        addWater();
        addBridges();
        addHuts();
        addWood();
        addObjectives();

        SoundPlayer.stopMusic();
        SoundPlayer.playGameMusic();
    }

    private void addObjectives() {
        addEnemyKillObjective(EnemyType.ENEMY_ARCHER, 7);
        addMovementObjective(6, 6);
        addMovementObjective(14, 6);
        addMovementObjective(14, 14);
        addMovementObjective(7, 25);
        addMovementObjective(13, 28);

    }

    private void addCharacters() {

        super.spawnPoints = new int[][]{{2, 15}, {2, 16}, {2, 17}, {2, 18}};

        addSelectedHeroes();
        addEnemy(new EnemyArcher(30, 0));
        addEnemy(new EnemyArcher(17, 1));
        addEnemy(new EnemyArcher(19, 2));
        addEnemy(new EnemyArcher(29, 2));
        addEnemy(new EnemyArcher(32, 2));
        addEnemy(new EnemyArcher(34, 12));
        addEnemy(new EnemyArcher(34, 13));
        addEnemy(new EnemyArcher(21, 31));
        addEnemy(new EnemyArcher(26, 23));
        addEnemy(new EnemyArcher(25, 33));
        addEnemy(new EnemyArcher(26, 34));
        addEnemy(new EnemyArcher(32, 27));
        addEnemy(new EnemyArcher(33, 25));
    }

    private void addSandPath() {
        int[][] sandPathLocations = {
                {0, 5},
                {0, 6},
                {0, 7},
                {0, 8},
                {0, 9},

                {1, 3},
                {1, 4},
                {1, 5},
                {1, 6},

                {2, 3},
                {2, 4},

                {3, 3},
                {3, 4},

                {4, 0},
                {4, 1},
                {4, 2},
                {4, 3},
                {4, 4},
                {4, 12},
                {4, 13},
                {4, 14},
                {4, 15},
                {4, 16},
                {4, 17},
                {4, 18},
                {4, 19},
                {4, 20},
                {4, 31},
                {4, 32},

                {5, 0},
                {5, 12},
                {5, 13},
                {5, 14},
                {5, 15},
                {5, 16},
                {5, 17},
                {5, 18},
                {5, 19},
                {5, 20},
                {5, 31},
                {5, 32},

                {6, 0},
                {6, 12},
                {6, 13},
                {6, 14},
                {6, 15},
                {6, 16},
                {6, 17},
                {6, 18},
                {6, 19},
                {6, 20},
                {6, 31},
                {6, 32},
                {6, 33},
                {6, 34},
                {6, 35},

                {7, 0},
                {7, 12},
                {7, 13},
                {7, 14},
                {7, 15},
                {7, 16},
                {7, 17},
                {7, 18},
                {7, 19},
                {7, 20},
                {7, 31},
                {7, 32},

                {8, 0},
                {8, 12},
                {8, 13},
                {8, 14},
                {8, 15},
                {8, 16},
                {8, 17},
                {8, 18},
                {8, 19},
                {8, 20},
                {8, 31},
                {8, 32},

                {9, 0},
                {9, 12},
                {9, 13},
                {9, 14},
                {9, 15},
                {9, 16},
                {9, 17},
                {9, 18},
                {9, 19},
                {9, 20},

                {10, 0},
                {11, 0},
                {12, 0},
                {13, 0},
                {14, 0},
                {15, 0},

                {16, 0},
                {16, 7},
                {16, 8},
                {16, 9},
                {16, 10},
                {16, 11},
                {16, 12},
                {16, 13},
                {16, 28},
                {16, 29},

                {17, 0},
                {17, 7},
                {17, 8},
                {17, 9},
                {17, 10},
                {17, 11},
                {17, 12},
                {17, 13},
                {17, 28},
                {17, 29},

                {18, 7},
                {18, 8},
                {18, 28},
                {18, 29},

                {19, 7},
                {19, 8},
                {19, 28},
                {19, 29},

                {20, 7},
                {20, 8},
                {20, 28},
                {20, 29},

                {21, 7},
                {21, 8},

                {22, 7},
                {22, 8},

                {23, 7},
                {23, 8},

                {24, 0},
                {24, 1},
                {24, 2},
                {24, 4},
                {24, 5},
                {24, 6},
                {24, 7},
                {24, 8},
                {24, 9},
                {24, 20},

                {25, 0},
                {25, 1},
                {25, 2},
                {25, 4},
                {25, 5},
                {25, 6},
                {25, 7},
                {25, 8},
                {25, 9},
                {25, 20},
                {25, 21},

                {26, 8},
                {26, 20},
                {26, 21},

                {27, 8},
                {27, 20},
                {27, 21},

                {32, 15},
                {32, 16},
                {32, 17},
                {32, 18},
                {32, 19},
                {32, 20},
                {32, 21},
                {32, 22},
                {32, 23},
                {32, 24},

                {33, 15},
                {34, 15},
                {35, 15}
        };

        for (int[] i : sandPathLocations) {
            setTile(i[0], i[1], TileType.DT_SAND_DARK_2);
        }
    }

    private void addMudPath() {
        int[][] mudPathLocations = {
                {0, 23},
                {0, 24},
                {0, 25},

                {1, 21},
                {1, 22},
                {1, 23},
                {1, 24},

                {2, 21},

                {3, 6},
                {3, 7},
                {3, 8},
                {3, 9},
                {3, 10},
                {3, 11},
                {3, 12},
                {3, 13},
                {3, 14},
                {3, 15},
                {3, 16},
                {3, 17},
                {3, 18},
                {3, 19},
                {3, 20},
                {3, 21},
                {3, 22},
                {3, 23},

                {4, 6},
                {4, 7},
                {4, 21},
                {4, 22},
                {4, 23},

                {5, 6},
                {5, 7},
                {5, 23},

                {6, 6},
                {6, 7},
                {6, 23},

                {7, 6},
                {7, 7},
                {7, 23},
                {7, 24},
                {7, 25},
                {7, 26},

                {8, 6},
                {8, 7},
                {8, 26},

                {9, 6},
                {9, 7},
                {9, 26},

                {10, 6},
                {10, 7},
                {10, 8},
                {10, 26},

                {11, 7},
                {11, 8},
                {11, 26},

                {12, 7},
                {12, 8},
                {12, 9},
                {12, 10},
                {12, 11},
                {12, 12},
                {12, 21},
                {12, 22},
                {12, 29},
                {12, 30},

                {13, 7},
                {13, 11},
                {13, 12},
                {13, 21},
                {13, 22},
                {13, 23},
                {13, 24},
                {13, 25},
                {13, 26},
                {13, 27},
                {13, 28},
                {13, 29},
                {13, 30},

                {14, 7},
                {14, 11},
                {14, 12},
                {14, 23},
                {14, 29},
                {14, 30},
                {14, 31},
                {14, 32},
                {14, 33},
                {14, 34},
                {14, 35},

                {15, 4},
                {15, 5},
                {15, 6},
                {15, 7},
                {15, 11},
                {15, 12},
                {15, 13},
                {15, 14},
                {15, 15},
                {15, 23},
                {15, 30},

                {16, 3},
                {16, 4},
                {16, 14},
                {16, 23},
                {16, 30},

                {17, 3},
                {17, 4},
                {17, 14},
                {17, 23},
                {17, 30},

                {18, 3},
                {18, 4},
                {18, 14},
                {18, 23},
                {18, 30},
                {18, 31},
                {18, 32},
                {18, 33},
                {18, 34},
                {18, 35},

                {19, 3},
                {19, 14},
                {19, 23},
                {19, 30},

                {20, 3},
                {20, 14},
                {20, 21},
                {20, 22},
                {20, 23},
                {20, 30},

                {21, 3},
                {21, 14},
                {21, 21},
                {21, 22},
                {21, 23},

                {22, 3},
                {22, 14},
                {22, 21},

                {23, 3},
                {23, 14},
                {23, 21},

                {24, 3},
                {24, 14},
                {24, 21},

                {25, 3},
                {25, 14},

                {26, 3},
                {26, 14},
                {26, 15},
                {26, 16},
                {26, 17},
                {26, 18},

                {27, 3},

                {28, 3},

                {29, 3},
                {29, 4},
                {29, 5},

                {30, 4},
                {31, 4},
                {32, 4},
                {33, 4},
                {34, 4}

        };

        for (int[] i : mudPathLocations) {
            setTile(i[0], i[1], TileType.RS_MUD2);
        }
    }

    private void addWater() {
        for (int i = 7; i <= 35; i++) {
            setTile(28, i, TileType.DT_WATER_LIGHT_2);
            setTile(29, i, TileType.DT_WATER_LIGHT_2);
            setTile(30, i, TileType.DT_WATER_LIGHT_2);
        }

        for (int i = 5; i <= 35; i++) {
            setTile(31, i, TileType.DT_WATER_LIGHT_2);
        }

        for (int i = 5; i <= 9; i++) {
            setTile(32, i, TileType.DT_WATER_LIGHT_2);
            setTile(33, i, TileType.DT_WATER_LIGHT_2);
        }

        for (int i = 5; i <= 6; i++) {
            setTile(34, i, TileType.DT_WATER_LIGHT_2);
            setTile(35, i, TileType.DT_WATER_LIGHT_2);
        }
    }

    private void addBridges() {
        addStackableScenery(SceneryType.RS_BRIDGE, 33, 21, 8, 2);
        addStackableScenery(SceneryType.RS_BRIDGE, 33, 34, 8, 2);
    }

    private void addHuts() {
        addScenery(SceneryType.SMALL_HUT, 5, 6);
        addScenery(SceneryType.SMALL_HUT, 6, 25);
        addScenery(SceneryType.SMALL_HUT, 13, 14);
        addScenery(SceneryType.SMALL_HUT, 13, 6);
        addScenery(SceneryType.SMALL_HUT, 12, 28);
        addScenery(SceneryType.SMALL_HUT, 17, 23);
    }

    private void addWood() {
        addScenery(SceneryType.RS_CAMPFIRE, 7, 16);

        int[][] woodLocations = {
                {1, 7},
                {1, 8},
                {1, 9},
                {1, 10},
                {1, 26},
                {1, 27},
                {1, 28},
                {1, 29},
                {1, 30},
                {1, 31},
                {1, 32},

                {2, 7},
                {2, 8},
                {2, 9},
                {2, 10},
                {2, 26},
                {2, 27},
                {2, 28},

                {5, 28},
                {5, 29},

                {6, 2},
                {6, 3},

                {7, 2},
                {7, 3},


                {10, 1},
                {11, 1},

                {17, 24},
                {17, 25},
                {17, 26},
                {18, 24},
                {18, 25},
                {18, 26},
                {19, 24},
                {19, 25},
                {19, 26},

                {18, 11},
                {18, 12},
                {18, 13},
                {19, 11},
                {19, 12},
                {19, 13},
                {20, 11},
                {20, 12},
                {20, 13},

                {19, 4},
                {19, 5},
                {20, 4},
                {20, 5},

                {24, 31},
                {24, 32},
                {25, 31},
                {25, 32}

        };

        for (int[] i : woodLocations) {
            int x = (int) (100 * Math.random());

            if (x <= 25) {
                addScenery(SceneryType.RS_TREE_BASIC, i[0], i[1]);

            } else if (x > 25 && x <= 50) {
                addScenery(SceneryType.RS_TREE_PINE, i[0], i[1]);
            } else if (x > 50 && x <= 75) {
                addScenery(SceneryType.RS_TREE_BRANCH, i[0], i[1]);
            } else {
                addScenery(SceneryType.RS_BUSH, i[0], i[1]);
            }
        }
    }
}
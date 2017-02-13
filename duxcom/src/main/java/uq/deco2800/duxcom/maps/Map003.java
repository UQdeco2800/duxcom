package uq.deco2800.duxcom.maps;

import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyArcher;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyKnight;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyRogue;
import uq.deco2800.duxcom.graphics.scenery.SceneryType;
import uq.deco2800.duxcom.sound.SoundPlayer;
import uq.deco2800.duxcom.tiles.LavaTile;
import uq.deco2800.duxcom.tiles.TileType;

/**
 * Created by winstonf6 on 19/10/2016
 */

public class Map003 extends AbstractGameMap {
    private static final int MAP_HEIGHT = 36;
    private static final int MAP_WIDTH = 36;

    public Map003(String name, boolean beta) {
        super.name = name;
        super.mapType = MapType.MAP003;
        super.setSize(MAP_WIDTH, MAP_HEIGHT);

        //grass terrain
        super.initialiseEmptyCheckeredMap(TileType.DT_GRASS_MID_3,
                TileType.DT_GRASS_MID_4, MAP_WIDTH, MAP_HEIGHT);

        addCharacters();
        addWater();
        addSand();
        addTrees();
        addBushes();
        addRocks();
        addLava();
        addScenery();
        addObjectives();

        SoundPlayer.stopMusic();
        SoundPlayer.playGameMusic();
    }

    private void addCharacters() {

        super.spawnPoints = new int[][]{{29, 31}, {30, 30}, {31, 29}, {32, 28}};

        addSelectedHeroes();

        addEnemy(new EnemyArcher(22, 3));
        addEnemy(new EnemyKnight(22, 4));
        addEnemy(new EnemyRogue(11, 4));
        addEnemy(new EnemyKnight(23, 7));
        addEnemy(new EnemyArcher(13, 8));
        addEnemy(new EnemyKnight(27, 11));
        addEnemy(new EnemyKnight(5, 12));
        addEnemy(new EnemyArcher(5, 12));
        addEnemy(new EnemyArcher(10, 13));
        addEnemy(new EnemyRogue(1, 14));
        addEnemy(new EnemyKnight(27, 14));
        addEnemy(new EnemyKnight(15, 17));
        addEnemy(new EnemyRogue(4, 20));
        addEnemy(new EnemyKnight(17, 20));
        addEnemy(new EnemyKnight(29, 20));
        addEnemy(new EnemyArcher(1, 22));
        addEnemy(new EnemyKnight(21, 22));
        addEnemy(new EnemyRogue(25, 26));
        addEnemy(new EnemyKnight(28, 26));
        addEnemy(new EnemyRogue(2, 28));
        addEnemy(new EnemyArcher(22, 28));
        addEnemy(new EnemyKnight(9, 33));
    }

    private void addObjectives() {
        addEnemyHitObjective(EnemyType.ENEMY_ROGUE);
        addEnemyKillObjective(EnemyType.ENEMY_ARCHER, 4);
        addMovementObjective(24, 24);
        addScoreObjective(10000);
    }

    private void addWater() {
        int[][] waterLocations = {
                {35, 0},
                {35, 1},
                {35, 2},
                {35, 3},
                {35, 4},
                {35, 5},
                {35, 6},
                {35, 7},
                {35, 8},
                {35, 9},
                {35, 10},
                {35, 11},
                {35, 12},
                {35, 13},
                {35, 14},
                {35, 15},
                {35, 16},
                {35, 17},
                {35, 18},
                {35, 19},
                {35, 20},
                {35, 21},
                {35, 22},
                {34, 0},
                {34, 1},
                {34, 2},
                {34, 3},
                {34, 4},
                {34, 5},
                {34, 6},
                {34, 7},
                {34, 8},
                {34, 9},
                {34, 10},
                {34, 11},
                {34, 12},
                {34, 13},
                {34, 14},
                {34, 15},
                {34, 16},
                {34, 17},
                {34, 18},
                {34, 19},
                {34, 20},
                {34, 21},
                {33, 0},
                {33, 1},
                {33, 2},
                {33, 3},
                {33, 4},
                {33, 5},
                {33, 6},
                {33, 7},
                {33, 8},
                {33, 9},
                {33, 10},
                {33, 11},
                {33, 12},
                {33, 13},
                {33, 14},
                {33, 15},
                {33, 16},
                {33, 17},
                {33, 18},
                {33, 19},
                {33, 20},
                {32, 0},
                {32, 1},
                {32, 2},
                {32, 3},
                {32, 4},
                {32, 5},
                {32, 6},
                {32, 7},
                {32, 8},
                {32, 9},
                {32, 10},
                {32, 11},
                {32, 12},
                {32, 13},
                {32, 14},
                {32, 15},
                {32, 16},
                {32, 17},
                {31, 0},
                {31, 1},
                {31, 2},
                {31, 3},
                {31, 4},
                {31, 5},
                {31, 6},
                {31, 7},
                {31, 8},
                {31, 9},
                {31, 10},
                {31, 11},
                {31, 12},
                {31, 13},
                {31, 14},
                {31, 15},
                {31, 16},
                {30, 0},
                {30, 1},
                {30, 2},
                {30, 3},
                {30, 4},
                {30, 5},
                {30, 6},
                {30, 7},
                {30, 8},
                {30, 9},
                {30, 10},
                {30, 13},
                {30, 14},
                {30, 15},
                {29, 0},
                {29, 1},
                {29, 2},
                {29, 3},
                {29, 4},
                {29, 5},
                {29, 6},
                {29, 7},
                {29, 8},
                {29, 9},
                {29, 10},
                {29, 14},
                {28, 0},
                {28, 1},
                {28, 2},
                {28, 3},
                {28, 4},
                {28, 5},
                {28, 6},
                {28, 7},
                {27, 0},
                {27, 1},
                {27, 2},
                {27, 3},
                {27, 4},
                {26, 0},
                {26, 1},
                {26, 2},
                {25, 0},
                {25, 1},
                {24, 0},
                {24, 1}
        };

        for (int[] waterLocation : waterLocations) {
            setTile(waterLocation[0], waterLocation[1], TileType.DT_WATER_DARK_2);
        }
    }

    private void addSand() {
        int[][] sandLocations = {
                {0, 21},
                {0, 22},
                {0, 23},
                {0, 24},
                {0, 25},
                {0, 26},
                {0, 27},
                {0, 28},
                {0, 29},
                {1, 21},
                {1, 22},
                {1, 23},
                {1, 24},
                {1, 25},
                {1, 26},
                {1, 27},
                {1, 28},
                {1, 29},
                {1, 30},
                {2, 19},
                {2, 20},
                {2, 21},
                {2, 22},
                {2, 23},
                {2, 24},
                {2, 25},
                {2, 26},
                {2, 27},
                {2, 28},
                {2, 29},
                {2, 30},
                {2, 31},
                {3, 19},
                {3, 20},
                {3, 21},
                {3, 22},
                {3, 23},
                {3, 24},
                {3, 25},
                {3, 26},
                {3, 27},
                {3, 28},
                {3, 29},
                {3, 30},
                {3, 31},
                {3, 32},
                {3, 33},
                {4, 16},
                {4, 17},
                {4, 18},
                {4, 19},
                {4, 20},
                {4, 21},
                {4, 22},
                {4, 23},
                {4, 24},
                {4, 25},
                {4, 26},
                {4, 27},
                {4, 28},
                {4, 29},
                {4, 30},
                {4, 31},
                {4, 32},
                {4, 33},
                {5, 17},
                {5, 18},
                {5, 19},
                {5, 20},
                {5, 21},
                {5, 22},
                {5, 23},
                {5, 24},
                {5, 25},
                {5, 26},
                {5, 27},
                {5, 28},
                {5, 29},
                {5, 30},
                {5, 31},
                {5, 32},
                {5, 33},
                {6, 17},
                {6, 18},
                {6, 19},
                {6, 20},
                {6, 21},
                {6, 22},
                {6, 23},
                {6, 24},
                {6, 25},
                {6, 26},
                {6, 27},
                {6, 28},
                {6, 29},
                {6, 30},
                {6, 31},
                {6, 32},
                {6, 33},
                {7, 17},
                {7, 18},
                {7, 19},
                {7, 20},
                {7, 21},
                {7, 22},
                {7, 23},
                {7, 24},
                {7, 25},
                {7, 26},
                {7, 27},
                {7, 28},
                {7, 29},
                {7, 30},
                {7, 31},
                {7, 32},
                {7, 33},
                {7, 34},
                {7, 35},
                {8, 17},
                {8, 18},
                {8, 19},
                {8, 20},
                {8, 21},
                {8, 22},
                {8, 23},
                {8, 24},
                {8, 25},
                {8, 26},
                {8, 27},
                {8, 28},
                {8, 29},
                {8, 30},
                {8, 31},
                {8, 32},
                {8, 33},
                {8, 34},
                {8, 35},
                {9, 17},
                {9, 18},
                {9, 19},
                {9, 20},
                {9, 21},
                {9, 22},
                {9, 23},
                {9, 24},
                {9, 25},
                {9, 26},
                {9, 27},
                {9, 28},
                {9, 29},
                {9, 30},
                {9, 31},
                {9, 32},
                {9, 33},
                {9, 34},
                {9, 35},
                {10, 17},
                {10, 18},
                {10, 19},
                {10, 20},
                {10, 21},
                {10, 22},
                {10, 23},
                {10, 24},
                {10, 25},
                {10, 26},
                {10, 27},
                {10, 28},
                {10, 29},
                {10, 30},
                {10, 31},
                {10, 32},
                {10, 33},
                {10, 34},
                {10, 35},
                {11, 17},
                {11, 18},
                {11, 19},
                {11, 20},
                {11, 21},
                {11, 22},
                {11, 23},
                {11, 24},
                {11, 25},
                {11, 26},
                {11, 27},
                {11, 28},
                {11, 29},
                {11, 30},
                {11, 31},
                {11, 32},
                {11, 33},
                {11, 34},
                {11, 35},
                {12, 17},
                {12, 18},
                {12, 19},
                {12, 20},
                {12, 21},
                {12, 22},
                {12, 23},
                {12, 24},
                {12, 25},
                {12, 26},
                {12, 27},
                {12, 28},
                {12, 29},
                {12, 30},
                {12, 31},
                {12, 32},
                {12, 33},
                {12, 34},
                {12, 35},
                {13, 17},
                {13, 18},
                {13, 19},
                {13, 20},
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
                {13, 31},
                {13, 32},
                {13, 33},
                {13, 34},
                {13, 35},
                {14, 17},
                {14, 18},
                {14, 19},
                {14, 20},
                {14, 21},
                {14, 22},
                {14, 23},
                {14, 24},
                {14, 25},
                {14, 26},
                {14, 27},
                {14, 28},
                {14, 29},
                {14, 30},
                {14, 31},
                {14, 32},
                {14, 33},
                {14, 34},
                {14, 35},
                {15, 17},
                {15, 18},
                {15, 19},
                {15, 20},
                {15, 21},
                {15, 22},
                {15, 23},
                {15, 24},
                {15, 25},
                {15, 26},
                {15, 27},
                {15, 28},
                {15, 29},
                {15, 30},
                {15, 31},
                {15, 32},
                {15, 33},
                {15, 34},
                {15, 35},
                {16, 17},
                {16, 18},
                {16, 19},
                {16, 20},
                {16, 21},
                {16, 22},
                {16, 23},
                {16, 24},
                {16, 25},
                {16, 26},
                {16, 27},
                {16, 28},
                {16, 29},
                {16, 30},
                {16, 31},
                {16, 32},
                {16, 33},
                {16, 34},
                {16, 35},
                {12, 16},
                {17, 18},
                {17, 19},
                {17, 20},
                {17, 24},
                {17, 25},
                {17, 26},
                {17, 27},
                {17, 28},
                {17, 29},
                {17, 30},
                {17, 31},
                {17, 32},
                {17, 33},
                {17, 34},
                {17, 35},
                {18, 24},
                {18, 25},
                {18, 26},
                {18, 27},
                {18, 28},
                {18, 29},
                {18, 30},
                {18, 31},
                {18, 32},
                {18, 33},
                {18, 34},
                {19, 24},
                {19, 25},
                {19, 26},
                {19, 27},
                {19, 29},
                {19, 30},
                {19, 31},
                {19, 32},
                {19, 33},
                {20, 26}
        };

        for (int[] sandLocation : sandLocations) {
            setTile(sandLocation[0], sandLocation[1], TileType.DT_SAND_DARK_2);
        }

    }

    private void addTrees() {
        addScenery(SceneryType.RS_TREE_PINE, 31, 21);
        addScenery(SceneryType.RS_TREE_PINE, 33, 23);
        addScenery(SceneryType.RS_TREE_BRANCH, 31, 25);
        addScenery(SceneryType.RS_TREE_PINE, 33, 25);
        addScenery(SceneryType.RS_TREE_PINE, 32, 28);
        addScenery(SceneryType.RS_TREE_PINE, 29, 30);
        addScenery(SceneryType.RS_TREE_BASIC, 33, 31);
        addScenery(SceneryType.RS_TREE_PINE, 29, 30);
        addScenery(SceneryType.RS_TREE_PINE, 33, 31);
        addScenery(SceneryType.RS_TREE_BRANCH, 30, 32);
        addScenery(SceneryType.RS_TREE_PINE, 29, 33);
        addScenery(SceneryType.RS_TREE_PINE, 31, 33);
        addScenery(SceneryType.RS_TREE_BASIC, 22, 33);
        addScenery(SceneryType.RS_TREE_PINE, 25, 32);
        addScenery(SceneryType.RS_TREE_BRANCH, 23, 31);
        addScenery(SceneryType.RS_TREE_BASIC, 26, 29);


    }

    private void addBushes() {
        addScenery(SceneryType.RS_BUSH, 27, 23);
        addScenery(SceneryType.RS_BUSH, 29, 24);
        addScenery(SceneryType.RS_BUSH, 27, 27);
        addScenery(SceneryType.RS_BUSH, 27, 33);
    }

    private void addLava() {
        int[][] lavaLocations = {
                {3, 24},
                {3, 25},
                {4, 23},
                {4, 24},
                {4, 25},
                {4, 26},
                {5, 23},
                {5, 24},
                {5, 25},
                {5, 26},
                {6, 22},
                {6, 23},
                {6, 24},
                {6, 25},
                {6, 26},
                {6, 27},
                {7, 22},
                {7, 23},
                {7, 24},
                {7, 25},
                {7, 26},
                {7, 27},
                {8, 21},
                {8, 22},
                {8, 23},
                {8, 24},
                {8, 25},
                {8, 26},
                {8, 27},
                {8, 28},
                {8, 29},
                {9, 20},
                {9, 21},
                {9, 22},
                {9, 23},
                {9, 24},
                {9, 25},
                {9, 26},
                {9, 27},
                {9, 28},
                {9, 29},
                {9, 30},
                {9, 31},
                {10, 20},
                {10, 21},
                {10, 22},
                {10, 23},
                {10, 24},
                {10, 25},
                {10, 26},
                {10, 27},
                {10, 28},
                {10, 29},
                {10, 30},
                {10, 31},
                {11, 19},
                {11, 20},
                {11, 21},
                {11, 22},
                {11, 23},
                {11, 24},
                {11, 25},
                {11, 26},
                {11, 27},
                {11, 28},
                {11, 29},
                {11, 30},
                {11, 31},
                {12, 19},
                {12, 20},
                {12, 21},
                {12, 22},
                {12, 23},
                {12, 24},
                {12, 25},
                {12, 26},
                {12, 27},
                {12, 28},
                {12, 29},
                {12, 30},
                {12, 31},
                {13, 20},
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
                {14, 19},
                {14, 20},
                {14, 21},
                {14, 22},
                {14, 23},
                {14, 24},
                {14, 25},
                {14, 26},
                {14, 27},
                {14, 28},
                {14, 29},
                {15, 19},
                {15, 20},
                {15, 21},
                {15, 22},
                {15, 23},
                {15, 25},
                {15, 26},
                {15, 27},
                {15, 28},
                {16, 25},
                {16, 26},
                {16, 27},
                {17, 25},
                {17, 26},
                {17, 27},
                {18, 26}
        };

        for (int[] lavaLocation : lavaLocations) {
            addLiveTile(new LavaTile(lavaLocation[0], lavaLocation[1]));
        }
    }

    private void addRocks() {
        int[][] lowRockLocations = {
                {18, 31},
                {3, 30},
                {4, 30},
                {6, 33},
                {17, 29},
                {18, 29},
                {12, 32},
                {18, 32}
        };

        int[][] highRockLocations = {
                {6, 18},
                {8, 18},
                {4, 29},
                {6, 29},
                {5, 32},
                {13, 32},
                {15, 31},
                {18, 31}
        };

        for (int[] lowRockLocation : lowRockLocations) {
            addScenery(SceneryType.RS_ROCK_LOW, lowRockLocation[0], lowRockLocation[1]);
        }

        for (int[] highRockLocation : highRockLocations) {
            addScenery(SceneryType.RS_ROCK_HIGH, highRockLocation[0], highRockLocation[1]);
        }
    }

    private void addScenery() {
        addScenery(SceneryType.RS_CASTLE, 19, 4);
        addScenery(SceneryType.RS_CASTLE, 24, 14);
    }

}
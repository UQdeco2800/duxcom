package uq.deco2800.duxcom.maps;


import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyArcher;
import uq.deco2800.duxcom.graphics.scenery.SceneryType;
import uq.deco2800.duxcom.sound.SoundPlayer;
import uq.deco2800.duxcom.tiles.TileType;


/**
 * Created by winstonf6 on 21/10/2016
 * Designed by nicquek.
 */

public class Map007 extends AbstractGameMap {
    private static final int MAP_HEIGHT = 36;
    private static final int MAP_WIDTH = 36;

    public Map007(String name, boolean beta) {
        super.name = name;
        super.mapType = MapType.MAP007;
        super.setSize(MAP_WIDTH, MAP_HEIGHT);
        super.initialiseEmptyCheckeredMap(TileType.DT_SAND_MID_3,
                TileType.DT_SAND_MID_5, MAP_WIDTH, MAP_HEIGHT);

        addCharacters();
        addEnemyLand();
        addEnemyCastles();
        addTrees();
        addObjectives();
        SoundPlayer.stopMusic();
        SoundPlayer.playGameMusic();
    }

    private void addObjectives() {
        addScoreObjective(40000);
        addMovementObjective(4, 17);
    }

    private void addCharacters() {
        super.spawnPoints = new int[][]{{32, 32}, {33, 31}, {33, 33}, {32, 33}};

        addSelectedHeroes();
        int[][] enemyLocations = {
                {2, 7},
                {2, 19},
                {4, 0},
                {4, 6},
                {5, 4},
                {5, 18},
                {7, 21},
                {13, 5},
                {13, 9},
                {14, 22},
                {15, 7},
                {16, 19},
                {18, 22},
                {18, 27},
                {18, 29},
                {19, 5},
                {21, 9},
                {29, 9},
                {31, 3},
                {31, 8},
                {34, 4}
        };

        for (int[] i : enemyLocations) {
            addEnemy(new EnemyArcher(i[0], i[1]));
        }
    }

    private void addEnemyLand() {
        int[][] floorLocations = {
                {0, 8, 0, 6},
                {15, 22, 0, 8},
                {3, 9, 0, 8},
                {3, 9, 11, 21},
                {18, 31, 14, 19},
                {1, 10, 27, 35}
        };

        for (int[] floorLocation : floorLocations) {
            for (int i = floorLocation[0]; i <= floorLocation[1]; i++) {
                for (int j = floorLocation[2]; j <= floorLocation[3]; j++) {
                    setTile(j, i, TileType.RS_FLOOR2);
                }
            }
        }

    }

    private void addEnemyCastles() {
        int[][] wallStoneTowerLocations = {
                {1, 1},
                {1, 4},

                {2, 17},
                {2, 18},
                {2, 22},

                {3, 1},
                {3, 4},

                {4, 22},

                {5, 8},

                {7, 16},

                {11, 3},
                {11, 5},
                {11, 7},
                {11, 9},

                {12, 3},
                {12, 5},
                {12, 7},
                {12, 9},

                {14, 19},

                {20, 3},
                {21, 5},

                {27, 7},
                {30, 7},

                {28, 10},
                {31, 10},

                {34, 2},
                {34, 3},

        };

        int[][] wallStoneRightLocations = {
                {1, 2},
                {1, 3},

                {14, 20},
                {14, 21},

                {17, 19},
                {17, 20},
                {17, 21},
                {17, 22},
                {17, 23},
                {17, 24},
                {17, 25},

                {27, 6},
                {30, 6},

        };

        int[][] wallStoneLeftLocations = {
                {2, 1},
                {2, 4},

                {3, 22},

                {15, 19},
                {16, 19},

                {28, 7},
                {29, 7},
        };


        for (int[] i : wallStoneTowerLocations) {
            addScenery(SceneryType.WALL_STONE_TOWER, i[0], i[1]);
        }

        for (int[] i : wallStoneRightLocations) {
            addScenery(SceneryType.WALL_STONE_RIGHT, i[0], i[1]);
        }

        for (int[] i : wallStoneLeftLocations) {
            addScenery(SceneryType.WALL_STONE_LEFT, i[0], i[1]);
        }

        addScenery(SceneryType.RS_CASTLE, 3, 18, 4, 4);
        addScenery(SceneryType.RS_CASTLE, 17, 30, 4, 4);
        addScenery(SceneryType.RS_CASTLE, 30, 5, 4, 4);
    }

    private void addTrees() {
        int[][] treeLocations = {
                {2, 27},
                {2, 28},
                {5, 12},
                {6, 12},
                {7, 26},
                {7, 34},
                {12, 0},
                {12, 1},
                {13, 0},
                {13, 1},
                {13, 34},
                {14, 0},
                {14, 1},
                {14, 12},
                {14, 13},
                {21, 30},
                {22, 1},
                {22, 12},
                {22, 13},
                {22, 14},
                {22, 15},
                {27, 12},
                {27, 13},
                {27, 14},
                {27, 25},
                {27, 26},
                {29, 19},
                {31, 27},
                {34, 12}
        };

        for (int[] i : treeLocations) {
            int x = (int) (100 * Math.random());

            if (x <= 25) {
                addScenery(SceneryType.CACTUS_SMALL, i[0], i[1]);

            } else if (x > 25 && x <= 50) {
                addScenery(SceneryType.CACTUS, i[0], i[1]);
            } else if (x > 50 && x <= 75) {
                addScenery(SceneryType.DESERT_PLANT, i[0], i[1]);
            } else {
                addScenery(SceneryType.CACTUS_SMALL_2, i[0], i[1]);
            }
        }
    }

}




















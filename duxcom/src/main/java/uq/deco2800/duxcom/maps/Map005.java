package uq.deco2800.duxcom.maps;

import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyKnight;
import uq.deco2800.duxcom.graphics.scenery.SceneryType;
import uq.deco2800.duxcom.sound.SoundPlayer;
import uq.deco2800.duxcom.tiles.LiveTileType;
import uq.deco2800.duxcom.tiles.TileType;


/**
 * Created by winstonf6 on 20/10/2016
 */

public class Map005 extends AbstractGameMap {
    private static final int MAP_HEIGHT = 22;
    private static final int MAP_WIDTH = 22;

    public Map005(String name, boolean beta) {
        super.name = name;
        super.mapType = MapType.MAP005;
        super.setSize(MAP_WIDTH, MAP_HEIGHT);
        super.initialiseEmptyCheckeredMap(TileType.DT_SAND_DARK_3,
                TileType.DT_SAND_DARK_4, MAP_WIDTH, MAP_HEIGHT);

        addCharacters();
        addTower();
        addWalls();
        addDynamics();
        addEnemies();
        addScenery();
        addObjectives();
        SoundPlayer.stopMusic();
        SoundPlayer.playGameMusic();
    }

    private void addObjectives() {
        addMovementObjective(5, 2);
        addEnemyKillObjective(EnemyType.ENEMY_KNIGHT, 20);

    }

    private void addCharacters() {
        super.spawnPoints = new int[][]{{16, 0}, {17, 0}, {18, 0}, {19, 1}};

        addSelectedHeroes();
    }
        private void addTower () {
            addScenery(SceneryType.TOWER, 1, 1);
        }

        private void addWalls () {
            int[][] leftWallPositions = {
                    {3, 0},
                    {4, 0},
                    {5, 0},
                    {6, 0},
                    {7, 0},
                    {8, 0},
                    {9, 0},
                    {10, 0},
                    {11, 0},
                    {12, 0},
                    {13, 0},
                    {14, 0},

                    {13, 3},
                    {14, 3},

                    {18, 4},
                    {19, 4},
                    {20, 4},

                    {2, 6},
                    {3, 6},
                    {4, 6},
                    {5, 6},
                    {6, 6},

                    {16, 7},
                    {17, 7},

                    {6, 10},
                    {7, 10},
                    {8, 10},
                    {9, 10},
                    {10, 10},
                    {11, 10},

                    {9, 13},
                    {10, 13},
                    {11, 13},
                    {12, 13},
                    {13, 13},
                    {14, 13},

                    {1, 16},
                    {2, 16},

                    {12, 17},
                    {13, 17},
                    {14, 17},


                    {1, 21},
                    {2, 21},
                    {3, 21},
                    {4, 21},
                    {6, 21},
                    {7, 21},
                    {8, 21},
                    {9, 21},
                    {11, 21},
                    {12, 21},
                    {13, 21},
                    {14, 21},
                    {15, 21},
                    {16, 21},
                    {17, 21},
                    {19, 21},
                    {20, 21}
            };

            for (int[] i : leftWallPositions) {
                addScenery(SceneryType.WALL_STONE_LEFT, i[0], i[1]);
            }

            int[][] rightWallLocations = {
                    {0, 3},
                    {0, 4},
                    {0, 5},
                    {0, 6},
                    {0, 7},
                    {0, 8},
                    {0, 9},
                    {0, 10},
                    {0, 11},
                    {0, 12},
                    {0, 13},
                    {0, 14},
                    {0, 15},
                    {0, 17},
                    {0, 18},
                    {0, 19},
                    {0, 20},


                    {5, 11},
                    {5, 12},
                    {5, 13},
                    {5, 14},
                    {5, 15},
                    {5, 16},
                    {5, 17},
                    {5, 18},
                    {5, 19},
                    {5, 20},

                    {7, 0},
                    {7, 1},
                    {7, 2},
                    {7, 3},
                    {7, 4},
                    {7, 5},

                    {8, 14},
                    {8, 15},
                    {8, 16},
                    {8, 17},

                    {10, 17},
                    {10, 18},
                    {10, 19},
                    {10, 20},


                    {12, 4},
                    {12, 5},
                    {12, 8},
                    {12, 9},

                    {15, 1},
                    {15, 2},
                    {15, 4},
                    {15, 5},
                    {15, 6},
                    {15, 11},
                    {15, 12},
                    {15, 14},
                    {15, 15},
                    {15, 16},
                    {18, 8},
                    {18, 9},
                    {18, 10},
                    {18, 11},
                    {18, 12},
                    {18, 13},
                    {18, 14},
                    {18, 15},
                    {18, 18},
                    {18, 19},
                    {18, 20},

                    {21, 5},
                    {21, 6},
                    {21, 7},
                    {21, 8},
                    {21, 9},
                    {21, 10},
                    {21, 11},
                    {21, 12},
                    {21, 13},
                    {21, 14},
                    {21, 15},
                    {21, 16},
                    {21, 17},
                    {21, 18},
                    {21, 19},
                    {21, 20}

            };

            for (int[] i : rightWallLocations) {
                addScenery(SceneryType.WALL_STONE_RIGHT, i[0], i[1]);
            }

            int[][] wallCornerLocations = {
                    {0, 0},
                    {0, 16},
                    {0, 21},
                    {5, 10},
                    {5, 21},
                    {7, 0},
                    {7, 6},
                    {8, 13},
                    {10, 21},
                    {12, 10},
                    {15, 0},
                    {15, 3},
                    {15, 7},
                    {15, 13},
                    {15, 17},
                    {18, 7},
                    {18, 21},
                    {21, 4},
                    {21, 21}
            };

            for (int[] i : wallCornerLocations) {
                addScenery(SceneryType.WALL_STONE_TOWER, i[0], i[1]);
            }

            addScenery(SceneryType.WALL_STONE_CORNER_NORTH, 12, 3);


        }

        private void addDynamics () {
            MapHelper.getLiveTileChunk(LiveTileType.SWAMP, 2, 15).forEach(this::addLiveTile);
            MapHelper.getLiveTileChunk(LiveTileType.SWAMP, 2, 13).forEach(this::addLiveTile);
            MapHelper.getLiveTileChunk(LiveTileType.POISON_TRAP, 2, 18).forEach(this::addLiveTile);
            MapHelper.getLiveTileChunk(LiveTileType.POISON_TRAP, 2, 20).forEach(this::addLiveTile);
            MapHelper.getLiveTileChunk(LiveTileType.LAVA, 9, 2).forEach(this::addLiveTile);
            MapHelper.getLiveTileChunk(LiveTileType.LAVA, 9, 4).forEach(this::addLiveTile);
            MapHelper.getLiveTileChunk(LiveTileType.LAVA, 9, 6).forEach(this::addLiveTile);

        }

        private void addEnemies () {
            int[][] enemyLocations = {
                    {2, 7},
                    {3, 9},
                    {4, 20},
                    {5, 2},
                    {6, 4},
                    {7, 17},
                    {7, 20},
                    {8, 8},
                    {9, 11},
                    {13, 1},
                    {13, 4},
                    {13, 15},
                    {13, 19},
                    {16, 12},
                    {17, 6},
                    {17, 9},
                    {17, 20},
                    {20, 5},
                    {20, 9},
                    {20, 16}
            };

            for (int[] i : enemyLocations) {
                addEnemy(new EnemyKnight(i[0], i[1]));
            }
        }

        private void addScenery () {
            addScenery(SceneryType.RS_ROCK_LOW, 11, 2);
            addScenery(SceneryType.RS_ROCK_HIGH, 5, 7);
            addScenery(SceneryType.RS_ROCK_LOW, 7, 7);
            addScenery(SceneryType.RS_ROCK_VANTAGE, 1, 8);
            addScenery(SceneryType.RS_ROCK_LOW, 19, 11);
            addScenery(SceneryType.RS_ROCK_VANTAGE, 11, 14);
            addScenery(SceneryType.RS_ROCK_LOW, 16, 15);
            addScenery(SceneryType.RS_ROCK_HIGH, 15, 8);

        }

    }
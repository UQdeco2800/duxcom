package uq.deco2800.duxcom.maps;

import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyArcher;
import uq.deco2800.duxcom.graphics.scenery.SceneryType;
import uq.deco2800.duxcom.sound.SoundPlayer;
import uq.deco2800.duxcom.tiles.LiveTileType;
import uq.deco2800.duxcom.tiles.TileType;


/**
 * Created by winstonf6 on 21/10/2016
 * Designed by nicquek.
 */

public class Map008 extends AbstractGameMap {
    private static final int MAP_HEIGHT = 36;
    private static final int MAP_WIDTH = 36;

    public Map008(String name, boolean beta) {
        super.name = name;
        super.mapType = MapType.MAP008;
        super.setSize(MAP_WIDTH, MAP_HEIGHT);
        super.initialiseEmptyCheckeredMap(TileType.RS_SNOW1,
                TileType.RS_SNOW2, MAP_WIDTH, MAP_HEIGHT);

        MapHelper mapHelper = new MapHelper(MAP_HEIGHT, MAP_WIDTH, tiles);

        addCharacters();
        addHuts();
        addSnowmen();
        addSnowTree();
        addLiveTiles(mapHelper);
        addRiver();
        addRocks();
        addObjectives();

        SoundPlayer.stopMusic();
        SoundPlayer.playGameMusic();
    }

    private void addObjectives() {
        addEnemyKillObjective(EnemyType.ENEMY_ARCHER, 19);
    }

    private void addCharacters() {

        super.spawnPoints = new int[][]{{31, 13}, {33, 11}, {33, 15}, {33, 13}};
        addSelectedHeroes();

        int[][] enemyLocations = {
                {2, 5},
                {4, 20},
                {4, 31},
                {5, 16},
                {5, 8},
                {8, 1},
                {12, 27},
                {17, 3},
                {18, 7},
                {18, 17},
                {23, 26},
                {26, 6},
                {26, 11},
                {26, 12},
                {31, 1},
                {31, 8},
                {31, 20},
                {31, 28},
                {33, 28},
        };

        for (int[] i : enemyLocations) {
            addEnemy(new EnemyArcher(i[0], i[1]));
        }
    }

    private void addHuts() {
        int[][] smallHutLocations = {
                {3, 3},
                {18, 3},
                {27, 5},
                {5, 28},
                {30, 29},
                {30, 32}
        };

        for (int[] i : smallHutLocations) {
            addScenery(SceneryType.IGLOO, i[0], i[1]);
        }
    }
    private void addSnowmen() {
        int[][] snowmenLocations = {
                {3, 7},
                {17, 14},
                {26, 27},
                {15, 30},
        };    
        for (int[] i : snowmenLocations) {
            addScenery(SceneryType.SNOWMAN, i[0], i[1]);
        }
    }            
    private void addSnowTree() {
        int[][] snowtreeLocations = {
                {20, 17},
                {32, 22},
                {27, 16},
                {7, 7},
                {3, 20},
                {10, 2},
        };    
        for (int[] i : snowtreeLocations) {
            addScenery(SceneryType.SNOW_TREE, i[0], i[1]);
        }
    }      
                
    private void addLiveTiles(MapHelper mapHelper) {
        int[][] livePuddleLocations = {
                {22, 22},
                {22, 23},
                {22, 24},
                {23, 21},
                {23, 22},
                {23, 23},
                {23, 24},
                {24, 21},
                {24, 22},
                {24, 23},
                {24, 24},
                {24, 25},
                {24, 26},
                {25, 20},
                {25, 21},
                {25, 22},
                {25, 23},
                {25, 24},
                {25, 25},
                {25, 26},
                {26, 20},
                {26, 21},
                {26, 22},
                {26, 23},
                {26, 24},
                {26, 25},
                {27, 19},
                {27, 20},
                {27, 21},
                {27, 22},
                {27, 23},
                {27, 24},
                {27, 25},
                {28, 20},
                {28, 21},
                {28, 22},
                {28, 23},
                {28, 24},
                {29, 22},
                {29, 23},
                {29, 24},
        };

        for (int[] i : livePuddleLocations) {
            MapHelper.getLiveTileChunk(LiveTileType.PUDDLE, i[0], i[1]).forEach(this::addLiveTile);

        }
    }

    private void addRiver() {
        int[][] riverLocations = {
                {0, 10},
                {0, 11},
                {0, 12},
                {0, 13},
                {1, 10},
                {1, 11},
                {1, 12},
                {1, 13},
                {1, 14},
                {1, 15},
                {2, 11},
                {2, 12},
                {2, 13},
                {2, 14},
                {2, 15},
                {2, 16},
                {3, 12},
                {3, 13},
                {3, 14},
                {3, 15},
                {3, 16},
                {3, 17},
                {4, 13},
                {4, 14},
                {4, 15},
                {4, 16},
                {4, 17},
                {5, 13},
                {5, 14},
                {5, 15},
                {5, 16},
                {5, 17},
                {6, 13},
                {6, 14},
                {6, 15},
                {6, 16},
                {6, 17},
                {6, 18},
                {6, 19},
                {6, 35},
                {7, 13},
                {7, 14},
                {7, 15},
                {7, 16},
                {7, 17},
                {7, 18},
                {7, 19},
                {7, 23},
                {7, 24},
                {7, 25},
                {7, 34},
                {7, 35},
                {8, 13},
                {8, 14},
                {8, 15},
                {8, 16},
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
                {8, 30},
                {8, 31},
                {8, 32},
                {8, 33},
                {8, 34},
                {8, 35},
                {9, 14},
                {9, 15},
                {9, 16},
                {9, 17},
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
                {10, 15},
                {10, 27},
                {10, 20},
                {10, 21},
                {10, 22},
                {10, 23},
                {10, 24},
                {10, 28},
                {10, 29},
                {10, 30},
                {10, 31},
                {10, 32},
                {10, 33},
                {10, 34},
                {10, 35},
                {11, 31},
                {11, 32},
                {11, 33},
                {11, 34},
                {11, 35},
                {12, 34},
                {12, 35}
        };

        for (int[] i : riverLocations) {
            setTile(i[0], i[1], TileType.DT_WATER_LIGHT_5);
        }

        addStackableScenery(SceneryType.RS_BRIDGE, 12, 23, 8, 2);
    }

    private void addRocks() {
        int[][] rockLocations = {
                {1, 1},
                {1, 32},
                {2, 24},
                {3, 9},
                {5, 11},
                {6, 32},
                {7, 4},
                {7, 12},
                {9, 18},
                {11, 12},
                {13, 5},
                {14, 2},
                {14, 23},
                {15, 6},
                {15, 9},
                {17, 25},
                {17, 33},
                {19, 21},
                {20, 31},
                {22, 7},
                {22, 10},
                {23, 5},
                {24, 20},
                {24, 28},
                {25, 2},
                {27, 30},
                {29, 2},
                {32, 7},
                {33, 3},
                {33, 4},
                {33, 26}
        };

        for (int[] i : rockLocations) {
            if (10 * Math.random() < 5) {
                addScenery(SceneryType.RS_ROCK_LOW, i[0], i[1]);
            } else {
                addScenery(SceneryType.RS_ROCK_HIGH, i[0], i[1]);
            }
        }
    }

}




















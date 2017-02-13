package uq.deco2800.duxcom.maps;

import uq.deco2800.duxcom.graphics.scenery.SceneryType;
import uq.deco2800.duxcom.tiles.TileType;

/**
 * Created by samuelthomas on 20/10/16.
 */
public class MultiplayerArena extends AbstractGameMap{
    private static final int MAP_HEIGHT = 37;
    private static final int MAP_WIDTH = 37;
    private MapHelper mapHelper;

    public MultiplayerArena(){
        super.mapType = MapType.MULTIPLAYERARENA;
        super.setSize(MAP_WIDTH, MAP_HEIGHT);
        super.initialiseEmptyCheckeredMap(TileType.GRASS_1,
                TileType.GRASS_2, MAP_WIDTH, MAP_HEIGHT);

        mapHelper = new MapHelper(MAP_HEIGHT, MAP_WIDTH, tiles);

        //Set special tiles
        createLake();

        //Add entities
        addBoundaryRing();
        createRingInternals();
        fillSouthernForest();
        fillNorthernForest();
    }

    private void createRingInternals(){
        int [][] longGrassLocations = {
                {8, 18},
                {9, 17},
                {9, 18},
                {9, 19},
                {11, 12},
                {11, 24},
                {12, 11},
                {12, 25},
                {17, 9},
                {17, 27},
                {18, 8},
                {18, 9},
                {18, 27},
                {18, 28},
                {19, 9},
                {19, 27},
                {24, 11},
                {24, 25},
                {25, 12},
                {25, 24},
                {27, 17},
                {27, 18},
                {27, 19},
                {28, 18}
        };
        setSceneryArray(longGrassLocations, SceneryType.RS_BUSH);

        //Create campfire area
        int [][] dirtLocations = {
                {16, 18},
                {17, 17},
                {17, 18},
                {17, 19},
                {18, 16},
                {18, 17},
                {18, 19},
                {18, 20},
                {19, 17},
                {19, 18},
                {19, 19},
                {20, 18}
        };
        mapHelper.SetTileArray(dirtLocations, TileType.DT_SAND_DARK_2);

        addScenery(SceneryType.RS_CAMPFIRE, 18, 18);
    }

    private void createLake() {
        //Set water
        mapHelper.drawRectangle(36, 0, 10, 4, TileType.DT_WATER_LIGHT_2, true);
        mapHelper.drawRectangle(36, 4, 6, 4, TileType.DT_WATER_LIGHT_2, true);
        mapHelper.drawRectangle(36, 8, 3, 2, TileType.DT_WATER_LIGHT_2, true);

        int [][] waterLocations = {
                {25, 2},
                {26, 1},
                {26, 2},
                {26, 3},
                {28, 4},
                {29, 4},
                {29, 5},
                {30, 4},
                {30, 5},
                {30, 6},
                {33, 8},
                {34, 10},
                {35, 10}
        };

        mapHelper.SetTileArray(waterLocations, TileType.DT_WATER_LIGHT_2);

        //Set sand
        int [][] sandLocations = {
                {24, 1},
                {24, 2},
                {24, 3},
                {25, 0},
                {25, 1},
                {25, 3},
                {25, 4},
                {26, 0},
                {26, 4},
                {27, 4},
                {27, 5},
                {28, 5},
                {28, 6},
                {29, 6},
                {29, 7},
                {30, 7},
                {30, 8},
                {31, 8},
                {31, 9},
                {32, 8},
                {32, 9},
                {32, 10},
                {33, 9},
                {33, 10},
                {33, 11},
                {34, 11},
                {34, 12},
                {35, 11},
                {35, 12},
                {36, 10},
                {36, 11}
        };

        mapHelper.SetTileArray(sandLocations, TileType.DT_SAND_LIGHT_2);
    }

    private void fillSouthernForest() {

        int[][] pineLocations = {
                {16, 1},
                {18, 2},
                {19, 1},
                {22, 1},
                {24, 36},
                {29, 32},
                {32, 32},
                {33, 30},
                {34, 14},
                {34, 35},
                {35, 13},
                {35, 22},
                {35, 33},
                {36, 25}
        };

        setSceneryArray(pineLocations, SceneryType.RS_TREE_PINE);

        int[][] rockLowLocations = {
                {17, 0},
                {17, 36},
                {20, 35},
                {22, 34},
                {27, 32},
                {31, 35},
                {32, 34},
                {35, 18},
                {35, 30},
                {36, 14},
                {36, 32},
                {36, 35}
        };

        setSceneryArray(rockLowLocations, SceneryType.RS_ROCK_LOW);

        int[][] bushLocations = {

                {20, 0},
                {23, 0},
                {26, 32},
                {28, 34},
                {30, 34},
                {31, 33},
                {33, 26},
                {33, 33},
                {36, 20}
        };

        setSceneryArray(bushLocations, SceneryType.RS_BUSH);

        int[][] rockHighLocations = {

                {16, 34},
                {19, 34},
                {28, 36},
                {30, 31},
                {31, 29},
                {32, 28},
                {34, 28},
                {35, 16},
                {36, 28}
        };

        setSceneryArray(rockHighLocations, SceneryType.RS_ROCK_HIGH);
    }

    private void fillNorthernForest() {
        int[][] TreeBasicLocations = {
                {0, 1},
                {0, 5},
                {0, 17},
                {1, 3},
                {1, 10},
                {1, 14},
                {1, 29},
                {2, 0},
                {2, 23},
                {3, 2},
                {6, 4},
                {7, 6},
                {7, 31},
                {7, 34},
                {8, 33},
                {10, 35},
                {11, 36},
                {13, 0},
                {14, 35},
                {15, 0},
                {15, 36},
                {17, 2}
        };

        setSceneryArray(TreeBasicLocations, SceneryType.RS_TREE_BASIC);

        int[][] TreeBranchLocations = {
                {0, 12},
                {1, 1},
                {1, 6},
                {1, 19},
                {1, 22},
                {1, 25},
                {2, 3},
                {2, 34},
                {4, 29},
                {4, 33},
                {5, 2},
                {5, 5},
                {6, 6},
                {6, 32},
                {7, 30},
                {8, 3},
                {9, 0},
                {12, 34},
                {14, 33},
                {19, 0},
                {21, 36}
        };

        setSceneryArray(TreeBranchLocations, SceneryType.RS_TREE_BRANCH);

        int[][] rockLowLocations = {
                {0, 8},
                {0, 31},
                {1, 18},
                {1, 27},
                {2, 8},
                {2, 12},
                {2, 17},
                {2, 35},
                {3, 11},
                {3, 22},
                {3, 31},
                {3, 33},
                {4, 0},
                {4, 6},
                {5, 31},
                {6, 0},
                {6, 29},
                {8, 1},
                {8, 31},
                {10, 33},
                {11, 1},
                {13, 2}
        };

        setSceneryArray(rockLowLocations, SceneryType.RS_ROCK_LOW);

        int[][] bushLocations = {
                {0, 23},
                {0, 36},
                {1, 7},
                {1, 13},
                {1, 33},
                {2, 5},
                {2, 10},
                {2, 29},
                {3, 7},
                {3, 13},
                {3, 26},
                {3, 35},
                {4, 35},
                {5, 8},
                {6, 35},
                {12, 35},
                {17, 35},
                {19, 36}
        };

        setSceneryArray(bushLocations, SceneryType.RS_BUSH);

        int[][] rockHighLocations = {
                {0, 20},
                {1, 16},
                {1, 31},
                {2, 21},
                {3, 9},
                {3, 24},
                {4, 4},
                {4, 28},
                {5, 27},
                {6, 7},
                {7, 1},
                {8, 36},
                {10, 3}
        };

        setSceneryArray(rockHighLocations, SceneryType.RS_ROCK_HIGH);


    }

    private void addBoundaryRing() {
        //Not written as arrays to increase future readability when
        //making modifications

        //North-East Quadrant
        addScenery(SceneryType.RS_TREE_BASIC, 3, 18);
        addScenery(SceneryType.RS_TREE_BRANCH, 3, 19);
        addScenery(SceneryType.RS_ROCK_LOW, 3, 20);
        addScenery(SceneryType.RS_TREE_BASIC, 3, 21);
        addScenery(SceneryType.RS_TREE_BASIC, 4, 22);
        addScenery(SceneryType.RS_TREE_BRANCH, 4, 23);
        addScenery(SceneryType.RS_BUSH, 4, 24);
        addScenery(SceneryType.RS_TREE_BASIC, 5, 25);
        addScenery(SceneryType.RS_ROCK_HIGH, 5, 26);
        addScenery(SceneryType.RS_TREE_BASIC, 6, 27);
        addScenery(SceneryType.RS_TREE_BRANCH, 7, 28);
        addScenery(SceneryType.RS_ROCK_LOW, 8, 29);
        addScenery(SceneryType.RS_TREE_BASIC, 9, 30);
        addScenery(SceneryType.RS_TREE_BASIC, 10, 31);
        addScenery(SceneryType.RS_BUSH, 11, 31);
        addScenery(SceneryType.RS_TREE_BASIC, 12, 32);
        addScenery(SceneryType.RS_ROCK_HIGH, 13, 32);
        addScenery(SceneryType.RS_BUSH, 14, 32);
        addScenery(SceneryType.RS_TREE_BASIC, 15, 33);
        addScenery(SceneryType.RS_ROCK_LOW, 16, 33);
        addScenery(SceneryType.RS_TREE_BRANCH, 17, 33);

        //South-East Quadrant
        addScenery(SceneryType.RS_TREE_PINE, 18, 33);
        addScenery(SceneryType.RS_TREE_PINE, 19, 33);
        addScenery(SceneryType.RS_ROCK_LOW, 20, 33);
        addScenery(SceneryType.RS_TREE_PINE, 21, 33);
        addScenery(SceneryType.RS_ROCK_HIGH, 22, 32);
        addScenery(SceneryType.RS_ROCK_LOW, 23, 32);
        addScenery(SceneryType.RS_TREE_PINE, 24, 32);
        addScenery(SceneryType.RS_TREE_PINE, 25, 31);
        addScenery(SceneryType.RS_TREE_PINE, 26, 31);
        addScenery(SceneryType.RS_BUSH, 27, 30);
        addScenery(SceneryType.RS_TREE_PINE, 28, 29);
        addScenery(SceneryType.RS_TREE_PINE, 29, 28);
        addScenery(SceneryType.RS_BUSH, 30, 27);
        addScenery(SceneryType.RS_TREE_PINE, 31, 26);
        addScenery(SceneryType.RS_TREE_PINE, 31, 25);
        addScenery(SceneryType.RS_TREE_PINE, 32, 24);
        addScenery(SceneryType.RS_TREE_PINE, 32, 23);
        addScenery(SceneryType.RS_ROCK_LOW, 32, 22);
        addScenery(SceneryType.RS_TREE_PINE, 33, 21);
        addScenery(SceneryType.RS_BUSH, 33, 20);
        addScenery(SceneryType.RS_ROCK_HIGH, 33, 19);

        //South-West Quadrant
        addScenery(SceneryType.RS_ROCK_LOW, 33, 18);
        addScenery(SceneryType.RS_TREE_PINE, 33, 17);
        addScenery(SceneryType.RS_ROCK_LOW, 33, 16);
        addScenery(SceneryType.RS_TREE_PINE, 33, 15);
        addScenery(SceneryType.RS_BUSH, 32, 14);
        addScenery(SceneryType.RS_ROCK_LOW, 32, 13);
        addScenery(SceneryType.RS_BUSH, 32, 12);
        addScenery(SceneryType.RS_ROCK_HIGH, 31, 11);
        addScenery(SceneryType.RS_TREE_PINE, 31, 10);
        addScenery(SceneryType.RS_ROCK_HIGH, 30, 9);
        addScenery(SceneryType.RS_ROCK_LOW, 29, 8);
        addScenery(SceneryType.RS_TREE_PINE, 28, 7);
        addScenery(SceneryType.RS_ROCK_HIGH, 27, 6);
        addScenery(SceneryType.RS_TREE_PINE, 26, 5);
        addScenery(SceneryType.RS_ROCK_LOW, 25, 5);
        addScenery(SceneryType.RS_BUSH, 24, 4);
        addScenery(SceneryType.RS_ROCK_LOW, 23, 4);
        addScenery(SceneryType.RS_TREE_PINE, 22, 4);
        addScenery(SceneryType.RS_TREE_PINE, 21, 3);
        addScenery(SceneryType.RS_BUSH, 20, 3);
        addScenery(SceneryType.RS_ROCK_HIGH, 19, 3);

        //North west quadrant
        addScenery(SceneryType.RS_ROCK_LOW, 18, 3);
        addScenery(SceneryType.RS_TREE_BASIC, 17, 3);
        addScenery(SceneryType.RS_TREE_BRANCH, 16, 3);
        addScenery(SceneryType.RS_ROCK_HIGH, 15, 3);
        addScenery(SceneryType.RS_TREE_BASIC, 14, 4);
        addScenery(SceneryType.RS_BUSH, 13, 4);
        addScenery(SceneryType.RS_TREE_BASIC, 12, 4);
        addScenery(SceneryType.RS_TREE_BASIC, 11, 5);
        addScenery(SceneryType.RS_TREE_BASIC, 10, 5);
        addScenery(SceneryType.RS_ROCK_LOW, 9, 6);
        addScenery(SceneryType.RS_ROCK_HIGH, 8, 7);
        addScenery(SceneryType.RS_TREE_BRANCH, 7, 8);
        addScenery(SceneryType.RS_TREE_BASIC, 6, 9);
        addScenery(SceneryType.RS_TREE_BASIC, 5, 10);
        addScenery(SceneryType.RS_BUSH, 5, 11);
        addScenery(SceneryType.RS_TREE_BRANCH, 4, 12);
        addScenery(SceneryType.RS_ROCK_HIGH, 4, 13);
        addScenery(SceneryType.RS_ROCK_LOW, 4, 14);
        addScenery(SceneryType.RS_TREE_BASIC, 3, 15);
        addScenery(SceneryType.RS_TREE_BASIC, 3, 16);
        addScenery(SceneryType.RS_ROCK_LOW, 3, 17);
    }

    /**
     * Takes an array of co-ordinates and fills them with the specified SceneryType.
     * Note: not declared in Map Helper as only used in this class and requires
     *       MapAssembly which would be messy to pass arround
     *
     * @param sceneryLocations integer array of {x, y} co-ordinates
     * @param sceneryType the type of scenery to set at each co-ordinate
     */
    private void setSceneryArray(int[][]sceneryLocations, SceneryType sceneryType) {
        for (int[] sceneryLocation : sceneryLocations) {
            addScenery(sceneryType, sceneryLocation[0], sceneryLocation[1]);
        }
    }
}

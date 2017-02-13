package uq.deco2800.duxcom.maps;

import javafx.util.Pair;
import uq.deco2800.duxcom.entities.Chest;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.HeroSpawn;
import uq.deco2800.duxcom.entities.dynamics.DynamicLiquid;
import uq.deco2800.duxcom.entities.dynamics.WaterBarrel;
import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.entities.enemies.enemychars.*;
import uq.deco2800.duxcom.entities.heros.*;
import uq.deco2800.duxcom.graphics.scenery.SceneryType;
import uq.deco2800.duxcom.sound.SoundPlayer;
import uq.deco2800.duxcom.tiles.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jay-grant on 8/10/2016.
 */
public class Falador extends AbstractGameMap {

    private static final int MAP_HEIGHT = 35*3;
    private static final int MAP_WIDTH = 35*3;

    /**
     * Creates the greatest map mankind has ever seen
     * <p>
     * GOTY - every year
     *
     * @param name the name which you will bestow upon this blessed map
     * @param beta beta elements on map
     */
    public Falador(String name, boolean beta) {
        super.name = name;
        super.mapType = MapType.FALADOR;
        super.setSize(MAP_WIDTH, MAP_HEIGHT);
        super.initialiseEmptyCheckeredMap(TileType.RS_GRASS_1, TileType.RS_GRASS_2, MAP_WIDTH, MAP_HEIGHT);

        /* paths */
        MapHelper mapHelper = new MapHelper(MAP_HEIGHT, MAP_WIDTH, tiles);

        mapHelper.drawLine(21, 19, 3, 'E', TileType.RS_PATH2);
        mapHelper.drawLine(23, 19, 4, 'S', TileType.RS_PATH2);
        mapHelper.drawLine(27, 16, 3, 'E', TileType.RS_PATH2);
        mapHelper.drawLine(29, 16, 2, 'N', TileType.RS_PATH2);
        mapHelper.drawLine(29, 17, 8, 'E', TileType.RS_PATH2);

        mapHelper.drawLine(37, 17, 20, 'N', TileType.RS_PATH2);


        mapHelper.drawLine(37, 39, 2, 'N', TileType.RS_PATH2);
        mapHelper.drawLine(37, 40, 2, 'E', TileType.RS_PATH2);
        mapHelper.drawLine(38, 40, 4, 'N', TileType.RS_PATH2);

        mapHelper.drawLine(38, 65, 10, 'S', TileType.RS_PATH2);
        mapHelper.drawLine(38, 64, 35, 'E', TileType.RS_PATH2);
        mapHelper.drawLine(38, 65, 35, 'E', TileType.RS_PATH2);
        mapHelper.drawLine(38, 66, 35, 'E', TileType.RS_PATH2);

        MapHelper.getLiveTileChunk(LiveTileType.PUDDLE, 42, 18).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.PUDDLE, 41, 17).forEach(this::addLiveTile);
        addEntity(new WaterBarrel(37, 15));
        addEnemy(new EnemyTank(37, 17));
        addEnemy(new EnemyTank(39, 16));
        addEnemy(new EnemyBear(42, 17));
        addEnemy(new EnemyWolf(40, 16));

        addEnemy(new EnemyRogue(39, 38));
        addEnemy(new EnemyRogue(35, 39));
        addEnemy(new EnemyArcher(35, 42));
        addEnemy(new EnemySupport(39, 40));

        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 43, 50).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 44, 49).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 43, 49).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 42, 49).forEach(this::addLiveTile);
        addEnemy(new EnemyDarkMage(45, 49));
        addEnemy(new EnemyWolf(44, 50));

        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 35, 49).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 49, 59).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 47, 56).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 49, 49).forEach(this::addLiveTile);

        MapHelper.getLiveTileChunk(LiveTileType.OIL, 61, 66).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.OIL, 62, 65).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.OIL, 62, 64).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.OIL, 61, 63).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.OIL, 59, 63).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.OIL, 58, 64).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.OIL, 57, 66).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.OIL, 59, 67).forEach(this::addLiveTile);
        addEnemy(new EnemyTank(61, 64));
        addEnemy(new EnemyBrute(60, 63));
        addEnemy(new EnemyGrunt(58, 63));
        addEnemy(new EnemyGrunt(58, 65));
        addEnemy(new EnemyKnight(64, 64));
        addEnemy(new EnemyKnight(65, 65));

        addScenery(SceneryType.RS_ROCK_LOW, 20, 12);
        addScenery(SceneryType.WALL_STONE_TOWER, 20, 15);
        addScenery(SceneryType.RS_CASTLE, 20, 20, 4, 4);
        addScenery(SceneryType.WALL_STONE_TOWER, 20, 22);

        addScenery(SceneryType.RS_PADS_GRASS, 21, 12);
        addScenery(SceneryType.RS_ROCK_VANTAGE, 21, 15);
        addScenery(SceneryType.DESERT_PLANT, 21, 23);
        addScenery(SceneryType.RS_ROCK_LOW, 20, 26);




        /* sp0ooky */
        addScenery(SceneryType.HEROGRAVE, 33, 43);
        addScenery(SceneryType.HEROGRAVE, 36, 47);
        addScenery(SceneryType.HEROGRAVE, 43, 51);
        addScenery(SceneryType.HEROGRAVE, 38, 50);

        addScenery(SceneryType.HEROGRAVE, 43, 44);
        addScenery(SceneryType.HEROGRAVE, 44, 40);
        addScenery(SceneryType.HEROGRAVE, 46, 47);
        addScenery(SceneryType.HEROGRAVE, 35, 53);

        /* 420 */
        addLiveTile(new FlameTile(45, 43));
        addLiveTile(new FlameTile(36, 45));
        addLiveTile(new FlameTile(35, 51));
        addLiveTile(new FlameTile(44, 52));

        /* shrubbin */
        addScenery(SceneryType.DESERT_PLANT, 43, 41);
        addScenery(SceneryType.DESERT_PLANT, 38, 47);
        addScenery(SceneryType.DESERT_PLANT, 42, 53);
        addScenery(SceneryType.DESERT_PLANT, 38, 54);
        addScenery(SceneryType.DESERT_PLANT, 35, 55);
        addScenery(SceneryType.DESERT_PLANT, 41, 55);

        /* wallin */
        mapHelper.generateEastWall(22, 11, 5).forEach(this::addEntity);
        addScenery(SceneryType.WALL_STONE_TOWER, 27, 11);
        mapHelper.generateEastWall(28, 11, 16).forEach(this::addEntity);
        addScenery(SceneryType.WALL_STONE_CORNER_NORTH, 44, 11);
        mapHelper.generateNorthWall(44, 12, 24).forEach(this::addEntity);
        addScenery(SceneryType.WALL_STONE_CORNER_SOUTH, 44, 36);

        mapHelper.generateEastWall(35, 36, 2).forEach(this::addEntity);
        mapHelper.generateEastWall(38, 36, 6).forEach(this::addEntity);

        // 44, 11

        /* bridge life */
        MapHelper.getBridge(27, 18, 3, 5).forEach(this::addEntity);
        mapHelper.drawRectangle(30, 16, 3, 3, TileType.RS_PATH2, true);

        /* billboard */
        addScenery(SceneryType.BILLBOARD, 82, 74, 1, -4);


        /* dead pixelz */
        for (int i = 0; i < 4; i++) {
            addScenery(SceneryType.INVISIBLE, 24, 12 + i);
            addScenery(SceneryType.INVISIBLE, 25, 12 + i);
            addScenery(SceneryType.INVISIBLE, 26, 12 + i);
        }

        for (int i = 0; i < 15; i++) {
            addScenery(SceneryType.INVISIBLE, 24, 19 + i);
            addScenery(SceneryType.INVISIBLE, 25, 19 + i);
            addScenery(SceneryType.INVISIBLE, 26, 19 + i);

        }






        /* forest */
        mapHelper.generateRandomForest(14, 0, 11, 21, 0.3).forEach(this::addEntity);
        mapHelper.generateRandomForest(0, 0, 35*3, 14, 0.3).forEach(this::addEntity);
        mapHelper.generateRandomForest(14, 27, 35*3 - 28, 21, 0.3).forEach(this::addEntity);

        mapHelper.generateRandomForest(39, 56, 7, 21, 0.3).forEach(this::addEntity);
        mapHelper.generateRandomForest(34, 56, 35*3 - 56, 3, 0.3).forEach(this::addEntity);

        mapHelper.generateRandomForest(38, 67, 35*3 - 67, 21, 0.3).forEach(this::addEntity);
        mapHelper.generateRandomForest(45, 0, 36, 35*3 - 45, 0.3).forEach(this::addEntity);

        mapHelper.generateRandomForest(35, 0, 9, 10, 0.3).forEach(this::addEntity);

        mapHelper.generateRandomForest(48, 36, 21, 57, 0.3).forEach(this::addEntity);

        mapHelper.generateRandomMixedForest(60, 56, 7, 45, 0.3).forEach(this::addEntity);
        mapHelper.generateRandomMixedForest(59, 67, 37, 15, 0.3).forEach(this::addEntity);
        //mapHelper.generateRandomMixedForest(81, 70, 37, 15, 0.3).forEach(this::addEntity);

        /* dirt boiz */
        mapHelper.drawLine(73, 64, 2, 'E', TileType.RS_PATH2);
        mapHelper.drawLine(73, 65, 8, 'E', TileType.RS_PATH2);
        mapHelper.drawLine(73, 66, 1, 'E', TileType.RS_PATH2);
        mapHelper.drawLine(80, 65, 6, 'N', TileType.RS_PATH2);
        mapHelper.drawLine(81, 70, 8, 'E', TileType.RS_PATH2);
        mapHelper.drawLine(88, 70, 10, 'N', TileType.RS_PATH2);


        scatterThatDirt(75, 63, 25, 20);
        addLiveTile(new LavaTile(81, 69));
        addLiveTile(new LavaTile(82, 68));
        addLiveTile(new LavaTile(81, 69));
        addLiveTile(new LavaTile(82, 69));
        addLiveTile(new LavaTile(87, 71));
        addLiveTile(new LavaTile(87, 72));
        addLiveTile(new LavaTile(87, 73));
        addLiveTile(new LavaTile(87, 74));
        addLiveTile(new LavaTile(87, 75));
        addLiveTile(new LavaTile(87, 76));

        addLiveTile(new LavaTile(83, 73));
        addLiveTile(new LavaTile(79, 76));
        addLiveTile(new LavaTile(81, 81));
        addLiveTile(new LavaTile(76, 79));
        addLiveTile(new LavaTile(88, 66));
        addLiveTile(new LavaTile(92, 79));

        /* now we livin */
        /*



        mapHelper.drawLine(75, 78, 3, 'N', TileType.RS_FLOOR2);
        mapHelper.drawLine(75, 80, 3, 'E', TileType.RS_FLOOR2);
        mapHelper.drawLine(77, 80, 3, 'S', TileType.RS_FLOOR2);
        mapHelper.drawLine(77, 78, 3, 'W', TileType.RS_FLOOR2);

        mapHelper.drawLine(78, 75, 3, 'N', TileType.RS_FLOOR2);
        mapHelper.drawLine(78, 77, 3, 'E', TileType.RS_FLOOR2);
        mapHelper.drawLine(80, 77, 3, 'S', TileType.RS_FLOOR2);
        mapHelper.drawLine(80, 75, 3, 'W', TileType.RS_FLOOR2);

        mapHelper.drawLine(80, 80, 3, 'N', TileType.RS_FLOOR2);
        mapHelper.drawLine(80, 82, 3, 'E', TileType.RS_FLOOR2);
        mapHelper.drawLine(82, 82, 3, 'S', TileType.RS_FLOOR2);
        mapHelper.drawLine(82, 80, 3, 'W', TileType.RS_FLOOR2);

        mapHelper.drawLine(82, 72, 3, 'N', TileType.RS_FLOOR2);
        mapHelper.drawLine(82, 74, 3, 'E', TileType.RS_FLOOR2);
        mapHelper.drawLine(84, 74, 3, 'S', TileType.RS_FLOOR2);
        mapHelper.drawLine(84, 72, 3, 'W', TileType.RS_FLOOR2);

        mapHelper.drawLine(87, 65, 3, 'N', TileType.RS_FLOOR2);
        mapHelper.drawLine(87, 67, 3, 'E', TileType.RS_FLOOR2);
        mapHelper.drawLine(89, 67, 3, 'S', TileType.RS_FLOOR2);
        mapHelper.drawLine(89, 65, 3, 'W', TileType.RS_FLOOR2);

        mapHelper.drawLine(81, 67, 2, 'N', TileType.RS_FLOOR2);
        mapHelper.drawLine(81, 67, 3, 'E', TileType.RS_FLOOR2);
        mapHelper.drawLine(83, 67, 3, 'N', TileType.RS_FLOOR2);

        mapHelper.drawLine(86, 71, 7, 'N', TileType.RS_FLOOR2);
        mapHelper.drawLine(86, 77, 7, 'E', TileType.RS_FLOOR2);
        */



        //addStackableScenery(SceneryType.MOUNTAIN, 65, 65);

        mapHelper.drawRectangle(26, 0, 3, 35*3, TileType.WATER, true);

        //mapHelper.drawRectangle(44, 37, 10, 2, TileType.WATER, true);
        //addScenery(SceneryType.RS_BRIDGE, 27, 16, 4, 1, 0.5);

        generateMagma(95, 64, 41, 10, 1);
        generateMagma(74, 88, 3*35 - 88, 21, 1);

        addCharacters();
        /*
        addChests();
        addLiveTiles();
        addMapHelperPatterns();
        burkesBackyard();
        theBlock();
        keepTheRabbitsOut();

        if (beta) {
            addBetaFeatures();
        }
        SoundPlayer.stopMusic();
        SoundPlayer.playGameMusic();
        */

        addObjectives();
    }

    public void generateMagma(int startX, int startY, int height, int width, double density) {

        Random randomGen = new Random();
        List<Pair> disgustingHack = new ArrayList<>();


        for (int i = startX; i < startX + width; i++) {
            for (int j = startY; j < startY + height; j++) {
                for (int ayy = 0; ayy < 2; ayy++) {
                    if (transmits()) {
                        Pair<Integer, Integer> iamashamedofthiscode = new Pair<Integer, Integer>(i, j);
                        disgustingHack.add(iamashamedofthiscode);
                        addStackableScenery(SceneryType.MOUNTAIN, i, j);
                    }
                }
            }
        }

        for (int i = startX; i < startX + width; i++) {
            for (int j = startY; j < startY + height; j++) {
                Pair<Integer, Integer> iamashamedofthiscode = new Pair<Integer, Integer>(i, j);

                if (!disgustingHack.contains(iamashamedofthiscode)) {
                    if (transmits()) {
                        addLiveTile(new LavaTile(i, j));
                    } else {
                        addLiveTile(new FlatLava(i, j));
                    }
                }
            }
        }
    }


    public boolean transmits() {
        return Math.random() < 0.4;
    }

    /**
     * ATTENTION
     * <p>
     * Please put any BETA testing related items onto the map in this function
     * Then launch through the OSRS Beta button
     * <p>
     * xx
     */
    private void addBetaFeatures() {
        keepTheRabbitsOut();

        addEntity(new HeroSpawn(29, 10));
        addEntity(new HeroSpawn(29, 11));
        addEntity(new HeroSpawn(29, 12));
        addEntity(new HeroSpawn(29, 13));
    }

    private void addChests() {
        Chest chest = new Chest(26,15);
        addChest(chest);
    }
    /**
     * Adds any patterns obtained from the MapHelper class
     */
    private void addMapHelperPatterns() {
        MapHelper.getFountain(16, 16).forEach(this::addEntity);
        MapHelper mapHelper = new MapHelper(MAP_HEIGHT, MAP_WIDTH, tiles);
        drawBuildings(mapHelper);
        drawPath(mapHelper);
    }

    private void drawFloor(MapHelper mapHelper) {
        // Floor for volcano level
        /**
         mapHelper.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 37, 34, 50);
         mapHelper.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 35, 33, 37);
         mapHelper.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 33, 32, 35);
         mapHelper.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 31, 30, 33);
         mapHelper.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 29, 28, 31);
         mapHelper.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 27, 26, 29);
         mapHelper.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 25, 24, 27);
         mapHelper.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 24, 23, 26);
         mapHelper.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 23, 22, 25);
         mapHelper.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 22, 21, 24);
         mapHelper.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 13, 17, 22);
         */
        mapHelper.checkerTile(TileType.RS_SAND1, TileType.RS_SAND2, 0, 0, 40, 50);
        mapHelper.checkerTile(TileType.RS_SAND1, TileType.RS_SAND2, 40, 0, 41, 39);
        mapHelper.checkerTile(TileType.RS_SAND1, TileType.RS_SAND2, 41, 0, 42, 37);
        mapHelper.checkerTile(TileType.RS_SAND1, TileType.RS_SAND2, 42, 0, 43, 4);
        mapHelper.checkerTile(TileType.RS_SAND1, TileType.RS_SAND2, 42, 31, 43, 34);

        mapHelper.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 38, 34, 50);
        mapHelper.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 36, 33, 38);
        mapHelper.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 34, 32, 36);
        mapHelper.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 32, 30, 34);
        mapHelper.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 30, 28, 32);
        mapHelper.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 28, 26, 30);
        mapHelper.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 27, 17, 28);
        mapHelper.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 26, 12, 27);
        mapHelper.checkerTile(TileType.RS_GRASS_1, TileType.RS_GRASS_2, 0, 25, 11, 28);

        mapHelper.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 0, 0, 27, 8);
        mapHelper.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 27, 0, 31, 6);
        mapHelper.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 31, 0, 32, 5);
        mapHelper.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 32, 0, 33, 5);
        mapHelper.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 33, 0, 36, 3);
        mapHelper.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 36, 0, 37, 2);
        mapHelper.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 37, 0, 38, 1);
        mapHelper.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 0, 0, 8, 30);
        mapHelper.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 0, 30, 7, 31);
        mapHelper.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 0, 31, 6, 32);
        mapHelper.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 0, 32, 4, 33);
        mapHelper.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 0, 33, 3, 34);
        mapHelper.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 8, 8, 9, 9);
        mapHelper.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 21, 8, 26, 9);
        mapHelper.checkerTile(TileType.RS_PATH1, TileType.RS_PATH2, 8, 25, 9, 29);

    }

    private void addLiveTiles() {

        MapHelper.getLiveTileChunk(LiveTileType.LAVA, 33, 11).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.PUDDLE, 33, 14).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.LONG_GRASS, 33, 17).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.FLAME, 33, 20).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.OIL, 30, 14).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.BLEED_TRAP, 33, 23).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.POISON_TRAP, 33, 26).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.SWAMP, 33, 29).forEach(this::addLiveTile);
        MapHelper.getLiveTileChunk(LiveTileType.FROST, 33, 32).forEach(this::addLiveTile);

        addEntity(new WaterBarrel(28, 23));

    }

    private void addCharacters() {
        addHero(new Knight(21, 17));
        addHero(new Rogue(21, 18));
        addHero(new Cavalier(21, 19));
        addHero(new Warlock(21, 20));

        /*
        addHero(new Knight(28, 18));
        addHero(new Knight(28, 20));
        addHero(new Warlock(27, 19));
        addHero(new Archer(26, 18));
        addHero(new Cavalier(27, 20));
        
        addEnemy(new EnemyKnight(12, 24));
        addEnemy(new EnemyKnight(15, 25));
        addEnemy(new EnemyArcher(15, 26));
        addEnemy(new EnemyBrute(13, 27));

        addEnemy(new EnemyKnight(6, 17));
        addEnemy(new EnemyKnight(4, 17));
        addEnemy(new EnemyArcher(6, 12));
        addEnemy(new EnemyTank(7, 16));
        addEnemy(new EnemyTank(7, 13));

        addEnemy(new EnemyBear(17, 5));
        addEnemy(new EnemyWolf(18, 7));
        addEnemy(new EnemyWolf(15, 6));
        */
    }

    private void addObjectives() {
        addEnemyKillObjective(EnemyType.ENEMY_KNIGHT, 3);
        addEnemyKillObjective(EnemyType.ENEMY_BEAR, 1);
        addEnemyHitObjective(EnemyType.ENEMY_WOLF);
        addMovementObjective(31, 31);
        addProtectionObjective(EntityType.KNIGHT, 3);
    }

    void drawBuildings(MapHelper mapHelper) {
        // Buildings
        mapHelper.drawRectangle(10, 4, 6, 3, TileType.RS_FLOOR1, true);
        mapHelper.drawRectangle(10, 7, 3, 2, TileType.RS_FLOOR1, true);
        mapHelper.drawLine(11, 5, 3, 'N', TileType.RS_FLOOR1);

        mapHelper.drawRectangle(17, 5, 4, 3, TileType.RS_FLOOR1, true);
        mapHelper.drawLine(17, 8, 2, 'W', TileType.RS_FLOOR1);

        mapHelper.drawRectangle(23, 7, 6, 2, TileType.RS_FLOOR2, true);
        mapHelper.drawLine(23, 6, 2, 'W', TileType.RS_FLOOR2);
        mapHelper.drawLine(18, 6, 1, 'W', TileType.RS_FLOOR2);

        mapHelper.drawRectangle(6, 8, 3, 3, TileType.RS_FLOOR1, true);
        mapHelper.drawRectangle(8, 24, 4, 3, TileType.RS_FLOOR1, true);
        mapHelper.drawRectangle(4, 9, 2, 7, TileType.RS_FLOOR1, true);
        mapHelper.drawRectangle(6, 13, 2, 3, TileType.RS_FLOOR1, true);
        mapHelper.drawRectangle(8, 27, 3, 3, TileType.RS_FLOOR1, true);

        mapHelper.drawRectangle(17, 23, 5, 4, TileType.RS_FLOOR1, true);
        mapHelper.drawLine(12, 23, 3, 'N', TileType.RS_FLOOR1);

        mapHelper.drawLine(13, 27, 3, 'E', TileType.RS_FLOOR1);

        mapHelper.drawRectangle(29, 22, 3, 2, TileType.RS_FLOOR1, true);
        mapHelper.drawLine(27, 18, 4, 'N', TileType.RS_FLOOR1);
        mapHelper.drawRectangle(29, 16, 3, 2, TileType.RS_FLOOR1, true);

        mapHelper.drawRectangle(18, 12, 9, 3, TileType.RS_FLOOR1, true);
        mapHelper.drawRectangle(16, 11, 4, 6, TileType.RS_FLOOR1, true);
        mapHelper.drawLine(17, 11, 1, 'N', TileType.RS_FLOOR1);
        mapHelper.drawLine(17, 15, 2, 'E', TileType.RS_FLOOR1);
    }

    private void drawPath(MapHelper mapHelper) {
        // Path
        mapHelper.drawRectangle(29, 19, 5, 2, TileType.RS_PATH2, true);

        mapHelper.drawRectangle(19, 19, 21, 2, TileType.RS_PATH1, true);

        mapHelper.drawRectangle(10, 16, 4, 6, TileType.RS_PATH1, true);

        mapHelper.drawRectangle(9, 9, 2, 7, TileType.RS_PATH1, true);
        mapHelper.drawLine(7, 9, 21, 'E', TileType.RS_PATH1);
        mapHelper.drawLine(13, 10, 13, 'E', TileType.RS_PATH1);

        mapHelper.drawRectangle(30, 6, 3, 2, TileType.RS_PATH1, true);
        mapHelper.drawRectangle(28, 7, 2, 2, TileType.RS_PATH2, true);
        mapHelper.drawRectangle(27, 8, 2, 2, TileType.RS_PATH1, true);

        mapHelper.drawRectangle(13, 3, 2, 5, TileType.RS_PATH4, true);
        mapHelper.drawLine(12, 8, 1, 'E', TileType.RS_PATH3);

        mapHelper.drawRectangle(20, 2, 8, 2, TileType.RS_PATH3, true);

        mapHelper.drawRectangle(4, 21, 2, 3, TileType.RS_PATH1, true);
        mapHelper.drawRectangle(3, 22, 2, 5, TileType.RS_PATH1, true);
        mapHelper.drawRectangle(5, 28, 2, 3, TileType.RS_PATH2, true);
        mapHelper.drawLine(4, 27, 3, 'W', TileType.RS_PATH2);

        mapHelper.drawRectangle(8, 22, 2, 2, TileType.RS_PATH2, true);

        mapHelper.drawLine(10, 22, 9, 'N', TileType.RS_PATH2);
        mapHelper.drawLine(9, 27, 3, 'N', TileType.RS_PATH4);
        mapHelper.drawRectangle(11, 23, 2, 2, TileType.RS_PATH4, true);
        mapHelper.drawLine(11, 23, 2, 'W', TileType.RS_PATH3);
        mapHelper.drawLine(10, 22, 2, 'W', TileType.RS_PATH3);
    }

    /**
     * "Dad, why did they build the Great Wall of China?"
     * <p>
     * "That...that was...during the time of Emperor Nasi Goreng, and...ahh...it was to keep the
     * rabbits out. Too many rabbits in China"
     * <p>
     * REAL TALK: Makes the wall 10 feet higher
     */
    private void keepTheRabbitsOut() {
        for (int x = 5; x < 22; x++) {
            addScenery(SceneryType.WALL_STONE_LEFT, x, 0);
        }
        addScenery(SceneryType.WALL_STONE_TOWER, 22, 0);

        addScenery(SceneryType.WALL_STONE_TOWER, 25, 0);

        for (int x = 26; x < 29; x++) {
            addScenery(SceneryType.WALL_STONE_LEFT, x, 0);
        }

        addScenery(SceneryType.WALL_STONE_RIGHT, 1, 4);
        addScenery(SceneryType.WALL_STONE_RIGHT, 0, 6);
        addScenery(SceneryType.WALL_STONE_RIGHT, 0, 7);

        addScenery(SceneryType.WALL_STONE_RIGHT, 1, 9);
        addScenery(SceneryType.WALL_STONE_RIGHT, 1, 10);
        addScenery(SceneryType.WALL_STONE_RIGHT, 1, 11);
        addScenery(SceneryType.WALL_STONE_RIGHT, 1, 12);

        addScenery(SceneryType.WALL_STONE_RIGHT, 0, 14);
        addScenery(SceneryType.WALL_STONE_RIGHT, 0, 15);
        addScenery(SceneryType.WALL_STONE_RIGHT, 0, 16);
        addScenery(SceneryType.WALL_STONE_TOWER, 0, 17);

        addScenery(SceneryType.WALL_STONE_TOWER, 4, 0);
        addScenery(SceneryType.WALL_STONE_CORNER_NORTH, 3, 1);
        addScenery(SceneryType.WALL_STONE_CORNER_NORTH, 2, 2);
        addScenery(SceneryType.WALL_STONE_CORNER_NORTH, 1, 3);
        addScenery(SceneryType.WALL_STONE_TOWER, 0, 5);
        addScenery(SceneryType.WALL_STONE_TOWER, 0, 8);
        addScenery(SceneryType.WALL_STONE_TOWER, 1, 8);
        addScenery(SceneryType.WALL_STONE_CORNER_NORTH, 0, 13);

        addScenery(SceneryType.WALL_STONE_CORNER_SOUTH, 4, 1);
        addScenery(SceneryType.WALL_STONE_CORNER_SOUTH, 3, 2);
        addScenery(SceneryType.WALL_STONE_CORNER_SOUTH, 2, 3);
        addScenery(SceneryType.WALL_STONE_CORNER_SOUTH, 1, 5);
        addScenery(SceneryType.WALL_STONE_CORNER_SOUTH, 1, 13);
    }

    /**
     * "G’day!
     * <p>
     * Who or what is Burke’s Backyard? We’re Australian, for starters.
     * <p>
     * We’re the original Backyard Lifestyle television program, 'Burke’s Backyard'. And now we’re
     * also the popular 'Burke’s Backyard' magazine.
     * <p>
     * And we produce books, and DVDs, and the multi Logie award-winning garden makeover TV show,
     * 'Backyard Blitz'.
     * <p>
     * Our founder is Don Burke. As well as being an expert gardener
     * he’s nuts about animals and pets, too. And healthy food, the environment, science, DIY
     * projects and good design anywhere it can be found."
     * <p>
     * REAL TALK: Adds the trees.
     */
    private void burkesBackyard() {
        addScenery(SceneryType.RS_TREE_PINE, 26, 6);
        addScenery(SceneryType.RS_TREE_PINE, 25, 7);
        addScenery(SceneryType.RS_TREE_BRANCH, 20, 11);
        addScenery(SceneryType.RS_TREE_BASIC, 20, 24);
        addScenery(SceneryType.RS_TREE_BRANCH, 19, 27);
        addScenery(SceneryType.RS_TREE_BRANCH, 20, 30);
        addScenery(SceneryType.RS_TREE_BRANCH, 4, 27);
        addStackableScenery(SceneryType.RS_BRIDGE, 26, 20, 8, 2);
    }

    /**
     * The Block is an Australian reality television series broadcast on the Nine Network.
     * <p>
     * The series follows four or five couples as they compete against each other to renovate
     * houses/apartments and sell them at auction for the highest price.
     * <p>
     * REAL TALK: Adds the buildings and rocks
     */
    private void theBlock() {
        addScenery(SceneryType.RS_ROCK_VANTAGE, 17, 7);
        addScenery(SceneryType.RS_PADS_GRASS, 16, 7);

        addScenery(SceneryType.RS_ROCK_HIGH, 15, 22);
        addScenery(SceneryType.RS_ROCK_LOW, 13, 22);
        addScenery(SceneryType.RS_ROCK_LOW, 16, 24);
        addScenery(SceneryType.RS_CAMPFIRE, 14, 24);

        addScenery(SceneryType.RS_CASTLE, 10, 8, 4, 4);
        addScenery(SceneryType.RS_CASTLE, 6, 16, 4, 4);
        addScenery(SceneryType.RS_CASTLE, 8, 26, 4, 4);
    }
}

package uq.deco2800.duxcom.maps;

import uq.deco2800.duxcom.entities.WoodStack;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.BetaTester;
import uq.deco2800.duxcom.entities.heros.Ibis;
import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.tiles.TileType;

import java.util.Random;

/**
 * Prettier demo map :P
 * Optimised demonstration map implementing texture pack functionality.
 *
 * Created by liamdm on 16/08/2016.
 */
public class OptimisedDemo extends AbstractGameMap {
    private static final int MAP_WIDTH = 50;
    private static final int MAP_HEIGHT = 50;

    /**
     * Creates liam's optimised demo map
     */
    public OptimisedDemo(){
        super.setSize(MAP_WIDTH, MAP_HEIGHT);
        super.mapType = MapType.OPTIMISE_DEMO;

        TileType[] sandstoneTypes = {
                TileType.REAL_SANDSTONE_1,
                TileType.REAL_SANDSTONE_2,
                TileType.REAL_SANDSTONE_3,
                TileType.REAL_SANDSTONEBRICK};

        TileType[] grassTypes = {
                TileType.GRASS_1,
                TileType.GRASS_2,
                TileType.GRASS_3,
                TileType.REAL_JACARANDA
        };

        TileType[] waterTypes = {
                TileType.REAL_WATER,
                TileType.REAL_FOUNTAIN
        };

        renderGress(grassTypes);
        renderWater(waterTypes);
        renderMagma();
        renderSandstone(sandstoneTypes);
        addEntities();
    }

    private void addEntities() {
        addEntity(new WoodStack(4,6));

        for (int i = 8; i < 9; i++) {
            for (int j = 8; j < 12; j++) {
                AbstractHero hero = new Ibis(i, j);
                addHero(hero);
            }
        }

        // Add the beta tester
        AbstractHero betaTester = new BetaTester(9, 12);
        addHero(betaTester);

        heroManager.setHero(0);
    }

    private void renderSandstone(TileType[] sandstoneTypes) {
        Random random = new Random();
        int sandstoneWidth = 5;
        int sandstoneHeight = 5;
        int sandstoneStartX = 4 - (sandstoneWidth / 2);
        int sandstoneStartY = 6 - (sandstoneHeight / 2);

        for (int x = sandstoneStartX; x < sandstoneStartX + sandstoneWidth; ++x) {
            for (int y = sandstoneStartY; y < sandstoneStartY + sandstoneHeight; ++y) {
                tiles.set(x, y, new Tile(sandstoneTypes[random.nextInt(3)]));
            }
        }
    }

    private void renderMagma() {
        Random random = new Random();
        int magmaSpan = 5;
        int magmaWidth = 3;
        int magmaHeight = 4;
        int magmaStartX = MAP_WIDTH - (magmaSpan + magmaWidth + 5);
        int magmaStartY = MAP_HEIGHT - (magmaSpan + magmaWidth + 5);

        int qutLocX = magmaStartX + (magmaWidth / 2);
        int qutLocY = magmaStartY + (magmaHeight / 2);

        for (int x = magmaStartX - magmaSpan; x < magmaStartX + magmaWidth + magmaSpan; ++x) {
            for (int y = magmaStartY - magmaSpan; y < magmaStartY + magmaHeight + magmaSpan; ++y) {
                if(!(x < magmaStartX || x > (magmaStartX + magmaWidth) || y < magmaStartY || y > (magmaStartY + magmaHeight))) {
                    tiles.set(x, y, new Tile(TileType.REAL_MAGMA));
                    continue;
                }
                if(random.nextInt(100) > 50){
                    tiles.set(x, y, new Tile(TileType.REAL_MAGMA));
                }
            }
        }
        tiles.set(qutLocX, qutLocY, new Tile(TileType.REAL_QUT));
    }

    private void renderWater(TileType[] waterTypes) {
        int waterStartX = 16;
        int waterWidth = 7;
        int waterStartY = 6;
        int waterHeight = 7;
        for (int x = waterStartX; x < waterStartX + waterWidth; ++x) {
            for (int y = waterStartY; y < waterStartY + waterHeight; ++y) {
                tiles.set(x, y, new Tile(waterTypes[0]));
            }
        }

        int fountainOffsetX = waterWidth / 2;
        int fountainOffsetY = waterHeight / 2;
        tiles.set(waterStartX + fountainOffsetX, waterStartY + fountainOffsetY, new Tile(waterTypes[1]));
    }

    private void renderGress(TileType[] grassTypes) {
        Random random = new Random();
        for (int y = 0; y < MAP_WIDTH; y++) {
            for (int x = 0; x < MAP_HEIGHT; x++) {
                tiles.set(x, y, new Tile(grassTypes[random.nextInt(3)]));
                if(random.nextInt(100) > 98){
                    tiles.set(x, y, new Tile(grassTypes[3]));
                }
            }
        }
    }
}

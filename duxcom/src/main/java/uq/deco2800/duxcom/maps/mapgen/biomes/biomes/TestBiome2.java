package uq.deco2800.duxcom.maps.mapgen.biomes.biomes;

import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.maps.mapgen.biomes.BiomeType;

/**
 * A biome meant to resemble a small unit test.
 *
 * Created by liamdm on 17/08/2016.
 */
public class TestBiome2 extends AbstractBiome {

    /**
     * Create the test biome
     */
    public TestBiome2() {

            // Just use default tile
            genericTiles.clear();
            genericPatterns.clear();
    }

    @Override
    public BiomeType getBiomeType() {
        return BiomeType.TEST_OBVIOUS;
    }

    @Override
    public TileType getDefaultTileResource() {
        return TileType.REAL_GRASS_1;
    }
}

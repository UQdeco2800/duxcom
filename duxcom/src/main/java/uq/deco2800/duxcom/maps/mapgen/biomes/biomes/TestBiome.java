package uq.deco2800.duxcom.maps.mapgen.biomes.biomes;

import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.maps.mapgen.biomes.BiomeType;
import uq.deco2800.duxcom.maps.mapgen.generator.terrain.tiles.GeneratorTile;
import uq.deco2800.duxcom.maps.mapgen.generator.terrain.patterns.TestWaterPattern;

/**
 * A biome meant to resemble a small unit test.
 *
 * Created by liamdm on 17/08/2016.
 */
public class TestBiome extends AbstractBiome {

    /**
     * Create the test biome
     */
    public TestBiome() {

            genericTiles.add(new GeneratorTile(TileType.REAL_GRASS_1, 0.4));
            genericTiles.add(new GeneratorTile(TileType.REAL_GRASS_2, 0.3));
            genericTiles.add(new GeneratorTile(TileType.REAL_GRASS_3, 0.2));
            genericTiles.add(new GeneratorTile(TileType.REAL_JACARANDA, 0.01));

            genericPatterns.add(new TestWaterPattern(0.005, 2, 10));

    }

    @Override
    public BiomeType getBiomeType() {
        return BiomeType.TEST_REALISTIC;
    }

    @Override
    public TileType getDefaultTileResource() {
        return TileType.REAL_GRASS_1;
    }
}

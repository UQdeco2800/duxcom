package uq.deco2800.duxcom.maps.mapgen.biomes.biomes;

import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.maps.mapgen.biomes.BiomeType;
import uq.deco2800.duxcom.maps.mapgen.generator.terrain.patterns.GeneratorPattern;
import uq.deco2800.duxcom.maps.mapgen.generator.terrain.tiles.GeneratorTile;

import java.util.List;

/**
 * Base interface for biomes
 *
 * Created by liamdm on 17/08/2016.
 */
public interface Biome {
    /**
     * Get the tiles in the biome
     * @return biome tiles
     */
    List<GeneratorTile> getTiles();

    /**
     * Gets the patterns in the biome
     * @return biome patterns
     */
    List<GeneratorPattern> getPatterns();

    /**
     * Gets the default biome resource
     * @return the biomes default resource
     */
    TileType getDefaultTileResource();

    /**
     * Gets the biome type
     * @return the type of the biome
     */
    BiomeType getBiomeType();
}

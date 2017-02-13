package uq.deco2800.duxcom.maps.mapgen.biomes.biomes;

import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.maps.mapgen.biomes.BiomeType;
import uq.deco2800.duxcom.maps.mapgen.generator.terrain.patterns.GeneratorPattern;
import uq.deco2800.duxcom.maps.mapgen.generator.terrain.tiles.GeneratorTile;

import java.util.ArrayList;
import java.util.List;

/**
 * The abstract biome implementing the list getter method
 * to return the default tiles and patterns for biome.
 *
 * Created by liamdm on 19/08/2016.
 */
public abstract class AbstractBiome implements  Biome {
    /**
     * Generic tiles that can spawn in a biome
     */
    List<GeneratorTile> genericTiles = new ArrayList<>();

    /**
     * Generic patterns that can spawn in a biome
     */
    List<GeneratorPattern> genericPatterns = new ArrayList<>();


    /**
     * Gets the tiles in this biome
     * @return the biomes tiles
     */
    @Override
    public List<GeneratorTile> getTiles() {
        return new ArrayList<>(genericTiles);
    }

    /**
     * Gets the patterns in this biome
     * @return the biomes tiles
     */
    @Override
    public List<GeneratorPattern> getPatterns() {
        return new ArrayList<>(genericPatterns);
    }

    /**
     * Gets the biome type of this biome
     * @return the biome type
     */
    public abstract BiomeType getBiomeType();

    /**
     * The base tile type to use if none others triggered
     * @return the base tile type
     */
    public abstract TileType getDefaultTileResource();


}

package uq.deco2800.duxcom.maps.mapgen.generator.biomegenerator;

import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.maps.mapgen.biomes.BiomeManager;
import uq.deco2800.duxcom.maps.mapgen.biomes.BiomeType;
import uq.deco2800.duxcom.maps.mapgen.biomes.biomes.Biome;
import uq.deco2800.duxcom.maps.mapgen.bounds.AreaBound;
import uq.deco2800.duxcom.maps.mapgen.bounds.CoverageArea;
import uq.deco2800.duxcom.maps.mapgen.generator.terrain.patterns.GeneratorPattern;
import uq.deco2800.duxcom.maps.mapgen.generator.terrain.tiles.GeneratorTile;
import uq.deco2800.duxcom.util.Array2D;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Implements the methods required to get succinct specifiers of
 * the biomes generated so they can be re-generated if requred.
 * <p>
 * Created by liamdm on 18/08/2016.
 */
public class BiomeGenerator {
    /**
     * The random generator being used by this class
     */
    protected Random random = new Random();

    /**
     * The seed this class is currently using for the random
     * generator
     */
    private long currentSeed = 0;

    // Generate the seed once on create
    public BiomeGenerator() {
        regenerateSeed();
    }

    /**
     * Preset the generator, can be used to re-generate a block
     *
     * @param seed
     */
    public BiomeGenerator(long seed) {
        currentSeed = seed;
        random.setSeed(currentSeed);
    }

    /**
     * Creates a new generator seed that will be used
     * to generate the next block created by this class
     *
     * @return the seed
     */
    public long regenerateSeed() {
        long seed = (long) String.valueOf(SecureRandom.getSeed(10)).hashCode();
        random.setSeed(seed);
        currentSeed = seed;
        return seed;
    }

    /**
     * Gets the current seed being used by the biome generator
     *
     * @return the current seed
     */
    public long getCurrentSeed() {
        return currentSeed;
    }

    /**
     * Sets the pattern
     *
     * @param block
     * @param pattern
     * @param patternResources
     * @param x
     * @param y
     */
    private void setPattern(Array2D<Tile> block, GeneratorPattern pattern, Array2D<TileType> patternResources,
                            int x, int y) {
        for (int y1 = y; y1 < pattern.getHeight() + y; ++y1) {
            for (int x1 = x; x1 < pattern.getWidth() + x; ++x1) {
                TileType resource = patternResources.get(x1 - x, y1 - y);
                if (resource == null) {
                    // Ignore, use underlying block
                    continue;
                }

                block.set(x1, y1, new Tile(resource));
            }
        }
    }

    /**
     * Overwrite with the patterns
     *
     * @param block
     * @param patternedArea
     * @param patterns
     * @param height
     * @param width
     * @param x
     * @param y
     */
    private void overWriteWithPatterns(Array2D<Tile> block, CoverageArea patternedArea,
                                           List<GeneratorPattern> patterns, int height, int width, int x, int y)  {

        for (GeneratorPattern pattern : patterns) {
            if (pattern.shouldPlace(random)) {
                // Add to the patterned area
                int bottomX = x + pattern.getWidth();
                int bottomY = y + pattern.getHeight();
                AreaBound thisArea  = new AreaBound(x, y, bottomX, bottomY);

                // Draw the pattern
                Array2D<TileType> patternResources = pattern.getPattern(random);

                // Pattern does not fit on the map || pattern is too close
                if (!pattern.doesPatternFit(x, y, width, height) ||
                        patternedArea.minimumDistanceToCoveredArea(thisArea) <= pattern.getMinimumDistance()) {
                    continue;
                }

                patternedArea.addBound(thisArea);

                // Mark as placed (will reduce probability of re-place
                pattern.markPlaced();

                setPattern(block, pattern, patternResources, x, y);
            }
        }
    }

    /**
     * Set the tiles
     *
     * @param block
     * @param biome
     * @param x
     * @param y
     * @param tiles
     */
    private void setTile(Array2D<Tile> block, Biome biome,int x, int y, List<GeneratorTile> tiles) {

            for (GeneratorTile tile : tiles) {
                if (tile.shouldPlace(random)) {
                    block.set(x, y, new Tile(tile.getResource()));
                    break;
                }
            }

            // Failed to set a tile, default
            block.set(x, y, new Tile(biome.getDefaultTileResource()));

    }

    /**
     * Generates a block with the given biome specifier
     *
     * @param width
     * @param height
     * @return the block
     */
    public Array2D<Tile> generateBlock(int width, int height, BiomeType biomeType) {
        Biome biome = BiomeManager.getBiome(biomeType);

        Array2D<Tile> block = new Array2D<>(width, height);

        List<GeneratorTile> tiles = biome.getTiles();
        List<GeneratorPattern> patterns = biome.getPatterns();

        /**
         * Sort the tiles in order of probability
         */

        Collections.sort(tiles, (GeneratorTile o1, GeneratorTile o2) ->
                Double.compare(o1.getProbability(), o2.getProbability()));

        Collections.sort(patterns, (GeneratorPattern o1, GeneratorPattern o2) ->
                Double.compare(o1.getOccurrenceProbability(), o2.getOccurrenceProbability()));

        CoverageArea patternedArea = new CoverageArea();

        // Go through and spawn the tiles
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {

                setTile(block, biome, x, y, tiles);
            }
        }

        // Go through and overwrite with patterns
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                // Skip if there is already a pattern
                if (patternedArea.contains(x, y)) {
                    continue;
                }

                overWriteWithPatterns(block, patternedArea, patterns, height, width, x, y);
            }
        }

        return block;
    }
}

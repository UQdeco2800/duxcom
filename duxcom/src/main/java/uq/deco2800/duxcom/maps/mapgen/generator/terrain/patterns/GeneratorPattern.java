package uq.deco2800.duxcom.maps.mapgen.generator.terrain.patterns;

import java.util.Random;

import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.util.Array2D;

/**
 * Stores data regarding a default pattern that can be found in a map
 * <p>
 * Created by liamdm on 17/08/2016.
 */
public abstract class GeneratorPattern {
    /**
     * Generate a pattern using the given random generator
     *
     * @param random
     */
    abstract void generatePattern(Random random);

    /**
     * The minimum distance between these patterns that must exist
     * for them to be spawned
     *
     * @return
     */
    private int minDistance;


    /**
     * Gets the minimum distance this should be from other patterns
     * @return the minimum distance
     */
    public int getMinimumDistance(){
        return minDistance;
    }

    /**
     * Generate the pattern when the class is loaded
     */
    public GeneratorPattern(Double occurenceProbability, int maxPerBlock, int minDistance) {
        this.occurrenceProbability = occurenceProbability;
        this.maxPerBlock = maxPerBlock;
        this.minDistance = minDistance;
    }

    /**
     * The maximum number of these patterns per block
     */
    protected int maxPerBlock = 0;

    /**
     * The probability of this pattern spawning
     */
    protected double occurrenceProbability = 0;

    /**
     * The number of this pattern placed in a block
     */
    protected int numberPlaced = 0;

    /**
     * Notifies this generator pattern that it has been placed
     * in a block once
     */
    public int markPlaced() {
        ++numberPlaced;
        return numberPlaced;
    }

    /**
     * Resets the number placed per block to 0
     */
    public void resetNumberPlaced() {
        numberPlaced = 0;
    }

    /**
     * The width of the pattern
     */
    protected int width = 0;

    /**
     * The height of the pattern
     */
    protected int height = 0;

    /**
     * The pattern itself
     */
    protected Array2D<TileType> pattern;

    /**
     * Gets the pattern and fills all missing areas
     * with the specified void fill
     */
    public Array2D<TileType> getPattern(Random random) {
        // Regenerate the internal pattern with the given random
        generatePattern(random);

        Array2D<TileType> patternCopy = new Array2D<>(pattern.getWidth(), pattern.getHeight());

        /**
         * Copy the array
         */
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                TileType targetTile = pattern.get(x, y);
                patternCopy.set(x, y, targetTile);
            }
        }

        return patternCopy;
    }

    /**
     * The maximum number of these patterns per block
     */
    public int getMaxPerBlock() {
        return maxPerBlock;
    }

    /**
     * The probability of this pattern spawning
     */
    public double getOccurrenceProbability() {
        return occurrenceProbability;
    }

    /**
     * The width of the pattern
     */
    public int getWidth() {
        return width;
    }

    /**
     * The height of the pattern
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns true if the generator pattern should be placed
     * (a random number exceeds the probability), given a random
     * generator which should be seeded to allow re-creation.
     *
     * @param r
     * @return boolean based on the randomly generated number
     */
    public boolean shouldPlace(Random r) {
        return r.nextDouble() < occurrenceProbability && numberPlaced < maxPerBlock;
    }

    /**
     * Returns true if the pattern fits at the given location on the given map
     *
     * @param x
     * @param y
     * @param mapWidth
     * @param mapHeight
     * @return true iff the pattern will fit
     */
    public boolean doesPatternFit(int x, int y, int mapWidth, int mapHeight) {
        return x + width < mapWidth && y + height < mapHeight && x >= 0 && y >= 0;
    }


}

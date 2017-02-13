package uq.deco2800.duxcom.maps.mapgen.generator.terrain.patterns;

import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.util.Array2D;

import java.util.Random;

/**
 * The test water pattern
 * <p>
 * Created by liamdm on 17/08/2016.
 */
public class TestWaterPattern extends GeneratorPattern {

    {
        width = 7;
        height = 7;
    }

    /**
     * Generate the pattern when the class is loaded
     *
     * @param occurenceProbability
     * @param maxPerBlock
     * @param minDistance
     */
    public TestWaterPattern(Double occurenceProbability, int maxPerBlock, int minDistance) {
        super(occurenceProbability, maxPerBlock, minDistance);
    }

    @Override
    void generatePattern(Random random) {
        final int randomBorder = 1;
        pattern = new Array2D<>(width, height);

        for (int x = randomBorder; x < width - randomBorder; ++x) {
            for (int y = randomBorder; y < height - randomBorder; ++y) {
                pattern.set(x, y, TileType.REAL_WATER);
            }
        }

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (y >= randomBorder && y < height - randomBorder && x >= randomBorder && x < width - randomBorder) {
                    continue;
                }

                // generate random edges
                if(random.nextDouble() > 0.5) {
                    pattern.set(x, y, TileType.REAL_WATER);
                }
            }
        }
    }

}

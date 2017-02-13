package uq.deco2800.duxcom.maps.mapgen.generator.entities.objects;

import java.util.Random;

/**
 * Used to spawn specific entites with the given probabilities
 *
 * Created by liamdm on 24/08/2016.
 */
public class GeneratorEntity {
    private final String tileResource;
    private final double probability;

    public GeneratorEntity(String tileResource, double probability) {
        this.tileResource = tileResource;
        this.probability = probability;
    }

    /**
     * Returns true if the tile should be placed given
     * a random number generator which should be seeded
     * to allow re-creation
     *
     * @param random random number generator
     * @return boolean based on the random number that is generated
     */
    public boolean shouldPlace(Random random){
        return random.nextDouble() < probability;
    }

    /**
     * Return the probability this block will spawn.
     * @return probability of the block spawning
     */
    public double getProbability(){
        return probability;
    }

    /**
     * Return the resource this tile is based on
     *
     * @return the resource representing the tile's texture
     */
    public String getResource(){
        return tileResource;
    }

}

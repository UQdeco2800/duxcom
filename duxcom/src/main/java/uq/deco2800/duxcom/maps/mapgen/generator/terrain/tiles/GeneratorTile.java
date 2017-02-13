package uq.deco2800.duxcom.maps.mapgen.generator.terrain.tiles;

import java.util.Random;

import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.maps.mapgen.textures.TextureVariantRegister;

/**
 * Stores a tile and the relative frequency of occurence
 *
 * Created by liamdm on 19/08/2016.
 */
public class GeneratorTile {
    private final TileType tileType;
    private final double probability;

    public GeneratorTile(TileType tileType, double probability) {
        this.tileType = tileType;
        this.probability = probability;
    }

    /**
     * Returns true if the tile should be placed given
     * a random number generator which should be seeded
     * to allow re-creation
     *
     * @param random
     * @return true iff should be placed
     */
    public boolean shouldPlace(Random random){
        return random.nextDouble() < probability;
    }

    /**
     * Return the probability this block will spawn.
     * @return the probability
     */
    public double getProbability(){
        return probability;
    }

    /**
     * Return the resource this tile is based on
     *
     * @return the tile type
     */
    public TileType getResource(){
        return tileType;
    }

    public TileType getVariantResource(Random random){
        return TextureVariantRegister.getVariantTile(tileType, random);
    }

}

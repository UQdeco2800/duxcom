package uq.deco2800.duxcom.maps.mapgen.textures;

import uq.deco2800.duxcom.tiles.TileType;

import java.util.ArrayList;
import java.util.Random;

/**
 * Stores multiple variants of a single textue so tiles can be varied transparently.
 *
 * Created by liamdm on 4/09/2016.
 */
public class TextureVariant {

    // The texture name this is a variant for
    private TileType originTexture;

    /**
     * Constructs a TextureVariant from a given texture
     *
     * @param originTexture the original texture of the variant
     */
    public TextureVariant(TileType originTexture) {
        this.originTexture = originTexture;
    }

    // The named variants list
    private ArrayList<TileType> variants = new ArrayList<>();

    /**
     * Add a variant to the register
     * @param variant
     */
    public void addVariant(TileType variant){
        variants.add(variant);
    }

    /**
     * Gets a variant using the given random generator
     *
     * @param random the random generator to use
     * @return the tile type
     */
    public TileType getVariant(Random random){
        if(variants.isEmpty()){
            return originTexture;
        }

        int value = random.nextInt(variants.size());
        return variants.get(value);
    }

    /**
     * Gets the original texture of the variant
     *
     * @return original texture
     */
    public TileType getOriginTexture() {
        return originTexture;
    }
}

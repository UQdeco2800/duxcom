package uq.deco2800.duxcom.maps.mapgen.textures;

import java.util.HashMap;
import java.util.Random;

import uq.deco2800.duxcom.annotation.UtilityConstructor;
import uq.deco2800.duxcom.tiles.TileType;

/**
 * Stores a list of texture variants that can be used
 *
 * Created by liamdm on 4/09/2016.
 */
public class TextureVariantRegister {
    private static HashMap<TileType, TextureVariant> variantMap = new HashMap<>();

    /**
     * Returns true if the origin texture is registered for variation
     *
     * @param originTexture
     * @return true iff registered for variation
     */
    public static boolean contains(String originTexture){
        return variantMap.containsKey(originTexture);
    }

    /**
     * Gets a texture variant map for the given origin texture
     *
     * @param textureOrigin the texture to vary
     * @return the texture variant
     */
    public static TextureVariant getVariant(TileType textureOrigin){
        if(!variantMap.containsKey(textureOrigin)){
            return null;
        }
        return variantMap.get(textureOrigin);
    }

    /**
     * Gets the variant tile name for a given texture origin
     *
     * @param textureOrigin the texture to vary
     * @return the tile type
     */
    public static TileType getVariantTile(TileType textureOrigin, Random random){
        TextureVariant tv = getVariant(textureOrigin);
        return tv == null ? textureOrigin : tv.getVariant(random);
    }

    /**
     * Assigns the given texture origin to the given variant map
     *
     * @param variant the varier
     */
    public static void addVariant(TextureVariant variant){
        variantMap.putIfAbsent(variant.getOriginTexture(), variant);
    }

    static {
        /* remember
        TextureVariant grassDarkVariant = new TextureVariant(TileType.DT_GRASS_DARK_1);
            for(int i = 1; i <= 5; ++i) {
                String s = "dt_grass_dark_" + String.valueOf(i);
                grassDarkVariant.addVariant(s);
            }

        TextureVariant grassLightVariant = new TextureVariant(TileType.DT_GRASS_LIGHT_1);
            for(int i = 1; i <= 5; ++i) {
                String s = "dt_grass_light_" + String.valueOf(i);
                grassLightVariant.addVariant(s);
            }


        TextureVariant grassMidVariant = new TextureVariant(TileType.DT_GRASS_MID_1);
            for(int i = 1; i <= 5; ++i) {
                String s = "dt_grass_mid_" + String.valueOf(i);
                grassMidVariant.addVariant(s);
            }

        TextureVariant sandDarkVariant = new TextureVariant(TileType.DT_SAND_DARK_1);
            for(int i = 1; i <= 5; ++i) {
                String s = "dt_sand_dark_" + String.valueOf(i);
                sandDarkVariant.addVariant(s);
            }

        TextureVariant sandLightVariant = new TextureVariant("sand_light");
            for(int i = 1; i <= 5; ++i) {
                String s = "dt_sand_light_" + String.valueOf(i);
                sandLightVariant.addVariant(s);
            }


        TextureVariant sandMidVariant = new TextureVariant("sand_mid");
            for(int i = 1; i <= 5; ++i) {
                String s = "dt_sand_mid_" + String.valueOf(i);
                sandMidVariant.addVariant(s);
            }

        TextureVariant waterDarkVariant = new TextureVariant("water_dark");
            for(int i = 1; i <= 5; ++i) {
                String s = "dt_water_dark_" + String.valueOf(i);
                waterDarkVariant.addVariant(s);
            }

        TextureVariant waterLightVariant = new TextureVariant("water_light");
            for(int i = 1; i <= 5; ++i) {
                String s = "dt_water_light_" + String.valueOf(i);
                waterLightVariant.addVariant(s);
            }


        TextureVariant waterMidVariant = new TextureVariant("water_mid");
            for(int i = 1; i <= 5; ++i) {
                String s = "dt_water_mid_" + String.valueOf(i);
                waterMidVariant.addVariant(s);
            }

        addVariant(grassDarkVariant);
        addVariant(grassLightVariant);
        addVariant(grassMidVariant);

        addVariant(sandDarkVariant);
        addVariant(sandLightVariant);
        addVariant(sandMidVariant);

        addVariant(waterDarkVariant);
        addVariant(waterLightVariant);
        addVariant(waterMidVariant);
*/

    }

    @UtilityConstructor
    private TextureVariantRegister(){}
}

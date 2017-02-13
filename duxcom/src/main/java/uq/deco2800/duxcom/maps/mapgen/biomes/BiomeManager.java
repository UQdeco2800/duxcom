package uq.deco2800.duxcom.maps.mapgen.biomes;

import uq.deco2800.duxcom.annotation.MethodContract;
import uq.deco2800.duxcom.annotation.UtilityConstructor;
import uq.deco2800.duxcom.maps.mapgen.biomes.biomes.Biome;
import uq.deco2800.duxcom.maps.mapgen.biomes.biomes.DesertBiome;
import uq.deco2800.duxcom.maps.mapgen.biomes.biomes.TestBiome;
import uq.deco2800.duxcom.maps.mapgen.biomes.biomes.TestBiome2;

import java.util.HashMap;

/**
 * Handles the generation of biomes by using a set of BiomeGenerators
 *
 * Created by liamdm on 17/08/2016.
 */
public class BiomeManager {

    /**
     * The map of biomes to biome types
     */
    static HashMap<BiomeType, Biome> biomeTypeMap = new HashMap<>();

    static {
        setBiome(BiomeType.TEST_REALISTIC, new TestBiome());
        setBiome(BiomeType.TEST_OBVIOUS, new TestBiome2());
        setBiome(BiomeType.DESERT, new DesertBiome());
    }

    /**
     * Sets the biome that applies to the given BiomeType
     */
    @MethodContract(precondition = {"biomeType != null", "biome != null"})
    public static void setBiome(BiomeType biomeType, Biome biome){
        biomeTypeMap.put(biomeType, biome);
    }

    /**
     * Returns true if this class has a biome for the given biome type
     * @return true iff has biome
     */
    @MethodContract(precondition = {"biomeType != null"})
    public static boolean hasGenerator(BiomeType biomeType){
        return biomeTypeMap.containsKey(biomeType);
    }

    /**
     * Gets the biome for a given biome type or null if the generator cannot be found.
     * @return the biome
     */
    @MethodContract(precondition = {"biomeType != null"})
    public static Biome getBiome(BiomeType biomeType){
        return !biomeTypeMap.containsKey(biomeType) ? null : biomeTypeMap.get(biomeType);
    }

    @UtilityConstructor
    private BiomeManager(){}
}

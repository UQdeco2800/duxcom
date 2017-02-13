package uq.deco2800.duxcom.maps.mapgen.biomes.biomes;

import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.maps.mapgen.biomes.BiomeType;
import uq.deco2800.duxcom.maps.mapgen.generator.terrain.tiles.GeneratorTile;

/**
 * Is a desert biome with lots of sand.
 *
 * Created by liamdm on 4/09/2016.
 */
public class DesertBiome extends AbstractBiome {

    public DesertBiome(){
        genericTiles.add(new GeneratorTile(TileType.DT_SAND_DARK_1, 0.4));
        genericTiles.add(new GeneratorTile(TileType.DT_SAND_MID_1, 0.3));
        genericTiles.add(new GeneratorTile(TileType.DT_SAND_LIGHT_1, 0.2));
    }

    @Override
    public BiomeType getBiomeType() {
        return BiomeType.DESERT;
    }

    @Override
    public TileType getDefaultTileResource() {
        return TileType.DT_SAND_LIGHT_1;
    }
}

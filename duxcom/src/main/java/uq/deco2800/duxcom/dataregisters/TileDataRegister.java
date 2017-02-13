package uq.deco2800.duxcom.dataregisters;

import uq.deco2800.duxcom.tiles.TileType;

/**
 * Links each tile type from the TileType enum with a set of data from TileDataClass.
 * Also stores the TILE_HEIGHT and TILE_WIDTH static variables.
 *
 * @author liamdm
 * @author Alex McLean
 */
public class TileDataRegister extends AbstractDataRegister<TileType, TileDataClass> {
    public static final int TILE_HEIGHT = 100;
    public static final int TILE_WIDTH = 174;
    static final String GREEN = "green";
    static final String DARK_GREEN = "darkgreen";
    static final String YELLOW = "yellow";
    static final String BLUE = "blue";
    static final String BLACK = "black";
    static final String DARK_TURQUOISE = "darkturquoise";
    static final String OLD_LACE = "oldlace";
    static final String DEEP_SKY_BLUE = "deepskyblue";
    static final String LIGHT_BLUE = "lightblue";
    static final String WHEAT = "wheat";
    static final String PLUM = "plum";
    static final String CRIMSON = "crimson";
    static final String DARK_BLUE = "darkblue";

    /**
     * Package private constructor
     *
     * Should only be called by DataRegisterManager
     */
    TileDataRegister() {
        super();
        linkDataToType(TileType.VOID, new TileDataClass("void", "void", BLACK));
        linkDataToType(TileType.GRASS_1, new TileDataClass("grass_1", "grass_1", GREEN));
        linkDataToType(TileType.GRASS_2, new TileDataClass("grass_2", "grass_2", GREEN));
        linkDataToType(TileType.GRASS_3, new TileDataClass("grass_3", "grass_3", GREEN));
        linkDataToType(TileType.WATER, new TileDataClass("water", "water", DARK_TURQUOISE, 5));

        // RuneScape textures
        linkDataToType(TileType.RS_GRASS_1, new TileDataClass("rs_grass1", "rs_grass1", GREEN));
        linkDataToType(TileType.RS_GRASS_2, new TileDataClass("rs_grass2", "rs_grass2", GREEN));
        linkDataToType(TileType.RS_FLOOR1, new TileDataClass("rs_floor1", "rs_floor1", GREEN));
        linkDataToType(TileType.RS_FLOOR2, new TileDataClass("rs_floor2", "rs_floor2", GREEN));
        linkDataToType(TileType.RS_MUD1, new TileDataClass("rs_mud1", "rs_mud1", GREEN, 3));
        linkDataToType(TileType.RS_MUD2, new TileDataClass("rs_mud2", "rs_mud2", GREEN, 3));
        linkDataToType(TileType.RS_PATH1, new TileDataClass("rs_path1", "rs_path1", GREEN));
        linkDataToType(TileType.RS_PATH2, new TileDataClass("rs_path2", "rs_path2", GREEN));
        linkDataToType(TileType.RS_PATH3, new TileDataClass("rs_path3", "rs_path3", GREEN));
        linkDataToType(TileType.RS_PATH4, new TileDataClass("rs_path4", "rs_path4", GREEN));
        linkDataToType(TileType.RS_SNOW1, new TileDataClass("rs_snow1", "rs_snow1", WHEAT, 2));
        linkDataToType(TileType.RS_SNOW2, new TileDataClass("rs_snow2", "rs_snow2", WHEAT, 2));
        linkDataToType(TileType.RS_SAND1, new TileDataClass("rs_sand1", "rs_sand1", YELLOW, 2));
        linkDataToType(TileType.RS_SAND2, new TileDataClass("rs_sand2", "rs_sand2", YELLOW, 2));

        // Realistic textures
        linkDataToType(TileType.REAL_GRASS_1, new TileDataClass("real_grass_1", "real_grass_1", DARK_GREEN));
        linkDataToType(TileType.REAL_GRASS_2, new TileDataClass("real_grass_2", "real_grass_2", DARK_GREEN));
        linkDataToType(TileType.REAL_GRASS_3, new TileDataClass("real_grass_3", "real_grass_3", DARK_GREEN));
        linkDataToType(TileType.REAL_WATER, new TileDataClass("real_water", "real_water", DEEP_SKY_BLUE, 5));
        linkDataToType(TileType.REAL_FOUNTAIN, new TileDataClass("real_fountain", "real_fountain", LIGHT_BLUE, 5));
        linkDataToType(TileType.REAL_SANDSTONEBRICK, new TileDataClass("real_sandstonebrick", "real_sandstonebrick", WHEAT));
        linkDataToType(TileType.REAL_SANDSTONE_1, new TileDataClass("real_sandstone_1", "real_sandstone_1", OLD_LACE));
        linkDataToType(TileType.REAL_SANDSTONE_2, new TileDataClass("real_sandstone_2", "real_sandstone_2", WHEAT));
        linkDataToType(TileType.REAL_SANDSTONE_3, new TileDataClass("real_sandstone_3", "real_sandstone_3", OLD_LACE));
        linkDataToType(TileType.REAL_JACARANDA, new TileDataClass("real_jacaranda", "real_jacaranda", PLUM));
        linkDataToType(TileType.REAL_MAGMA, new TileDataClass("real_magma", "real_magma", CRIMSON, 10));
        linkDataToType(TileType.REAL_QUT, new TileDataClass("real_qut", "real_qut", DARK_BLUE));

        // Danktextures
        linkDataToType(TileType.DT_GRASS_DARK_1, new TileDataClass("dt_grass_dark_1", "dt_grass_dark_1", DARK_GREEN));
        linkDataToType(TileType.DT_GRASS_DARK_2, new TileDataClass("dt_grass_dark_2", "dt_grass_dark_2", DARK_GREEN));
        linkDataToType(TileType.DT_GRASS_DARK_3, new TileDataClass("dt_grass_dark_3", "dt_grass_dark_3", DARK_GREEN));
        linkDataToType(TileType.DT_GRASS_DARK_4, new TileDataClass("dt_grass_dark_4", "dt_grass_dark_4", DARK_GREEN));
        linkDataToType(TileType.DT_GRASS_DARK_5, new TileDataClass("dt_grass_dark_5", "dt_grass_dark_5", DARK_GREEN));
        linkDataToType(TileType.DT_GRASS_LIGHT_1, new TileDataClass("dt_grass_light_1", "dt_grass_light_1", GREEN));
        linkDataToType(TileType.DT_GRASS_LIGHT_2, new TileDataClass("dt_grass_light_2", "dt_grass_light_2", GREEN));
        linkDataToType(TileType.DT_GRASS_LIGHT_3, new TileDataClass("dt_grass_light_3", "dt_grass_light_3", GREEN));
        linkDataToType(TileType.DT_GRASS_LIGHT_4, new TileDataClass("dt_grass_light_4", "dt_grass_light_4", GREEN));
        linkDataToType(TileType.DT_GRASS_LIGHT_5, new TileDataClass("dt_grass_light_5", "dt_grass_light_5", GREEN));
        linkDataToType(TileType.DT_GRASS_MID_1, new TileDataClass("dt_grass_mid_1", "dt_grass_mid_1", GREEN));
        linkDataToType(TileType.DT_GRASS_MID_2, new TileDataClass("dt_grass_mid_2", "dt_grass_mid_2", GREEN));
        linkDataToType(TileType.DT_GRASS_MID_3, new TileDataClass("dt_grass_mid_3", "dt_grass_mid_3", GREEN));
        linkDataToType(TileType.DT_GRASS_MID_4, new TileDataClass("dt_grass_mid_4", "dt_grass_mid_4", GREEN));
        linkDataToType(TileType.DT_GRASS_MID_5, new TileDataClass("dt_grass_mid_5", "dt_grass_mid_5", GREEN));

        linkDataToType(TileType.DT_SAND_DARK_1, new TileDataClass("dt_sand_dark_1", "dt_sand_dark_1", YELLOW, 2));
        linkDataToType(TileType.DT_SAND_DARK_2, new TileDataClass("dt_sand_dark_2", "dt_sand_dark_2", YELLOW, 2));
        linkDataToType(TileType.DT_SAND_DARK_3, new TileDataClass("dt_sand_dark_3", "dt_sand_dark_3", YELLOW, 2));
        linkDataToType(TileType.DT_SAND_DARK_4, new TileDataClass("dt_sand_dark_4", "dt_sand_dark_4", YELLOW, 2));
        linkDataToType(TileType.DT_SAND_DARK_5, new TileDataClass("dt_sand_dark_5", "dt_sand_dark_5", YELLOW, 2));
        linkDataToType(TileType.DT_SAND_LIGHT_1, new TileDataClass("dt_sand_light_1", "dt_sand_light_1", YELLOW, 2));
        linkDataToType(TileType.DT_SAND_LIGHT_2, new TileDataClass("dt_sand_light_2", "dt_sand_light_2", YELLOW, 2));
        linkDataToType(TileType.DT_SAND_LIGHT_3, new TileDataClass("dt_sand_light_3", "dt_sand_light_3", YELLOW, 2));
        linkDataToType(TileType.DT_SAND_LIGHT_4, new TileDataClass("dt_sand_light_4", "dt_sand_light_4", YELLOW, 2));
        linkDataToType(TileType.DT_SAND_LIGHT_5, new TileDataClass("dt_sand_light_5", "dt_sand_light_5", YELLOW, 2));
        linkDataToType(TileType.DT_SAND_MID_1, new TileDataClass("dt_sand_mid_1", "dt_sand_mid_1", YELLOW, 2));
        linkDataToType(TileType.DT_SAND_MID_2, new TileDataClass("dt_sand_mid_2", "dt_sand_mid_2", YELLOW, 2));
        linkDataToType(TileType.DT_SAND_MID_3, new TileDataClass("dt_sand_mid_3", "dt_sand_mid_3", YELLOW, 2));
        linkDataToType(TileType.DT_SAND_MID_4, new TileDataClass("dt_sand_mid_4", "dt_sand_mid_4", YELLOW, 2));
        linkDataToType(TileType.DT_SAND_MID_5, new TileDataClass("dt_sand_mid_5", "dt_sand_mid_5", YELLOW, 2));

        linkDataToType(TileType.DT_WATER_DARK_1, new TileDataClass("dt_water_dark_1", "dt_water_dark_1", BLUE, 5));
        linkDataToType(TileType.DT_WATER_DARK_2, new TileDataClass("dt_water_dark_2", "dt_water_dark_2", BLUE, 5));
        linkDataToType(TileType.DT_WATER_DARK_3, new TileDataClass("dt_water_dark_3", "dt_water_dark_3", BLUE, 5));
        linkDataToType(TileType.DT_WATER_DARK_4, new TileDataClass("dt_water_dark_4", "dt_water_dark_4", BLUE, 5));
        linkDataToType(TileType.DT_WATER_DARK_5, new TileDataClass("dt_water_dark_5", "dt_water_dark_5", BLUE, 5));
        linkDataToType(TileType.DT_WATER_LIGHT_1, new TileDataClass("dt_water_light_1", "dt_water_light_1", BLUE, 5));
        linkDataToType(TileType.DT_WATER_LIGHT_2, new TileDataClass("dt_water_light_2", "dt_water_light_2", BLUE, 5));
        linkDataToType(TileType.DT_WATER_LIGHT_3, new TileDataClass("dt_water_light_3", "dt_water_light_3", BLUE, 5));
        linkDataToType(TileType.DT_WATER_LIGHT_4, new TileDataClass("dt_water_light_4", "dt_water_light_4", BLUE, 5));
        linkDataToType(TileType.DT_WATER_LIGHT_5, new TileDataClass("dt_water_light_5", "dt_water_light_5", BLUE, 5));
        linkDataToType(TileType.DT_WATER_MID_1, new TileDataClass("dt_water_mid_1", "dt_water_mid_1", BLUE, 5));
        linkDataToType(TileType.DT_WATER_MID_2, new TileDataClass("dt_water_mid_2", "dt_water_mid_2", BLUE, 5));
        linkDataToType(TileType.DT_WATER_MID_3, new TileDataClass("dt_water_mid_3", "dt_water_mid_3", BLUE, 5));
        linkDataToType(TileType.DT_WATER_MID_4, new TileDataClass("dt_water_mid_4", "dt_water_mid_4", BLUE, 5));
        linkDataToType(TileType.DT_WATER_MID_5, new TileDataClass("dt_water_mid_5", "dt_water_mid_5", BLUE, 5));
    }
}

package uq.deco2800.duxcom.graphics.scenery;

/**
 * Stores a register of SceneryType types
 *
 * You must update the ENUM (PURPLE) to a unique value
 * and the String ("green_in_qoutations") to the registered name of your graphic
 *
 * The String (registeredName) must be registered in TextureRegister
 *
 * Created by jay-grant on 9/10/2016.
 */
public enum SceneryType {

    // SceneryTypes go here
    TOMBSTONE("rs_tomb"),
    HEROGRAVE("rs_tomb"),
    ENEMYGRAVE("rs_magma"),
    RS_MAGMA("rs_magma"),
    RS_BUSH("rs_bush"),
    RS_TREE_BASIC("rs_tree_basic"),
    RS_TREE_BASIC_BURNED("rs_tree_basic_burned"),
    RS_TREE_BRANCH("rs_tree_branch"),
    RS_TREE_BRANCH_BURNED("rs_tree_branch_burned"),
    RS_TREE_PINE("rs_tree_pine"),
    RS_TREE_PINE_BURNED("rs_tree_pine_burned"),
    RS_ROCK_LOW("rs_rock_low"),
    RS_ROCK_HIGH("rs_rock_high"),
    RS_ROCK_VANTAGE("rs_rock_vantage", 0.62),
    RS_WATER_ROCK("rs_water_rock"),
    RS_CAMPFIRE("rs_campfire"),
    RS_PADS_GRASS("rs_pads_grass"),
    RS_WALL_STONE("rs_wall_stone"),
    RS_CASTLE("rs_castle", 3.6),
    RS_BRIDGE("bridge", 1.0),
    WALL_STONE_LEFT("wall_stone_left"),
    WALL_STONE_RIGHT("wall_stone_right"),
    WALL_STONE_TOWER("wall_stone_tower"),
    WALL_STONE_CORNER_NORTH("wall_stone_corner_north"),
    WALL_STONE_CORNER_SOUTH("wall_stone_corner_south"),
    BIG_HOUSE("big_house"),
    SMALL_HUT("small_hut"),
    TOWER("tower"),
    CACTUS("cactus"),
    CACTUS_SMALL("cactus_small_1"),
    CACTUS_SMALL_2("cactus_small_2"),
    DESERT_PLANT("desert_plant"),
    IGLOO("igloo"),
    SNOWMAN("snowman"),
    SNOW_TREE("snow_tree"),
    MOUNTAIN("mountain", 1),
    BRIDGE_NEAR_END("bridge_near_end", 0.26),
    BRIDGE_NEAR_RAMP("bridge_near_ramp", 0.5),
    BRIDGE_MIDDLE("bridge_middle", 0.5),
    BRIDGE_FAR_RAMP("bridge_far_ramp", 0.5),
    BRIDGE_FAR_END("bridge_far_end", 0.26),
    INVISIBLE("dead_pixel_shadow"),
    BILLBOARD("billboard"),
    ;
    // they do not go down here -----
    private String resourceName;
    private double height;

    SceneryType(String string, double height) {
        this.resourceName = string;
        this.height = height;
    }

    SceneryType(String string) {
        this.resourceName = string;
        this.height = 1;
    }

    /**
     * Returns the height (elevation) of the SceneryType if one has been
     * assigned.
     *
     * @return SceneryType height.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the assigned Texture String.
     *
     * @return the assigned texture string.
     */
    @Override
    public String toString() {
        return resourceName;
    }
}

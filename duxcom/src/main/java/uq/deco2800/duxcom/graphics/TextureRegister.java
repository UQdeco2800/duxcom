package uq.deco2800.duxcom.graphics;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.GameLoop;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles the storing of in game textures.
 *
 * @author liamdm
 * @author Alex McLean
 */
public class TextureRegister {

    TextureRegister() {
        // Makes a juice register full of textures
    }

    private static Logger logger = LoggerFactory.getLogger(TextureRegister.class);

    private static final String PLACEHOLDER_IMAGE = "/items/placeholder.png";

    private static ConcurrentHashMap<String, String> resourceLinkMap = new ConcurrentHashMap<>();

    // Stores a cache of images
    private static ConcurrentHashMap<String, Image> resourceCache = new ConcurrentHashMap<>();

    private static HashSet<String> dontSplit = new HashSet<>();

    static {
        
        /*
        Textures that should be considered to have shadows go in here
         */
        final String[][] shadowedTextures = {

                // Water Barrel
                {"water_barrel_1", "/dynamics/barrel_water_1.png"},
                {"water_barrel_2", "/dynamics/barrel_water_2.png"},
                {"water_barrel_3", "/dynamics/barrel_water_3.png"},
                {"water_barrel_4", "/dynamics/barrel_water_4.png"},
                {"water_barrel_5", "/dynamics/barrel_water_5.png"},
                {"water_barrel_6", "/dynamics/barrel_water_6.png"},
                {"water_barrel_7", "/dynamics/barrel_water_7.png"},

                // Falador Scenery
                {"rs_tomb", "/falador/tombstone.png"},
                {"rs_magma", "/falador/magma_exp.png"},
                {"rs_bush", "/falador/bush.png"},
                {"rs_tree_basic", "/falador/tree_basic.png"},
                {"rs_tree_basic_burned", "/falador/tree_basic_burned.png"},
                {"rs_tree_branch", "/falador/tree_branch.png"},
                {"rs_tree_branch_burned", "/falador/tree_branch_burned.png"},
                {"rs_tree_pine", "/falador/pine.png"},
                {"rs_tree_pine_burned", "/falador/pine_burned.png"},
                {"rs_rock_low", "/falador/rock_low.png"},
                {"rs_rock_high", "/falador/rock_high.png"},
                {"rs_rock_vantage", "/falador/rock_vantage.png"},
                {"rs_water_rock", "/falador/water_rock.png"},
                {"rs_campfire", "/falador/campfire5.png"},
                {"rs_pads_grass", "/falador/pads_grass.png"},
                {"rs_wall_stone", "/falador/wall_stone.png"},
                {"rs_castle", "/falador/castle.png"},
                {"bridge", "/falador/bridge.png"},
                {"wall_stone_left", "/falador/wall_stone_left.png"},
                {"wall_stone_right", "/falador/wall_stone_right.png"},
                {"wall_stone_tower", "/falador/wall_stone_tower.png"},
                {"wall_stone_corner_north", "/falador/wall_stone_corner_north.png"},
                {"wall_stone_corner_south", "/falador/wall_stone_corner_south.png"},

                // Scenery
                {"castle", "/falador/castle.png"},
                {"sc_bush", "/sceneryEntities/bush.png"},
                {"sc_tree_basic", "/sceneryEntities/tree_basic.png"},
                {"sc_tree_thicc", "/sceneryEntities/tree_thicc.png"},
                {"sc_tree_branch", "/sceneryEntities/tree_branch.png"},
                {"sc_tree_pine", "/sceneryEntities/tree_pine.png"},
                {"rock_low", "/sceneryEntities/rock_low.png"},
                {"rock_high", "/sceneryEntities/rock_high.png"},
                {"rock_vantage", "/sceneryEntities/rock_vantage.png"},
                {"water_rock", "/sceneryEntities/water_rock.png"},
                {"pads_grass", "/sceneryEntities/pads_grass.png"},
                {"pads_wood", "/sceneryEntities/pads_wood.png"},
                {"big_house", "/sceneryEntities/big_house.png"},
                {"small_hut", "/sceneryEntities/small_hut.png"},
                {"tower", "/sceneryEntities/tower.png"},
                {"cactus", "/sceneryEntities/Cactus_1.png"},
                {"cactus_small_1", "/sceneryEntities/CactusSmall_1.png"},
                {"cactus_small_2", "/sceneryEntities/CactusSmall_2.png"},
                {"desert_plant", "/sceneryEntities/DesertPlants_1.png"},
                {"igloo", "/sceneryEntities/igloo_1.png"},
                {"snowman", "/sceneryEntities/snowman_1.png"},
                {"snow_tree", "/sceneryEntities/tree_snow_1.png"},
                //{"mountain", "/sceneryEntities/tree_snow_1.png"},
                {"mountain", "/falador/mountain/1.png"},

                {"chest", "/items/chest.png"},

                // Skins
                {"test_skin", "/playerRewards/heroPreviews/hero-silhouette.png"},
                {"silver_archer", "/playerRewards/heroPreviews/archer_silver_none.png"},
                {"gold_archer", "/playerRewards/heroPreviews/archer_gold_none.png"},
                {"hat_archer", "/playerRewards/heroPreviews/archer_hat_none.png"},
                {"silver_cavalier", "/playerRewards/heroPreviews/cavalier_silver_none.png"},
                {"gold_cavalier", "/playerRewards/heroPreviews/cavalier_gold_none.png"},
                {"hat_cavalier", "/playerRewards/heroPreviews/cavalier_hat_none.png"},
                {"silver_knight", "/playerRewards/heroPreviews/knight_silver_none.png"},
                {"gold_knight", "/playerRewards/heroPreviews/knight_gold_none.png"},
                {"hat_knight", "/playerRewards/heroPreviews/knight_hat_none.png"},
                {"silver_rogue", "/playerRewards/heroPreviews/rogue_silver_none.png"},
                {"gold_rogue", "/playerRewards/heroPreviews/rogue_gold_none.png"},
                {"hat_rogue", "/playerRewards/heroPreviews/rogue_hat_none.png"},
                {"silver_warlock", "/playerRewards/heroPreviews/warlock_silver_none.png"},
                {"gold_warlock", "/playerRewards/heroPreviews/warlock_gold_none.png"},
                {"hat_warlock", "/playerRewards/heroPreviews/warlock_hat_none.png"},
                {"silver_priest", "/playerRewards/heroPreviews/priest_silver_none.png"},
                {"gold_priest", "/playerRewards/heroPreviews/priest_gold_none.png"},
                {"hat_priest", "/playerRewards/heroPreviews/priest_hat_none.png"},

                {"tombstone", "/items/tombstone.png"},
                {"hero_spawn", "/mapobjects/hero_spawn.png"},
                {"wood_stack", "/mapobjects/wood_stack.png"},

                {"duck", "/heroes/duck.png"},
                {"tall", "/mapobjects/longboystack.png"},
                
                //Archer Sprites
                {"archer_none_none", "/heroes/archer/archer_naked.png"},
                {"archer_bow1_none", "/heroes/archer/archer_bow1.png"},
                {"archer_bow2_none", "/heroes/archer/archer_bow2.png"},
                {"archer_bow3_none", "/heroes/archer/archer_bow3.png"},
                //Cavalier Sprites
                {"cavalier_none_none", "/heroes/cavalier/horseman_naked.png"},
                {"cavalier_none_shield", "/heroes/cavalier/horseman_naked_shield.png"},
                {"cavalier_lance1_none", "/heroes/cavalier/cavalier_lance1.png"},
                {"cavalier_lance2_none", "/heroes/cavalier/cavalier_lance2.png"},
                {"cavalier_lance3_none", "/heroes/cavalier/cavalier_lance3.png"},
                {"cavalier_lance1_shield", "/heroes/cavalier/cavalier_lance1_shield.png"},
                {"cavalier_lance2_shield", "/heroes/cavalier/cavalier_lance2_shield.png"},
                {"cavalier_lance3_shield", "/heroes/cavalier/cavalier_lance3_shield.png"},
                {"cavalier_sword1_none", "/heroes/cavalier/cavalier_sword1.png"},
                {"cavalier_sword2_none", "/heroes/cavalier/cavalier_sword2.png"},
                {"cavalier_sword3_none", "/heroes/cavalier/cavalier_sword3.png"},
                {"cavalier_sword1_shield", "/heroes/cavalier/cavalier_sword1_shield.png"},
                {"cavalier_sword2_shield", "/heroes/cavalier/cavalier_sword2_shield.png"},
                {"cavalier_sword3_shield", "/heroes/cavalier/cavalier_sword3_shield.png"},
                //Knight Sprites
                {"knight_none_none", "/heroes/knight/knight_naked.png"},
                {"knight_none_shield", "/heroes/knight/knight_naked_shield.png"},
                {"knight_sword1_none", "/heroes/knight/knight_sword1.png"},
                {"knight_sword2_none", "/heroes/knight/knight_sword2.png"},
                {"knight_sword3_none", "/heroes/knight/knight_sword3.png"},
                {"knight_sword1_shield", "/heroes/knight/knight_sword1_shield.png"},
                {"knight_sword2_shield", "/heroes/knight/knight_sword2_shield.png"},
                {"knight_sword3_shield", "/heroes/knight/knight_sword3_shield.png"},
                {"knight_hammer1_none", "/heroes/knight/knight_hammer1.png"},
                {"knight_hammer2_none", "/heroes/knight/knight_hammer2.png"},
                {"knight_hammer3_none", "/heroes/knight/knight_hammer3.png"},
                {"knight_hammer1_shield", "/heroes/knight/knight_hammer1_shield.png"},
                {"knight_hammer2_shield", "/heroes/knight/knight_hammer2_shield.png"},
                {"knight_hammer3_shield", "/heroes/knight/knight_hammer3_shield.png"},
                {"knight_axe1_none", "/heroes/knight/knight_axe1.png"},
                {"knight_axe2_none", "/heroes/knight/knight_axe2.png"},
                {"knight_axe3_none", "/heroes/knight/knight_axe3.png"},
                {"knight_axe1_shield", "/heroes/knight/knight_axe1_shield.png"},
                {"knight_axe2_shield", "/heroes/knight/knight_axe2_shield.png"},
                {"knight_axe3_shield", "/heroes/knight/knight_axe3_shield.png"},
                //Priest Sprites
                {"priest_none_none", "/heroes/Priest/priest.png"},
                //Rogue sprites
                {"rogue_none_none", "/heroes/rogue/rogue_naked.png"},
                {"rogue_dagger1_none", "/heroes/rogue/rogue_dagger1.png"},
                {"rogue_dagger2_none", "/heroes/rogue/rogue_dagger2.png"},
                {"rogue_dagger3_none", "/heroes/rogue/rogue_dagger3.png"},
                //Warlock Sprites
                {"warlock_none_none", "/heroes/warlock/warlock_naked.png"},
                {"warlock_staff1_none", "/heroes/warlock/warlock_staff1.png"},
                {"warlock_staff2_none", "/heroes/warlock/warlock_staff2.png"},
                {"warlock_staff3_none", "/heroes/warlock/warlock_staff3.png"},

                // Bridge sprites
                {"bridge_near_end", "/bridge/bridge_bottom_1.png"},
                {"bridge_near_ramp", "/bridge/bridge_bottom_2.png"},
                {"bridge_middle", "/bridge/bridge_middle.png"},
                {"bridge_far_end", "/bridge/bridge_top_1.png"},
                {"bridge_far_ramp", "/bridge/bridge_top_2.png"},

                // Realistic textures
                {"real_wood_stack", "/realistic/wood_stack.png"},
                {"real_ibis", "/realistic/duck.png"},
                {"beta_ibis", "/realistic/beta_ibis.png"},

                // Enemy Sprites
                {"enemy_knight", "/enemies/dark_knight.png"},
                {"enemy_support", "/enemies/plague_doctor.png"},
                {"enemy_tank", "/enemies/paladin.png"},
                {"enemy_archer", "/enemies/archer.png"},
                {"enemy_cavalier", "/enemies/brute.png"},
                {"enemy_dark_mage", "/enemies/dark_mage.png"},
                {"enemy_grunt", "/enemies/grunt.png"},
                {"enemy_rogue", "/enemies/rogue.png"},
                {"wolf", "/enemies/wolf.png"},
                {"fox", "/enemies/fox.png"},
                {"bear", "/enemies/bear.png"},

                // Dynamics Sprites
                {"cavalier_aqua", "/dynamics/cavalier_aqua.png"},
                {"cavalier_blue", "/dynamics/cavalier_blue.png"},
                {"cavalier_green", "/dynamics/cavalier_green.png"},
                {"cavalier_orange", "/dynamics/cavalier_orange.png"},
                {"cavalier_pink", "/dynamics/cavalier_pink.png"},
                {"cavalier_purple", "/dynamics/cavalier_purple.png"},
                {"cavalier_red", "/dynamics/cavalier_red.png"},
                {"cavalier_yellow", "/dynamics/cavalier_yellow.png"},

                // Dynamic tiles
                {"water_1", "/dynamics/water_1.png"},
                {"water_2", "/dynamics/water_2.png"},
                {"water_3", "/dynamics/water_3.png"},
                {"water_4", "/dynamics/water_4.png"},
                {"water_5", "/dynamics/water_5.png"},
                {"water_6", "/dynamics/water_6.png"},
                {"water_barrel", "/dynamics/water_barrel.png"},

                {"liquid_lava_1", "/dynamics/lava_1.png"},
                {"liquid_lava_2", "/dynamics/lava_2.png"},
                {"liquid_lava_3", "/dynamics/lava_3.png"},
                {"liquid_lava_4", "/dynamics/lava_4.png"},
                {"liquid_lava_5", "/dynamics/lava_5.png"},
                {"liquid_lava_6", "/dynamics/lava_6.png"},

                // Billboard
                {"billboard", "/sceneryEntities/billboard.png"},


                // Locks
                {"LOCK_0001", "/overWorld/lock/lock0001.png"},
                {"LOCK_0002", "/overWorld/lock/lock0002.png"},
                {"LOCK_0003", "/overWorld/lock/lock0003.png"},
                {"LOCK_0004", "/overWorld/lock/lock0004.png"},
                {"LOCK_0005", "/overWorld/lock/lock0005.png"},
                {"LOCK_0006", "/overWorld/lock/lock0006.png"},
                {"LOCK_0007", "/overWorld/lock/lock0007.png"},
                {"LOCK_0008", "/overWorld/lock/lock0008.png"},
                {"LOCK_0009", "/overWorld/lock/lock0009.png"},
                {"LOCK_0010", "/overWorld/lock/lock0010.png"},
                {"LOCK_0011", "/overWorld/lock/lock0011.png"},
                {"LOCK_0012", "/overWorld/lock/lock0012.png"},
                {"LOCK_0013", "/overWorld/lock/lock0013.png"},
                {"LOCK_0014", "/overWorld/lock/lock0014.png"},
                {"LOCK_0015", "/overWorld/lock/lock0015.png"},
                {"LOCK_0016", "/overWorld/lock/lock0016.png"},
                {"LOCK_0017", "/overWorld/lock/lock0017.png"},
                {"LOCK_0018", "/overWorld/lock/lock0018.png"},
                {"LOCK_0019", "/overWorld/lock/lock0019.png"},
                {"LOCK_0020", "/overWorld/lock/lock0020.png"},
                {"LOCK_0021", "/overWorld/lock/lock0021.png"},
                {"LOCK_0022", "/overWorld/lock/lock0022.png"},
                {"LOCK_0023", "/overWorld/lock/lock0023.png"},
                {"LOCK_0024", "/overWorld/lock/lock0024.png"},
                {"LOCK_0025", "/overWorld/lock/lock0025.png"},
                {"LOCK_0026", "/overWorld/lock/lock0026.png"},
                {"LOCK_0027", "/overWorld/lock/lock0027.png"},
                {"LOCK_0028", "/overWorld/lock/lock0028.png"},
                {"LOCK_0029", "/overWorld/lock/lock0029.png"},
                {"LOCK_0030", "/overWorld/lock/lock0030.png"},
                {"LOCK_0031", "/overWorld/lock/lock0031.png"},
                {"LOCK_0032", "/overWorld/lock/lock0032.png"},
                {"LOCK_0033", "/overWorld/lock/lock0033.png"},
                {"LOCK_0034", "/overWorld/lock/lock0034.png"},
                {"LOCK_0035", "/overWorld/lock/lock0035.png"},
                {"LOCK_0036", "/overWorld/lock/lock0036.png"},
                {"LOCK_0037", "/overWorld/lock/lock0037.png"},
                {"LOCK_0038", "/overWorld/lock/lock0037.png"},
                {"LOCK_0040", "/overWorld/lock/lock0038.png"},
                {"LOCK_0041", "/overWorld/lock/lock0039.png"},
                {"LOCK_0042", "/overWorld/lock/lock0040.png"},
                {"LOCK_0043", "/overWorld/lock/lock0041.png"},
                {"LOCK_0044", "/overWorld/lock/lock0042.png"},
                {"LOCK_0045", "/overWorld/lock/lock0043.png"},
                {"LOCK_0046", "/overWorld/lock/lock0044.png"},
                {"LOCK_0047", "/overWorld/lock/lock0045.png"},
                {"LOCK_0048", "/overWorld/lock/lock0046.png"},
                {"LOCK_0049", "/overWorld/lock/lock0047.png"},
                {"LOCK_0050", "/overWorld/lock/lock0048.png"},
                {"LOCK_0051", "/overWorld/lock/lock0049.png"},
                {"LOCK_0052", "/overWorld/lock/lock0050.png"},
                {"LOCK_0053", "/overWorld/lock/lock0051.png"},
                {"LOCK_0054", "/overWorld/lock/lock0052.png"},
                {"LOCK_0055", "/overWorld/lock/lock0053.png"},
                {"LOCK_0056", "/overWorld/lock/lock0054.png"},
                {"LOCK_0057", "/overWorld/lock/lock0056.png"},
                {"LOCK_0058", "/overWorld/lock/lock0057.png"},
                {"LOCK_0059", "/overWorld/lock/lock0058.png"},
                {"LOCK_0060", "/overWorld/lock/lock0059.png"},
                {"LOCK_0061", "/overWorld/lock/lock0060.png"},
                {"LOCK_0062", "/overWorld/lock/lock0061.png"},
                {"LOCK_0063", "/overWorld/lock/lock0062.png"},
                {"LOCK_0064", "/overWorld/lock/lock0063.png"},
                {"LOCK_0065", "/overWorld/lock/lock0064.png"},
                {"LOCK_0066", "/overWorld/lock/lock0065.png"},
                {"LOCK_0067", "/overWorld/lock/lock0066.png"},
                {"LOCK_0068", "/overWorld/lock/lock0067.png"},
                {"LOCK_0069", "/overWorld/lock/lock0068.png"},
                {"LOCK_0070", "/overWorld/lock/lock0070.png"},

                // Swords

                {"SWORD_0001", "/overWorld/sword/sword0001.png"},
                {"SWORD_0002", "/overWorld/sword/sword0002.png"},
                {"SWORD_0003", "/overWorld/sword/sword0003.png"},
                {"SWORD_0004", "/overWorld/sword/sword0004.png"},
                {"SWORD_0005", "/overWorld/sword/sword0005.png"},
                {"SWORD_0006", "/overWorld/sword/sword0006.png"},
                {"SWORD_0007", "/overWorld/sword/sword0007.png"},
                {"SWORD_0008", "/overWorld/sword/sword0008.png"},
                {"SWORD_0009", "/overWorld/sword/sword0009.png"},
                {"SWORD_0010", "/overWorld/sword/sword0010.png"},
                {"SWORD_0011", "/overWorld/sword/sword0011.png"},
                {"SWORD_0012", "/overWorld/sword/sword0012.png"},
                {"SWORD_0013", "/overWorld/sword/sword0013.png"},
                {"SWORD_0014", "/overWorld/sword/sword0014.png"},
                {"SWORD_0015", "/overWorld/sword/sword0015.png"},
                {"SWORD_0016", "/overWorld/sword/sword0016.png"},
                {"SWORD_0017", "/overWorld/sword/sword0017.png"},
                {"SWORD_0018", "/overWorld/sword/sword0018.png"},
                {"SWORD_0019", "/overWorld/sword/sword0019.png"},
                {"SWORD_0020", "/overWorld/sword/sword0020.png"},
                {"SWORD_0021", "/overWorld/sword/sword0021.png"},
                {"SWORD_0022", "/overWorld/sword/sword0022.png"},
                {"SWORD_0023", "/overWorld/sword/sword0023.png"},
                {"SWORD_0024", "/overWorld/sword/sword0024.png"},
                {"SWORD_0025", "/overWorld/sword/sword0025.png"},
                {"SWORD_0026", "/overWorld/sword/sword0026.png"},
                {"SWORD_0027", "/overWorld/sword/sword0027.png"},
                {"SWORD_0028", "/overWorld/sword/sword0028.png"},
                {"SWORD_0029", "/overWorld/sword/sword0029.png"},
                {"SWORD_0030", "/overWorld/sword/sword0030.png"},
                {"SWORD_0031", "/overWorld/sword/sword0031.png"},
                {"SWORD_0032", "/overWorld/sword/sword0032.png"},
                {"SWORD_0033", "/overWorld/sword/sword0033.png"},
                {"SWORD_0034", "/overWorld/sword/sword0034.png"},
                {"SWORD_0035", "/overWorld/sword/sword0035.png"},
                {"SWORD_0036", "/overWorld/sword/sword0036.png"},
                {"SWORD_0037", "/overWorld/sword/sword0037.png"},
                {"SWORD_0038", "/overWorld/sword/sword0038.png"},
                {"SWORD_0039", "/overWorld/sword/sword0039.png"},
                {"SWORD_0040", "/overWorld/sword/sword0040.png"},
                {"SWORD_0041", "/overWorld/sword/sword0041.png"},
                {"SWORD_0042", "/overWorld/sword/sword0042.png"},
                {"SWORD_0043", "/overWorld/sword/sword0043.png"},
                {"SWORD_0044", "/overWorld/sword/sword0044.png"},
                {"SWORD_0045", "/overWorld/sword/sword0045.png"},
                {"SWORD_0046", "/overWorld/sword/sword0046.png"},
                {"SWORD_0047", "/overWorld/sword/sword0047.png"},
                {"SWORD_0048", "/overWorld/sword/sword0048.png"},
                {"SWORD_0049", "/overWorld/sword/sword0049.png"},
                {"SWORD_0050", "/overWorld/sword/sword0050.png"},
                {"SWORD_0051", "/overWorld/sword/sword0051.png"},
                {"SWORD_0052", "/overWorld/sword/sword0052.png"},
                {"SWORD_0053", "/overWorld/sword/sword0053.png"},
                {"SWORD_0054", "/overWorld/sword/sword0054.png"},
                {"SWORD_0055", "/overWorld/sword/sword0055.png"},
                {"SWORD_0056", "/overWorld/sword/sword0056.png"},
                {"SWORD_0057", "/overWorld/sword/sword0057.png"},
                {"SWORD_0058", "/overWorld/sword/sword0058.png"},
                {"SWORD_0059", "/overWorld/sword/sword0059.png"},
                {"SWORD_0060", "/overWorld/sword/sword0060.png"},
                {"unregistered_texture", "unregistered_texture.png"},
                {"dead_pixel_shadow", "/livetiles/dead_pixel.png"}
        };

        /*
        Textures that should NOT be considered to have shadows go in here
         */
        final String[][] unshadowedTextures = {
                {"grass_1", "/tiles/grass_1.png"},
                {"grass_2", "/tiles/grass_2.png"},

                {"void", "/tiles/black.png"},
                {"grass_1", "/tiles/grass_1.png"},
                {"grass_2", "/tiles/grass_2.png"},
                {"grass_3", "/tiles/grass_3.png"},
                {"water", "/tiles/water.png"},

                // RS tile textures
                {"rs_grass1", "/falador/rs_grass1.png"},
                {"rs_grass2", "/falador/rs_grass2.png"},
                {"rs_floor1", "/falador/rs_floor1.png"},
                {"rs_floor2", "/falador/rs_floor2.png"},
                {"rs_mud1", "/falador/rs_mud1.png"},
                {"rs_mud2", "/falador/rs_mud2.png"},
                {"rs_path1", "/falador/path1.png"},
                {"rs_path2", "/falador/path2.png"},
                {"rs_path3", "/falador/path3.png"},
                {"rs_path4", "/falador/path4.png"},
                {"rs_sand1", "/falador/rs_sand1.png"},
                {"rs_sand2", "/falador/rs_sand2.png"},
                {"rs_snow1", "/falador/rs_snow1.png"},
                {"rs_snow2", "/falador/rs_snow2.png"},

                // Non-shadow LiveTiles
                {"dead_pixel", "/livetiles/dead_pixel.png"},
                {"lava_1", "/livetiles/lava/lava_1.png"},
                {"lava_2", "/livetiles/lava/lava_2.png"},
                {"lava_3", "/livetiles/lava/lava_3.png"},
                {"lava_4", "/livetiles/lava/lava_4.png"},
                {"lava_5", "/livetiles/lava/lava_5.png"},
                {"lava_6", "/livetiles/lava/lava_6.png"},
                {"lava_7", "/livetiles/lava/lava_7.png"},
                {"lava_8", "/livetiles/lava/lava_8.png"},
                {"lava_base_1", "/livetiles/lava/lava_base_1.png"},
                {"lava_base_2", "/livetiles/lava/lava_base_2.png"},
                {"flame_1", "/livetiles/flame/flame_1.png"},
                {"flame_2", "/livetiles/flame/flame_2.png"},
                {"flame_3", "/livetiles/flame/flame_3.png"},
                {"flame_4", "/livetiles/flame/flame_4.png"},
                {"flame_destroyed", "/livetiles/flame/flame_destroyed.png"},
                {"longgrass_base_1", "/livetiles/grass/longgrass_1.png"},
                {"longgrass_base_2", "/livetiles/grass/longgrass_2.png"},
                {"longgrass_base_3", "/livetiles/grass/longgrass_3.png"},
                {"longgrass_destroyed", "/livetiles/grass/grass_destroyed.png"},
                {"puddle_base_1", "/livetiles/puddle/puddle_1.png"},
                {"puddle_base_2", "/livetiles/puddle/puddle_2.png"},
                {"bleed_base_1", "/livetiles/bleed/livetile_bleed_1.png"},
                {"bleed_base_2", "/livetiles/bleed/livetile_bleed_2.png"},
                {"bleed_destroyed", "/livetiles/bleed/destroyed.png"},
                {"poison_base_1", "/livetiles/poison/livetile_poison_1.png"},
                {"poison_base_2", "/livetiles/poison/livetile_poison_2.png"},
                {"poison_base_3", "/livetiles/poison/livetile_poison_3.png"},
                {"poison_base_4", "/livetiles/poison/livetile_poison_4.png"},
                {"poison_base_5", "/livetiles/poison/livetile_poison_5.png"},
                {"poison_base_6", "/livetiles/poison/livetile_poison_6.png"},
                {"poison_base_7", "/livetiles/poison/livetile_poison_7.png"},
                {"poison_base_8", "/livetiles/poison/livetile_poison_8.png"},
                {"swamp_base_1", "/livetiles/swamp/livetile_swamp_1.png"},
                {"swamp_base_2", "/livetiles/swamp/livetile_swamp_2.png"},
                {"swamp_base_3", "/livetiles/swamp/livetile_swamp_3.png"},
                {"swamp_base_4", "/livetiles/swamp/livetile_swamp_4.png"},
                {"swamp_base_5", "/livetiles/swamp/livetile_swamp_5.png"},
                {"frost_base_1", "/livetiles/frost/livetile_frost_1.png"},
                {"frost_base_2", "/livetiles/frost/livetile_frost_2.png"},
                {"frost_base_3", "/livetiles/frost/livetile_frost_3.png"},
                {"frost_base_4", "/livetiles/frost/livetile_frost_4.png"},
                {"frost_base_5", "/livetiles/frost/livetile_frost_5.png"},
                {"frost_base_6", "/livetiles/frost/livetile_frost_6.png"},
                {"oil_tile_base_1", "/livetiles/oil/livetile_oil_1.png"},
                {"oil_tile_base_2", "/livetiles/oil/livetile_oil_2.png"},
                {"oil_tile_base_3", "/livetiles/oil/livetile_oil_3.png"},
                {"oil_tile_base_4", "/livetiles/oil/livetile_oil_4.png"},
                {"oil_tile_base_5", "/livetiles/oil/livetile_oil_5.png"},

                // Enemy UI and Particles

                {"arrow_cast", "/abilities/arrow_indicator.png"},
                {"arrow_hit", "/abilities/arrow_effect.png"},

                {"sword_cast", "/abilities/melee_indicator.png"},
                {"sword_hit", "/abilities/sword_hit.png"},

                {"crush_cast", "/abilities/crushing_indicator.png"},
                {"crush_hit", "/abilities/crushing_effect.png"},

                {"slash_cast", "/abilities/slashing_indicator.png"},
                {"slash_hit", "/abilities/slashing_effect.png"},

                {"water_cast", "/abilities/water_indicator.png"},
                {"water_hit", "/abilities/sword_hit.png"}, // delet this

                {"fire_cast", "/abilities/fire_indicator.png"},
                {"fire_hit", "/abilities/fire_hit.png"}, // delet this

                {"electric_cast", "/abilities/electric_indicator.png"},
                {"electric_hit", "/abilities/electric_hit.png"}, // delet this

                {"explosive_cast", "/abilities/explosive_indicator.png"},
                {"explosive_hit", "/abilities/sword_hit.png"}, // delet this

                {"thrust_cast", "/abilities/thrusting_indicator.png"},
                {"thrust_hit", "/abilities/thrusting_effect.png"},

                {"heal_cast", "/abilities/healing_indicator.png"},
                {"heal_hit", "/abilities/healing_effect.png"},

                {"enemy_move_indicator", "/enemies/movement_indicator.png"},
                {"enemy_selection", "/enemies/selection_enemy.png"},
                {"hitsplat", "/particles/hitsplat.png"},

                // Buff Icons
                {"onfire_icon", "/buffs/on_fire.png"},
                {"buff_placeholder", "/buffs/placeholder.png"},

                // Caves
                {"cave2_1", "/falador/cave/cave2_1.png"},
                {"cave3_1", "/falador/cave/cave3_1.png"},
                {"cave4_1", "/falador/cave/cave4_1.png"},
                {"cave4_2", "/falador/cave/cave4_2.png"},
                {"cave4_3", "/falador/cave/cave4_3.png"},
                {"cave4_4", "/falador/cave/cave4_4.png"},
                {"cave4_base_1", "/falador/cave/cave4_base.png"},

                // Realistic textures
                {"real_grass_1", "/realistic/grass_1.png"},
                {"real_grass_2", "/realistic/grass_2.png"},
                {"real_grass_3", "/realistic/grass_3.png"},
                {"real_water", "/tiles/water.png"},
                {"real_fountain", "/realistic/fountain.png"},
                {"real_sandstonebrick", "/realistic/sandstone/sandstonebrick_1.png"},
                {"real_sandstone_1", "/realistic/sandstone/sandstone_1.png"},
                {"real_sandstone_2", "/realistic/sandstone/sandstone_2.png"},
                {"real_sandstone_3", "/realistic/sandstone/sandstone_3.png"},
                {"real_jacaranda", "/realistic/jacaranda.png"},
                {"real_magma", "/falador/magma_exp.png"},
                {"real_qut", "/realistic/qut.png"},

                // Danktextures
                {"dt_grass_dark_1", "/tiles/Grass-D1.png"},
                {"dt_grass_dark_2", "/tiles/Grass-D2.png"},
                {"dt_grass_dark_3", "/tiles/Grass-D3.png"},
                {"dt_grass_dark_4", "/tiles/Grass-D4.png"},
                {"dt_grass_dark_5", "/tiles/Grass-D5.png"},
                {"dt_grass_light_1", "/tiles/Grass-L1.png"},
                {"dt_grass_light_2", "/tiles/Grass-L2.png"},
                {"dt_grass_light_3", "/tiles/Grass-L3.png"},
                {"dt_grass_light_4", "/tiles/Grass-L4.png"},
                {"dt_grass_light_5", "/tiles/Grass-L5.png"},
                {"dt_grass_mid_1", "/tiles/Grass-M1.png"},
                {"dt_grass_mid_2", "/tiles/Grass-M2.png"},
                {"dt_grass_mid_3", "/tiles/Grass-M3.png"},
                {"dt_grass_mid_4", "/tiles/Grass-M4.png"},
                {"dt_grass_mid_5", "/tiles/Grass-M5.png"},

                {"dt_sand_dark_1", "/tiles/Sand-D1.png"},
                {"dt_sand_dark_2", "/tiles/Sand-D2.png"},
                {"dt_sand_dark_3", "/tiles/Sand-D3.png"},
                {"dt_sand_dark_4", "/tiles/Sand-D4.png"},
                {"dt_sand_dark_5", "/tiles/Sand-D5.png"},
                {"dt_sand_light_1", "/tiles/Sand-L1.png"},
                {"dt_sand_light_2", "/tiles/Sand-L2.png"},
                {"dt_sand_light_3", "/tiles/Sand-L3.png"},
                {"dt_sand_light_4", "/tiles/Sand-L4.png"},
                {"dt_sand_light_5", "/tiles/Sand-L5.png"},
                {"dt_sand_mid_1", "/tiles/Sand-M1.png"},
                {"dt_sand_mid_2", "/tiles/Sand-M2.png"},
                {"dt_sand_mid_3", "/tiles/Sand-M3.png"},
                {"dt_sand_mid_4", "/tiles/Sand-M4.png"},
                {"dt_sand_mid_5", "/tiles/Sand-M5.png"},

                {"dt_water_dark_1", "/tiles/Water-D1.png"},
                {"dt_water_dark_2", "/tiles/Water-D2.png"},
                {"dt_water_dark_3", "/tiles/Water-D3.png"},
                {"dt_water_dark_4", "/tiles/Water-D4.png"},
                {"dt_water_dark_5", "/tiles/Water-D5.png"},
                {"dt_water_light_1", "/tiles/Water-L1.png"},
                {"dt_water_light_2", "/tiles/Water-L2.png"},
                {"dt_water_light_3", "/tiles/Water-L3.png"},
                {"dt_water_light_4", "/tiles/Water-L4.png"},
                {"dt_water_light_5", "/tiles/Water-L5.png"},
                {"dt_water_mid_1", "/tiles/Water-M1.png"},
                {"dt_water_mid_2", "/tiles/Water-M2.png"},
                {"dt_water_mid_3", "/tiles/Water-M3.png"},
                {"dt_water_mid_4", "/tiles/Water-M4.png"},
                {"dt_water_mid_5", "/tiles/Water-M5.png"},

                {"selection", "/tiles/selection.png"},
                {"real_selection_standard", "/realistic/selection/selection_standard.png"},
                {"real_selection_fail", "/realistic/selection/selection_fail.png"},
                {"real_selection_special", "/realistic/selection/selection_special.png"},
                {"real_turnmarker", "/realistic/turn_marker.png"},

                {"movement_range_1", "/realistic/movement_range_1.png"},
                {"movement_range_2", "/realistic/movement_range_2.png"},
                {"movement_range_3", "/realistic/movement_range_3.png"},
                {"movement_range_4", "/realistic/movement_range_4.png"},
                
                {"movement_path_1", "/realistic/movement_path_1.png"},
                {"movement_path_2", "/realistic/movement_path_2.png"},
                {"movement_path_3", "/realistic/movement_path_3.png"},
                {"movement_path_4", "/realistic/movement_path_4.png"},

                {"enemy_movement_range_1", "/realistic/enemy_movement_range_1.png"},
                {"enemy_movement_range_2", "/realistic/enemy_movement_range_2.png"},
                {"enemy_movement_range_3", "/realistic/enemy_movement_range_3.png"},
                {"enemy_movement_range_4", "/realistic/enemy_movement_range_4.png"},

                {"green_range_1", "/realistic/green_range_1.png"},
                {"green_range_2", "/realistic/green_range_2.png"},
                {"green_range_3", "/realistic/green_range_3.png"},
                {"green_range_4", "/realistic/green_range_4.png"},

                // Vision sprites
                {"hidden", "/tiles/hidden.png"},

                // Particle Textures
                {"rain_4small", "/weather/rain4small.png"},
                {"snowsmall", "/weather/snowflake-small.png"},
                {"hailsmall", "/weather/hail-small.png"},
                {"fog", "/weather/fog1.png"},
                {"lightning", "/weather/lightning.png"},

                //overWorld icons
                {"complete", "overWorld/complete.png"},
                {"swordIconOpen", "overWorld/sword.png"},
                {"swordIcon", "overWorld/swordOpen/sword0045.png"},
                {"backgroundRoadless", "overWorld/backgroundRoadless.png"},
                {"backgroundBorderless", "overWorld/backgroundNotBordered.png"},
                {"levelLabel", "overWorld/placeholderLabel.png"},
                {"exitCross", "overWorld/cross.png"},
                {"exitCrossSarah", "overWorld/button.png"},
                {"boxSarah", "overWorld/box.png"},
                {"lock", "/overWorld/lock.png"},
                {"playerIcon", "overWorld/shield .png"},
                {"path", "overWorld/path.png"},
                {"testBackground", "/levelmap/bg.png"},
                {"overworldBackground", "overWorld/background.png"},
                {"bg", "/levelmap/bg.png"},
                {"map2.0", "/overWorld/sarahversionMap2.0.png"},
                {"descriptionBackground", "/overWorld/descriptionBackground.png"},


                // Weapons
                {"bronze_sword", "/weapons/bronze_sword.png"},
                {"iron_sword", "/weapons/iron_sword.png"},
                {"steel_sword", "/weapons/steel_sword.png"},
                {"platinum_sword", "/weapons/platinum_sword.png"},
                {"valyrian_steel_sword", "/weapons/valyrian_steel_sword.png"},

                {"bronze_axe", "/weapons/bronze_axe.png"},
                {"iron_axe", "/weapons/iron_axe.png"},
                {"steel_axe", "/weapons/steel_axe.png"},
                {"platinum_axe", "/weapons/platinum_axe.png"},
                {"shinning_axe", "/weapons/shinning_axe.png"},

                {"bronze_dagger", "/weapons/bronze_dagger.png"},
                {"iron_dagger", "/weapons/iron_dagger.png"},
                {"steel_dagger", "/weapons/steel_dagger.png"},
                {"platinum_dagger", "/weapons/platinum_dagger.png"},
                {"ceremonial_dagger", "/weapons/ceremonial_dagger.png"},

                {"bronze_hammer", "/weapons/bronze_hammer.png"},
                {"iron_hammer", "/weapons/iron_hammer.png"},
                {"steel_hammer", "/weapons/steel_hammer.png"},
                {"platinum_hammer", "/weapons/platinum_hammer.png"},
                {"thors_hammer", "/weapons/thors_hammer.png"},

                {"wooden_shield", "/shields/wooden_shield.png"},
                {"iron_shield", "/shields/iron_shield.png"},
                {"steel_shield", "/shields/steel_shield.png"},
                {"platinum_shield", "/shields/platinum_shield.png"},
                {"adamantium_shield", "/shields/adamantium_shield.png"},

                {"poison_bomb", PLACEHOLDER_IMAGE},
                {"wet_bomb", PLACEHOLDER_IMAGE},
                {"fire_bomb", PLACEHOLDER_IMAGE},
                {"poison_bomb", PLACEHOLDER_IMAGE},

                {"novice_staff", "/weapons/novice_staff.png"},
                {"apprentice_staff", "/weapons/apprentice_staff.png"},
                {"mage_staff", "/weapons/mage_staff.png"},
                {"wizard_staff", "/weapons/wizard_staff.png"},
                {"gandalfs_staff", "/weapons/gandalfs_staff.png"},
                {"moist_staff", PLACEHOLDER_IMAGE},
                {"damp_staff", PLACEHOLDER_IMAGE},
                {"wave_staff", PLACEHOLDER_IMAGE},
                {"tsunami_staff", PLACEHOLDER_IMAGE},
                {"zappy_staff", PLACEHOLDER_IMAGE},
                {"spark_staff", PLACEHOLDER_IMAGE},
                {"thunder_staff", PLACEHOLDER_IMAGE},
                {"storm_staff", PLACEHOLDER_IMAGE},
                {"ember_staff", PLACEHOLDER_IMAGE},
                {"flame_staff", PLACEHOLDER_IMAGE},
                {"inferno_staff", PLACEHOLDER_IMAGE},
                {"hot_staff", PLACEHOLDER_IMAGE},

                //Weapons
                {"basic_sword", "/weapons/sword.png"},
                {"novice_staff", "/weapons/apprentice_staff.png"},
                {"apprentice_staff", "/weapons/apprentice_staff.png"},

                {"training_bow", "/weapons/training_bow.png"},
                {"wooden_bow", "/weapons/wooden_bow.png"},
                {"composite_bow", "/weapons/wooden_bow.png"},
                {"elvish_bow", "/weapons/training_bow.png"},
                {"ice_bomb", "/weapons/potion-grenade-blue.png"}, // {"ice_bomb", "/items/ice_bomb.png");
                {"bronze_shield", "/weapons/wooden_shield.png"},// {"wooden_shield", "/items/wooden_shield.png");

                {"guzmans_burrito", PLACEHOLDER_IMAGE},
                {"bread", PLACEHOLDER_IMAGE},
                {"health_potion", PLACEHOLDER_IMAGE},
                {"ap_potion",PLACEHOLDER_IMAGE},

                {"training_bow", "/weapons/training_bow.png"},
                {"wooden_bow", "/weapons/wooden_bow.png"},
                {"composite_bow", "/weapons/composite_bow.png"},
                {"elvish_bow", "/weapons/elvish_bow.png"},

                {"guzmans_burrito", "/consumables/guzmans_burrito.png"},
                {"bread", "/consumables/bread.png"},
                {"health_potion", "/consumables/health_potion.png"},
                {"ap_potion", "/consumables/ap_potion.png"},

                {"sacred_bow", "/weapons/sacred_bow.png"},

                // coin sprites
                {"large_coin", "/items/coin_large_pile.png"},
                {"medium_coin", "/items/coin_medium_pile.png"},
                {"small_coin", "/items/coin_small_pile.png"},
                
                //Profile Graphics
                {"profile_placeholder", "/profileImages/placeholder.png"},
                {"profile_knight", "/profileImages/Knight.png"},
                {"profile_archer", "/profileImages/Archer.png"},
                {"profile_cavalier", "/profileImages/Cavalier.png"},
                {"profile_priest", "/profileImages/Priest.png"},
                {"profile_rogue", "/profileImages/Rogue.png"},
                {"profile_warlock", "/profileImages/Warlock.png"},
        };

        for (String[] shadowedTexture : shadowedTextures) {
            addTexture(shadowedTexture[0], shadowedTexture[1], true);
        }

        for (String[] unshadowedTexture : unshadowedTextures) {
            addTexture(unshadowedTexture[0], unshadowedTexture[1], false);
        }

        addTexture("empty", "/ui/empty.png", true);
    }

    /**
     * Adds a texture to the register
     *
     * @param name The name of the texture to be added.
     * @param path The path of the texture
     */
    private static void addTexture(String name, String path, boolean split) {
        if (resourceLinkMap.containsKey(name)) {
            logger.error("Texture already exists for " + name);
        }

        if (!split) {
            dontSplit.add(name);
        }

        resourceLinkMap.putIfAbsent(name, path);
    }

    /**
     * Gets the image of the given texture
     *
     * @param textureName The name of the texture
     * @return The Image of the texture
     */
    public static Image getTexture(String textureName) {

        if (!checkValidKey(textureName)) {
            textureName = "unregistered_texture";
        }

        if (resourceCache.containsKey(textureName)) {
            return resourceCache.get(textureName);
        }

        Image baseImage = new Image(resourceLinkMap.get(textureName));

        if (dontSplit.contains(textureName)) {
            resourceCache.put(textureName, baseImage);
            return resourceCache.get(textureName);
        }

        Image[] splitImages = splitImage(baseImage);
        resourceCache.put(textureName, splitImages[0]);
        resourceCache.put(textureName + "shadow", splitImages[1]);

        return resourceCache.get(textureName);
    }

    public static boolean checkValidKey(String textureName) {
      if (!resourceLinkMap.containsKey(textureName)) {

          System.out.println("  ATTEMPTING TO LOAD TEXTURE: " + textureName);

            if (GameLoop.getCurrentGameManager() != null && GameLoop.getCurrentGameManager().getLogger() != null) {
                GameLoop.getCurrentGameManager().getLogger().error("Invalid texture! - " + textureName);
                return false;
            }
//            throw new InvalidRegisterAccessException(
//                    "Attempted to access non-registered Texture \"" + textureName
//                            + "\" from TextureRegister");
        }
        return true;
    }
    
    public static Boolean searchTextureString(String textureName){
        return resourceLinkMap.containsKey(textureName);
    }

    /**
     * Splits an image into the core image and its shadow.
     *
     * @param baseImage the image to split
     * @return array with shadowless image first, then the shadow
     */
    private static Image[] splitImage(Image baseImage) {
        WritableImage core = new WritableImage((int) baseImage.getWidth(), (int) baseImage.getHeight());
        WritableImage shadow = new WritableImage((int) baseImage.getWidth(), (int) baseImage.getHeight());
        PixelWriter corePixelWriter = core.getPixelWriter();
        PixelWriter shadowPixelWriter = shadow.getPixelWriter();
        PixelReader imagePixelReader = baseImage.getPixelReader();
        for (int k = 0; k < baseImage.getWidth(); k++) {
            for (int l = 0; l < baseImage.getHeight(); l++) {
                Color oldColor = imagePixelReader.getColor(k, l);
                if (oldColor.getOpacity() < 0.9) {
                    corePixelWriter.setArgb(k, l, 0);
                    shadowPixelWriter.setColor(k, l, oldColor);
                } else {
                    corePixelWriter.setColor(k, l, oldColor);
                    shadowPixelWriter.setArgb(k, l, 0);
                }
            }
        }
        return new Image[]{core, shadow};
    }

    /**
     * Gets a faded version of a texture
     * Creates the Image if not cached
     *
     * @param textureName the name of the texture
     * @return the faded texture
     */
    public static Image getFadedTexture(String textureName) {

        if (!checkValidKey(textureName)) {
            textureName = "unregistered_texture";
        }

        String fadedName = textureName + "faded";

        if (resourceCache.containsKey(fadedName)) {
            return resourceCache.get(fadedName);
        }

        Image image = getTexture(textureName);
        WritableImage fadedImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelWriter imagePixelWriter = fadedImage.getPixelWriter();
        PixelReader imagePixelReader = image.getPixelReader();
        for (int k = 0; k < image.getWidth(); k++) {
            for (int l = 0; l < image.getHeight(); l++) {
                Color oldColor = imagePixelReader.getColor(k, l);
                imagePixelWriter.setColor(k, l, oldColor.darker().darker());
            }
        }
        resourceCache.put(fadedName, fadedImage);
        return resourceCache.get(fadedName);
    }

    /**
     * Gets a shadow version of a texture
     *
     * @param textureName the name of the texture
     * @return the shadow texture
     */
    public static Image getShadowTexture(String textureName) {

        if (!checkValidKey(textureName)) {
            textureName = "unregistered_texture";
        }

        String shadedName = textureName + "shadow";

        if (resourceCache.containsKey(shadedName)) {
            return resourceCache.get(shadedName);
        }

        getTexture(textureName);
        return resourceCache.get(shadedName);
    }

}

package uq.deco2800.duxcom.dataregisters;

import uq.deco2800.duxcom.entities.EntityType;

/**
 * Links each entity type from the EntityType enum with a set of data from EntityDataClass.
 *
 * @author liamdm
 * @author Alex McLean
 */
public class EntityDataRegister extends AbstractDataRegister<EntityType, EntityDataClass> {

    /**
     * Package private constructor
     *
     * Should only be called by DataRegisterManager
     */
    EntityDataRegister() {
        super();

        final String yellow = "yellow";
        final String orange = "orange";
        final String red = "red";
        final String blue = "blue";
        final String green = "green";
        final String sienna = "sienna";
        final String darkTurquoise = "darkturquoise";
        final String lightgray = "lightgray";


        // Basics
        linkDataToType(EntityType.WOOD_STACK, new EntityDataClass("wood_stack", "wood_stack", sienna));
        linkDataToType(EntityType.TOMB_STONE, new EntityDataClass("tombstone", "tombstone", lightgray));
        linkDataToType(EntityType.HERO_SPAWN, new EntityDataClass("hero_spawn", "hero_spawn", lightgray));
        linkDataToType(EntityType.DEATH_MAGMA, new EntityDataClass("death_magma", "rs_magma", lightgray));
        linkDataToType(EntityType.SC, new EntityDataClass("void", "void", lightgray));
        linkDataToType(EntityType.DUCK, new EntityDataClass("duck", "duck", yellow));
        linkDataToType(EntityType.TALL, new EntityDataClass("tall", "tall", orange));
        linkDataToType(EntityType.TRAP, new EntityDataClass("trap", "trap", red));
        linkDataToType(EntityType.CHEST, new EntityDataClass("chest", "chest", yellow));

        // Hero sprites
        linkDataToType(EntityType.PRIEST, new EntityDataClass("priest", "priest", lightgray));
        linkDataToType(EntityType.WARLOCK, new EntityDataClass("warlock", "warlock", "darkorchid"));
        linkDataToType(EntityType.KNIGHT, new EntityDataClass("knight", "knight", "mintcream"));
        linkDataToType(EntityType.ARCHER, new EntityDataClass("archer", "archer", "thistle"));
        linkDataToType(EntityType.ROGUE, new EntityDataClass("rogue", "rogue", "tomato"));
        linkDataToType(EntityType.CAVALIER, new EntityDataClass("cavalier", "cavalier", "salmon"));

        // Realistic textures
        linkDataToType(EntityType.REAL_WOOD_STACK, new EntityDataClass("real_wood_stack", "real_wood_stack", "burlywood"));
        linkDataToType(EntityType.REAL_IBIS, new EntityDataClass("real_ibis", "real_ibis", yellow));
        linkDataToType(EntityType.BETA_IBIS, new EntityDataClass("beta_ibis", "beta_ibis", "ghostwhite"));

        // Enemy Sprites
        linkDataToType(EntityType.ENEMY_KNIGHT, new EntityDataClass("enemy_knight", "enemy_knight", red));
        linkDataToType(EntityType.ENEMY_SUPPORT, new EntityDataClass("enemy_support", "enemy_support", red));
        linkDataToType(EntityType.ENEMY_TANK, new EntityDataClass("enemy_tank", "enemy_tank", red));
        linkDataToType(EntityType.ENEMY_ARCHER, new EntityDataClass("enemy_archer", "enemy_archer", red));
        linkDataToType(EntityType.ENEMY_CAVALIER, new EntityDataClass("enemy_cavalier", "enemy_cavalier", red));
        linkDataToType(EntityType.ENEMY_DARK_MAGE, new EntityDataClass("enemy_dark_mage", "enemy_dark_mage", red));
        linkDataToType(EntityType.ENEMY_GRUNT, new EntityDataClass("enemy_grunt", "enemy_grunt", red));
        linkDataToType(EntityType.ENEMY_ROGUE, new EntityDataClass("enemy_rogue", "enemy_rogue", red));

        // Dynamics Sprites
        linkDataToType(EntityType.CAVALIER_AQUA, new EntityDataClass("cavalier_aqua", "cavalier_aqua", "aqua"));
        linkDataToType(EntityType.CAVALIER_BLUE, new EntityDataClass("cavalier_blue", "cavalier_blue", blue));
        linkDataToType(EntityType.CAVALIER_GREEN, new EntityDataClass("cavalier_green", "cavalier_green", green));
        linkDataToType(EntityType.CAVALIER_ORANGE, new EntityDataClass("cavalier_orange", "cavalier_orange", orange));
        linkDataToType(EntityType.CAVALIER_PINK, new EntityDataClass("cavalier_pink", "cavalier_pink", "pink"));
        linkDataToType(EntityType.CAVALIER_PURPLE, new EntityDataClass("cavalier_purple", "cavalier_purple", "purple"));
        linkDataToType(EntityType.CAVALIER_RED, new EntityDataClass("cavalier_red", "cavalier_red", red));
        linkDataToType(EntityType.CAVALIER_YELLOW, new EntityDataClass("cavalier_yellow", "cavalier_yellow", yellow));
        linkDataToType(EntityType.WATER_BARREL, new EntityDataClass("water_barrel", "water_barrel", blue));

        // Dynamic tiles
        linkDataToType(EntityType.WATER_1, new EntityDataClass("water_1", "water_1", darkTurquoise));
        linkDataToType(EntityType.WATER_2, new EntityDataClass("water_2", "water_2", darkTurquoise));
        linkDataToType(EntityType.WATER_3, new EntityDataClass("water_3", "water_3", darkTurquoise));
        linkDataToType(EntityType.WATER_4, new EntityDataClass("water_4", "water_4", darkTurquoise));
        linkDataToType(EntityType.WATER_5, new EntityDataClass("water_5", "water_5", darkTurquoise));
        linkDataToType(EntityType.WATER_6, new EntityDataClass("water_6", "water_6", darkTurquoise));


        linkDataToType(EntityType.LAVA_1, new EntityDataClass("liquid_lava_1", "liquid_lava_1", darkTurquoise));
        linkDataToType(EntityType.LAVA_2, new EntityDataClass("liquid_lava_2", "liquid_lava_2", darkTurquoise));
        linkDataToType(EntityType.LAVA_3, new EntityDataClass("liquid_lava_3", "liquid_lava_3", darkTurquoise));
        linkDataToType(EntityType.LAVA_4, new EntityDataClass("liquid_lava_4", "liquid_lava_4", darkTurquoise));
        linkDataToType(EntityType.LAVA_5, new EntityDataClass("liquid_lava_5", "liquid_lava_5", darkTurquoise));
        linkDataToType(EntityType.LAVA_6, new EntityDataClass("liquid_lava_6", "liquid_lava_6", darkTurquoise));

        // Vision sprites
        linkDataToType(EntityType.HIDDEN, new EntityDataClass("hidden", "hidden", lightgray));

        // Water Barrel
        linkDataToType(EntityType.WATER_BARREL_1, new EntityDataClass("water_barrel_1", "water_barrel_1", darkTurquoise));
        linkDataToType(EntityType.WATER_BARREL_2, new EntityDataClass("water_barrel_2", "water_barrel_2", darkTurquoise));
        linkDataToType(EntityType.WATER_BARREL_3, new EntityDataClass("water_barrel_3", "water_barrel_3", darkTurquoise));
        linkDataToType(EntityType.WATER_BARREL_4, new EntityDataClass("water_barrel_4", "water_barrel_4", darkTurquoise));
        linkDataToType(EntityType.WATER_BARREL_5, new EntityDataClass("water_barrel_5", "water_barrel_5", darkTurquoise));
        linkDataToType(EntityType.WATER_BARREL_6, new EntityDataClass("water_barrel_6", "water_barrel_6", darkTurquoise));
        linkDataToType(EntityType.WATER_BARREL_7, new EntityDataClass("water_barrel_7", "water_barrel_7", darkTurquoise));
    }

}

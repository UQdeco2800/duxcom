package uq.deco2800.duxcom.graphics.particles;

/**
 * Created by jay-grant on 12/10/2016.
 *
 * Stores a register of Particle types
 *
 * You must update the ENUM (PURPLE) to a unique value
 * and the String ("green_in_qoutations") to the registered name of your graphic
 *
 * The String (registeredName) must be registered in TextureRegister
 *
 * Created by jay-grant on 9/10/2016.
 */
public enum IconType {

    // ParticleTypes go here
    SPLAT_DEFAULT("hitsplat"),

    CAST_SWORD("sword_cast"),
    HIT_SWORD("sword_hit"),

    CAST_ARROW("arrow_cast"),
    HIT_ARROW("arrow_hit"),

    CAST_CRUSH("crush_cast"),
    HIT_CRUSH("crush_hit"),

    CAST_SLASH("slash_cast"),
    HIT_SLASH("slash_hit"),

    CAST_WATER("water_cast"),
    HIT_WATER("water_hit"),

    CAST_FIRE("fire_cast"),
    HIT_FIRE("fire_hit"),

    CAST_ELECTRIC("electric_cast"),
    HIT_ELECTRIC("electric_hit"),

    CAST_EXPLOSIVE("explosive_cast"),
    HIT_EXPLOSIVE("explosive_hit"),

    CAST_THRUST("thrust_cast"),
    HIT_THRUST("thrust_hit"),

    CAST_HEAL("heal_cast"),
    HIT_HEAL("heal_hit");




    // they do not go down here -----
    private String resourceName;

    IconType(String string) {
        this.resourceName = string;
    }

    @Override
    public String toString() {
        return resourceName;
    }
}
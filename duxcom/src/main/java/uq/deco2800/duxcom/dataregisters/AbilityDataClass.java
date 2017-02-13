package uq.deco2800.duxcom.dataregisters;

import uq.deco2800.duxcom.abilities.AbilityType;
import uq.deco2800.duxcom.common.DamageType;
import uq.deco2800.duxcom.graphics.particles.IconType;

/**
 * Stores the data that all instances of all abilities must share.
 *
 * Also provides public getter and setter methods for all data.
 *
 * @author spress11
 */
public class AbilityDataClass implements AbstractDataClass {
    private AbilityType abilityType;
    private String name;
    private String description;

  //positive value - will be subtracted from ap
    private int costAP;
    private int cooldownTurns;
    private int range;
    private int aoeRange;

  //negative for damage, positive for heal
    private double damage;
    private DamageType damageType;

    // IconType enums
    private IconType castIcon;
    private IconType hitIcon;
    private IconType splatIcon;

    // Ability logic booleans
    private boolean canUseOnFriend = false;
    private boolean canUseOnFoe = false;
    private boolean canUseOnPoint = false;

    /**
     * Class constructor to set all the required parameters
     *
     * @param abilityType   	the ability's AbilityType
     * @param name        		name as a string
     * @param description 		description
     * @param costAP        	AP cost of ability (positive)
     * @param cooldownTurns    	cooldown of ability
     * @param range      		range
     * @param damage      		damage scaling factor
     * @param damageType  		the ability's DamageType
     */
    public AbilityDataClass(AbilityType abilityType, String name, String description, int costAP,
                          int cooldownTurns, int range, int aoeRange, double damage, DamageType damageType,
                            boolean canFriend, boolean canFoe, boolean canPoint){

    	this.abilityType = abilityType;
        this.name = name;
        this.description = description;
        this.costAP = costAP;
        this.cooldownTurns = cooldownTurns;
        this.range = range;
        this.aoeRange = aoeRange;
        this.damage = damage;
        this.damageType = damageType;
        this.canUseOnFriend = canFriend;
        this.canUseOnFoe = canFoe;
        this.canUseOnPoint = canPoint;

        assignIconTypes(damageType);

    }



    /**
     * Gets the AbilityType of the data class
     *
     * @return The data class' AbilityType
     */
    public AbilityType getAbilityType() {
        return abilityType;
    }

    /**
     * Sets the data class' AbilityType
     *
     * @param abilityType the new AbilityType
     */
    public void setAbilityType(AbilityType abilityType) {
        this.abilityType = abilityType;
    }

    /**
     * Gets the name of the data class
     *
     * @return The data class' name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the data class' name
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the data class
     *
     * @return The data class' description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the data class' description
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the AP cost of the data class
     *
     * @return The data class' costAP
     */
    public int getCostAP() {
        return costAP;
    }

    /**
     * Sets the data class' costAP
     *
     * @param costAP the new AP cost
     */
    public void setCostAP(int costAP) {
        this.costAP = costAP;
    }

    /**
     * Gets the cooldownTurns of the data class
     *
     * @return The data class' cooldownTurns
     */
    public int getCooldownTurns() {
        return cooldownTurns;
    }

    /**
     * Sets the data class' cooldownTurns
     *
     * @param cooldownTurns the new cooldownTurns
     */
    public void setCooldownTurns(int cooldownTurns) {
        this.cooldownTurns = cooldownTurns;
    }

    /**
     * Gets the range of the data class
     *
     * @return The data class' range
     */
    public int getRange() {
        return range;
    }

    /**
     * Sets the data class' range
     *
     * @param range the new range
     */
    public void setRange(int range) {
        this.range = range;
    }

    /**
     * Gets the area of effect range of the data class
     *
     * @return The data class' area of effect range
     */
    public int getAoeRange() {
        return aoeRange;
    }

    /**
     * Sets the data class' area of effect range
     *
     * @param aoeRange the new area of effect range
     */
    public void setAoeRange(int aoeRange) {
        this.aoeRange = aoeRange;
    }
    
    /**
     * Gets the damage of the data class
     *
     * @return The data class' damage
     */
    public double getDamage() {
        return damage;
    }

    /**
     * Sets the data class' damage
     *
     * @param damage the new damage
     */
    public void setDamage(double damage) {
        this.damage = damage;
    }

    /**
     * Gets the damage type of the data class
     *
     * @return The data class' DamageType
     */
    public DamageType getDamageType() {
        return damageType;
    }

    /**
     * Sets the data class' DamageType
     *
     * @param damageType the new damage type
     */
    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    /**
     * Possibly temporary way of assigning static icons to their corresponding
     * damageType given a new instance of an AbstractAbility.
     *
     * @param damageType can't you read, son? it's right there in the name.
     */
    private void assignIconTypes(DamageType damageType) {

        switch (damageType) {
            case FIRE:
                castIcon = IconType.CAST_FIRE;
                hitIcon = IconType.HIT_FIRE;
                break;
            case WATER:
                castIcon = IconType.CAST_WATER;
                hitIcon = IconType.HIT_WATER;
                break;
            case ELECTRIC:
                castIcon = IconType.CAST_ELECTRIC;
                hitIcon = IconType.HIT_ELECTRIC;
                break;
            case GROUND:
                castIcon = IconType.CAST_SWORD; // DELET
                hitIcon = IconType.HIT_SWORD; // DELET
                break;
            case PIERCING:
                castIcon = IconType.CAST_ARROW;
                hitIcon = IconType.HIT_ARROW;
                break;
            case MELEE:
                castIcon = IconType.CAST_SWORD;
                hitIcon = IconType.HIT_SWORD;
                break;
            case SLASHING:
                castIcon = IconType.CAST_SLASH;
                hitIcon = IconType.HIT_SLASH;
                break;
            case EXPLOSIVE:
                castIcon = IconType.CAST_EXPLOSIVE;
                hitIcon = IconType.HIT_EXPLOSIVE;
                break;
            case NORMAL:
                castIcon = IconType.CAST_SWORD;
                hitIcon = IconType.HIT_SWORD;
                break;
            case THRUSTING:
                castIcon = IconType.CAST_THRUST;
                hitIcon = IconType.HIT_THRUST;
                break;
            case CRUSHING:
                castIcon = IconType.CAST_CRUSH;
                hitIcon = IconType.HIT_CRUSH;
                break;
            case HEAL:
                castIcon = IconType.CAST_HEAL;
                hitIcon = IconType.HIT_HEAL;
                break;
            default:
                castIcon = IconType.CAST_SWORD;
                hitIcon = IconType.HIT_SWORD;
                break;
        }
        splatIcon = IconType.SPLAT_DEFAULT;
    }

    public IconType getCastIcon() {
        return castIcon;
    }

    public IconType getHitIcon() {
        return hitIcon;
    }

    public IconType getSplatIcon() {
        return splatIcon;
    }

    /**
     * Gets the canUseOnFriend status of the ability
     *
     * @return boolean of the canUseOnFriend status
     */
    public boolean canUseOnFriend() {
        return canUseOnFriend;
    }

    /**
     * Gets the canUseOnFoe status of the ability
     *
     * @return boolean of the canUseOnFoe status
     */
    public boolean canUseOnFoe() {
        return canUseOnFoe;
    }

    /**
     * Gets the canUseOnPoint status of the ability
     *
     * @return boolean of the canUseOnPoint status
     */
    public boolean canUseOnPoint() {
        return canUseOnPoint;
    }
}

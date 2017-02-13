package uq.deco2800.duxcom.buffs;

import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.heros.HeroStat;
import uq.deco2800.duxcom.tiles.LiveTileType;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstraction of the buffs to be used in duxcom.
 * Created by spress11 on 30-Jul-16.
 */
public abstract class AbstractBuff {

    protected String name;
    protected String description;
    protected BuffRegister type;
    protected int originalDuration;
    protected int duration;
    protected double strength;
    protected HeroStat statAffected;
    protected LiveTileType associatedLiveTileType;

    /**
     * Gets the name of the buff
     *
     * @return String of the buff's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the buff
     *
     * @param name the buff's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description tied to the buff
     *
     * @return String describing the buff
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the remaining duration of the buff
     *
     * @return number of turns left on the buff
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the remaining duration of the buff
     *
     * @param duration the number of turns remaining on the buff
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Get the stat that is affected by the buff
     *
     * @return The HeroStat affected
     */
    public HeroStat getStatAffected() {
        return statAffected;
    }

    /**
     * Gets the strength by which the buff affects the StatAffected
     *
     * @return the strength of the buff's affect on a hero stat
     */
    public double getBuffStrength() {
        return strength;
    }

    /**
     * Sets the strength by which the buff affects the StatAffected
     *
     * @param strength The new strength of the buff
     */
    public void setBuffStrength(int strength) {
        this.strength = strength;
    }

    /**
     * Ticks over by lowering the duration of the buff (if there is any left)
     *
     * @return true if the buff has remaining duration, else false
     */
    public boolean tickBuff() {
        if (duration <= 0) {
            return false;
        } else {
            duration--;
            return true;
        }
    }

    /**
     * Class constructor
     *
     * @param strength the strength of the buff
     * @param duration the duration of the buff
     */
    public AbstractBuff(double strength, int duration) {
        this.strength = strength;
        this.originalDuration = duration;
        this.duration = duration;
        this.associatedLiveTileType = null;
    }

    public void onAbilityEffect(Entity origin, AbstractAbility ability) {
        //Do nothing by default
    }

    /**
     * @param character the AbstractHero affected
     */
    public void onTurn(AbstractCharacter character) {
        //Do nothing by default
    }
    
    /**
     * 
     * @param character the AbstractCharacter affected
     */
    public void onEndTurn(AbstractCharacter character) {
    	//do nothing by default
    }

    /**
     * Sees if two game states are equal. They will be if they have the same sets
     * of statistics
     */
    @Override
    public boolean equals(Object toCompare) {
        if (!(toCompare instanceof AbstractBuff)) {
            return false;
        }
        AbstractBuff ab = (AbstractBuff) toCompare;
        return this.name.equals(ab.name);
    }

    /**
     * Generates and returns a hashcode for the buff
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Returns the String of the Texture corresponding to the current instance
     * of AbstractBuff. This method is expected to be Overwritten, but will
     * return a placeholder if it has not been.
     *
     * @return String of icon texture.
     */
    public String getIconTextureName() {
        return "buff_placeholder";
    }

    /**
     * Return true if the Buff has a LiveTileType associated with it. This is used
     * in determining whether an effect should be rendered ontop of an
     * affected Entity/Tile (because LiveTile basically doubles as a particle
     * effects class now :/ ).
     *
     * @return true iff buff has associatedLiveTileType.
     */
    public boolean hasAssociatedLiveTile() {
        return associatedLiveTileType != null;
    }

    /**
     * Sets the associated LiveTile to a new LiveTileType.
     *
     * @param liveTileType the new LiveTileType.
     */
    public void linkLiveTile(LiveTileType liveTileType) {
        this.associatedLiveTileType = liveTileType;
    }

    /**
     * Returns the associated LiveTileType of the Buff
     *
     * @return a LiveTileType
     */
    public LiveTileType getAssociatedLiveTileType() {
        return associatedLiveTileType;
    }

    /**
     * Abstracted method for ensuring that buffs tick consistently across all
     * forms of whatever they are applied to.
     *
     * @param buffs list of AbstractBuffs to tick.
     * @param o     Owner of buffs.
     */
    public static void tickBuffs(List<AbstractBuff> buffs, Object o) {
        ArrayList<AbstractBuff> buffsToRemove = new ArrayList<>();
        for (AbstractBuff buff : buffs) {
            if (o instanceof AbstractCharacter) {
                buff.onTurn((AbstractCharacter) o);
            }
            if (!buff.tickBuff()) {
                buffsToRemove.add(buff);
            }
        }
        buffs.removeAll(buffsToRemove);
    }

    /**
     * Returns the original duration that the buff was intended to last,
     * rather than the number of ticks that it has left before expiring
     *
     * @return int originalDuration
     */
    public int getOriginalDuration() {
        return originalDuration;
    }

    /**
     * Returns the buff type (from BuffRegister) linked to the Buff
     *
     * @return a BuffRegister enum
     */
    public BuffRegister getType() {
        return type;
    }

    /**
     * Bootleg sweatshop factory for producing new instances of Buffs for
     * application in extreme circumstances.
     *
     * @param buffType a string.
     * @param strength a double.
     * @param duration an integer.
     * @return an AbstractBuff.
     */
    public static AbstractBuff getNewBuff(BuffRegister buffType, double strength, int duration) {

        switch (buffType) {
            case BLEEDING:
                return new Bleeding(strength, duration);
            case CALL_TO_ARMS:
                return new CallToArmsBuff(strength, duration);
            case DEFENDER:
                return new DefenderBuff(strength, duration);
            case GROUND_HOG:
            	//cannot be used
            	return new DefenderBuff(strength, duration);
            case ARMOUR_PLUS:
                return new InnerStrengthArmourBuff(strength, duration);
            case DAMAGE_PLUS:
                return new InnerStrengthDamageBuff(strength, duration);
            case SPOTTED:
                return new InSightBuff(strength, duration);
            case BLIND:
                return new ObstructedVision(strength, duration);
            case ON_FIRE:
                return new OnFire(strength, duration);
            case POISONED:
                return new Poisoned(strength, duration);
            case SLOW:
                return new Slow(strength, duration);
            case VULNERABLE:
                return new Vulnerable(strength, duration);
            case WET:
                return new Wet(strength, duration);

            default:
                return new DefenderBuff(strength, duration);
        }
    }
}

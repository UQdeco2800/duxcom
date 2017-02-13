package uq.deco2800.duxcom.entities;

import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.Targetable;
import uq.deco2800.duxcom.buffs.AbstractBuff;
import uq.deco2800.duxcom.common.DamageType;
import uq.deco2800.duxcom.entities.enemies.listeners.DeathListener;
import uq.deco2800.duxcom.entities.heros.listeners.HealthListener;
import uq.deco2800.duxcom.util.TurnTickable;

import java.util.ArrayList;
import java.util.List;

/**
 * This is to be used as an abstraction for any characters in the game.
 * Initial intentions is that this will be extended by AbstractHero and AbstractEnemy.
 *
 * @author Alex McLean
 */
public abstract class AbstractCharacter extends Entity implements TurnTickable, Targetable {

    // Info
    protected String graphic;
    protected String name;

    // Stats
    protected double baseHealth;
    protected double health;
    protected double damage;
    protected double armour;
    protected double speed;
    protected List<AbstractAbility> abilities;

    // Buffs
    protected List<AbstractBuff> currentBuffs = new ArrayList<>();

    // Listeners
    protected boolean healthListenersEnabled = false;
    protected List<DeathListener> deathListeners = new ArrayList<>();
    protected List<HealthListener> healthListeners = new ArrayList<>();

    /**
     * Creates a new entity at position x, y with default elevation of 1
     *
     * @param entityType the type of the entity
     * @param x          x coordinate
     * @param y          y coordinate
     * @param lengthX    x length
     * @param lengthY    y length
     */
    public AbstractCharacter(EntityType entityType, int x, int y, int lengthX, int lengthY) {
        super(entityType, x, y, lengthX, lengthY);
    }

    /**
     * Gets the health of the character
     *
     * @return character health
     */
    public double getHealth() {
        return this.health;
    }

    /**
     * This method is just a pass-through so that the Targetable method is pseudo-overwritten
     *
     * @param rawChange the change in the health of the character
     * @param damageType damage type (if applicable)
     */
    public void changeHealth(double rawChange, DamageType damageType) {
        changeHealth(rawChange);
    }

    /**
     * Attempt to change health of a character, character cannot have more health than baseHealth
     *
     * @param rawChange the change in health of the character, can be positive or negative
     */
    public void changeHealth(double rawChange) {
        double change;
        if (rawChange <= 0) {
            change = rawChange * Math.round(100 / (100 + armour));
        } else {
            change = rawChange;
        }
        if (health + change <= 0) {
            health = 0;
            notifyDeath();
        } else if (health + change <= baseHealth) {
            health += change;
        } else {
            health = baseHealth;
        }
        notifyHealthChange();
    }

    /**
     * Returns the bodyArmour value of the character
     *
     * @return bodyArmour
     */
    public double getArmour() {
        return armour;
    }

    /**
     * Set the bodyArmour value of the character
     *
     * @param armour The new bodyArmour of the character
     */
    public void setArmour(double armour) {
        this.armour = armour;
    }

    /**
     * Return the current speed modifier of the character
     *
     * @return speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Set the speed modifier for the character
     *
     * @param speed New speed modifier
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Get the maximum/starting health of this character.
     *
     * @return Maximum/starting health of this character.
     */
    public double getBaseHealth() {
        return baseHealth;
    }

    /**
     * set character's health
     * DO NOT USE THIS NORMALLY, USE changeHealth(change)
     *
     * @param health character's new health
     */
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Attempt to change damage of a character, character cannot have negative damage
     *
     * @param rawChange the change in health of the character, can be positive or negative
     */
    public void changeDamage(double rawChange) {
        if (damage + rawChange <= 0) {
            damage = 0;
        } else {
            damage += rawChange;
        }
    }

    /**
     * Get the current damage of this character.
     *
     * @return Damage of this character.
     */
    public double getDamage() {
        return damage;
    }

    /**
     * set character's damage
     *
     * @param damage character's new damage
     */
    public void setDamage(double damage) {
        this.damage = damage;
    }

    /**
     * On change of health, notify all the listeners of this change.
     */
    private void notifyHealthChange() {
        if (healthListenersEnabled) {
            for (HealthListener healthListener : healthListeners) {
                healthListener.onHealthChange(health, baseHealth);
            }
        }
    }

    /**
     * Enable the character's listener so that when an AP or health change occurs, its listeners are updated.
     * <p>
     * This method is typically called at the start of the turn. For now only the current turn character fires health
     * and AP change events.
     */
    public void enableListeners() {
        healthListenersEnabled = true;
        notifyHealthChange();
    }

    /**
     * Disable the character's listeners so that none of them receive health or AP change notifications.
     * <p>
     * Note that the listeners are NOT removed - they will receive notifications again when they are reenabled.
     */
    public void disableListeners() {
        healthListenersEnabled = false;
    }

    /**
     * On death of character, notify all listeners.
     */
    private void notifyDeath() {
        for (DeathListener deathListener : deathListeners) {
            deathListener.onDeath(this);
        }
    }

    /**
     * Adds a health listener to the character.
     *
     * @param healthListener the listener to be added
     */
    public void addHealthListener(HealthListener healthListener) {
        if (!healthListeners.contains(healthListener)) {
            healthListeners.add(healthListener);
        }
    }

    /**
     * Removes a given health listener from the character.
     *
     * @param healthListener the listener to be removed
     */
    public void removeHealthListener(HealthListener healthListener) {
        healthListeners.remove(healthListener);
    }

    /**
     * Adds the given death listener to the current character.
     *
     * @param deathListener, the listener to be added.
     */
    public void addDeathListener(DeathListener deathListener) {
        if (!deathListeners.contains(deathListener)) {
            deathListeners.add(deathListener);
        }
    }

    /**
     * Removes the given death listener from the current character.
     *
     * @param deathListener, the listener to be removed.
     */
    public void removeDeathListener(DeathListener deathListener) {
        deathListeners.remove(deathListener);
    }

    /**
     * Returns the name of the character graphic
     *
     * @return graphic
     */
    @Override
    public String getImageName() {
        return graphic;
    }

    /**
     * Sets the name of the character graphic
     *
     * @param graphic the character graphic
     */
    public void setImageName(String graphic) {
        this.graphic = graphic;
    }

    /**
     * Get the abilities of the character
     *
     * @return list of the character abilities
     */
    public List<AbstractAbility> getAbilities() {
        return this.abilities;
    }

    /**
     * Sets the abilities of the character
     *
     * @param abilities a list of abilities
     */
    public void setAbilities(List<AbstractAbility> abilities) {
        this.abilities = abilities;
    }

    /**
     * Gets state of health listeners
     *
     * @return health listener state
     */
    public boolean isHealthListenersEnabled() {
        return healthListenersEnabled;
    }

    /**
     * Gets the ratio of health the character has remaining
     *
     * @return the character's current health divided by its base health
     */
    public double getRatioHealthRemaining() {
        return health / baseHealth;
    }

    public void upgradeSpeed(int tier) {
        switch (tier) {
            case 1:
                speed += 0.1;
                break;
            case 2:
                speed += 0.2;
                break;
            case 3:
                speed += 0.4;
                break;
            default:
                break;
        }
    }

    public void upgradeArmour(int tier) {
        switch (tier) {
            case 1:
                armour += 0.1;
                break;
            case 2:
                armour += 0.2;
                break;
            case 3:
                armour += 0.4;
                break;
            default:
                break;
        }
    }

    public void upgradeBaseHealth(int tier) {
        switch (tier) {
            case 1:
                baseHealth += 10;
                break;
            case 2:
                baseHealth += 12;
                break;
            case 3:
                baseHealth += 16;
                break;
            default:
                break;
        }
    }

    /**
     * Secure method for adding an AbstractBuff to a Character's list of
     * current Buffs. This method is to ensure that buff application is
     * consistent across all instances of AbstractCharacter.
     * <p>
     * This is currently used to ensure that buffs are not duplicated upon a
     * single character.
     *
     * @param buff the buff to be added.
     */
    @Override
    public void addBuff(AbstractBuff buff) {
        for (AbstractBuff existingBuff :
                currentBuffs) {
            if (buff.getName().equals(existingBuff.getName())) {
                return;
            }
        }
        currentBuffs.add(buff);
    }

    /**
     * Ticks the buffs that are affecting this hero
     * Will automatically remove the buff if its duration is over
     */
}

package uq.deco2800.duxcom.passives;


import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.Targetable;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.util.TurnTickable;


/**
 * Created by woody on 26-Jul-16.
 */
public abstract class AbstractPassive implements TurnTickable{

    protected String name;

    protected String description;

    protected AbstractHero hero;

    protected int activationTimer;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public int getActivationTimer() {
        return activationTimer;
    }

    public void setActivationTimer(int activationTimer) {
        this.activationTimer = activationTimer;
    }

    public void turnTick() {
        //do nothing by default
    }

    public void activateOnAbilityUse(Targetable target, AbstractAbility ability) {
        //do nothing by default
    }

    public void activateOnMove() {
        //do nothing by default
    }

    public void activateOnAbilityEffect(Entity origin, AbstractAbility ability) {
        //do nothing by default
    }

    /**
     * Overridden equals method to compare two passives. returns true if the passives have the same name
     * @param toCompare - the object to compare
     * @return - a boolean whether or not the two object have the same name
     */
    @Override
    public boolean equals(Object toCompare) {
        if(!(toCompare instanceof AbstractPassive))
            return false;
        AbstractPassive ap = (AbstractPassive) toCompare;
        return this.name.equals(ap.name);
    }

    /**
     * Generates and returns a hashcode for a passive
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

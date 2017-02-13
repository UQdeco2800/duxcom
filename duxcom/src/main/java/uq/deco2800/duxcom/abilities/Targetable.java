package uq.deco2800.duxcom.abilities;

import uq.deco2800.duxcom.buffs.AbstractBuff;
import uq.deco2800.duxcom.common.DamageType;
import uq.deco2800.duxcom.entities.Entity;

/**
 * Implement this interface to make an Entity targetable using abilities. #49
 * 
 * @author houraineet
 */
public interface Targetable {
	/**
	 * Should be overridden when targeting a Targetable with an ability to have 
	 * specialised events
	 * @return 
	 *		False iff the Targetable is immune to a specific ability.
	 */
	default boolean target(AbstractAbility ability) {
		return true;
	}

	/**
	 * Adds a buff to the targetable object
	 *
	 * @param buff the buff to add
	 */
	default void addBuff(AbstractBuff buff) {}

	/**
	 * Changes the health of the targetable object
	 *
	 * @param damage the damage to change the health by
	 */
	default void changeHealth(double damage, DamageType damageType) {}

	default void abilityEffect(Entity origin, AbstractAbility ability) {}
	

	/**
	 * Gets the x coordinate of the targetable object
	 *
	 * @return the x coordinate
	 */
	int getX();

	/**
	 * Gets the y coordinate of the targetable object
	 *
	 * @return the y coordinate
	 */
	int getY();

}

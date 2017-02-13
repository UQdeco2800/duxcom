package uq.deco2800.duxcom.entities.enemies.listeners;

import uq.deco2800.duxcom.entities.AbstractCharacter;

/**
 * Provides a system for notifying interested classes on the death of an Enemy.
 *
 * @author Lucas Wallin (shufflertoxin) on 26/09/2016.
 */

@FunctionalInterface
public interface DeathListener {

	/**
	 * To be overwritten and called upon a death
	 *
	 * @param character the dead character
	 */
	void onDeath(AbstractCharacter character);
}

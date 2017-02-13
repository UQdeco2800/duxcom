package uq.deco2800.duxcom.entities.heros.listeners;

import uq.deco2800.duxcom.entities.heros.AbstractHero;

/**
 * Provides a system for notifying current turn hero change.
 *
 * Any class which wishes to be notified of changes in the AP of a hero should implement this class and add itself as an
 * AP listener (generally through the hero manager).
 *
 * @author Eric.
 */

@FunctionalInterface
public interface CurrentHeroChangeListener {
	/**
	 * This method is called whenever the current hero selection is changed.
	 *
	 * @param AbstractHero      hero Object
	 */
	void onHeroChange(AbstractHero hero);
}

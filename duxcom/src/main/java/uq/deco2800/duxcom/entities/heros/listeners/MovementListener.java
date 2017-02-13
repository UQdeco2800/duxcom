package uq.deco2800.duxcom.entities.heros.listeners;

/**
 * Provides a system for notifying interested classes when a hero moves.
 *
 * Any class which wishes to be notified of changes in the position of a hero should implement this class and add itself
 * as a health listener (generally through the hero manager).
 *
 * @author shamous123 on 17/10/2016.
 */

@FunctionalInterface
public interface MovementListener {
	/**
	 * This method is called whenever a hero (being listened to) moves.
	 *
	 * @param x - the x position of the hero before movement
	 * @param y  - the y position of the hero before movement
	 */
	void onMovement(int x, int y);
}

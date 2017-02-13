package uq.deco2800.duxcom.util;

@FunctionalInterface
public interface TurnTickable {
	
	/**
	 * Updates the TurnTickable every time a turn is ended
	 */
	void turnTick();

}

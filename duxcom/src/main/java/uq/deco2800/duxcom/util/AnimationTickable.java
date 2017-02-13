package uq.deco2800.duxcom.util;

import uq.deco2800.duxcom.graphics.AnimationManager;

/**
 * Created by wondertroy on 28/08/2016.
 * Animation tick used for updating visuals.
 */
public interface AnimationTickable {

	/**
	 * @return String used to dynamically synchronise multiple instances
	 * of AnimationTickables. AnimationTickables with the same syncString
	 * will be ticked at the same time. (This probably will not work nicely 
	 * with a varying tick interval)
	 * 
	 * Default = null = unsynchronised.
	 */
	default String getSyncString() {
	    return null;
	}
	
	/**
	 * Registers the AnimationTickable to the AnimationManager to be ticked
	 */
	default void register() {
	    AnimationManager.register(this);
	}
	
	/**
	 * Unregistering from the AnimationManager
	 */
	default void unregister() {
	    AnimationManager.unregister(this);
	}
	
	/**
	 * @return the interval for animation ticks in miliseconds
	 */
	long getTickInterval();
	
	/**
	 * Updates AnimationTickable 
	 */
	void animationTick();
}

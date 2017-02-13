package uq.deco2800.duxcom.graphics;

import javafx.animation.AnimationTimer;
import org.apache.commons.lang3.tuple.MutablePair;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.util.AnimationTickable;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Handles AnimationTickables
 * 
 * Created by houraineet 27/09/16
 */
public class AnimationManager extends AnimationTimer {
	private GameManager gameManager;

	private LinkedHashMap<AnimationTickable, Long> animationTickableMap = new LinkedHashMap<>();
	private LinkedHashMap<String, MutablePair<LinkedList<AnimationTickable>, Long>> synchronisedATM = new LinkedHashMap<>();
	private static LinkedList<AnimationTickable> registerCache = new LinkedList<>();
	private static LinkedList<AnimationTickable> unregisterCache = new LinkedList<>();
	private static boolean paused = false;

	/**
	 * Initialises AnimationManager with reference to the GameManager instance
	 */
	public AnimationManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	/**
	 * Registers an AnimationTickable to be added to the animationTickableMap
	 * in the next AnimationManager#handle(long) method.
	 * 
	 * @param a 
	 *		AnimationTickable instance to be registered
	 */
	public static void register(AnimationTickable a) {
		registerCache.add(a);
	}
	
	/**
	 * Unregisters an AnimationTickable to be removed from the animationTickableMap
	 * in the next AnimationManager#handle(long) method.
	 * 
	 * @param a 
	 *		AnimationTickable instance to be unregistered
	 */
	public static void unregister(AnimationTickable a) {
		unregisterCache.add(a);
	}

	@Override
	public void handle(long now) {

		if (paused) {
			return;
		}

		animationTickableMap.entrySet().stream().filter(e -> e.getValue() < now).forEach(e -> {
			gameManager.setGameChanged(true);
			e.getKey().animationTick();
			e.setValue(nextTick(e.getKey(), now));
		});

		synchronisedATM.values().stream().filter(p -> p.getValue() < now).forEach(p -> {
			gameManager.setGameChanged(true);
			p.getKey().forEach(AnimationTickable::animationTick);
			p.setRight(nextTick(p.getKey().getFirst(), now));
		});

		for (AnimationTickable a : registerCache) {
			if (a.getSyncString() == null) {
				animationTickableMap.put(a, nextTick(a, now));
				continue;
			}
			if (!synchronisedATM.containsKey(a.getSyncString())) {
				synchronisedATM.put(a.getSyncString(), new MutablePair<>(new LinkedList<>(), nextTick(a, now)));
			}
			synchronisedATM.get(a.getSyncString()).getKey().add(a);
		}
		registerCache.clear();
		
		for (AnimationTickable a : unregisterCache) {
			if (a.getSyncString() == null) {
				animationTickableMap.remove(a);
				continue;
			}
			if (synchronisedATM.containsKey(a.getSyncString())) {
				synchronisedATM.get(a.getSyncString()).getKey().remove(a);
				if (synchronisedATM.get(a.getSyncString()).getKey().isEmpty()) {
					synchronisedATM.remove(a.getSyncString());
				}
			}
		}
		unregisterCache.clear();
	}
	
	/**
	 * Calculate the next tick time using the AnimationTickable's tick interval
	 * and current time, `now`.
	 * @return 
	 *		Next tick time for the AnimationTickable, a, in nanoseconds.
	 */
	private static long nextTick(AnimationTickable a, long now) {
		return (a.getTickInterval() * 1000000) + now;
	}

	/**
	 * Pauses all animations on the map
	 */
	public static void pause() {
		if (!paused) {
			paused = true;
		}
	}

	/**
	 * Resumes all animations on the map
	 */
	public static void unpause() {
		if (paused) {
			paused = false;
		}
	}
}

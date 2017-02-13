package uq.deco2800.duxcom.entities.dynamics;

import java.util.ArrayList;
import java.util.List;

import uq.deco2800.duxcom.abilities.Targetable;
import uq.deco2800.duxcom.common.DamageType;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.util.AnimationTickable;

/**
 * Created by woody on 16-Oct-16.
 * Simple water barrel - when attacked will burst and release water which will spread out and create a water surface.
 * The surface is implemented using livetiles.
 */
public class WaterBarrel extends DynamicEntity implements Targetable, AnimationTickable {

	private static final List<Phase> PHASES = new ArrayList<>();
	static {
		PHASES.add(new Phase(EntityType.WATER_BARREL_1).transmissionRate(0.5));
		PHASES.add(new Phase(EntityType.WATER_BARREL_2).transmissionRate(0.5));
		PHASES.add(new Phase(EntityType.WATER_BARREL_3).transmissionRate(0.5));
		PHASES.add(new Phase(EntityType.WATER_BARREL_4).transmissionRate(0.5));
		PHASES.add(new Phase(EntityType.WATER_BARREL_5).transmissionRate(0.5));
		PHASES.add(new Phase(EntityType.WATER_BARREL_6).transmissionRate(0.5));
		PHASES.add(new Phase(EntityType.WATER_BARREL_7).transmissionRate(0.5));
	}

	/**
	 * Constructs an instance of WaterBarrel at the given coordinates.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public WaterBarrel(int x, int y) {
		super(EntityType.WATER_BARREL, x, y);
		setPhases(PHASES);
	}

	/**
	 * Returns the tick interval (for animation tick) for the WaterBarrel dynamic entity.
	 *
	 * @return tick interval, represented as a long
	 */
	@Override
	public long getTickInterval() {
		return 500L;
	}

	/**
	 * Determines how the WaterBarrel dynamic entity behaves for each animation tick.
	 * This entity just cycles through its phases to create an animation effect.
	 */
	@Override
	public void animationTick() {
		advanceCurrentPhase();
	}

	/**
	 * Any time the WaterBarrel dynamic entity takes damage, it will break.
	 * This results in the entity being destroyed and a water entity being added
	 * in its place to create a water spill effect.
	 *
	 * @param damage the damage to change the health by
     */
	@Override
	public void changeHealth(double damage, DamageType damageType) {
		int x = super.getX();
		int y = super.getY();

		destroy();

		//place water entity
		//@todo add range to Water...
		dynamicsManager.addEntity(new Water(x, y, 5));

	}

	/**
	 * Determines how the entity behaves for each turn tick. This simply
	 * advances the current phase to aid with the animation effect.
     */
	@Override
	public void turnTick() {
		advanceCurrentPhase();
	}

	/**
	 * Returns the sync string (for animation tick) for the WaterBarrel dynamic entity.
	 *
	 * @return the sync string, represented as a string (what a surprise)
	 */
	@Override
	public String getSyncString() {
		return "WATER_BARREL";
	}

}

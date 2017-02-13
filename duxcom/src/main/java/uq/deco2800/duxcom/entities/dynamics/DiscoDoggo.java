package uq.deco2800.duxcom.entities.dynamics;

import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.util.AnimationTickable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houraineet on 10/10/2016.
 */
public class DiscoDoggo extends DynamicEntity implements AnimationTickable {

	private static final List<Phase> PHASES = new ArrayList<>();
	static {
		PHASES.add(new Phase().transmissionRate(0.5));
		PHASES.add(new Phase(EntityType.CAVALIER_AQUA).transmissionRate(0.5));
		PHASES.add(new Phase(EntityType.CAVALIER_BLUE).transmissionRate(0.5));
		PHASES.add(new Phase(EntityType.CAVALIER_GREEN).transmissionRate(0.5));
		PHASES.add(new Phase(EntityType.CAVALIER_ORANGE).transmissionRate(0.5));
		PHASES.add(new Phase(EntityType.CAVALIER_PINK).transmissionRate(0.5));
		PHASES.add(new Phase(EntityType.CAVALIER_RED).transmissionRate(0.5));
		PHASES.add(new Phase(EntityType.CAVALIER_YELLOW).transmissionRate(0.5));
	}

	/**
	 * Constructs a strong, independent doggo who animates like it's nobodies business
	 *
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public DiscoDoggo(int x, int y) {
		super(EntityType.CAVALIER, x, y);
		setPhases(PHASES);
	}

	/**
	 * Returns the sync string (for animation tick) for the doggo dynamic entity.
	 *
	 * @return the sync string, represented as a string (what a surprise)
	 */
	@Override
	public String getSyncString() {
		return "DISCO_DOGGO";
	}

	/**
	 * Returns the tick interval (for animation tick) for the doggo dynamic entity.
	 *
	 * @return tick interval, represented as a long
	 */
	@Override
	public long getTickInterval() {
		return 1000L;
	}

	/**
	 * Determines how the doggo dynamic entity behaves for each animation tick. Specifically,
	 * the doggo entity just advances its phase. Rave on.
	 */
	@Override
	public void animationTick() {
		advanceCurrentPhase();
	}

	/**
	 * Determines how the doggo dynamic entity behaves for each turn tick. Specifically,
	 * the doggo entity has a 50% chance to transmit itself to surrounding tiles on each turn.
	 * Spread on, doggo, spread on.
     */
	@Override
	public void turnTick() {
		dynamicsManager.forEachSurrounding(getX(), getY(), 1, false, 
				(Integer x, Integer y) -> {
					if (dynamicsManager.isEmpty(x, y) && getCurrentPhase().transmits()) {
						dynamicsManager.addEntity(new DiscoDoggo(x, y));
					}
				});
		this.advanceCurrentPhase();
	}
}

package uq.deco2800.duxcom.entities.dynamics;

import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.util.AnimationTickable;

/**
 * More of a proof of concept thing. The final version of the game probably
 * won't require this kind of animating entity, hence will not get much more
 * support/development/refactoring.
 *
 * Created by houraineet 31/08/16
 */
public class Water extends DynamicLiquid implements AnimationTickable {

    /**
     * Constructs an instance of Water with the given coordinates
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Water(int x, int y, int startingRange) {
        super(EntityType.WATER_1, x, y, startingRange);
    }

    /**
     * Returns the tick interval (for animation tick) for the Water dynamic entity.
     *
     * @return tick interval, represented as a long.
     */
    @Override
    public long getTickInterval() {
        return 150L;
    }

    /**
     * Returns the sync string (for animation tick) for the Water dynamic entity.
     *
     * @return the sync string, represented as a string.
     */
    @Override
    public String getSyncString() {
        return "WATER";
    }
}

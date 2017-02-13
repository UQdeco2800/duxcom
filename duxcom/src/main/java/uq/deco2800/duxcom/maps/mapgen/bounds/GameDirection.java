package uq.deco2800.duxcom.maps.mapgen.bounds;

import uq.deco2800.duxcom.annotation.UtilityConstructor;

/**
 * Handles the different direction concepts
 *
 * Created by liamdm on 19/08/2016.
 */
public class GameDirection {
    /**
     * Translates an oClock direction to an absolute direction. For example in the map a FOUR_OCLOCK is LEFT
     * @param direction
     * @return the direction
     */
    public static Direction translateToUniversalFormat(Direction direction){
        switch(direction){
            case FOUR_OCLOCK:
                return Direction.DOWN;
            case SEVEN_OCLOCK:
                return Direction.LEFT;
            case TEN_OCLOCK:
                return Direction.UP;
            case TWO_OCLOCK:
                return Direction.RIGHT;
            default:
                return direction;
        }
    }

    @UtilityConstructor
    private GameDirection(){}
}

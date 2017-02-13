package uq.deco2800.duxcom.objectives;

import uq.deco2800.duxcom.objectivesystem.ObjectiveCoordinate;

/**
 * Represents an objective for reaching a particular position (x, y)
 * on a map.
 * EDIT: Now accepts a ObjectiveCoordinate (x, y) object instead of
 * a tile
 * @require position != null and position coordinates not less than 0
 * or greater than the size of the map being placed on
 * @ensure description = "Reach the tile at position (-x-, -y-)"
 * Created by Tyler Beutel on 9/15/2016.
 */
public class MovementObjective extends Objective {
    public MovementObjective(ObjectiveCoordinate position) {
        super(position, false, "Reach the tile at position (" +
                position.getX() + ", " + position.getY() + ")" );
    }
}
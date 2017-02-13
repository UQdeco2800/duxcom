package uq.deco2800.duxcom.objectivesystem;

/**
 * Like the Point class, except to be used ONLY for storing
 * the target spot on the map to move to for a MovementObjective.
 * Created by Tom B on 14/10/2016.
 */

public class ObjectiveCoordinate {
    private int x;
    private int y;

    /**
     * Creates an ObjectiveCoordinate.
     * @param x - x coordinate
     * @param y - y coordinate
     * @require 0 <= x < size of map && 0 <= y < size of map
     */
    public ObjectiveCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get x-coordinate.
     * @return x-coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get y-coordinate.
     * @return y-coordinate
     */
    public int getY() {
        return this.y;
    }

    /**
     * Determines whether or not two ObjectiveCoordinate objects
     * are equal - they will be if their x and y coordinates are equal.
     * @param o object being compared for equality
     * @return whether or not two given ObjectiveCoordinate objects
     *          are equal according to the above description.
     * @require o != null
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ObjectiveCoordinate)) {
            return false;
        }

        ObjectiveCoordinate toCompare = (ObjectiveCoordinate)o;
        return toCompare.getX() == this.getX() && toCompare.getY() == this.getY();
    }

    /**
     * Returns a hashcode representation of the given ObjectiveCoordinate.
     * object.
     * @return 23 * this.x + 33 * this.y
     * @ensure for any two ObjectiveCoordinate objects a, b who have x1, y1
     *         and x2, y2 as their x and y coordinates - if x1 == y2 and
     *         x2 == y1, then a.hashCode() != b.hashCode()
     */
    @Override
    public int hashCode() {
        return 23 * this.x + 33 * this.y;
    }

    /**
     * Returns a string representation of the given ObjectiveCoordinate
     * object.
     * @return (this.x, this.y)
     */
    @Override
    public String toString() {
        return this.x + "," + this.y;
    }
}

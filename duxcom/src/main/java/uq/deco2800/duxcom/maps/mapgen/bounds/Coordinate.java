package uq.deco2800.duxcom.maps.mapgen.bounds;

/**
 * Represents a simple x-y coordinate without the memory overhead of a Point
 *
 * Created by liamdm on 19/08/2016.
 */
public class Coordinate {
    public final int x;
    public final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return x * 7 + y * 13;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Coordinate)){
            return false;
        }
        Coordinate c = (Coordinate) obj;
        return c.x == this.x && c.y == this.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
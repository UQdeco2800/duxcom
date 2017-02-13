package uq.deco2800.duxcom.maps.mapgen.bounds;

import java.util.ArrayList;

/**
 * Stores a set of area bounds to notify if any point is covered in this set of bounds
 *
 * Created by liamdm on 19/08/2016.
 */
public class CoverageArea {
    ArrayList<AreaBound> bounds = new ArrayList<>();

    /**
     * Adds a bound to the list of bounds
     * @param bound
     */
    public void addBound(AreaBound bound){
        bounds.add(bound);
    }

    /**
     * Returns true if the point is in these bounds
     * @param x
     * @param y
     * @return true iff point in bounds
     */
    public boolean contains(int x, int y){
        for(AreaBound bound : bounds){
            if(bound.contains(x, y)){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the minimum distance to the covered area
     * @return minimum distance
     */
    public int minimumDistanceToCoveredArea(AreaBound newBound){
        int minDistance = Integer.MAX_VALUE;
        for(AreaBound bound : bounds){
            minDistance = Math.min(minDistance, bound.leastDistanceTo(newBound));
        }
        return minDistance;
    }

}

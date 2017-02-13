package uq.deco2800.duxcom.maps.mapgen.bounds;

import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores data regarding groups of multiple blocks
 *
 * Created by liamdm on 19/08/2016.
 */
public class BlockGroup {

    /**
     * Stores the list of block numbers in this group
     */
    private ArrayList<Coordinate> blockPoints = new ArrayList<>();
    private List<AreaBound> blockAreas = new ArrayList<>();
    private AreaBound storedArreaBound;
    private int storedAreaBoundHash = 0;

    private int getAreaBoundHash() {
        return blockPoints.hashCode();
    }

    /**
     * @param current - current assigned value
     * @param compareTo - current will be compare to compareTo value
     * @param signal - zero for comparing min and 1 for comparing max
     * @return result Integer value
     */
    private Integer comparePoints(Integer current, Integer compareTo, int signal) {

        Integer result;
        if (signal == 0) {

            result = current == null || compareTo < current ? compareTo : current;
        } else {

            result = current == null || compareTo > current ? compareTo : current;
        }

        return result;
    }

    /**
     * Gets the group bounds, the smallest rectangle that encloses all the blocks completely.
     * This will likely have empty space in some areas.
     *
     * @return the group bound
     */
    public AreaBound getGroupBound() {
        // The bound does not need to be regenerated
        if (getAreaBoundHash() == storedAreaBoundHash) {
            return storedArreaBound;
        }

        Integer minX = null;
        Integer minY = null;
        Integer maxX = null;
        Integer maxY = null;

        for (Coordinate blockPoint : blockPoints) {
            int startX = BlockPointMapper.blockPointToWorldPoint(blockPoint.x);
            int startY = BlockPointMapper.blockPointToWorldPoint(blockPoint.y);
            int endX = BlockPointMapper.blockPointToWorldPoint(blockPoint.x) + MapAssembly.getBlockSize();
            int endY = BlockPointMapper.blockPointToWorldPoint(blockPoint.y) + MapAssembly.getBlockSize();

            minX = comparePoints(minX, startX, 0);
            minY = comparePoints(minY, startY, 0);

            maxX = comparePoints(maxX, endX, 1);
            maxY = comparePoints(maxY, endY, 1);
        }

        // No area bound
        if (minX == null) {
            return null;
        }

        // Store for later use
        storedArreaBound = new AreaBound(minX, minY, maxX, maxY);
        storedAreaBoundHash = getAreaBoundHash();

        return storedArreaBound;
    }

    /**
     * Adds a block point to the block point list
     *
     * @param blockPoint the block point to add
     */
    public void addBlock(Coordinate blockPoint) {
        blockPoints.add(blockPoint);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < (blockPoints.size() - 1); ++i){
            sb.append(blockPoints.get(i).toString());
            sb.append(", ");
        }
        sb.append(blockPoints.get(blockPoints.size() - 1).toString());

        return sb.toString();
    }

    /**
     * Gets the area bounds for the blocks in this block group
     * @return the list of bounds
     */
    public List<AreaBound> getBlockLocations(){

        for(Coordinate blockPoint : blockPoints){
            AreaBound bound = new AreaBound(blockPoint);
            blockAreas.add(bound);
        }
        return blockAreas;
    }

    /**
     * Returns true if the given point is in any of the block groups
     *
     * @param x
     * @param y
     * @return true iff point in a block group
     */
    public boolean contains(int x, int y) {

        for(Coordinate blockPoint : blockPoints) {
            AreaBound areaBound = new AreaBound(blockPoint);
            if(areaBound.contains(x, y)){
                return true;
            }
        }
        return false;
    }
}

package uq.deco2800.duxcom.maps.mapgen.bounds;


import uq.deco2800.duxcom.annotation.UtilityConstructor;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Stores a mapping of world points to their relative blocks
 *
 * Created by liamdm on 19/08/2016.
 */
public class BlockPointMapper {

    public static class BlockPointMapperRuntimeException extends RuntimeException {

        public BlockPointMapperRuntimeException(String string) {
            super(string);
        }

    }

    /**
     * need to have both maps (regardless of the redundant information) as without either
     * the delay for lookup is significant
     *
     * @require for every key in mapC2I there is a unique value in mapI2C
     */
    private static HashMap<Coordinate, Integer> mapC2I = new HashMap<>();

    /**
     * Resets the block point map
     */
    public static void clearCache(){
        mapC2I.clear();
        growMap(2000);
    }

    /**
     * Converts a block point to the relevant world point
     * @param blockPoint
     * @return the world point
     */
    public static int blockPointToWorldPoint(int blockPoint){
        return blockPoint *  MapAssembly.getBlockSize();
    }

    /**
     * Returns a list of blocks that are within the given distance of the target
     *
     * @return the block list
     */
    public static List<Coordinate> getInRangeBlocks(int pointX, int pointY, int distance){
        ArrayList<Coordinate> blocksInRange = new ArrayList<>();

        int startSampleBoxX = pointX - distance;
        int startSampleBoxY = pointY - distance;

        int endSampleBoxX = pointX + distance;
        int endSampleBoxY = pointY + distance;

        Coordinate blockPointStart = BlockPointMapper.getBlockPoint(startSampleBoxX, startSampleBoxY);
        Coordinate blockPointEnd = BlockPointMapper.getBlockPoint(endSampleBoxX, endSampleBoxY);

        /**
         * Get all the block coordinates in attackRange
         */
        for(int y = blockPointStart.y; y < blockPointEnd.y; ++y) {
            for (int x = blockPointStart.x; x < blockPointEnd.x; ++x) {
                Coordinate blockPoint = new Coordinate(x, y);
                if (!blocksInRange.contains(blockPoint)) {
                    blocksInRange.add(blockPoint);
                }
            }
        }

        return blocksInRange;
    }

    public static int coordinateWithinBlock(int originalCoordiante){
        if (MapAssembly.getBlockSize() == 0) {
            return 0;
        }
        return Math.abs(originalCoordiante) % MapAssembly.getBlockSize();
    }

    public static void init(){
        // Do nothing, static class will auto-grow map
    }

    /**
     * generate the spiral map for 200 blocks
     */
    static {
        growMap(2000);
    }

    /**
     * Gets the block number for a given coordinate
     *
     * @param coordinate
     * @return the block number
     */
    public static int getBlockNumber(Coordinate coordinate){
        hasCoordinate:
        {
            for (int i = 0; i < 10; ++i) {
                if (!mapC2I.containsKey(coordinate)) {
                    growMap(2000);
                } else {
                    break hasCoordinate;
                }
            }
            // Failed
            throw new BlockPointMapperRuntimeException("Out of memory, map too large!");
        }

        return mapC2I.get(coordinate);
    }

    /**
     * Try and convert the block number to a coordinate. If this fails returns null.
     * Will NOT grow map as should only be called with reference to known blocks.
     *
     * @param blockNumber
     * @return the coordinate
     */
    public static Coordinate tryConvertNumberToCoordinate(int blockNumber){
        for(Map.Entry<Coordinate, Integer> entry : mapC2I.entrySet()){
            if(entry.getValue() == blockNumber){
                return entry.getKey();
            }
        }
        return null;
    }

    private static void growMap(int growBy){
        int bX = 0;
        int bY = 0;

        int lbX = 0;
        int lbY = 0;

        int direction = 0;

        int grownBy = 0;

        for(int blockNum = 0; grownBy < growBy; ++blockNum){
            if(mapC2I.containsKey(blockNum)){
                continue;
            } else {
                ++grownBy;
                mapC2I.put(new Coordinate(bX, bY), blockNum);
            }
            switch (direction) {
                case 0:
                    if (++bY > lbY) {
                        lbY = bY;
                        direction = 1;
                    }
                    break;
                case 1:
                    if (++bX > lbX) {
                        lbX = bX;
                        direction = 2;
                    }
                    break;
                case 2:
                    if (--bY <= -1 * lbY) {
                        direction = 3;
                    }
                    break;
                case 3:
                    if (--bX <= -1 * lbX) {
                        direction = 0;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Gets the next block in the given direction. Only call this function on known block numbers
     * as this will not cause the block map to grow!
     *
     * @param currentBlock the current block to use as a reference
     * @param direction the direction to travel
     */
    public static int getNextBlock(int currentBlock, Direction direction){

        // Convert in case anyone wants to use the clock positions
        Direction transformedDirection = GameDirection.translateToUniversalFormat(direction);

        // DO NOT ERROR CHECK THESE VALUES
        // This function should only be called on known blocks so a null pointer should be thrown
        // if this is used on an invalid block
        Coordinate c = BlockPointMapper.tryConvertNumberToCoordinate(currentBlock);

        if(c == null){
            return -1;
        }

        int addY = transformedDirection == Direction.UP ? 1  : transformedDirection == Direction.DOWN ? -1 : 0;
        int addX = transformedDirection == Direction.RIGHT ? 1 : transformedDirection == Direction.LEFT ? -1 : 0;

        return BlockPointMapper.getBlockNumber(new Coordinate(c.x + addX, c.y + addY));
    }

    /**
     * Get the block number from the world point
     *
     * @param worldPointX
     * @param worldPointY
     * @return the block number
     */
    public static Coordinate getBlockPoint(int worldPointX, int worldPointY) {

        double adjustedX = (double)worldPointX / (double)MapAssembly.getBlockSize();
        double adjustedY = (double)worldPointY / (double)  MapAssembly.getBlockSize();

        int finalX = (int)Math.floor(adjustedX);
        int finalY = (int)Math.floor(adjustedY);

        return new Coordinate(finalX, finalY);
    }

    @UtilityConstructor
    private BlockPointMapper(){}
}

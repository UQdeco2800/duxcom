package uq.deco2800.duxcom.maps.mapgen.bounds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

/**
 * Represents an area bounded by two coordinates
 *
 * Created by liamdm on 17/08/2016.
 */
public class AreaBound {

    private static Logger logger = LoggerFactory.getLogger(AreaBound.class);

    //each of the four points
    int topLeftX;
    int topLeftY;
    int bottomRightX;
    int bottomRightY;

    /**
     * Defines the bounds of the area
     * @require topLeft != null && bottomRight != null
     * && bottomRight.getX() >= topLeft.getX()
     * && bottomRight.getY() >= topLeft.getY();
     *
     */
    public AreaBound(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {

        // TODO ASSERT REMOVE ON RELEASE
        // <editor-fold desc="Assert statements here, do not remove till release!  ">

        if(bottomRightX <= topLeftX){
            logger.error("The bottom right coordinate's x position "+bottomRightX +" was before that of the top left " + bottomRightX + "!");
        }

        if(bottomRightY <= topLeftY){
            logger.error("The bottom right coordinate's x position "+bottomRightY +" was before that of the top left " + bottomRightY + "!");
        }
        // </editor-fold>

        this.bottomRightX = bottomRightX;
        this.bottomRightY = bottomRightY;
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
    }

    /**
     * Returns true if the given point is within bounds of this area
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public boolean contains(int x, int y){
        boolean containedHorizontally = x >= topLeftX && x < bottomRightX;
        boolean containedVertically = y >= topLeftY && y < bottomRightY;
        return containedHorizontally && containedVertically;
    }

    /**
     * Gets the area bounds for a block given a block point
     * @param blockPoint the block point to check
     */
    public AreaBound(Coordinate blockPoint) {
        int startWorldX = BlockPointMapper.blockPointToWorldPoint(blockPoint.x);
        int startWorldY = BlockPointMapper.blockPointToWorldPoint(blockPoint.y);
        int endWorldX = BlockPointMapper.blockPointToWorldPoint(blockPoint.x) + MapAssembly.getBlockSize();
        int endWorldY = BlockPointMapper.blockPointToWorldPoint(blockPoint.y) + MapAssembly.getBlockSize();

        this.bottomRightX = endWorldX;
        this.bottomRightY = endWorldY;
        this.topLeftX = startWorldX;
        this.topLeftY = startWorldY;
    }

    /**
     * Gets the width of this bound
     */
    public int getWidth(){
        return bottomRightX - topLeftX;
    }

    /**
     * Gets the height of this bound
     */
    public int getHeight(){
        return bottomRightY - topLeftY;
    }

    /**
     * Gets the starting x coordinate of the AreaBound
     *
     * @return top left x coordinate
     */
    public int getStartX(){
        return topLeftX;
    }

    /**
     * Gets the starting y coordinate of the AreaBound
     *
     * @return top left y coordinate
     */
    public int getStartY(){
        return topLeftY;
    }

    /**
     * Gets the ending x coordinate of the AreaBound
     *
     * @return bottom right x coordinate
     */
    public int getEndX(){
        return bottomRightX;
    }

    /**
     * Gets the ending y coordinate of the AreaBound
     *
     * @return bottom right y coordinate
     */
    public int getEndY(){
        return bottomRightY;
    }

    /**
     * Gets the least distance between this pattern and another pattern
     *
     * @return the minimum distance
     */
    public int leastDistanceTo(AreaBound otherArea) {
        ArrayList<Coordinate> thisBounds = new ArrayList<>();
        thisBounds.add( new Coordinate(getStartX(), getStartY()));
        thisBounds.add(new Coordinate(getStartX(), getEndY()));
        thisBounds.add(new Coordinate(getEndX(), getStartY()));
        thisBounds.add(new Coordinate(getEndX(), getEndY()));

        ArrayList<Coordinate> otherBounds = new ArrayList<>();
        otherBounds.add( new Coordinate(otherArea.getStartX(), otherArea.getStartY()));
        otherBounds.add(new Coordinate(otherArea.getStartX(), otherArea.getEndY()));
        otherBounds.add(new Coordinate(otherArea.getEndX(), otherArea.getStartY()));
        otherBounds.add(new Coordinate(otherArea.getEndX(), otherArea.getEndY()));


        double minimumDistance = Integer.MAX_VALUE;
        for(Coordinate p1 : thisBounds){
            for(Coordinate p2 : otherBounds){
                minimumDistance = Math.min(minimumDistance, Math.abs(java.awt.geom.Point2D.distance(p1.x, p1.y, p2.x, p2.y)));
            }
        }

        return (int) Math.floor(minimumDistance);
    }

    @Override
    public String toString() {
        return "(" + getStartX() + ", " + getStartY() + ") --> (" + getEndX() + ", " + getEndY() + ")";
    }
}

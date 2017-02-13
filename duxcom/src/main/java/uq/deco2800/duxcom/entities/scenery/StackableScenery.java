package uq.deco2800.duxcom.entities.scenery;

import uq.deco2800.duxcom.graphics.scenery.SceneryType;

/**
 * Created by Emma on 15/10/2016.
 */
public class StackableScenery extends AbstractScenery {

    private double height;

    /**
     * Constructs a new instance of AbstractScenery given the coordinates and scenery type
     *
     * @param sceneryType the type of scenery
     * @param x           the x coordinate
     * @param y           the y coordinate
     */
    public StackableScenery(SceneryType sceneryType, int x, int y) {
        super(sceneryType, x, y);
        this.height = sceneryType.getHeight();
    }

    /**
     * SCALED
     */
    public StackableScenery(SceneryType sceneryType, int x, int y, double scale) {
        super(sceneryType, x, y, scale);
        this.height = sceneryType.getHeight();
    }

    /**
     * Constructs a new instance of AbstractScenery given the coordinates and scenery type
     *
     * @param sceneryType the type of scenery
     * @param x           the x coordinate
     * @param y           the y coordinate
     * @param xLength     length in the x direction
     * @param yLength     length in the y direction
     */
    public StackableScenery(SceneryType sceneryType, int x, int y, int xLength, int yLength) {
        super(sceneryType, x, y, xLength, yLength);
        this.height = sceneryType.getHeight();
    }

    /**
     * SCALED
     */
    public StackableScenery(SceneryType sceneryType, int x, int y, int xLength, int yLength, double scale) {
        super(sceneryType, x, y, xLength, yLength, scale);
        this.height = sceneryType.getHeight();
    }

    /**
     * Gets the height of the stackable entity
     *
     * @return height
     */
    public double getHeight() {
        return height;
    }
}

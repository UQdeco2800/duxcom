package uq.deco2800.duxcom.entities.scenery;

import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.graphics.scenery.SceneryType;

/**
 * This is the class used to place Scenery entities on the map.
 * <p>
 * Created by jay-grant on 9/10/2016.
 */
public class AbstractScenery extends Entity {

    private SceneryType sceneryType;
    double scale = 1;

    /**
     * Constructs a new instance of AbstractScenery given the coordinates and scenery type
     *
     * @param sceneryType the type of scenery
     * @param x           the x coordinate
     * @param y           the y coordinate
     */
    public AbstractScenery(SceneryType sceneryType, int x, int y) {
        super(EntityType.SC, x, y, 1, 1);
        this.sceneryType = sceneryType;
    }

    public AbstractScenery(SceneryType sceneryType, int x, int y, double scale) {
        super(EntityType.SC, x, y, 1, 1);
        this.sceneryType = sceneryType;
        this.scale = scale;
    }

    /**
     * Constructs a new instance of AbstractScenery given the coordinates and scenery type and lengths
     *
     * @param sceneryType the type of scenery
     * @param x           the x coordinate
     * @param y           the y coordinate
     * @param xLength     the x length
     * @param yLength     the y length
     */
    public AbstractScenery(SceneryType sceneryType, int x, int y, int xLength, int yLength) {
        super(EntityType.SC, x, y, xLength, yLength);
        this.sceneryType = sceneryType;
    }

    public AbstractScenery(SceneryType sceneryType, int x, int y, int xLength, int yLength, double scale) {
        super(EntityType.SC, x, y, xLength, yLength);
        this.sceneryType = sceneryType;
        this.scale = scale;
    }

    /**
     * Gets the name of the scenery
     *
     * @return scenery name
     */
    public String getSceneryString() {
        return sceneryType.toString();
    }

    /**
     * Gets the type of the scenery
     *
     * @return scenery type
     */
    public SceneryType getSceneryType() {
        return sceneryType;
    }

    /**
     * Gets the the name of the scenery's associated image. This should
     * be the same as the scenery's type.
     *
     * @return scenery image's name
     */
    @Override
    public String getImageName() {
        return sceneryType.toString();
    }

    public double getScale() {
        return scale;
    }
}

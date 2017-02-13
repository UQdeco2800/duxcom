package uq.deco2800.duxcom.interfaces.mapcreatorinterface;

import uq.deco2800.duxcom.interfaces.AbstractDisplayManager;
import uq.deco2800.duxcom.interfaces.RenderOrder;

/**
 * Manages the order in which objects are loaded to the Map Creator stage
 *
 * @author Lucas Reher
 * @author Sam Thomas
 */
public class MapCreatorDisplayManager extends AbstractDisplayManager<MapCreatorDisplays> {
    /**
     * Manages all the handlers used by Map Creator and the
     * order in which to load them.
     *
     * @param currentDisplay           The display types which are avaiable for Map Creator
     *                                 (MAP_CREATOR being the only one)
     */
    public MapCreatorDisplayManager(MapCreatorDisplays currentDisplay) {
        super(currentDisplay);

        // Loads Background, will have changing weather
        addHandler(currentDisplay, RenderOrder.BACKGROUND, new MapCreatorWeatherGraphicsHandler());

        // Loads Map Tiles
        addHandler(currentDisplay, RenderOrder.FLOOR, new MapCreatorMapGraphicsHandler());
    }
}

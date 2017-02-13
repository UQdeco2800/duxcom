package uq.deco2800.duxcom.entities;

import uq.deco2800.duxcom.entities.dynamics.DynamicEntity;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.maps.mapgen.bounds.Coordinate;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * This should be initialised in MapAssembly.
 * 
 * This will be available to all DynamicEntity objects as a static variable.
 * 
 * Created by houraineet 31/08/16
 */
public class DynamicsManager {
	
	private MapAssembly map;

	/**
	 * Constructs the DynamicsManager for the given map
	 *
	 * @param map the map to be tied to the manager
	 */
	public DynamicsManager(MapAssembly map) {
		this.map = map;
		DynamicEntity.setManager(this);
	}
	
	/**
	 * Direct access to the map. Not recommended.
	 * 
	 * @return 
	 *		The MapAssembly instance this manager was initialised with.
	 */
	public MapAssembly getMap() {
		return map;
	}
	
	/**
	 * Checks if the tile at x,y is occupied.
	 * 
	 * @param x
	 * @param y
	 *		X/Y coordinates of the tile to be checked.
	 * @return 
	 *		True iff tile is not occupied.
	 */
	public boolean isEmpty(int x, int y) {
		return !map.getTile(x, y).isOccupied();
	}
	
	/**
	 * Same as getMap().addEntity
	 * 
	 * @param entity
	 *		The Entity to be added to the map.
	 */
	public void addEntity(Entity entity) {
		map.addEntity(entity);
	}
	
	/**
	 * Removes an entity from a tile. This should get changed when Tiles get
	 * support for multiple entities. Does nothing if the target Tile is not
	 * occupied.
	 * 
	 * @param x
	 * @param y 
	 *		Coordinates of the entity to be removed from the map.
	 */
	public void destroyEntity(int x, int y) {
		if (map.getTile(x, y).isOccupied()) {
			map.getTile(x, y).removeMovableEntity();
		}
	}
	
	/**
	 * See {@link DynamicsManager#forEachSurrounding}
	 * for info on the parameters.
	 * 
	 * @return
	 *		List of Point objects that surrounds the tile (targetX, targetY).
	 *		This will omit the coordinates that go off the map.
	 */
	public List<Coordinate> getSurrounding(int targetX, int targetY, int size, boolean square) {
		List<Coordinate> ret = new ArrayList<>();
		forEachSurrounding(targetX, targetY, size, square, 
				(Integer x, Integer y) -> ret.add(new Coordinate(x, y)));
		return ret;
	}
	
	/**
	 * Allows for a simple way to iterate through surrounding tiles of a source
	 * tile to perform an action on them, e.g. deal damage to heroes, create new
	 * entities, remove entities, etc.
	 * 
	 * @param targetX
	 * @param targetY
	 *		The X and Y coordinates of the target tile.
	 * @param size
	 *		The distance from the target tile to the surrounding tiles.
	 * @param square
	 *		Set true to get a square of surrounding tiles, false to get a diamond.
	 * @param consumer
	 *		BiConsumer to perform the action on the X/Y coordinates. Use of 
	 *		lambdas is recommended.
	 */
	public void forEachSurrounding(int targetX, int targetY, int size, boolean square, 
			BiConsumer<Integer, Integer> consumer) {
		int startX = Math.max(0, targetX - size);
		int startY = Math.max(0, targetY - size);
		int endX = Math.min(map.getHeight(), targetX + size + 1);
		int endY = Math.min(map.getWidth(), targetY + size + 1);
		
		Polygon diamond = null;
		if (!square) { // Polygon magic
			diamond = new Polygon(
					new int[]{targetX - size, targetX, targetX + size + 1, targetX}, 
					new int[]{targetY, targetY + size + 1, targetY, targetY - size - 1}, 4);
		}
		
		for (int x = startX; x < endX; x++) {
			for (int y = startY; y < endY; y++) {
				if (square || diamond.contains(x, y)) { // diamond is initialised if square is false
					consumer.accept(x, y);
				}
			}
		}
	}
	
	/**
	 * @param sourceX
	 * @param sourceY
	 *		The X and Y coordinates of the target tile.
	 * @param targetX
	 * @param targetY
	 *		The X and Y coordinates of the target tile.
	 * @param distance
	 *		How far do ya want it to go towards the target?
	 * @return 
	 *		A Point between the source and the target, where distance specifies 
	 *		the approx. number of tiles from the source towards the target.
	 *		A Point with targetX/Y will be returned if the specified distance is 
	 *		greater than the distance between the source and the target.
	 */
	public static Coordinate getTowards(int sourceX, int sourceY, int targetX, int targetY, double distance) {
		if (Math.sqrt(Math.pow((double) (targetX - sourceX), 2) + 
				Math.pow((double) (targetY - sourceY), 2)) < distance) {
			return new Coordinate(targetX, targetY);
		}
		
		double angle = Math.atan2((double) (targetY - sourceY), 
				(double) (targetX - sourceX));
		return new Coordinate(sourceX + (int) Math.round(distance * Math.cos(angle)),
				sourceY + (int) Math.round(distance * Math.sin(angle)));
	}
	
	/**
	 * Same as the {@link #getTowards(int, int, int, int, double) getTowards} 
	 * method but away from the target.
	 * If sourceX/Y == targetX/Y, @return - Point(sourceX + distance, sourceY)
	 */
	public static Coordinate getAwayFrom(int sourceX, int sourceY, int targetX, int targetY, double distance) {
		double angle = Math.atan2((double) (sourceY - targetY), 
				(double) (sourceX - targetX));
		return new Coordinate(sourceX + (int) Math.round(distance * Math.cos(angle)),
				sourceY + (int) Math.round(distance * Math.sin(angle)));
	}
}

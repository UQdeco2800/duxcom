package uq.deco2800.duxcom.entities;

import java.util.List;

import uq.deco2800.duxcom.dataregisters.EntityDataRegister;
import uq.deco2800.duxcom.savegame.SaveObject;
import uq.deco2800.duxcom.util.AnimationTickable;
import uq.deco2800.duxcom.util.TurnTickable;

/**
 * Represents all entities on a map. length
 */
public abstract class Entity implements Comparable<Entity>, TurnTickable, SaveObject {

	private int x;
	private int y;
	private double elevation;

	/**
	 * The type of the entity as defined in {@link EntityDataRegister}
	 */
	private EntityType entityType;

	/**
	 * The length in the direction parallel to the x axis - right.
	 */
	private int lengthX;

	/**
	 * The length in the direction parallel to the y axis - left.
	 */
	private int lengthY;

	/**
	 * The distance from the closest point of the entity to the line where y=x.
	 */
	private int distanceInside;

	/**
	 * The distance from the top most point of the entity to the point (0, 0).
	 */
	private int distanceTop;

	/**
	 * The distance from the bottom most point of the entity to the point (0,
	 * 0).
	 */
	private int distanceBottom;
	
	/**
	 * Indicates whether the entity should be hidden by the renderer.
	 */
	private boolean hidden;

	/**
	 * Creates a new entity at position x, y with default elevation of 1
	 * @param x
	 * @param y
	 * @param lengthX
	 * @param lengthY
	 */
	public Entity(EntityType entityType, int x, int y, int lengthX, int lengthY) {
		this.x = x;
		this.y = y;
		this.lengthX = lengthX;
		this.lengthY = lengthY;
		this.entityType = entityType;
		this.elevation = 0;

		setValues();
		if (this instanceof AnimationTickable) {
			((AnimationTickable) this).register();
		}
	}

	/**
	 * Sets the type of this Entity to the specified value.
	 *
	 * @param entityType
	 *            The type to set this Entity to
	 */
	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	/**
	 * Gets the type of this Entity.
	 *
	 * @return The type of the Entity
	 */
	public EntityType getEntityType() {
		return entityType;
	}

	/**
	 * Sets or resets values to be used for the compareTo method to ensure entities are rendered
	 * in the correct order.
	 */
	private void setValues() {
		if (x > y) {
			distanceInside = x - y - lengthX;
		} else if (y > x) {
			distanceInside = y - x - lengthY;
		} else {
			distanceInside = -Math.max(lengthY, lengthX);
		}

		this.distanceBottom = y + x;
		this.distanceTop = distanceBottom - (lengthX + lengthY);
	}

	/**
	 * Considers entities position in relation to another to decide which one should be rendered above or below
	 * @param entity
	 * @return comparison
	 */
	@Override
	public int compareTo(Entity entity) {
		if (this.distanceTop == entity.distanceTop) {
			if (this.distanceBottom == entity.distanceBottom) {
				return this.distanceInside - entity.distanceInside;
			} else {
				return this.distanceBottom - entity.distanceBottom;
			}
		}
		return this.distanceTop - entity.distanceTop;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Entity)) {
			return false;
		}

		Entity entity = (Entity) object;
		return this.x == entity.x && this.y == entity.y
				&& this.lengthX == entity.lengthX
				&& this.lengthY == entity.lengthY;
	}

	@Override
	public int hashCode() {
		int prime = 13;
		prime += prime * x;
		prime += prime * y;
		prime += lengthX * prime;
		prime += lengthY * prime;
		return prime;
	}

	@Override
	public String toString() {
		return String.format("[%d %d %d %d    %d]", x, y, lengthX, lengthY,
				distanceInside);
	}

	/**
	 * Returns entity x coordinate
	 *
	 * @return x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets entity x coordinate
	 *
	 * @param x entity x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns entity y coordinate
	 *
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets entity y coordinate
	 *
	 * @param y entity y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Gets entity y length
	 *
	 * @return entity y length
	 */
	public int getYLength() {
		return lengthY;
	}

	/**
	 * Sets entity y length
	 *
	 * @param length entity y length
	 */
	public void setYLength(int length) {
	    this.lengthX = length;
    }

	/**
	 * Gets entity x length
	 *
	 * @return entity x length
	 */
	public int getXLength() {
		return lengthX;
	}

	/**
	 * Sets entity x length
	 *
	 * @param length entity x length
	 */
	public void setXLength(int length) {
		this.lengthX = length;
	}

	/**
	 * Moves the entity and returns true if successful. This should be overridden for entities
	 * that need to check if they can move a certain distance.
	 *
	 * If an entity should not be able to move, override this method and return false.
	 * @param x
	 * @param y
	 * @return true iff moved
	 */
	public boolean move(int x, int y) {
		this.x = x;
		this.y = y;
		setValues();
		return true;
	}

	/**
	 * Sets the hidden status of the enemy to a given boolean
	 *
	 * @param bool new hidden status
	 */
	public void setHidden(boolean bool){
		this.hidden = bool;
	}

	/**
	 * Gets the hidden status of the enemy to a given boolean
	 *
	 * @return entity hidden status
	 */
	public boolean isHidden(){
		return hidden;
	}
	
	/**
	 * Returns the string corresponding to the image of the entity in entityRegister
	 * @return the image name
	 */
	public abstract String getImageName();

	@Override
	public void turnTick() {

	}
	
	/**
     * Encodes the current hero
     */
	@Override
    public String encode() {
        return getEntityType() + " "+getX()+" "+getY()+" "+getXLength() + " " + getYLength();
    }
	
	@Override
	public void decode(List<String> state) {
		int xPos = Integer.parseInt(state.get(1));
		int yPos = Integer.parseInt(state.get(2));
		int distanceX = Integer.parseInt(state.get(3));
		int distanceY = Integer.parseInt(state.get(4));
		setX(xPos);
		setXLength(distanceX);
		setY(yPos);
		setYLength(distanceY);
	}

	/**
	 * Gets the elevation of the entity
	 *
	 * @return the elevation
	 */
	public double getElevation() {
		return elevation;
	}

	/**
	 * Sets the elevation of the entity
	 *
	 * @return the new elevation
	 */
	public void setElevation(double elevation) {
		this.elevation = elevation;
	}
}

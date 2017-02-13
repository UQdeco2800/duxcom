package uq.deco2800.duxcom.tiles;


import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.scenery.AbstractScenery;
import uq.deco2800.duxcom.entities.scenery.StackableScenery;
import uq.deco2800.duxcom.util.AnimationTickable;
import uq.deco2800.duxcom.util.TurnTickable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a square unit of the maps.
 * 
 * @author Leggy, Anonymousthing
 *
 */
public class Tile implements TurnTickable {
	/**
	 * The type of this tile.
	 */
	private TileType tileType;

	private double stackableEntitiesHeight;

	/**
	 * A list of any entities on this tile
	 */
	private List<Entity> entities = new ArrayList<>();

	private Entity movableEntity;

	/**
	 * A list of the LiveTiles that exist on this Tile
	 */
	private List<LiveTile> liveTiles = new ArrayList<>(1);

	/**
	 * Indicates whether a tile is visible to any hero entities, used
	 * for vision.
	 */
	private boolean hidden;

	private boolean occupied = false;

	/**
	 * Initialises this Tile with a specified tile type.
	 * 
	 * @param tileType
	 *            The tile type to create this Tile with
	 */
	public Tile(TileType tileType) {
		stackableEntitiesHeight = 0;
		this.tileType = tileType;
	}


	/**
	 * Sets the type of this Tile to the specified value.
	 * 
	 * @param tileType
	 *            The type to set this Tile to
	 */
	public void setTileType(TileType tileType) {
		this.tileType = tileType;
	}



	/**
	 * Gets the type of this Tile.
	 * 
	 * @return The type of the Tile
	 */
	public TileType getTileType() {
		return tileType;
	}


	/**
	 * Find out if there is an entity on this tile
	 * @return true if an entity is on the tile
	 */
	public boolean isOccupied() {
		return occupied;
	}

	/**
	 * Get the entity currently on the Tile
	 * @return Entity of occupied, else null
	 */
	public List<Entity> getEntities() {
		return entities;
	}

	/**
	 * Returns the LiveTile that is currently occupying the Tile
	 * @return LiveTile is exists, null otherwise
     */
	public LiveTile getLiveTile() {
		if (!liveTiles.isEmpty()) {
			return liveTiles.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Remove the first entity in the list from the tile. If multiple entities
	 * can exist on a tile in the future this will need to be modified
	 */
	public void removeMovableEntity() {
		if (movableEntity instanceof AnimationTickable) {
		    ((AnimationTickable) movableEntity).unregister();
		}
		entities.remove(movableEntity);
		movableEntity = null;
		occupied = false;
	}

	/**
	 * attempt to add an entity to the tile.
	 *
	 * @param entity entity to add
	 * @return true if adding entity was successful
	 */
	public boolean addEntity(Entity entity) {
		if (entity instanceof StackableScenery) {
			entity.setElevation(stackableEntitiesHeight);
			entities.add(entity);
			stackableEntitiesHeight += ((StackableScenery) entity).getHeight();
			return true;
		}
		if (movableEntity != null) {
			return false;
		} else {
			entity.setElevation(stackableEntitiesHeight);
			entities.add(entity);
			movableEntity = entity;
			occupied = true;
			return true;
		}
	}
	
	public boolean addLiveTile(LiveTile liveTile) {
		if (!liveTiles.isEmpty() || Math.round(stackableEntitiesHeight) != 0) {
			return false;
		} else {
			liveTiles.add(liveTile);
			return true;
		}
	}

	/**
	 * Gets the hidden status of this tile.
	 * @return true if the tile is considered hidden, false otherwise
	 */
	public boolean isHidden() {
		return this.hidden;
	}

	/**
	 * Sets the hidden status of this tile.
	 * 
	 * @param bool
	 * 		the boolean to set the tile's hidden status to
	 */
	public void setHidden(boolean bool) {
		this.hidden = bool;
	}
	/**
	 * Tick the turn
	 */
	@Override
	public void turnTick() {
		// turn ticks all entities residing on this tile
		entities.forEach(Entity::turnTick);

		// turn ticks for liveTiles
		for (LiveTile liveTile :
				liveTiles) {
			// TODO choose between AbstractPassive v AbstractBuff and implement the proper turn tick onto them
		}

	}

	public void changeStackableEntityHeight(double change) {
		stackableEntitiesHeight += change;
	}

	/**
	 * Returns a the Entity on the Tile.
	 *
	 * @return an Entity
     */
	public Entity getMovableEntity() {
		return movableEntity;
	}

	/**
	 * Sets the occupied status of the Tile.
	 *
	 * @param occupied the new occupied status.
     */
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	/**
	 * Returns the height of the stackable entites on the tile
	 *
	 * @return a double value of the height
     */
	public double getStackableEntityHeight() {
		return stackableEntitiesHeight;
	}

	/**
	 * Returns true if the tile contains an AbstractCharacter
	 *
	 * @return true iff tile contains an AbstractCharacter
     */
	public boolean hasCharacter() {
		for (Entity entity :
				entities) {
			if (entity instanceof AbstractCharacter) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if the tile contains an AbstractHero
	 *
	 * @return true iff tile contains an AbstractHero
	 */
	public boolean hasHero() {
		for (Entity entity :
				entities) {
			if (entity instanceof AbstractHero) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if the tile contains an AbstractEnemy
	 *
	 * @return true iff tile contains an AbstractEnemy
	 */
	public boolean hasEnemy() {
		for (Entity entity :
				entities) {
			if (entity instanceof AbstractEnemy) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if the tile contains an AbstractScenery
	 *
	 * @return true iff tile contains an AbstractScenery
	 */
	public boolean hasScenery() {
		for (Entity entity :
				entities) {
			if (entity instanceof AbstractScenery) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if the tile contains a LiveTile
	 *
	 * @return true iff tile contains a LiveTile
	 */
	public boolean hasLiveTile() {
		return !liveTiles.isEmpty();
	}

	/**
	 * Removes the LiveTile from the tile if any exists
	 */
	public void removeLiveTile() {
		if (hasLiveTile()) {
			liveTiles.clear();
		}
	}

	public boolean hasEntity() {
		return !entities.isEmpty();
	}

	public boolean containsAny() {
		return (hasCharacter() || hasLiveTile() || hasScenery() || hasEntity());
	}
}

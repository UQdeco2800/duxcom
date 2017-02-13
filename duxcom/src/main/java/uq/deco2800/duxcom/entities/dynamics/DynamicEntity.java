package uq.deco2800.duxcom.entities.dynamics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.entities.DynamicsManager;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.EntityType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josh on 8/28/16.
 */
public abstract class DynamicEntity extends Entity {

    private static Logger logger = LoggerFactory.getLogger(DynamicEntity.class);
	
	protected static DynamicsManager dynamicsManager;
	
    protected int currentPhaseIndex;
    protected List<Phase> phases = new ArrayList<>();

    public DynamicEntity(EntityType entityType, int x, int y, int lengthX, int lengthY) {
        super(entityType, x, y, lengthX, lengthY);
    }
	
	public DynamicEntity(EntityType entityType, int x, int y) {
		super(entityType, x, y, 1, 1);
	}

	public final List<Phase> getPhases() {
		return this.phases;
	}

	public final void setPhases(List<Phase> phases) {
		this.phases = phases;
	}
	
	/**
	 * Increment currentPhase by 1, wraps to 0 if out of range
	 * @return
	 *		False iff currentPhaseIndex == phases.size()
	 */
    public boolean advanceCurrentPhase() {
		currentPhaseIndex++;
		if (currentPhaseIndex >= phases.size()) {
			currentPhaseIndex = 0;
			return false;
		}
		return true;
    }

	/**
	 * @return 
	 *		The current phase denoted by the current phase index. Null if the
	 *		current phase index is invalid.
	 */
    public final Phase getCurrentPhase() {
		if (currentPhaseIndex >= 0 && currentPhaseIndex < phases.size()) {
			return phases.get(currentPhaseIndex);
		} else {
			return null;
		}
	}
	
	/**
	 * @return 
	 *		The index of the current phase.
	 */
	public final int getCurrentPhaseIndex() {
		return currentPhaseIndex;
	}
	
	/**
	 * Set the currentPhaseIndex to newPhase if newPhase if equal to or greater
	 * than 0, and less the the total number of phases.
	 * 
	 * @param newPhase 
	 *		The new currentPhaseIndex.
	 */
	public final void setCurrentPhaseIndex(int newPhase) {
		if (newPhase >= 0 && newPhase < phases.size()) {
			currentPhaseIndex = newPhase;
		} else {
		    logger.error("Specified newPhase " + newPhase + " is out of bounds.");
		}
	}
	
	/**
	 * Removes this entity from the map using the DynamicsManager instance.
	 */
	public void destroy() {
		dynamicsManager.destroyEntity(getX(), getY());
	}
	
	/**
	 * Returns the EntityType of the current phase. If there are no phases or
	 * the phase does not have an EntityType, it will return the EntityType the 
	 * Entity was initialised with.
	 */
	@Override
	public EntityType getEntityType() {
		if (currentPhaseIndex < phases.size()) {
			EntityType ret = getCurrentPhase().getEntityType();
			if (ret != null) {
				return ret;
			}
		}

		return getSuperEntityType();
	}
	
	/**
	 * @return 
	 *		The EntityType this DynamicEntity was initialised with.
	 */
	public EntityType getSuperEntityType() {
		return super.getEntityType();
	}
	
	/**
	 * Remove this
	 */
	@Override
	public String getImageName() {
		return null;
	}
	
	/**
	 * This will be called when DynamicsManager is initialised, and will give
	 * the DynamicEntities a static reference to the instance for extended
	 * functionalities.
	 * 
	 * @param dm
	 *		Initialised DynamicsManager instance.
	 */
	public static void setManager(DynamicsManager dm) {
		dynamicsManager = dm;
	}
	
	public static DynamicsManager getManager() {
		return dynamicsManager;
	}
}
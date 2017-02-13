package uq.deco2800.duxcom.objectives;

import uq.deco2800.duxcom.entities.EntityType;

/**
 * An objective that can be met by preventing a
 * at least a certain number of a certain entity type from being destroyed.
 * If at least the given number of the particular entity attached to this
 * objective is destroyed, the objective is permanently failed for the
 * rest of a game.
 * @require entityProtected != null && minNum > 0
 * @ensure description = "Protect at least -minNum- -entityType-
 *                        from being destroyed"
 * Created by Tom B on 3/09/2016.
 */
public class ProtectionObjective extends Objective {
    public ProtectionObjective(EntityType entityProtected, int minNum) {
        super(entityProtected, minNum, "Protect at least " + minNum + " " +
                entityProtected.toString().toLowerCase().replace('_', ' ') +
                " from being destroyed");
        this.isMet = true;
    }

    /**
     * You've failed to protect the specified entity from destruction!
     */
    public void failed() {
        this.isMet = false;
    }
}

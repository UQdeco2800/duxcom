package uq.deco2800.duxcom.objectives;

import uq.deco2800.duxcom.entities.enemies.EnemyType;

/**
 * An objective met for hitting an enemy of a particular type.
 * @require enemyType != null
 * @ensure description = "Hit an enemy -enemy name-"
 * Created by Tom B on 17/09/2016.
 */
public class EnemyHitObjective extends Objective {

    public EnemyHitObjective (EnemyType enemyType) {
        super(new EnemyObjectiveType(enemyType, false), false,
                "Hit an " + enemyType.toString().toLowerCase().replace('_', ' '));
    }

    /**
     * Sets the enemy hit objective as being complete.
     */
    public void hit() {
        super.setObjectiveValue(true);
        this.complete();
    }

    /**
     * Gets the current value of this objective (true or false).
     */
    public Boolean getObjectiveValue() {
        return (Boolean)super.getObjectiveValue();
    }

    /**
     * Returns a string representation specific to EnemyHitObjectives for
     * a given EnemyHitObjective, in the following format:
     * "Target Type: -type-, Value: -value-; Description: -description-; Met:
     * -met-"
     * @return a string in the above format describing the entire enemy hit objective.
     */
    @Override
    public String toString() {
        EnemyObjectiveType eot = (EnemyObjectiveType)this.getObjectiveTarget();
        return "Target Type: " + eot.getType() + ", Value: " +
                this.getObjectiveValue() + "; Description: " +
                this.getDescription() + "; " + "Met: " + this.isMet;
    }
}

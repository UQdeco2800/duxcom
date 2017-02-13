package uq.deco2800.duxcom.objectives;

import uq.deco2800.duxcom.entities.enemies.EnemyType;

/**
 * Wrapper class used within GameState and Objective types
 * to fix issue of having different objective types tracking
 * the same enemy type.
 * @require enemyType != null
 * Created by Tom B on 14/10/2016.
 */
public class EnemyObjectiveType {
    // Type of enemy
    private EnemyType type;

    // Relating to deaths (true) or hits only (false)
    private boolean death;

    public EnemyObjectiveType(EnemyType type, boolean death) {
        this.type = type;
        this.death = death;
    }

    /**
     * Get the enemy type within this wrapper.
     * @return enemy type held by this wrapper.
     */
    public EnemyType getType() {
        return this.type;
    }

    /**
     * Find out whether or not this wrapper is for enemy hit objectives
     * (false) or for enemy kill objectives (true).
     * @return true if for enemy kill objectives, else false.
     */
    public boolean isForDeath() {
        return this.death;
    }

    /**
     * Determines whether or not two enemy objective types
     * are the same. They will be the same if their types are
     * both EnemyObjectiveTypes, the types being wrapped are the
     * same and the death flag are the same.
     * @param o object being checked for equality
     * @require o != null
     * @return whether or not two EnemyObjectiveType objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EnemyObjectiveType)) {
            return false;
        }

        EnemyObjectiveType eot = (EnemyObjectiveType)o;

        // Will be equal if both type and death flag match
        return this.type.equals(eot.getType()) && (this.death == eot.isForDeath());
    }

    /**
     * Returns a hashcode representation of an EnemyObjectiveType
     * object based on the value of the death flag and the hashcode
     * of the enemy type being wrapped.
     * @return hashcode as described above.
     * @ensure 23 * -hashcode of enemy type- + 3 if death == true, else
     *          37 * -hashcode of enemy type- + 5
     */
    @Override
    public int hashCode() {
        if (this.death) {
            return 23 * this.type.hashCode() + 3;
        } else {
            return 37 * this.type.hashCode() + 5;
        }
    }

    /**
     * Returns a string representation of an EnemyObjectiveType based on
     * the following format:
     * "EOT: Type - -enemyType-; Kill - -death-"
     * @return a string according to the above format describing the
     * EnemyObjectiveType object
     */
    @Override
    public String toString() {
        return "EOT: Type - " + this.type + "; Kill - " + this.death;
    }
}

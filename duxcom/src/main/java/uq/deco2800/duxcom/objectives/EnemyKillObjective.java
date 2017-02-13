package uq.deco2800.duxcom.objectives;

import uq.deco2800.duxcom.entities.enemies.EnemyType;

/**
 * An objective for killing a certain amount of a specified
 * enemy type.
 * @require killGoal > 0, enemyType is an actual enemy type and not null
 * @ensure description = "Kill + killGoal + " " +
 *          enemyType.toString().toLowerCase() + "s"
 * Created by Tom B on 26/08/2016.
 */
public class EnemyKillObjective extends Objective {

    public EnemyKillObjective(EnemyType enemyType, int killGoal) {
        super(new EnemyObjectiveType(enemyType, true), killGoal, "Kill " + killGoal + " " +
                (enemyType.toString()).toLowerCase().replace('_', ' ') + "(s)");
    }
}
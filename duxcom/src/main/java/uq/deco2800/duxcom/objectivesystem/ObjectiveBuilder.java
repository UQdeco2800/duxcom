package uq.deco2800.duxcom.objectivesystem;

import java.util.ArrayList;
import java.util.List;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.items.Item;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.maps.AbstractGameMap;
import uq.deco2800.duxcom.objectives.*;
import uq.deco2800.duxcom.scoring.ScoreSystem;

/**
 * Allows the creation of new objectives that a game
 * map can contain.
 * Created by Tom B on 26/08/2016.
 */
public class ObjectiveBuilder {

    // Objectives built
    private List<Objective> objectives;

    // Map being used as reference point for getting objectives from
    private AbstractGameMap gameMap;

    public ObjectiveBuilder(AbstractGameMap gameMap) {
        this.objectives = new ArrayList<>();
        this.gameMap = gameMap;
        if (!(gameMap == null)) {
            fillObjectivesFromMap();
        }
    }

    /**
     * Get list of objectives made in the given ObjectiveBuilder.
     * @return objectives built
     */
    public List<Objective> getObjectives() {
        return new ArrayList<>(this.objectives);
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = new ArrayList<>(objectives);
    }

    /**
     * Transfers all objectives defined on a map to another builder that
     * will be placed in the main game at the beginning of a level.
     */
    private void fillObjectivesFromMap() {
        for (Objective o: this.gameMap.getObjectives()) {
            this.objectives.add(o);
        }
    }

    /**
     * Adds an EnemyKillObjective to the level, as long
     * as another EnemyKillObjective targeting the same
     * enemy type doesn't already exist
     *
     * @param type - type of objective being added
     * @param value - target value being met
     * @require
     *      value > 0, EnemyType != null
     * @ensure
     *      no multiple objectives with the same type
     */
    public void add(EnemyType type, int value) {
        EnemyKillObjective toAddE = new EnemyKillObjective(type, value);
        EnemyObjectiveType typeOb = (EnemyObjectiveType)(toAddE.getObjectiveTarget());

        if (!this.contains(typeOb)) {
            this.objectives.add(toAddE);
        }
    }

    /**
     * Adds a new ScoreObjective, as long as the amounts don't match up.
     * @param scoring - score system being monitored
     * @param amount - score goal being aimed for
     * @require amount > 0 && scoring != null
     */
    public void add(ScoreSystem scoring, int amount) {
        ScoreObjective scoreGoal = new ScoreObjective(scoring, amount);

        if (!this.contains(scoring)) {
            this.objectives.add(scoreGoal);
        }

    }

    /**
     * Adds a new ProtectionObjective, as long as the same entity isn't
     * being monitored more than once as a result.
     * @param toProtect - entity being protected
     * @param threshold - minimum count needed to avoid being destroyed
     * @require toProtect != null
     */
    public void add(EntityType toProtect, int threshold) {
        ProtectionObjective protectThis = new ProtectionObjective(toProtect, threshold);

        if (!this.contains(toProtect)) {
            this.objectives.add(protectThis);
        }
    }

    /**
     * Adds a new EnemyHitObjective, as long as the same enemy type isn't
     * being monitored more than once as a result in the same objective type.
     * @param type - enemy type being monitored
     * @require type != null
     */
    public void add(EnemyType type) {
        EnemyHitObjective hitThis = new EnemyHitObjective(type);
        if (!containsAHitObjective(type)) {
            this.objectives.add(hitThis);
        }
    }

    /**
     * Adds a new ItemObjective, as long as the same item type isn't
     * being monitored more than once as a result.
     * @param type - entity being looked for
     * @require item != null
     */
    public void add(ItemType type, int needed) {
        ItemObjective collectItem = new ItemObjective(type, needed);

        if (!this.contains(type)) {
            this.objectives.add(collectItem);
        }
    }

    /**
     * Adds a new MovementObjective, as long as the same entity isn't
     * being monitored more than once as a result.
     * @param x x-coordinate being checked
     * @param y y-coordinate being checked
     * @require item != null && x > 0 && y > 0
     */
    public void add(int x, int y) {
        ObjectiveCoordinate position = new ObjectiveCoordinate(x, y);
        MovementObjective movementObj = new MovementObjective(position);

        if (!this.contains(position)) {
            this.objectives.add(movementObj);
        }
    }

    /**
     * Checks to see whether an objective of the specified type and value
     * has been built yet.
     * @param type type of objective being located
     * @return whether the objective with the specified type and value exists
     * @require type != null && an actual enemy
     */
    public boolean contains(EnemyObjectiveType type) {
        for (Objective o: this.objectives) {
            if ((o.getObjectiveTarget()).equals(type)) {
                return true;
            }

        }
        return false;
    }

    /**
     * Checks to see whether an objective of the specified type and value
     * has been built yet.
     * @param scoring scoring system being tracked
     * @return whether the objective with the specified type and value exists
     * @require scoring != null && amount > 0
     */
    public boolean contains(ScoreSystem scoring) {
        for (Objective o: this.objectives) {
            if ((o.getObjectiveTarget()).equals(scoring)) {
                return true;
            }

        }
        return false;
    }

    /**
     * Checks to see whether a MovementObjective with the same
     * position target already exists.
     * @param position coordinates being target
     * @require position != null && position.size() == 2 &&
     *          position.get(i) > 0 for each 0 <= i < position.size()
     * @return whether or not a movement objective
     * with the given position exists
     *
     */
    public boolean contains(ObjectiveCoordinate position) {
        for (Objective o: this.objectives) {
            if (o.getObjectiveTarget().equals(position)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if a protection objective with the specified entity
     * is already present.
     * @param protect entity being requested
     * @return whether or not the specified entity is already being protected
     * @require protect != null
     */
    public boolean contains(EntityType protect) {
        for (Objective o: this.objectives) {
            if (protect.equals(o.getObjectiveTarget())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks to see if an item objective with the specified type
     * is already present.
     * @param type item type being requested
     * @return whether or not the specified item type is already being monitored
     * @require type != null
     */
    public boolean contains(ItemType type) {
        for (Objective o: this.objectives) {
            if (type.equals(o.getObjectiveTarget())) {
                return true;
            }
        }

        return false;
    }
    /**
     * Checks to see if an EnemyKillObjective monitoring the given enemy type
     * already exists.
     * @param type - enemy type
     * @require type != null
     * @return whether or not an existing EnemyKillObjective with the specified
     * type exists
     */
    public boolean containsAKillObjective(EnemyType type) {
        EnemyObjectiveType eot = new EnemyObjectiveType(type, true);
        for (Objective o: this.objectives) {
            if (o instanceof EnemyKillObjective && eot.equals(o.getObjectiveTarget())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if an EnemyHitObjective monitoring a particular enemy type
     * already exists.
     * @param type - enemy type
     * @require type != null
     * @return whether or not an existing EnemyHitObjective with the specified
     * type exists
     */
    public boolean containsAHitObjective(EnemyType type) {
        EnemyObjectiveType eot = new EnemyObjectiveType(type, false);
        for (Objective o: this.objectives) {
            if ((o instanceof EnemyHitObjective) && eot.equals(o.getObjectiveTarget())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if an ProtectionObjective monitoring a particular entity
     * already exists.
     * @param e - entity to protect
     * @require type != null
     * @return whether or not an existing ProtectionObjective with the specified
     * type exists
     */
    public boolean containsProtectionObjective(EntityType e) {
        for (Objective o: this.objectives) {
            if (o instanceof ProtectionObjective && e.equals(o.getObjectiveTarget())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if a score objective is already present.
     * @return true if a score objective is present, else false;
     */
    public boolean containsScoreObjective() {
        for (Objective o: this.objectives) {
            if (o instanceof ScoreObjective) {
                return true;
            }
        }
        return false;
    }

    /**
     * Clears all objectives built by the objective builder.
     * @ensure this.objectives.size() == 0
     */
    public void clearObjectives() {
        this.objectives = new ArrayList<>();
    }

}

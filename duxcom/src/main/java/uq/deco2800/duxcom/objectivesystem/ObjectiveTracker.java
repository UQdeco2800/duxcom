package uq.deco2800.duxcom.objectivesystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.objectives.*;
import uq.deco2800.duxcom.savegame.SaveObjectives;
import uq.deco2800.duxcom.scoring.ScoreSystem;
import javax.ws.rs.WebApplicationException;
import java.util.*;


/**
 * Will track all predefined objectives in the game.
 * Created by Tom B on 29/08/2016.
 * @require gameState != null && objectives != null && no nulls in objectives
 * @ensure the gameState given is being watched by its own ObjectiveTracker &&
 */
public class ObjectiveTracker extends Observable implements Observer {
    private List<Objective> objectives = new ArrayList<>();
    private GameState gameState = null;
    private SaveObjectives savePoint = new SaveObjectives();
    private static Logger logger = LoggerFactory.getLogger(ObjectiveTracker.class);
    private List<Observer> watchers = new ArrayList<>();

    public ObjectiveTracker(GameState gameState, List<Objective> objectives) {
        this.gameState = gameState;
        this.objectives = objectives;
        this.gameState.addObserver(this);
    }

    /**
     * Get objectives being modified by this tracker
     * @return objectives being looked at in-game
     */
    public List<Objective> getObjectives() {
        return this.objectives;
    }

    /**
     * Get game state being watched by this ObjectiveTracker
     * @return game state being watched
     */
    public GameState getGameState() {
        return this.gameState;
    }

    /**
     * Get all objects watching this ObjectiveTracker for changes.
     * @return objects watching this ObjectiveTracker
     */
    public List<Observer> getObservers() {
        return new ArrayList<>(this.watchers);
    }

    /**
     * Adds an observer to monitor this tracker.
     * @param watcher an entity that will keep an eye on this game state
     *                for any changes
     * @require watcher != null
     */
    @Override
    public void addObserver(Observer watcher) {
        this.watchers.add(watcher);
    }

    /**
     * Deletes an observer that was monitoring this tracker.
     * @param watcher an entity that will no longer keep an eye on this game
     *                state for any changes
     * @require watcher != null
     */
    @Override
    public void deleteObserver(Observer watcher) {
        this.watchers.remove(watcher);
    }

    /**
     * Lets all objects watching this tracker that a change has occurred.
     */
    @Override
    public void notifyObservers() {
        for (Observer w : this.watchers) {
            w.update(this, 1);
        }
    }

    /**
     * Completes any uncompleted objectives that are eligible for
     * completion given the game state type being monitored (i.e.
     * enemies kills)
     * @param gameState game state entity being watched
     * @param placeholder list of objectives being monitored and compared
     * @require gameState != null && objectives != null
     */
    @Override
    public void update(Observable gameState, Object placeholder) {
        Map<Object, Object> currentStatistics = ((GameState) gameState).getStatistics();
        List<Object> statisticTypes = new ArrayList<>(currentStatistics.keySet());
        for (Objective o : this.objectives) {
            for (Object k : statisticTypes) {
                handleObjectiveCompletion(o, k);
            }
        }
    }

    /**
     * Handles completion of all objectives.
     * @param o objective being checked
     * @param k statistic key being used
     * @require o != null && k != null
     */
    public void handleObjectiveCompletion(Objective o, Object k) {
        // Both the statistic key and objective target must have matching types to be examined
        // together, otherwise this pairing will be skipped
        if (!k.getClass().getName().equals(o.getObjectiveTarget().getClass().getName())) {
            return;
        }

        if (k instanceof EnemyObjectiveType && o.getObjectiveTarget() instanceof EnemyObjectiveType) {
            // EnemyKillObjective, EnemyHitObjective
            EnemyObjectiveType key = (EnemyObjectiveType) k;
            handleEnemyBasedObjectives(o, key);
        } else if (k instanceof ScoreSystem && o.getObjectiveTarget() instanceof ScoreSystem) {
            // ScoreObjective
            ScoreSystem s = (ScoreSystem) k;
            handleScoreBasedObjectives((ScoreObjective) o, s);
        } else if (k instanceof ItemType && o.getObjectiveTarget() instanceof ItemType) {
            // ItemObjective
            ItemType key = (ItemType) k;
            handleItemBasedObjectives((ItemObjective) o, key);
        } else if (k instanceof EntityType && o.getObjectiveTarget() instanceof EntityType) {
            // ProtectionObjective
            EntityType key = (EntityType) k;
            int targetValue = (Integer)o.getObjectiveValue();
            int currentValue = (Integer)gameState.getStatistics().get(key);
            handleProtectionBasedObjectives(o, currentValue, targetValue);
        } else if (k instanceof ObjectiveCoordinate && o.getObjectiveTarget() instanceof ObjectiveCoordinate) {
            // MovementObjective
            ObjectiveCoordinate position = (ObjectiveCoordinate)k;
            handleMovementObjectives((MovementObjective) o, position);
        }

    }

    // *** OBJECTIVE HANDLING FUNCTIONS ***

    /**
     * Handles completion of enemy-based objectives: EnemyHit and EnemyKill.
     * @param o   objective being analysed
     * @param key type of enemy that will be looked up
     * @require o != null && (o instanceof EnemyKillObjective ||
     * o instanceof EnemyHitObjective) && key != null
     */
    private void handleEnemyBasedObjectives(Objective o, EnemyObjectiveType key) {
        Object statisticToCheck = (gameState.getStatistics()).get(key);
        // Make sure correct statistic value is chosen - EnemyHitObjectives use Boolean
        // while EnemyKillObjectives use Integer
        if (o instanceof EnemyKillObjective && key.isForDeath()) {
            Integer currentKilled = (Integer) statisticToCheck;
            Integer targetKill = (Integer) o.getObjectiveValue();
            compareTargetAgainstCurrentValue(o, currentKilled, targetKill);
        } else if (o instanceof EnemyHitObjective && !key.isForDeath()){
            Boolean hitStatus = (Boolean) statisticToCheck;
            analyseHitStatus(o, hitStatus);
        }
    }

    /**
     * Handles completion of ScoreObjectives.
     * @param o objective being analysed
     * @param s score system that will be looked up
     * @require o != null && s != null
     */
    private void handleScoreBasedObjectives(ScoreObjective o, ScoreSystem s) {
        Integer targetScore = (Integer) o.getObjectiveValue();
        Integer currentScore = (Integer) (gameState.getStatistics()).get(s);
        compareTargetAgainstCurrentValue(o, currentScore, targetScore);
    }

    /**
     * Handles completion of ProtectionObjectives
     * @param o protection objective being analysed
     * @param current current amount of entity remaining
     * @param target current amount of entity there should be
     * @require o != null && target > 0 && current >= 0
     */
    private void handleProtectionBasedObjectives(Objective o, int current, int target) {
        // Objective failed if current number of tracked entity type
        // is lower than it should be
        if (current < target && o.met()) {
            o.fail();
            notifyObservers();
        }
    }

    /**
     * Handles completion of ItemObjectives.
     * @param o item objective being changed
     * @param key type of item being compared with
     * @require key != null && o != null
     */
    private void handleItemBasedObjectives(ItemObjective o, ItemType key) {
        Integer targetFind = (Integer) o.getObjectiveValue();
        Integer currentFind = (Integer) (gameState.getStatistics()).get(key);
        compareTargetAgainstCurrentValue(o, currentFind, targetFind);
    }

    /**
     * Handles completion of MovementObjectives.
     * @param o - movement objective being changed
     * @param key objective coordinate obtained to compare with
     * @require o != null && key != null && key is a valid ObjectiveCoordinate
     */
    private void handleMovementObjectives(MovementObjective o, ObjectiveCoordinate key) {
        if (key.equals(o.getObjectiveTarget()) &&
                gameState.getStatistics().get(key).equals(new Boolean(Boolean.TRUE))) {
            o.complete();
            notifyObservers();
        }
    }

    /**
     * Attempts to complete a specified objective based on the current
     * and target values provided.
     *
     * @param o objective being monitored
     * @param current current value
     * @param target  target value to reach
     * @require target > 0 && current > 0 && o != null && o is an objective
     * using integers as it goal data type
     */
    private void compareTargetAgainstCurrentValue(Objective o, int current, int target) {
        if (current >= target && (!o.met())) {
            o.complete();
            notifyObservers();
        }
    }

    /**
     * Checks whether or not a particular enemy hit objective
     * has been met based on a given boolean hit flag, updating
     * the objective if this flag is true and the objective
     * hasn't already been met.
     * @param o enemy hit objective being changed
     * @param hit flag used to indicate whether or not
     *        the objective should be updated.
     * @require objective != null && objective instanceof EnemyHitObjective
     */
    private void analyseHitStatus(Objective o, boolean hit) {
        if (hit && !(o.met())) {
            ((EnemyHitObjective) o).hit();
            notifyObservers();
        }
    }

    /**
     * Provides a list of objectives that have been completed.
     * @return completed objectives
     */
    public List<Objective> getCompletedObjectives() {
        List<Objective> completed = new ArrayList<>();
        for (Objective o : this.objectives) {
            if (o.met()) {
                completed.add(o);
            }
        }

        return completed;
    }

    /**
     * Provides a list of objectives that have not been completed.
     * @return objectives not completed.
     */
    public List<Objective> getIncompleteObjectives() {
        List<Objective> notCompleted = new ArrayList<>();
        for (Objective o : this.objectives) {
            if (!o.met()) {
                notCompleted.add(o);
            }
        }
        return notCompleted;
    }

    /**
     * Reports on whether or not all of the objectives have been met.
     * @require this.objectives.size() > 0
     * @ensure for all o in this.objectives, o.met() == true
     */
    public boolean allObjectivesMet() {
        for (Objective o : this.objectives) {
            if (!(o.met())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculates the % of objectives currently completed.
     * @ensure return value >= 0 &&  <= 100
     */
    public double getCurrentCompletionPercentage() {
        int totalObjectiveCount = this.objectives.size();
        // Account for levels without objectives - would
        // raise an exception for dividing by zero
        if (totalObjectiveCount == 0) {
            return 0;
        }

        int objectivesMetCount = this.getCompletedObjectives().size();

        // Allow for proper numerical division to occur
        double objectivesMet = (double) objectivesMetCount;
        return Math.round(100 * (objectivesMet / totalObjectiveCount));
    }

    /**
     * Attempts to save current state of the objectives in-game.
     */
    public void saveAllObjectives() {
        try {
            logger.info("Saving objectives...");
            savePoint.saveObjectives("ObjectiveSave", this.objectives);
        } catch (WebApplicationException exception) {
            logger.error("Save failed due to connection issue", exception);
        } catch (JsonProcessingException j) {
            logger.error("Save failed due to JSON processing issue", j);
        } catch (NullPointerException n) {
            logger.error("Null pointer exception, cannot save", n);
        }
    }

    /**
     * Checks if two objective trackers are equal. They will be if they are
     * watching the same game state and have the same objectives to watch.
     * @param ob tracker being compared
     * @return whether or not trackers are equal
     * @require ob != null
     */
    @Override
    public boolean equals(Object ob) {
        if (!(ob instanceof ObjectiveTracker)) {
            return false;
        } else {
            ObjectiveTracker test = (ObjectiveTracker) ob;
            return this.objectives.equals(test.getObjectives()) &&
                    this.gameState.equals(test.getGameState()) &&
                    this.watchers.equals(test.getObservers());
        }
    }

    /**
     * Returns an ObjectiveTracker's hashcode based the hashcode of its objectives
     * and of its GameState.
     * @return 13 * hash code of objectives + 17 * hash code of GameState
     */
    @Override
    public int hashCode() {
        return 13 * this.objectives.hashCode() + 17 * this.gameState.hashCode();
    }
}

package uq.deco2800.duxcom.objectivesystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.HeroManager;
import uq.deco2800.duxcom.entities.heros.HeroType;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.objectives.*;
import uq.deco2800.duxcom.scoring.ScoreSystem;

/**
 * Keeps a running tally of various statistics (i.e. enemies, money),
 * so the objective system can monitor it and update the status of its
 * objectives. When a new objective type is created, add a new updateXYZ
 * method that notifies all observers of this state what just happened.
 * Forms statistics to track based on objectives provided to it
 * Created by Tom B on 29/08/2016.
 * @require statistics != null && no nulls in statistics
 * @require objectives != null && no nulls in objectives
 */
public class GameState extends Observable {
    // All map objects watching the game state
    private List<Observer> watchers = new ArrayList<>();

    // What is being tracked - could be enemy counts, money, score etc
    private Map<Object, Object> statistics = new HashMap<>();

    // New game
    public GameState(List<Objective> objectives) {
        fillUpStatistics(objectives);
    }

    // Load game
    public GameState(Map<Object, Object> statistics) {
        this.statistics = statistics;
    }

    /**
     * Given the objectives provided, creates a hash-map
     * for each one according to the following rules:
     * EnemyKillObjective - EnemyType, 0/current killed
     * ScoreObjective - Scoresystem, goal score/0
     * ProtectionObjective - EntityType, true/false
     * MovementObjective - ObjectiveCoordinate, true/false
     * EnemyHitObjective - EnemyType, true/false
     * ItemObjective - ItemType, 0/current found.
     * This method is called when a GameState instance is created only,
     * or whenever statistics need to be reset whenever a particular map
     * is restarted.
     * @require objectives != null && no nulls in objectives && each o
     *          are instances of EnemyKill/EnemyHit/Item/Score/Protection/
     *          MovementObjective
     */
    public void fillUpStatistics(List<Objective> objectives) {
        for (Objective o: objectives) {
            if (o instanceof EnemyKillObjective) {
                EnemyKillObjective e = (EnemyKillObjective)o;
                this.statistics.put(e.getObjectiveTarget(), 0);
            } else if (o instanceof ScoreObjective) {
                ScoreObjective s = (ScoreObjective)o;
                ScoreSystem scoring = (ScoreSystem)s.getObjectiveTarget();
                // Allows for integration of score system with statistics
                this.statistics.put(s.getObjectiveTarget(), scoring.getScore());
            } else if (o instanceof ProtectionObjective) {
                ProtectionObjective p = (ProtectionObjective)o;
                this.statistics.put(p.getObjectiveTarget(), p.getObjectiveValue());
            } else if (o instanceof EnemyHitObjective) {
                EnemyHitObjective e = (EnemyHitObjective)o;
                this.statistics.put(e.getObjectiveTarget(), false);
            } else if (o instanceof ItemObjective) {
                ItemObjective i = (ItemObjective) o;
                this.statistics.put(i.getObjectiveTarget(), 0);
            } else {
                MovementObjective m = (MovementObjective) o;
                this.statistics.put(m.getObjectiveTarget(), false);
            }
        }
    }

    /**
     * Gets a list of objects watching this game state entity.
     * @return a list of objects observing this entity
     */
    public List<Observer> getObservers() {
        return new ArrayList<>(this.watchers);
    }

    /**
     * Gets a full mapping of statistics being monitored and manipulated by
     * the game state.
     * @return the current statistics being monitored
     */
    public Map<Object, Object> getStatistics() {
        return new HashMap<>(this.statistics);
    }

    /**
     * Adds an observer to monitor this state.
     * @param watcher an entity that will keep an eye on this game state
     *                for any changes
     * @require watcher != null
     */
    @Override
    public void addObserver(Observer watcher) {
        this.watchers.add(watcher);
    }

    /**
     * Deletes an observer that was monitoring this state.
     * @param watcher an entity that will no longer keep an eye on this game
     *                state for any changes
     * @require watcher != null
     */
    @Override
    public void deleteObserver(Observer watcher) {
        this.watchers.remove(watcher);
    }

    /**
     * Lets all objects watching this state that a change has occurred.
     */
    @Override
    public void notifyObservers() {
        for (Observer w: this.watchers) {
            w.update(this, statistics);
        }
    }

    /**
     * Returns a string representation of the given GameState, according
     * to the given format:
     * "Game State: -statistics list-"
     * @return string of format given above.
     */
    @Override
    public String toString() {
        return "Game State: " + this.statistics;
    }

    /**
     * Updates the amount of a particular enemy type killed by the
     * given amount
     * @param enemyType - type of enemy killed
     * @param kills - amount of enemies killed
     * @require enemyType != null && kills > 0
     */
    public void updateEnemyKills(EnemyType enemyType, int kills) {
        EnemyObjectiveType toLookUp = new EnemyObjectiveType(enemyType, true);
        if (this.statistics.containsKey(toLookUp)) {
            Integer totalKilled = kills + (Integer)(this.statistics.get(toLookUp));
            this.statistics.put(toLookUp, totalKilled);
            notifyObservers();
        }
    }

    /**
     * Updates score goal of the same name as a given new
     * score system.
     * @param newSystem new score system whose name will
     *                  be looked up in the statistics list
     *                  and updated with the new score
     * @require newSystem != null
     */
    public void updateScoreGoals(ScoreSystem newSystem) {
        ScoreSystem oldSystem;

        // Locate old scoring system for replacement
        for (Object k: this.statistics.keySet()) {
            if (k instanceof ScoreSystem) {
                oldSystem = (ScoreSystem) k;
                this.statistics.remove(oldSystem);
                break;
            }
        }

        // Replace with new score system
        int newScore = newSystem.getScore();
        this.statistics.put(newSystem, newScore);
        notifyObservers();
    }

    /**
     * Updates protection objectives with given entity type
     * and the new amount of these entities.
     * @param entityType - entity type being protected
     * @param current - number of entity type still remaining
     * @require entityType != null && current >= 0
     */
    public void updateProtectionGoals(EntityType entityType, int current) {
        if (this.statistics.containsKey(entityType)) {
            this.statistics.put(entityType, current);
            notifyObservers();
        }
    }

    /**
     * Carries out checking on any present protection goals that are related
     * to heroes.
     * @param heroManager - hero source
     * @require heroManager != null
     */
    public void updateAllHeroProtectionGoals(HeroManager heroManager) {
        Map<EntityType, Integer> eTypeList = new HashMap<>();

        // Prepare a map for comparison of counts for each hero
        for (AbstractHero h: heroManager.getHeroList()) {
            HeroType hType = h.getHeroType();
            EntityType eType = EntityType.valueOf(hType.toString());
            if (eTypeList.containsKey(eType)) {
                Integer newValue = eTypeList.get(eType) + 1;
                eTypeList.put(eType, newValue);
            } else {
                eTypeList.put(eType, 1);
            }
        }

        // Send notifications to update protection goals for each hero type
        // present
        for (EntityType e: eTypeList.keySet()) {
            updateProtectionGoals(e, eTypeList.get(e));
        }
    }

    /**
     * Updates enemy hit objective of given enemy type
     * @param type - enemy type being monitored
     * @require type != null
     */
    public void updateEnemyHitGoals(EnemyType type) {
        EnemyObjectiveType eot = new EnemyObjectiveType(type, false);
        if (this.statistics.containsKey(eot)) {
            this.statistics.put(new EnemyObjectiveType(type, false), true);
            notifyObservers();
        }
    }

    /**
     * Updates the amount of a particular item type picked up by
     * given amount
     * @param type- The chosen item type
     * @require type != null
     */
    public void updateItemGoal(ItemType type) {
        Integer itemsCollected = 1 + (Integer)(this.statistics.get(type));
        this.statistics.put(type, itemsCollected);
        notifyObservers();
    }

    /**
     * Sends an alert that a particular movement goal has been reached.
     * @param x - The x position being monitored
     * @param y - the y position being monitored
     * @require x >= 0 && y >= 0
     */
    public void updateMovementGoal(int x, int y) {
        ObjectiveCoordinate oc = new ObjectiveCoordinate(x, y);
        if (this.statistics.containsKey(oc)) {
            this.statistics.put(oc, true);
            notifyObservers();
        }
    }

    /**
     * Sees if two game states are equal. They will be if they have the same sets
     * of statistics.
     * @param toCompare object being compared for equality
     * @return whether or not two GameStates are equal according
     *          to their statistics
     * @require toCompare != null
     */
    @Override
    public boolean equals(Object toCompare) {
        if (!(toCompare instanceof GameState)) {
            return false;
        }
        GameState gm = (GameState)toCompare;
        return this.statistics.equals(gm.getStatistics());
    }

    /**
     * Returns a hash code representation of the GameState, using the hash code
     * of the statistics hash map.
     * @return 23 * hash code of statistics
     */
    @Override
    public int hashCode() {
        return this.statistics.hashCode() * 23;
    }
}

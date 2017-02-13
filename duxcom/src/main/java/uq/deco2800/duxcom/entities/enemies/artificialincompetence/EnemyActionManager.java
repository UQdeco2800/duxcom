package uq.deco2800.duxcom.entities.enemies.artificialincompetence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jay-grant on 31/08/2016.
 * <p>
 * A container class for holding a set of EnemyActions and providing all
 * supporting methods for updating and accessing them.
 */
public class EnemyActionManager {

    private Logger logger = LoggerFactory.getLogger(EnemyActionManager.class);

    protected ArrayList<EnemyAction> actions;
    protected AbstractEnemy owner;
    protected MapAssembly map;

    /**
     * Creates a new ActionManager for the enemy.
     *
     * @param owner - The current Enemy
     */
    public EnemyActionManager(AbstractEnemy owner) {
        this.owner = owner;
        actions = new ArrayList<>();
    }

    /**
     * Set the map the enemy is currently on.
     *
     * @param map
     */
    public void setMap(MapAssembly map) {
        this.map = map;
    }

    /**
     * Sets the current action for enemy to be the first possible.
     */
    public void getAction() {
        owner.setAction(actions.get(0));
    }

    /**
     * Returns ith highest rated action. i.e. i(0) = 1st, i(1) = 2nd, etc.
     *
     * @param i position.
     */
    public void getAction(int i) {
        owner.setAction(actions.get(i));
    }

    /**
     * Returns ith highest rated action with given number of movePoints.
     *
     * @param i     position
     * @param moves desired number of movePoints
     */
    public void getAction(int i, int moves) {
        int a = -1;
        int n = 0;
        while (a != i) {
            while (actions.get(n).getNumberMoves() != moves) {
                n++;
            }
            a++;
        }
        owner.setAction(actions.get(n));
    }

    /**
     * Clears all actions then generates new actions for the current enemy.
     */
    public void updateActions() {
        actions.clear();
        map.getEnemyActionGenerator().generateAction(owner);
    }

    /**
     * Add an action to the queue. Action will be sorted within the current
     * queue.
     *
     * @param action - The action to add.
     */
    public void addAction(EnemyAction action) {
        if (action != null) {
            actions.add(action);
        }
        Collections.sort(actions, (o1, o2) -> {
            double compareRating = o2.getRating();
            return (int) (o1.getRating() - compareRating);
        });

    }

    /**
     * Remove the action from the queue.
     *
     * @param action - The action to remove.
     */
    public void dropAction(EnemyAction action) {
        actions.remove(action);
    }

    /**
     * Determine whether an action exists within the queue.
     *
     * @param action - The action to check for.
     * @return True if the action exists in the queue.
     */
    public boolean contains(EnemyAction action) {
        return actions.contains(action);
    }

    /**
     * Determine if the target moved.
     *
     * @return True if the target moved.
     */
    public boolean targetMoved() {
        if (!actions.isEmpty() && !(actions.get(0).getTarget().getX() == actions.get(0).getTargetPointX()
                && actions.get(0).getTarget().getY() == actions.get(0).getTargetPointY())) {
            logger.info("EnemyActionManager: targetMoved()");
            return true;
        }
        return false;
    }

    /**
     * Determine if the current action queue is empty.
     *
     * @return True if queue is empty.
     */
    public boolean isEmpty() {
        return actions.isEmpty();
    }

    /**
     * Clears the action queue. Will NOT generate a new set of actions. Use
     * updateActions() if you actions should be cleared and regenerated.
     */
    public void clear() {
        actions.clear();
    }
}

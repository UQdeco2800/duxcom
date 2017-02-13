package uq.deco2800.duxcom.entities.enemies;

import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.abilities.AbilityAnimationHandler;
import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.TargetDesignation;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.enemies.listeners.DeathListener;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.maps.mapgen.bounds.Coordinate;

import java.util.ArrayList;
import java.util.List;

import static java.awt.geom.Point2D.distance;

/**
 * EnemyManager will communicate with the current level and its TurnTickable to
 * support the rotation of enemies actions and reactions
 */
public class EnemyManager implements DeathListener {

    private ArrayList<AbstractEnemy> enemies = new ArrayList<>();
    AbstractEnemy currentEnemy;
    int enemyIndex = 0;
    MapAssembly map;
    boolean performingTurn = false;

    /**
     * Class constructor
     */
    public EnemyManager() {
        // Constructs EnemyManager instance
    }

    /**
     * Added by Thomas Bricknell - needed to be able to construct enemy kill
     * objectives based on amount of enemies that are on the map
     */
    public List<AbstractEnemy> getEnemyList() {
        return new ArrayList<>(enemies);
    }

    /**
     * Returns an Enemy given an index.
     *
     * @param index
     * @return AbstractEnemy
     */
    public AbstractEnemy get(int index) {
        return enemies.get(index);
    }

    /**
     * Checks to see if an enemy of a given type is present
     *
     * @param type - enemy type being looked for
     * @return true if at least one enemy of the specified type exists, else
     * false
     * @require type != null Thomas Bricknell - 9/10/16
     */
    public boolean hasType(EnemyType type) {
        for (AbstractEnemy e : this.enemies) {
            if (e.getEnemyType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns how many active enemies match the given type.
     *
     * @param type - The type of the Enemy
     * @return The count of enemies of specified type.
     */
    public int getCountOfEnemyType(EnemyType type) {
        int count = 0;
        for (AbstractEnemy e : this.enemies) {
            if (e.getEnemyType().equals(type)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Set the map the Enemy Manager will use. This will also set the map for
     * all Action Manager's.
     *
     * @param map - The Map.
     */
    public void setMap(MapAssembly map) {
        this.map = map;
        for (AbstractEnemy enemy : enemies) {
            enemy.getActionManager().setMap(map);
        }
    }

    /**
     * Returns the current Enemy.
     *
     * @return the current enemy
     */
    public AbstractEnemy getCurrentEnemy() {
        return currentEnemy;
    }

    /**
     * Set the current Enemy.
     *
     * @param i
     */
    public void setEnemy(int i) {
        if (currentEnemy != null) {
            currentEnemy.disableListeners(); // Disable Listeners for previous
            // Enemy.
        }
        if (!enemies.isEmpty()) {
            currentEnemy = enemies.get(i);
            currentEnemy.enableListeners();
        }
    }

    /**
     * Set the next Enemy to be the current Enemy.
     */
    public void nextEnemy() {
        if (currentEnemy != null) {
            currentEnemy.disableListeners(); // Disable Listeners for previous
            // Enemy.
        }
        enemyIndex++;
        if (enemyIndex >= enemies.size()) {
            enemyIndex = 0;
        }
        currentEnemy = enemies.get(enemyIndex);
        currentEnemy.enableListeners();
    }

    /**
     * Add a new Enemy to the active list. Index of enemy will be total enemies
     * - 1.
     *
     * @param enemy
     */
    public void addEnemy(AbstractEnemy enemy) {
        enemyIndex++;
        enemy.setHidden(true);
        enemies.add(enemy);
    }

    /**
     * Returns whether the list of active enemies is empty. Will be true when
     * all enemies are dead or EnemyManager has just been created.
     *
     * @return true if active list of enemies is empty
     */
    public boolean isEmpty() {
        return enemies.isEmpty();
    }

    /**
     * Returns whether enemies are currently taking their turn.
     *
     * @return true if enemies taking turn.
     */
    public boolean isPerformingTurn() {
        return performingTurn;
    }

    /**
     * Make Enemies take a Turn.
     *
     * @param gameManager
     */
    public void takeTurn(GameManager gameManager) {
        if (!performingTurn) {
            actionHandler(gameManager);
        }
    }

    /**
     * Handles the execution of an Action, including the moves, abilities, and
     * reloading of the action in the event that it is no longer valid.
     */
    private void actionHandler(GameManager gameManager) {
        performingTurn = true;

        // Enemy has no currentAction or target has moved
        if (!currentEnemy.isCommitted() || currentEnemy.getActionManager().targetMoved()) {
            currentEnemy.getActionManager().updateActions();
            if (!currentEnemy.getActionManager().isEmpty()) {
                currentEnemy.getActionManager().getAction();
            } else {
                return;
            }
        }

        gameManager.moveViewTo(currentEnemy.getX(), currentEnemy.getY());
        AbilityAnimationHandler.delay(250);

        // Perform move if any
        if (currentEnemy.getAction().hasMoves()) {
            performMove(gameManager);
            currentEnemy.getAction().dropMove();
            currentEnemy.setCommit(true);
        }

        // Moves completed, perform ability and await next action
        performAbility(gameManager);
        currentEnemy.setCommit(false);

        performingTurn = false;
    }

    /**
     * Make an Enemy perform a valid move. Moves are handled in the
     * ActionManager for the entity. Movement indicators are handled on move.
     * Handles camera movement for realism. Visibility range also handled.
     *
     * @param gameManager
     */
    private void performMove(GameManager gameManager) {
        Coordinate newCoordinate = currentEnemy.getValidCoordinate(map, currentEnemy.getAction().getMove(0));
        int oldPointX = currentEnemy.getX();
        int oldPointY = currentEnemy.getY();

        // Apply move indicator
        gameManager.setEnemyMoveIntent(newCoordinate.x, newCoordinate.y);
        gameManager.setMovementChanged(true);

        AbilityAnimationHandler.delay(600);
        map.getTile(oldPointX, oldPointY).removeMovableEntity();
        currentEnemy.move(newCoordinate.x, newCoordinate.y);
        map.updateVisibilityArray();
        gameManager.setVisionChanged(true);
        map.getTile(newCoordinate.x, newCoordinate.y).addEntity(currentEnemy);
        gameManager.setEnemyMoveIntent(-1, -1);
        AbilityAnimationHandler.delay(100);
        gameManager.moveViewTo((newCoordinate.x + oldPointX) / 2, (newCoordinate.y + oldPointY) / 2);
        AbilityAnimationHandler.delay(850);
    }

    /**
     * Make an Enemy perform an Ability.
     *
     * @param gameManager
     */
    private void performAbility(GameManager gameManager) {
        AbstractAbility ability = currentEnemy.getAction().getAbility();
        AbstractCharacter target = currentEnemy.getAction().getTarget();
        if (ability != null && ability.getRange() >= distance(currentEnemy.getX(), currentEnemy.getY(), target.getX(),
                target.getY()) && !currentEnemy.isHidden()) {

            AbilityAnimationHandler.executeCompleteTurn(currentEnemy, ability, target.getX(), target.getY(), gameManager, TargetDesignation.FOE);

            if (target.getHealth() <= 0) {
                gameManager.moveViewTo(target.getX(), target.getY());
                gameManager.setGameChanged(true);
                AbilityAnimationHandler.delay(1250);
            }

            currentEnemy.getActionManager().dropAction(currentEnemy.getAction());
        }
    }

    /**
     * Returns the number of enemies still active.
     *
     * @return Number of active enemies.
     */
    public int numberEnemies() {
        return enemies.size();
    }

    /**
     * Adds deathListener as a listener for all enemies.
     *
     * @param deathListener the health listener to be added.
     */
    public void addDeathListenerAllEnemies(DeathListener deathListener) {
        for (AbstractEnemy enemy : enemies) {
            enemy.addDeathListener(deathListener);
        }
    }

    /**
     * Handles what will happen on the death of an Enemy.
     *
     * @param character The Enemy that has just died.
     */
    @Override
    public void onDeath(AbstractCharacter character) {
        AbstractEnemy enemy = (AbstractEnemy) character;
        enemy.disableListeners();
        enemies.remove(enemy);
    }

    /**
     * Returns true iff at lease one enemy is not hidden
     * @return
     */
    public boolean hasRevealedEnemy() {
        for (AbstractEnemy enemy :
                enemies) {
            if (!enemy.isHidden()) {
                return true;
            }
        }
        return false;
    }
}

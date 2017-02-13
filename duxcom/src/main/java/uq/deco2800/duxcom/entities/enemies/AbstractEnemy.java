package uq.deco2800.duxcom.entities.enemies;

import uq.deco2800.duxcom.dataregisters.EnemyDataClass;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.enemies.artificialincompetence.EnemyAction;
import uq.deco2800.duxcom.entities.enemies.artificialincompetence.EnemyActionManager;
import uq.deco2800.duxcom.entities.enemies.artificialincompetence.EnemyAttitude;
import uq.deco2800.duxcom.entities.enemies.artificialincompetence.EnemyMode;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.maps.mapgen.bounds.Coordinate;

import java.util.ArrayList;

/**
 * Created by jay-grant on 23/08/2016.
 * <p>
 * Abstract class for on which all enemies are structured
 */
public abstract class AbstractEnemy extends AbstractCharacter {

    /**
     * Action Manager
     */
    protected EnemyActionManager actionManager;
    private EnemyAction action;
    protected boolean commit;
    private int mx;
    private int my;

    /**
     * Enemy Attributes
     */
    protected EnemyType enemyType;
    protected EnemyAttitude attitude;
    protected EnemyMode mode;

    /**
     * Enemy Stats
     */
    protected double critChance;
    protected double magicResist;
    protected double actionPoints;
    protected int moveRange;
    protected int attackRange;

    /**
     * Class constructor
     *
     * @param entityType the type of entity of the enemy
     * @param x          the x location of the enemy
     * @param y          the y location of the enemy
     */
    public AbstractEnemy(EntityType entityType, int x, int y) {
        super(entityType, x, y, 1, 1);
    }

    /**
     * Transfers specific character stats into Abstract super class;
     * to be called from individual character construction to reduce
     * duplicate code
     *
     * @param enemyType      the type of the enemy
     * @param enemyDataClass The data class of the enemy
     */
    public void setStats(EnemyType enemyType, EnemyDataClass enemyDataClass) {
        this.enemyType = enemyType;
        this.name = enemyDataClass.getEnemyName();
        this.graphic = enemyDataClass.getEnemyGraphic();
        this.attitude = enemyDataClass.getAttitude();
        this.mode = enemyDataClass.getMode();
        this.baseHealth = enemyDataClass.getBaseHealth();
        this.health = enemyDataClass.getBaseHealth();
        this.damage = enemyDataClass.getBaseDamage();
        this.critChance = enemyDataClass.getBaseCritChance();
        this.armour = enemyDataClass.getBaseArmour();
        this.magicResist = enemyDataClass.getBaseMagicResist();
        this.actionPoints = enemyDataClass.getBaseAP();
        this.speed = enemyDataClass.getBaseSpeed();
        this.moveRange = enemyDataClass.getMoveRange();
        this.attackRange = enemyDataClass.getBaseAttackRange();
        this.abilities = new ArrayList<>();
        this.actionManager = new EnemyActionManager(this);
        this.commit = false;
    }

    /* Combat Related Methods
     * These methods are intended to equate effects of actions such as damage
     * from an attack or healing from an ability, and update the values on the
     * corresponding Entity (either AbstractHero or AbstractEnemy)
     */

    /**
     * Sets the commit status of the enemy
     *
     * @param status the new commit status
     */
    public void setCommit(boolean status) {
        this.commit = status;
    }


    /**
     * Returns the commit status of the enemy
     *
     * @return enemy commit status
     */
    public boolean isCommitted() {
        return commit;
    }


    /* Movement Related Methods
     * In the future these methods will be supporting of the behaviour
     * algorithms of the Enemy entities
     */

    /**
     * Returns an entirely valid point which an Enemy may move to, as close to
     * the original desired point (input).
     *
     * @param map        the map on which the Enemy exists.
     * @param coordinate the desired point.
     * @return a valid point as close to the desired point as possible.
     */
    public Coordinate getValidCoordinate(MapAssembly map, Coordinate coordinate) {

        // Trim move to moveRange circle
        mx = getRangeX(coordinate.x);
        my = getRangeY(coordinate.y);

        // Trim move to map borders
        borderCheck(map);

        // Adjust for entity conflicts
        int testX = mx;
        int testY = my;
        while (map.getTile(testX, testY).isOccupied()
                && map.getTile(testX, testY).getMovableEntity() != this) {
            testX = backtrackX(map);
            testY = backtrackY(map);
        }

        return new Coordinate(mx, my);
    }

    private int backtrackX(MapAssembly map) {
        int currentX = this.getX();
        if (mx < currentX) {
            mx++;
        }
        if (mx > currentX) {
            mx--;
        }
        borderCheck(map);
        return mx;
    }

    private int backtrackY(MapAssembly map) {
        int currentY = this.getY();
        if (my < currentY) {
            my++;
        }
        if (my > currentY) {
            my--;
        }
        borderCheck(map);
        return my;
    }

    private void borderCheck(MapAssembly map) {
        if (mx < 0) {
            mx = 0;
        }
        if (mx > map.getWidth() - 1) {
            mx = map.getWidth() - 1;
        }
        if (my < 0) {
            my = 0;
        }
        if (my > map.getHeight() - 1) {
            my = map.getHeight() - 1;
        }
    }

    /**
     * Returns the point that meets a compromise between inputs (x,y) and the
     * inherited movement attackRange restriction of the given Enemy.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @return adjusted point based on movement attackRange.
     */
    private Coordinate getRangeMove(int x, int y) {
        return new Coordinate(getRangeX(x), getRangeY(y));
    }

    private int getRangeX(int x) {
        if (getX() <= x + moveRange && getX() >= x - moveRange) {
            return x;
        } else {
            return getX() + moveRange;
        }
    }

    private int getRangeY(int y) {
        if (getY() <= y + moveRange && getY() >= y - moveRange) {
            return y;
        } else {
            return getY() + moveRange;
        }
    }

    /**
     * Gets the move range of the enemy
     *
     * @return enemy move range
     */
    public int getMoveRange() {
        return moveRange;
    }

    /* Other Methods */

    /**
     * To be the primary trunk of the Enemy """"AI"""" behaviour
     * tree, and will likely accept many variables in the future.
     */
    public void onTurn() {
        // Execute """AI"""
    }

    /* Graphic Related Methods
     * Functions indented to aid in rendering sprites, and
     * in the future particles and effects
     */

    /**
     * Added by Thomas Bricknell - Team 06 for objective system testing purposes
     * (may remove later on)
     *
     * @return the enemy type
     */
    public EnemyType getEnemyType() {
        return this.enemyType;
    }


    /**
     * Gets the enemy action manager of the enemy
     *
     * @return enemy action manager
     */
    public EnemyActionManager getActionManager() {
        return this.actionManager;
    }

    /**
     * Gets the current enemy action
     *
     * @return enemy current action
     */
    public EnemyAction getAction() {
        return this.action;
    }

    /**
     * Gets the enemy's attitude
     *
     * @return enemy attitude
     */
    public EnemyAttitude getAttitude() {
        return this.attitude;
    }

    /**
     * Sets the enemy action to a new given action
     *
     * @param action the new action
     */
    public void setAction(EnemyAction action) {
        this.action = action;
    }

    /**
     * Sets the enemy health to a given value
     *
     * @param health the new health of the enemy
     */
    public void setHealth(int health) {
        this.health = health;
    }
}

package uq.deco2800.duxcom.entities.enemies.artificialincompetence;

import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.maps.mapgen.bounds.Coordinate;

import java.util.ArrayList;
import java.util.List;

import static java.awt.geom.Point2D.distance;

/**
 * Created by jay-grant on 31/08/2016.
 * <p>
 * Support class to contain all functionality to receive a game state (map and
 * all entities) and an Enemy, and produce all possible actions that the enemies
 * is able to take.
 * <p>
 * These actions will be stored in EnemyAction Objects, which in turn will be
 * parsed through EnemyActionManager to filter the less favourable actions and
 * identify the most.
 */
public class EnemyActionGenerator {

    /**
     * Imported Vars
     */
    protected MapAssembly map;

    /**
     * Temporary Local Vars
     */
    protected AbstractEnemy owner;

    /**
     * Recursive Local Vars
     */
    protected boolean isAggro = true;
    protected int numberOfMoves = 0;
    protected int maxMoves = 10;
    protected ArrayList<Coordinate> movePoints;
    protected int xMove;
    protected int yMove;
    protected EnemyAction primaryAction = null;

    /**
     * Constructor for the EnemyActionGenerator.
     *
     * @param map The current map.
     */
    public EnemyActionGenerator(MapAssembly map) {
        this.map = map;
    }

    /**
     * Updates the EnemyActionManager of the AbstractEnemy owner to include a
     * new EnemyAction that should be the most favourable for the owner to
     * perform subject to the current state of the game; namely the stats and
     * locations of all entities.
     * <p>
     * In general, a preferable move should aim to be a balance of: - lowest
     * number of moves - greatest effect on target - preferred target Role -
     * safest location to move to
     *
     * @param owner the AbstractEnemy for which an Action will be generated.
     */
    public void generateAction(AbstractEnemy owner) {
        primaryAction = null;
        this.owner = owner;
        xMove = owner.getX();
        yMove = owner.getY();
        isAggro = checkAttitudeAggro();
        this.movePoints = new ArrayList<>();

        if (owner.getAttitude().equals(EnemyAttitude.BALANCED)) {
            getBalanced();
        } else if (owner.getAttitude().equals(EnemyAttitude.SUPPORTIVE)) {
            getSupportive();
        } else if (owner.getAttitude().equals(EnemyAttitude.DEFENSIVE)) {
            getDefensive();
        } else if (owner.getAttitude().equals(EnemyAttitude.EVASIVE)) {
            getEvasive();
        }

        owner.getActionManager().addAction(primaryAction);
    }

    /**
     * Determines if the attitude of the current enemy is aggro.
     *
     * @return True if attitude is aggro.
     */
    private boolean checkAttitudeAggro() {
        // return owner.getAttitude().equals(EnemyAttitude.BERSERK)
        // || owner.getAttitude().equals(EnemyAttitude.BALANCED)
        // || owner.getAttitude().equals(EnemyAttitude.RESERVED)
        return true;
    }

    /**
     * Returns a list of characters surrounding the current enemy within a given
     * range. If Aggro, then list will be filled with Heros's. Else list will be
     * filled with Enemies.
     *
     * @param range    The range in which entities should be searched for.
     * @param movement True if the movement radius of the owner should be used.
     * @param enemies  Set to True if a list of enemies should be returned (Used for
     *                 Passive Attacks)
     * @param heroes   Set to True if a list of heroes should be returned (Used for
     *                 Active Attacks)
     * @return List of entites in the range.
     */
    private ArrayList<AbstractCharacter> getSurrounding(int range, boolean movement, boolean enemies, boolean heroes) {
        int movementRadius = 0;
        if (movement) {
            movementRadius = owner.getMoveRange() * numberOfMoves;
        }
        int xMin = owner.getX() - range - movementRadius;
        int yMin = owner.getY() - range - movementRadius;
        int xMax = owner.getX() + range + movementRadius;
        int yMax = owner.getY() + range + movementRadius;

        ArrayList<AbstractCharacter> entities = new ArrayList<>();
        for (int x = xMin; x < xMax; x++) {
            for (int y = yMin; y < yMax; y++) {
                if (!map.canSelectPoint(x, y) || map.getMovableEntity(x, y) == null) {
                    continue;
                }
                if (map.getMovableEntity(x, y) instanceof AbstractHero && heroes) {
                    // We have an AbstractHero that can be reached
                    entities.add((AbstractCharacter) map.getMovableEntity(x, y));
                    // WE MUST now generate a path
                }
                if (map.getMovableEntity(x, y) instanceof AbstractEnemy && enemies
                        && !this.owner.equals(map.getMovableEntity(x, y))) {
                    // We have an AbstractEnemy that can be reached
                    entities.add((AbstractCharacter) map.getMovableEntity(x, y));
                    // WE MUST now generate a path
                }
            }
        }
        return entities;
    }

    /**
     * Returns a list of Points (representative of a path) that would result in
     * the owner Enemy being in the optimal position to use ability on entity.
     * i.e. the final Point in the path will be that from which the enemy will
     * case the ability.
     * <p>
     * (assuming the target does not move... lol).
     *
     * @param target  the target.
     * @param ability the ability to be cast on target.
     * @return a list of Points forming a path.
     */
    private ArrayList<Coordinate> generatePath(AbstractCharacter target, AbstractAbility ability) {
        int xTarget = target.getX();
        int yTarget = target.getY();
        int xMovePathGen = owner.getX();
        int yMovePathGen = owner.getY();
        ArrayList<Coordinate> path = new ArrayList<>();

        while (distance(xMovePathGen, yMovePathGen, xTarget, yTarget) > ability.getRange()) {
            int offset = 0;

            while (offset < owner.getMoveRange()
                    && distance(xMovePathGen, yMovePathGen, xTarget, yTarget) > ability.getRange()) {

                double xOffset = distance(xMovePathGen, yMovePathGen, xTarget, yMovePathGen);
                double yOffest = distance(xMovePathGen, yMovePathGen, xMovePathGen, yTarget);
                double xyRatio = xOffset / yOffest;
                
                if (xTarget < xMovePathGen && xyRatio >= 1) {
                    xMovePathGen--;
                }
                if (xTarget > xMovePathGen && xyRatio >= 1) {
                    xMovePathGen++;
                }
                if (yTarget < yMovePathGen && xyRatio <= 1) {
                    yMovePathGen--;
                }
                if (yTarget > yMovePathGen && xyRatio <= 1) {
                    yMovePathGen++;
                }
                offset++;
            }
            path.add(new Coordinate(xMovePathGen, yMovePathGen));
        }
        return path;
    }

    /**
     * Updates the primary action with an action suitable for a balanced game
     * play style. Will find the closest hero (the hero with the least number of
     * moves from the current enemy) and generate an action to attack such hero.
     */
    private void getBalanced() {
        // Ability is compatible with Enemy Attitude
        // New Action has a higher rating than its predecessor

        for (AbstractAbility ability : owner.getAbilities()) {

            // Ability is compatible with Enemy Attitude
            List<AbstractCharacter> surroundingTargets = getSurrounding(ability.getRange(), true, false, true);
            if (surroundingTargets.isEmpty() || !ability.canUseOnFoe()) {
                continue;
            }

            for (AbstractCharacter target : surroundingTargets) {
                double rating = 50 - 10 * numberOfMoves
                        - distance(owner.getX(), owner.getY(), target.getX(), target.getY());

                // New Action has a higher rating than its predecessor
                if (primaryAction == null || primaryAction.getRating() < rating) {
                    primaryAction = new EnemyAction(target, target.getX(), target.getY(), ability,
                            generatePath(target, ability), rating);
                }
            }
        }

        // No actions were found, try again for +1 move
        if (primaryAction == null && numberOfMoves < maxMoves) {
            numberOfMoves++;
            getBalanced();
        }
    }

    /**
     * Updates the primary action with an action suitable for a supportive game
     * play style.
     */
    private void getSupportive() {
        for (AbstractAbility ability : owner.getAbilities()) {

            // Ability is compatible with Enemy Attitude
            List<AbstractCharacter> surroundingEnemies = getSurrounding(ability.getRange(), true, true, false);
            if (surroundingEnemies.isEmpty() && ability.canUseOnFoe() && primaryAction == null) {
            	getBalanced();
            }

            for (AbstractCharacter target : surroundingEnemies) {
                double rating = 300 - 10 * numberOfMoves
                        - distance(owner.getX(), owner.getY(), target.getX(), target.getY()) - target.getHealth();

                // New Action has a higher rating than its predecessor
                if ((primaryAction == null || primaryAction.getRating() < rating) && ability.canUseOnFriend()) {
                    primaryAction = new EnemyAction(target, target.getX(), target.getY(), ability,
                            generatePath(target, ability), rating);
                    target.changeHealth(10);
                }
            }
        }

        // No actions were found, try again for +1 move
        if (primaryAction == null && numberOfMoves < maxMoves) {
            numberOfMoves++;
            getSupportive();
        }
    }

    /**
     * Updates the primary action with an action suitable for a defensive game
     * play style.
     */
    private void getDefensive() {
        getBalanced();
        // Get inbetween enemies and heroes.
    }

    /**
     * Updates the primary action with an action suitable for a evasive game
     * play style.
     */
    private void getEvasive() {
        for (AbstractAbility ability : owner.getAbilities()) {
            // If there are no enemies within 3 blocks, move towards closest
            // one.
            List<AbstractCharacter> surroundingEnemies3 = getSurrounding(3, false, true, false);
            if (surroundingEnemies3.isEmpty()) {
                // There are no Enemies nearby in a range of 3.
                List<AbstractCharacter> surroundingEnemiesAll = getSurrounding(50, false, true, false);
                // For all Enemies, find the closest.
                for (AbstractCharacter enemy : surroundingEnemiesAll) {
                    double rating = 50 - 10 * numberOfMoves
                            - distance(owner.getX(), owner.getY(), enemy.getX(), enemy.getY());

                    // New Action has a higher rating than its predecessor
                    if (primaryAction == null || primaryAction.getRating() < rating) {
                        primaryAction = new EnemyAction(enemy, enemy.getX(), enemy.getY(), null,
                                generatePath(enemy, ability), rating);
                    }
                }
            } else {
                // There are Enemies nearby. Enemy can attack at Heros.
                if (primaryAction == null) {
                    getBalanced();
                }
            }
        }
        // No actions were found, try again for +1 move
        if (primaryAction == null && numberOfMoves < maxMoves) {
            numberOfMoves++;
            getEvasive();
        }
    }

    /**
     * Returns the current Primary EnemyAction of the generator class. If there is no action
     * returns null.
     *
     * @return an EnemyAction or null
     */
    public EnemyAction getPrimaryAction() {
        return primaryAction;
    }

}

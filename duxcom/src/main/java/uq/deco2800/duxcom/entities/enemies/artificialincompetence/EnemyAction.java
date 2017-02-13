package uq.deco2800.duxcom.entities.enemies.artificialincompetence;

import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.maps.mapgen.bounds.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay-grant on 31/08/2016.
 * <p>
 * A Container class for holding all information that relates to a potential
 * action that can be taken by an Enemy can take across one or many turns.
 */
public class EnemyAction {

    protected AbstractCharacter target;
    protected int targetPointX;
    protected int targetPointY;
    protected AbstractAbility ability;
    protected List<Coordinate> moves;
    protected double rating;

    /**
     * Constructor for an EnemyAction.
     *
     * @param target       The entity to target. This can be either an Enemy for passive
     *                     abilities, or Hero for active abilities.
     * @param targetPointX The x-coordinate of the target.
     * @param targetPointY The y-coordinate of the target.
     * @param ability      The ability to use on the target.
     * @param moves        A list of moves for the enemy.
     * @param rating       The rating of the action. Used in the EnemyActionGenerator.
     */
    public EnemyAction(AbstractCharacter target, int targetPointX, int targetPointY, AbstractAbility ability,
                       List<Coordinate> moves, double rating) {
        this.target = target;
        this.targetPointX = targetPointX;
        this.targetPointY = targetPointY;
        this.ability = ability;
        if (moves == null) {
            this.moves = new ArrayList<>();
        } else {
            this.moves = moves;
        }
        this.rating = rating;
    }

    /**
     * Returns the rating of the Action.
     *
     * @return The rating as a double.
     */
    public double getRating() {
        return rating;
    }

    /**
     * Returns the ith Move for the action.
     *
     * @param index of Move
     * @return Point in position i. Null if there are no moves.
     */
    public Coordinate getMove(int index) {
        if (!moves.isEmpty()) {
            return moves.get(index);
        }
        return null;
    }

    /**
     * Drops the first move (index 0).
     */
    public void dropMove() {
        if (!moves.isEmpty()) {
            moves.remove(0);
        }
    }

    /**
     * Returns the target x point of the Enemy.
     *
     * @return The x point of the Target.
     */
    public int getTargetPointX() {
        return targetPointX;
    }

    /**
     * Returns the target y point of the Enemy.
     *
     * @return The y point of the Target.
     */
    public int getTargetPointY() {
        return targetPointY;
    }

    /**
     * Returns whether there are moves available.
     *
     * @return True/False
     */
    public boolean hasMoves() {
        return !moves.isEmpty();
    }

    /**
     * Returns the AbstractAbility to be done by the Action of the Enemy.
     *
     * @return The Ability
     */
    public AbstractAbility getAbility() {
        return this.ability;
    }

    /**
     * Returns the number of moves to make.
     *
     * @return Number of moves
     */
    public int getNumberMoves() {
        return moves.size();
    }

    /**
     * Returns the target.
     *
     * @return The AbstractCharacter that is the target.
     */
    public AbstractCharacter getTarget() {
        return this.target;
    }

    /**
     * Returns the HashCode for the EnemyAction
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = target.hashCode();
        result = 31 * result + ability.hashCode();
        result = 31 * result + moves.hashCode();
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * Returns whether two EnemyAction's are the same.
     */
    @Override
    public boolean equals(Object o) {

        if (o instanceof EnemyAction) {
            return compareActions((EnemyAction) o);
        } else {
            return false;
        }
    }

    /**
     * Compares the current Action against another, determining if they are the
     * same. In particular, it will check if the ratings, abilities, targets and
     * number of moves are the same.
     *
     * @param thatAction The action that is being compared against.
     * @return True if the actions are the same. False if they are different.
     */
    private boolean compareActions(EnemyAction thatAction) {

        // this is used to calculate a tolerance for floating point comparisons
        double eps;
        if (this.rating >= (thatAction).getRating()) {
            eps = (thatAction).getRating() / 10000;
        } else {
            eps = this.rating / 10000;
        }

        return Math.abs(this.rating - (thatAction).getRating()) <= eps && abilitiesEqual(thatAction)
                && targetsEqual(thatAction) && numberOfMovesEqual(thatAction);

    }

    /**
     * Determines if the current action and requested action have the same
     * number of moves.
     *
     * @param thatAction The action that is being compared against.
     * @return True if the moves are the same. False if they are different.
     */
    private boolean numberOfMovesEqual(EnemyAction thatAction) {
        if (this.getNumberMoves() == (thatAction).getNumberMoves()) {
            for (int i = 0; i < this.getNumberMoves(); i++) {
                if (!this.getMove(i).equals((thatAction).getMove(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Determines if the current action and requested action have the same
     * targets.
     *
     * @param thatAction The action that is being compared against.
     * @return True if the targets are the same. False if they are different.
     */
    private boolean targetsEqual(EnemyAction thatAction) {
        if (this.target != null && (thatAction).getTarget() != null) {
            if (!this.target.equals((thatAction).getTarget())) {
                return false;
            }
        } else {
            if (this.target == null && (thatAction).getTarget() != null
                    || this.target != null && (thatAction).getTarget() == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if the current action and requested action have the same
     * abilities.
     *
     * @param thatAction The action that is being compared against.
     * @return True if the abilities are the same. False if they are different.
     */
    private boolean abilitiesEqual(EnemyAction thatAction) {
        if (this.ability != null && (thatAction).getAbility() != null) {
            if (!this.getAbility().equals((thatAction).getAbility())) {
                return false;
            }
        } else {
            if (this.ability == null && (thatAction).getAbility() != null
                    || this.ability != null && (thatAction).getAbility() == null) {
                return false;
            }
        }
        return true;
    }
}

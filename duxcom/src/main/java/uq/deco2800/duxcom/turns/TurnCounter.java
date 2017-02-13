package uq.deco2800.duxcom.turns;

/**
 * Implementation of a simple turn counter, without an upper limit.
 * Created by Tom B on 4/09/2016.
 */
public class TurnCounter {
    protected int turns;

    public TurnCounter() {
        this.turns = 0;
    }

    public int getTurns() {
        return this.turns;
    }

    /**
     * Increases turns made by specified amount.
     * @param turnChange how much you're increasing turns
     * @require turnChange > 0
     */
    public void changeTurns(int turnChange) {
        this.turns += turnChange;
    }

    /**
     * Increases turns made by 1
     */
    public void incrementTurn() {
        this.changeTurns(1);
    }

    /**
     * Resets counter back to zero.
     */
    public void reset() {
        this.turns = 0;
    }

    @Override
    public String toString() {
        return "Turns made: " + this.turns;
    }
}

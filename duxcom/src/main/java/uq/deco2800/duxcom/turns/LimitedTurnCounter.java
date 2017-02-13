package uq.deco2800.duxcom.turns;

/**
 * Implementation of a simple turn counter, with a specified
 * turn limit.
 */
public class LimitedTurnCounter extends TurnCounter {
    private int turnLimit;

    public LimitedTurnCounter(int turnLimit) {
        super();
        this.turnLimit = turnLimit;
    }

    /**
     * Increases turns made by specified amount, until the
     * limit of turns is reached.
     * @param turnChange how much you're increasing turns by
     * @require turnChange > 0
     * @ensure this.turns <= this.turnLimit
     */
    public void changeTurns(int turnChange) {
        this.turns += turnChange;
        if (this.turns > this.turnLimit) {
            this.turns = this.turnLimit;
        }
    }

    /**
     * Increases turns made by 1
     * @ensure this.turns <= this.turnLimit
     */
    public void incrementTurn() {
        this.turns++;
    }


    public int getTurnLimit() {
        return this.turnLimit;
    }

    @Override
    public String toString() {
        return "Limit: " + this.turnLimit + "; " + super.toString();
    }
}

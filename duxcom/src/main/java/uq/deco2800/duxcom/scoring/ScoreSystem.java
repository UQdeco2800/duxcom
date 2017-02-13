package uq.deco2800.duxcom.scoring;

/**
 * Represents the basic framework to keep track of the score during a game session.
 */
public class ScoreSystem {
    private int score;
    private String name;

    public ScoreSystem() {
        this.name = "Scoring System";
        this.score = 0;
    }

    /**
     * Get current score of this ScoreSystem.
     * @return current score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Obtain name of this ScoreSystem.
     * @return name of this ScoreSystem.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Change score system's name.
     * @param name new name to call this ScoreSystem
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets score back to zero.
     */
    public void resetScore() {
        this.score = 0;
    }

    /**
     * Increases/decreases the score
     * @param amount amount to change score by
     * @param increaseDecrease direction to change score in - increase
     *                         or decrease
     * @require amount > 0 && (increaseDecrease == '+' || increaseDecrease
     *          == '-')
     * @ensure score changed by specified amount in given direction
     */
    public void changeScore(int amount, char increaseDecrease) {
        if (increaseDecrease == '+') {
            this.score += amount;
        } else {
            this.score -= amount;

        }
        if (this.score < 0) {
            this.resetScore();
        }
    }

    /**
     * Checks to see whether or not two ScoreSystem objects
     * are equal - will be if both are ScoreSystems and have
     * the same name.
     * @param toCompare object being compared for equality
     * @return whether or not two ScoreSystems are equal based
     *          on the above requirements.
     * @require toCompare != null
     */
    @Override
    public boolean equals(Object toCompare) {
        if (!(toCompare instanceof ScoreSystem)) {
            return false;
        } else {
            ScoreSystem system = (ScoreSystem)toCompare;
            return this.name.equals(system.getName());
        }
    }

    /**
     * Returns a hashcode representation of the name using the
     * hashcode representation of the ScoreSystem's name.
     * @return 13 * hashcode of name of ScoreSystem
     */
    @Override
    public int hashCode() {
        return 13 * this.name.hashCode();
    }

    /**
     * Returns the string representation of a given ScoreSystem -
     * simply its name.
     * @return name of ScoreSystem object.
     */
    @Override
    public String toString() {
        return this.name;
    }
}

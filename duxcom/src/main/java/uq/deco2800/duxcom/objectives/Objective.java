package uq.deco2800.duxcom.objectives;

/**
 * Represents an objective that can be met in the game (not to be used
 * directly)
 * @require targetType != null && targetValue > 0 && description.length() > 0
 * Created by Tom B on 25/08/2016.
 */
public abstract class Objective {

    // Type of object being tracked
    private Object targetType;

    // Goal that must be met to complete the objective
    private Object targetValue;

    // Has objective been met?
    protected boolean isMet;

    // Text describing what to do to meet the objective
    private String description;

    // Number of times objective has already been displayed on completion
    private int displayed;

    public Objective(Object targetType, Object targetValue,
                     String description) {
        this.description = description;
        this.targetType = targetType;
        this.targetValue = targetValue;
        this.isMet = false;
        this.displayed = 0;
    }

    /**
     * Get directions on how to complete the objective.
     * @return how to complete the objective
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Get the object being the main focus of the objective.
     * @return objective target
     */
    public Object getObjectiveTarget() {
        return this.targetType;
    }

    /**
     * Get value needed to meet said objective.
     * @return target value of objective
     */
    public Object getObjectiveValue() {
        return this.targetValue;
    }

    /**
     * Gets the number of times that this objective has been displayed
     */
    public int getDisplayed() {
        return this.displayed;
    }

    /**
     * Increase number of times this objective has been displayed by 1
     */
    public void incrementDisplayed() {
        this.displayed++;
    }

    /**
     * Changes objective value to a new value.
     * @param v new value for objective.
     * @require v != null
     */
    public void setObjectiveValue(Object v) {
        this.targetValue = v;
    }

    /**
     * Returns an indication of whether or not the objective has been met.
     * @return
     */
    public boolean met() {
        return this.isMet;
    }

    /**
     * Sets objective as being complete.
     */
    public void complete() {
        this.isMet = true;
        this.displayed++;
    }

    /**
     * Sets objective as not being complete.
     */
    public void fail() {
        this.isMet = false;
    }

    /**
     * Generates a string representation of the objective in the following
     * format:
     * "Target Type: -type-, Value: -value-; Description: -description-; Met:
     * -met-"
     * @return a string in the above format describing the entire objective
     */
    @Override
    public String toString() {
        String typeString;
        if (this instanceof EnemyKillObjective) {
            EnemyObjectiveType eot = (EnemyObjectiveType)this.getObjectiveTarget();
            typeString = eot.getType().toString();
        } else {
            typeString = this.targetType.toString();
        }
        return "Target Type: " + typeString + ", Value: " +
                this.targetValue + "; Description: " +
                this.description + "; " + "Met: " + this.isMet;

    }

    /**
     * Checks if two enemy kill objectives are equal. They will be equal if they have the
     * same target type in general.
     * @param object - an objective to be compared
     * @require obj != null
     * @ensure only equal if the descriptions, target values and type match.
     * @return whether or not objectives are equal
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Objective)) {
            return false;
        } else {
            Objective testObjective = (Objective) object;
            return (this.targetType).equals(testObjective.getObjectiveTarget());
        }
    }

    /**
     * Returns a hashcode representation of this objective, based on
     * what the objective is targeting.
     * @return 13 * hashCode() of objective's target type.
     */
    @Override
    public int hashCode() {
        return 13 * targetType.hashCode();
    }
}

package uq.deco2800.duxcom.items;

import uq.deco2800.duxcom.entities.heros.HeroStat;

//TODO: add the docs

/**
 * @author Ducksters Team 10
 *
 *         This StatisticModifier class is designed to easily enable EquippableItem of any type (any
 *         class that inherits EquippableItem) to change the statistics of the Hero. In other words
 *         an EquippableItems will have zero or more of these instances where each instance effects
 *         the Hero statistics.
 */
public class StatisticModifier {
    private HeroStat statistic;
    private StatisticModifyAction action;
    private double value;

    /**
     * @param statistic of the statistic to modify
     * @param action    to do on the statistic
     * @param value     input for the action
     */
    public StatisticModifier(HeroStat statistic, StatisticModifyAction action, double value) {
        this.statistic = statistic;
        this.action = action;
        this.value = value;
    }

    /**
     * returns the statistic this class modifies
     *
     * @return the statistic this class modifies
     */
    public HeroStat getStatistic() {
        return statistic;
    }

    /**
     * Gets the action (ENUM StatisticModifyAction)
     *
     * @return the action
     */
    public StatisticModifyAction getAction() {
        return action;
    }

    /**
     * Get the value
     *
     * @return the value of the action.
     */
    public double getValue() {
        return value;
    }

    /**
     * Evaluates if two statisticModifier have equivalent properties.
     *
     * @param other is the object to compare
     * @return true if equivalent else false
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof StatisticModifier)) {
            return false;
        }
        StatisticModifier statisticModifier = (StatisticModifier) other;
        if (this.statistic == statisticModifier.getStatistic()
                && this.action == statisticModifier.getAction()
                && (int) this.value == (int) statisticModifier.getValue()) {
            return true;
        }
        // else
        return false;
    }

    /**
     * @return a hash code unique to the StatisticModifier properties
     */
    @Override
    public int hashCode() {
        int hash = 65535;
        hash = hash * 17 + this.statistic.hashCode();
        hash = hash * 41 + this.action.hashCode();
        hash = (int) (hash * 127 + this.value);
        return hash;
    }
}

/*
 * Contact Group 10 - ducksters for detail
 * Ticket Issue: #12 - Pickup Items and Inventory
 */
package uq.deco2800.duxcom.items;

import uq.deco2800.duxcom.entities.heros.HeroStat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Equippable Item
 *
 * @author Team 10 = ducksters
 */
public abstract class EquippableItem extends Item {
    // EquippableItem properties

    private int durability;
    protected Set<StatisticModifier> statisticModifiers;
    protected RarityLevel rarity;

    /**
     * EquippableItem constructor
     *
     * @param name                of the item
     * @param cost                of the item
     * @param weight              of the item
     * @param inventorySpriteName is the resource location of the item
     * @param tradable            allows the item to be tradable
     * @param durability          is how tough it is
     */
    public EquippableItem(String name, int cost, int weight,
                          String inventorySpriteName, boolean tradable, int durability,
                          RarityLevel rarity, ItemType type) {
        super(name, cost, weight, inventorySpriteName, tradable, type);
        statisticModifiers = new HashSet<>();
        this.durability = durability;
        this.rarity = rarity;
    }

    /**
     * Returns the current balance for the player
     *
     * @return the balance of the player
     */
    public int getDurability() {
        return durability;
    }

    /**
     * Set the durability of this item
     *
     * @param durability the new durability for this item
     */
    public void setDurability(int durability) {
        this.durability = durability;
    }

    /**
     * Returns the rarity of the item
     *
     * @return the rarity of the item
     */
    public RarityLevel getRarity() {
        return this.rarity;
    }

    /**
     * Adds a statisticModifier to the item
     *
     * @param statisticModifier modifies statistics of the hero
     */
    public void addStatisticModifier(StatisticModifier statisticModifier) {
        this.statisticModifiers.add(statisticModifier);
    }

    /**
     * Modifies the hero statistics and returns the modified statistics.
     *
     * @param statistics to be modified
     */
    public Map<HeroStat, Double> getModifiedStatistics(Map<HeroStat, Double> statistics)
            throws StatisticActionException {
        Map<HeroStat, Double> modifiedStatistics = new HashMap<>();

        // Add modified stats to the modifiedStatistics Map
        for (StatisticModifier statisticModifier : this.statisticModifiers) {
            double statVal = statistics.get(statisticModifier.getStatistic());
            switch (statisticModifier.getAction()) {
                case ADD:
                    statVal += statisticModifier.getValue();
                    break;
                case SUBTRACT:
                    statVal -= statisticModifier.getValue();
                    break;
                case MULTIPLY:
                    statVal *= statisticModifier.getValue();
                    break;
                case DIVIDE:
                    statVal /= statisticModifier.getValue();
                    break;
                default:
                    throw new StatisticActionException();
            }
            modifiedStatistics.put(statisticModifier.getStatistic(), statVal);
        }

        // Add non-modified stats to the modifiedStatistics Map
        for (HeroStat heroStat : statistics.keySet()) {
            if (!modifiedStatistics.containsKey(heroStat)) {
                modifiedStatistics.put(heroStat, statistics.get(heroStat));
            }
            // else skip, its already in the map
        }

        return modifiedStatistics;
    }

    /**
     * Gives access to a copy of the collection of statistic modifiers
     *
     * @return a collection of statistic modifiers
     */
    public Set<StatisticModifier> getStatisticModifiers() {
        // create copy
        Set<StatisticModifier> copy = new HashSet<>();
        for (StatisticModifier sm : statisticModifiers) {
            copy.add(sm);
        }
        return copy; // give a copy of the immutable
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + durability;
        result = 31 * result + (statisticModifiers != null ? statisticModifiers.hashCode() : 0);
        result = result * 31 + this.name.hashCode();
        result = result * 31 + this.weight;
        result = result * 31 + this.cost;
        result = result * 31 + this.inventorySpriteName.hashCode();
        result = result * 31 + (this.tradable ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EquippableItem that = (EquippableItem) o;

        if (durability != that.durability) {
            return false;
        }
        if (cost != that.cost) {
            return false;
        }
        if (weight != that.weight) {
            return false;
        }
        if (tradable != that.tradable) {
            return false;
        }
        if (!name.equals(that.name)) {
            return false;
        }
        return (statisticModifiers != null ? statisticModifiers.equals(that.statisticModifiers) :
                that.statisticModifiers == null)
                && (inventorySpriteName != null ?
                inventorySpriteName.equals(that.inventorySpriteName) :
                that.inventorySpriteName == null);

    }
}

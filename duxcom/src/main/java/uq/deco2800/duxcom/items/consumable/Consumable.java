package uq.deco2800.duxcom.items.consumable;

import uq.deco2800.duxcom.entities.heros.HeroStat;
import uq.deco2800.duxcom.items.Item;
import uq.deco2800.duxcom.items.ItemType;
/**
 * Base Item
 *
 * @author Team 10 = ducksters
 */
public class Consumable extends Item {
    private static boolean tradable = true;

    public Consumable(String name, int cost, int weight, String inventorySpriteName, HeroStat stat,
                      int strength, ItemType it) {
        super(name, cost, weight, inventorySpriteName, tradable, it);
        // TODO Auto-generated constructor stub
    }

    /**
     * Returns the durations of the consumable effects. If 0 is returned then the items duration is
     * infinite
     *
     * @return durations of the consumable effect
     */
    public int getDuration() {
        return 0; // TODO: finish
    }

    // TODO: have method that applies a temporary effect on the player

}

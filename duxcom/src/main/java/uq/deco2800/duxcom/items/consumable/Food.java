package uq.deco2800.duxcom.items.consumable;

import uq.deco2800.duxcom.entities.heros.HeroStat;
import uq.deco2800.duxcom.items.ItemType;

/**
 * Food
 *
 * @author Team 10 = ducksters
 */
public class Food extends Consumable {
    public Food(String name, int cost, int weight, String inventorySpriteName, HeroStat stat,
                int strength) {
        super(name, cost, weight, inventorySpriteName, stat, strength, ItemType.FOOD);
    }
}

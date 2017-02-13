package uq.deco2800.duxcom.items.consumable;

import uq.deco2800.duxcom.entities.heros.HeroStat;
import uq.deco2800.duxcom.items.ItemType;

/**
 * Created by charl on 15/09/2016.
 */
public class Potion extends Consumable {

    private int duration;

    public Potion(String name, int cost, int weight, String inventorySpriteName, HeroStat stat,
                  int strength, int duration) {
        super(name, cost, weight, inventorySpriteName, stat, strength, ItemType.POTION);
        this.duration = duration;
    }
}

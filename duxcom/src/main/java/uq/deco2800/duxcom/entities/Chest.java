package uq.deco2800.duxcom.entities;

import uq.deco2800.duxcom.inventory.Inventory;
import uq.deco2800.duxcom.inventory.LootInventory;
import uq.deco2800.duxcom.loot.LootRarity;

/**
 * Created by Charlton Groves for team Ducksters
 *
 * A really simple chest entity. Every chest has its own inventory which is set in the map creation
 */
public class Chest extends PickableEntities {

    private Inventory inventory;

    public Chest(int x, int y) {
        super(EntityType.CHEST, x, y, 1,1);
    }
    public Inventory getInventory() {
        return inventory;
    }
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getImageName() {
        return "chest";
    }
}

/*
 * Contact Group 10 - ducksters for detail
 * Ticket Issue: #12 - Pickup Items and Inventory
 */

package uq.deco2800.duxcom.inventory;

import org.junit.Test;

import uq.deco2800.duxcom.items.*;
import uq.deco2800.duxcom.items.weapon.Axe;
import uq.deco2800.duxcom.items.weapon.Sword;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Equipable Item Test
 *
 * @author Team 10 = ducksters
 */
public class TransferInventoryTest {
    @Test
    public void transferringItemsTest() {
        Axe ironAxe = ItemGenerate.axe.BRONZE_AXE.generate();
        Sword ironSword = ItemGenerate.sword.BRONZE_SWORD.generate();
        HeroInventory duck1 = new HeroInventory(12);
        HeroInventory duck2 = new HeroInventory(12);
        duck1.addItem(ironAxe);
        duck1.addItem(ironSword);
        assertTrue(duck1.transferItem(duck2, ironAxe));
        assertTrue(duck2.containsItem(ironAxe));
        assertFalse(duck1.containsItem(ironAxe));
    }
    /* TODO: needs fixing
    @Test
    public void transferInventoryTest() {
        Inventory chest1 = new Inventory(20);
        Inventory chest2 = new Inventory(20);

        Weapon sword = new Weapon.Builder(ItemType.SWORD, "normal sword", new Sprite()).weight(110).cost(12).build();
        BuildingMaterial stick = new BuildingMaterial.Builder(ItemType.STICK, "rotten stick", new Sprite()).weight(140).cost(120).build();
        Weapon mace = new Weapon.Builder(ItemType.MACE, "gold mace", new Sprite()).weight(120).cost(102).build();
        Consumable food = new Consumable.Builder(ItemType.FOOD_BANANA, "yummy banana", new Sprite()).weight(120).cost(102).build();

        chest1.addItem(sword);
        chest1.addItem(stick);
        chest1.addItem(mace);
        chest2.addItem(food);

        chest1.transferItem(chest2, mace);

        assertTrue(chest2.containsItem(mace));
        assertFalse(chest1.containsItem(mace));
    }*/

}

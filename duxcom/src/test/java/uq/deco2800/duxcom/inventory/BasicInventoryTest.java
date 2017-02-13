/*
 * Contact Group 10 - ducksters for detail
 * Ticket Issue: #12 - Pickup Items and Inventory
 */

package uq.deco2800.duxcom.inventory;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

import uq.deco2800.duxcom.items.Item;
import uq.deco2800.duxcom.items.ItemGenerate;
import uq.deco2800.duxcom.items.weapon.Sword;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Equipable Item Test
 *
 * @author Team 10 = ducksters
 */


public class BasicInventoryTest {

    @Test
    public void initInventoryTest() {
        Inventory chest = new Inventory(20);
        assertEquals(chest.getItemCount(), 0);
        assertEquals(chest.getTotalCost(), 0);
        assertEquals(chest.getTotalWeight(), 0);
    }

    @Test
    public void InventoryMethodsTest() {
        Inventory chest = new Inventory(20);
        Map<Integer, Item> items = chest.getItems();
        assertEquals(chest.getInventorySize(), 20);
        Sword sword = ItemGenerate.sword.BRONZE_SWORD.generate();
        assertFalse(chest.addItem(sword, 25));
    }

   /* TODO: fix tests
    @Test
    public void addAndRemoveInventoryTest() {
        Inventory chest = new Inventory(20);

        int weight1 = 10;
        int weight2 = 5;
        int weight3 = 22;
        int value1 = 12;
        int value2 = 2;
        int value3 = 40;
        
        EquippableItem sword = new EquippableItemInstanceClass(ItemType.SWORD, "normal sword", 1, 15, 15, "here", true, SlotType.PRIMARY_WEAPON, 60);
        EquippableItem stick = new EquippableItemInstanceClass(ItemType.STICK, "rotten stick", 1, 34, 25, "there", true, SlotType.GENERAL, 256);
        EquippableItem mace = new EquippableItemInstanceClass(ItemType.MACE, "gold mace", 2, 346, 19, "everywhere", false, SlotType.PRIMARY_WEAPON, 25);

        assertTrue(chest.addItem(sword));
        assertTrue(chest.addItem(stick));
        assertTrue(chest.addItem(mace, 10));

        assertEquals(chest.getItemCount(), 3);
        assertEquals(chest.getTotalCost(), value1+value2+value3);
        assertEquals(chest.getTotalWeight(), weight1+weight2+weight3);

        assertTrue(chest.removeItem(mace));
        assertFalse(chest.addItem(mace, 30));

        assertEquals(chest.getItemCount(), 2);
        assertEquals(chest.getTotalCost(), value1+value2);
        assertEquals(chest.getTotalWeight(), weight1+weight2);
    }*/
}

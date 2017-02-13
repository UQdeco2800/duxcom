package uq.deco2800.duxcom.items;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ItemTest {

    // This class make it possible to test the abstract class Item.
    public static class ItemInstanceClass extends Item {
        public ItemInstanceClass(String name, int cost, int weight,
                                 String inventorySpriteName, boolean tradable, ItemType type) {
            super(name, cost, weight, inventorySpriteName, tradable, type);
        }
    }

    @Test
    public void initItemTest() {
        // Create a varying set of test properties

        String name1 = "Sword";
        int cost1 = 15;
        int weight1 = 10;
        String inventorySpriteName1 = "here";
        boolean tradable1 = true;
        ItemType type1 = ItemType.SWORD;

        Item item1 = new ItemInstanceClass(name1, cost1, weight1, inventorySpriteName1, tradable1, type1);
        Item item2 = new ItemInstanceClass(name1, cost1, 9, inventorySpriteName1, tradable1, type1);
        Item item3 = new ItemInstanceClass("Stick", cost1, weight1, inventorySpriteName1, tradable1, type1);
        // check if the item was constructed correctly
        assertEquals(name1, item1.getName());
        assertEquals(cost1, item1.getCost());
        assertEquals(weight1, item1.getWeight());
        assertEquals(inventorySpriteName1, item1.getInvetorySpriteName());
        assertEquals(tradable1, item1.isTradable());
        assertFalse(item1.equals(item2));
        assertFalse(item1.equals(item3));
        assertTrue(item1.getType().equals(ItemType.SWORD));
    }

    @Test
    public void equalsTest() {
        String name = "Sword";
        int cost = 15;
        int weight = 10;
        String inventorySpriteName = "here";
        boolean tradable = true;
        ItemType type = ItemType.SWORD;

        Item item1 = new ItemInstanceClass(name, cost, weight, inventorySpriteName, tradable, type);
        Item item2 = new ItemInstanceClass(name, cost, weight, inventorySpriteName, tradable, type);
        Item item3 = new ItemInstanceClass(name, cost, weight, inventorySpriteName, !tradable, type);

        // test equals method
        assertTrue(item1.equals(item2));
        assertFalse(item1.equals(item3));

        // test hashcode method
        assertTrue(item1.hashCode() == item2.hashCode());
        assertFalse(item1.hashCode() == item3.hashCode());
    }
}

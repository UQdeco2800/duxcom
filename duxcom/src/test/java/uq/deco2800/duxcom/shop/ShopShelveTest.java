/*
 * Contact Group 1 - the_magic_karps for detail
 * Ticket Issue: #11 - Player Economy and shop
 */
package uq.deco2800.duxcom.shop;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import uq.deco2800.duxcom.entities.heros.HeroStat;
import uq.deco2800.duxcom.items.Item;
import uq.deco2800.duxcom.items.ItemGenerate;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.items.consumable.Consumable;

/*
 * Contact Group 1 - the_magic_karps for detail
 * Ticket Issue: #11 - Player Economy and shop
 * @author the_magic_karps
 */
public class ShopShelveTest {

    @Test
    public void constructorTest() {
        ShopShelve shelve = new ShopShelve();
        shelve.addInitialItem();
        for (Item item : ItemGenerate.allItems()) {
            assertNotEquals(shelve.getAllItemsByType(item.getType()), null);
        }
    }

    @Test
    public void addTest() {
        ShopShelve shelve = new ShopShelve();
        shelve.addInitialItem();
        Item item1 = new Consumable("Potion", 200, 100, "somewhere", HeroStat.HEALTH, 10, ItemType.CONSUMABLE);
        assertTrue(shelve.add(item1.getType(), item1));
        assertEquals(shelve.getItem(item1.getType(), item1), item1);
        // If product already in
        Item existItem = shelve.getAllItemsByType(ItemType.AXE.name()).get(0);
        assertFalse(shelve.add(existItem.getType(), existItem));
    }

    @Test
    public void getShelfNamesTest() {
    	ShopShelve shelve = new ShopShelve();
    	Item item1 = new Consumable("Potion", 200, 100, "somewhere", HeroStat.HEALTH, 10, ItemType.CONSUMABLE);
    	shelve.add(item1.getType(), item1);
    	List<String> expected = new ArrayList<>();
    	expected.add("CONSUMABLE");
    	List<String> actual = shelve.getShelfNames();
    	assertEquals(expected, actual);
    }

    @Test
    public void removeTest() {
        ShopShelve shelve = new ShopShelve();
        shelve.addInitialItem();
        Item existShield = shelve.getAllItemsByType(ItemType.SHIELD.name()).get(0);
        assertTrue(shelve.remove(existShield.getType(), existShield));
        // If product don't exist
        Item item1 = new Consumable("Potion", 200, 100, "somewhere", HeroStat.HEALTH, 10, ItemType.CONSUMABLE);
        assertFalse(shelve.remove(item1.getType(), item1));
    }
    
    @Test
    public void getItemTest() {
        ShopShelve shelve = new ShopShelve();
        shelve.addInitialItem();
        Item existShield = ItemGenerate.shield.STEEL_SHIELD.generate();
        assertEquals(shelve.getItem(null, null), null);
        assertEquals(shelve.getItem(null, existShield), null);
        assertEquals(shelve.getItem(existShield.getType(), null), null);
    }

    @Test
    public void getAllItemTest() {
        ShopShelve shelve = new ShopShelve();
        shelve.addInitialItem();
        // String item type parameter
        List<Item> itemList = shelve.getAllItemsByType(ItemType.SHIELD);
        for (int i = 0; i < itemList.size(); i++) {
            assertNotNull(shelve.getItem(itemList.get(i).getType(), itemList.get(i)));
        }
        // ItemType type as parameter
        List<Item> itemListName = shelve.getAllItemsByType(ItemType.SHIELD.name());
        for (int i = 0; i < itemListName.size(); i++) {
            assertNotNull(shelve.getItem(itemListName.get(i).getType(), itemListName.get(i)));
        }
    }


}

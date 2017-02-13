/*
 * Contact Group 1 - the_magic_karps for detail
 * Ticket Issue: #11 - Player Economy and shop
 */
package uq.deco2800.duxcom.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uq.deco2800.duxcom.items.Item;
import uq.deco2800.duxcom.items.ItemGenerate;
import uq.deco2800.duxcom.items.ItemType;

/**
 * Shop Shelve for regulating products in the shop
 *
 * @author Group 1 = the_magic_karps
 */
public class ShopShelve {

    // Shelve to store products(items) by category 
    private HashMap<String, List<Item>> shelve;

    /**
     * Construct ShopShelve and adding initial items into the shop
     */
    public ShopShelve() {
        shelve = new HashMap<>();
        for (ItemType type : ItemType.values()) {
            shelve.put(type.name(), new ArrayList<>());
        }
    }

    /**
     * Returns a list of the names of each shelf as a string with the
     * underscores replaced with spaces.
     * 
     * @return the list of shelf names
     */
    public List<String> getShelfNames() {
    	ArrayList<String> shelfNames = new ArrayList<>();
        for (String shelfName : shelve.keySet()) {
            if (!shelve.get(shelfName).isEmpty()) {
                shelfName = shelfName.replaceAll("_", " ");
                shelfNames.add(shelfName);
            }
        }
    	return shelfNames;
    }

    /**
     * Adds all the items onto the shelve that will be in the shop in on new
     * shelve instance
     */
    public void addInitialItem() {
        for (Item item : ItemGenerate.allItems()) {
            shelve.get(item.getType().name()).add(item);
        }
    }

    /**
     * Add a product to the shop's shelve
     *
     * @param type the type of item the product belongs to
     * @param item the product to be added onto the shelve
     * @return true if successfully added onto shelve, false if failed to add
     * onto shelve
     */
    public boolean add(ItemType type, Item item) {
    	if (!shelve.containsKey(type.name())){
    		shelve.put(type.name(), new ArrayList<>());
    	}
        List<Item> shelveList = shelve.get(type.name());
        for (Item existItem : shelveList) {
            if (existItem.equals(item)) {
                return false;
            }
        }
        shelveList.add(item);
        return true;
    }

    /**
     * Removes a product from the shop's shelve
     *
     * @param type the type of item the product belongs to
     * @param item the product to be removed from the shelve
     * @return true if successfully removed from shelve, false if failed to
     * remove from shelve
     */
    public boolean remove(ItemType type, Item item) {
    	if (!shelve.containsKey(type.name())){
    		return false;
    	}
        List<Item> shelveList = shelve.get(type.name());
        for (Item existItem : shelveList) {
            if (existItem.equals(item)) {
                shelveList.remove(existItem);
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieve the item that is stored on the shop's shelve
     *
     * @param type the type of item the item belongs to
     * @param item the item to be retrieved from the shelve
     * @return the item to be retrieved from the shop if exist, else null
     */
    public Item getItem(ItemType type, Item item) {
        if (type != null && item != null) {
            for (Item shopItem : shelve.get(type.name())) {
                if (shopItem.equals(item)) {
                    return shopItem;
                }
            }
        }
        return null;
    }

    /**
     * Retrieves all the item of the type that is stored on the shop's shelve
     * using ItemType
     *
     * @param type the item type of the products to be retrieved
     * @return An arraylist of items belonging to the type
     */
    public List<Item> getAllItemsByType(ItemType type) {
        return this.getAllItemsByType(type.name());
    }

    /**
     * Retrieves all the item of the type that is stored on the shop's shelve
     * using ItemType
     *
     * @param type the item type of the products to be retrieved by string
     * @return An arraylist of items belonging to the type
     */
    public List<Item> getAllItemsByType(String type) {
        List<Item> itemList = new ArrayList<>();
        for (Item item : shelve.get(type)) {
            itemList.add(item);
        }
        return itemList;//weak invariant
    }
}

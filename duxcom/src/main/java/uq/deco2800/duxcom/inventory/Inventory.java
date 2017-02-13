/*
 * Contact Group 10 - ducksters for detail
 * Ticket Issue: #12 - Pickup Items and Inventory
 */

package uq.deco2800.duxcom.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import uq.deco2800.duxcom.items.Item;

/**
 * Base Item
 *
 * @author Team 10 = ducksters
 */
public class Inventory {

    private static Logger logger = LoggerFactory.getLogger(Inventory.class);

    //HashMap of slotNumber and the Item that is inside that slot
    protected HashMap<Integer, Item> items;

    //HashMap of slotNumber and the amount of items in that slot
    protected HashMap<Integer, Integer> stackSize;

    // How many slots there are in the inventory
    protected int inventorySize;

    /**
     * All the items currently in the inventory
     *
     * @return the items in the inventory
     */
    public Map<Integer, Item> getItems() {
        return items;
    }

    /**
     *
     * @return
     */
    public int getInventorySize() {
        return inventorySize;
    }

    /**
     * Contructor for creating a new inventory
     *
     * @param inventorySize the amount of slots this inventory has
     */
    public Inventory(int inventorySize) {
        this.inventorySize = inventorySize;
        this.items = new HashMap<>();
        this.stackSize = new HashMap<>();
        for (int i = 0; i < inventorySize; i++) {
            items.put(i + 1, null);
            stackSize.put(i + 1, 0);
        }
    }

    /**
     * Adds an item to the inventory
     *
     * @param item the item to be added
     * @return true if the item was successfully added, false if not.
     */
    public boolean addItem(Item item) {
        /* Iterate over all items. If a free slot is found,
            add item to that slot
         */
        for (HashMap.Entry<Integer, Item> slot : items.entrySet()) {
            if (slot.getValue() == null) {
                items.put(slot.getKey(), item);
                stackSize.put(slot.getKey(), 1);
                return true;
            }
        }
        // If there is no room for the item, return false
        return false;
    }

    /**
     * extended functionality for addItem. Adds item to a specified slot
     *
     * @param item       the item to be added
     * @param slotNumber the slot which the item is to be added to.
     * @return true if the item was successfully added, false if not.
     */
    public boolean addItem(Item item, Integer slotNumber) {

        // Make sure that the slotNumber is valid
        if (!withinBounds(slotNumber)) {
            return false;
        }
        if (items.get(slotNumber) == null) {
            items.put(slotNumber, item);
            stackSize.put(slotNumber, 1);
            return true;
        }
        // If there is no room for the item, return false
        return false;
    }

    /**
     * Removes Item from the inventory
     *
     * @param item the item to be removed
     * @return true if the item was successfully removed, false if not.
     */
    public boolean removeItem(Item item) {
        //Find the item in the inventory and remove it
        for (HashMap.Entry<Integer, Item> slot : items.entrySet()) {
            if (slot.getValue() != null && slot.getValue() == item) {
                items.put(slot.getKey(), null);
                stackSize.put(slot.getKey(), 0);
                return true;
            }
        }
        // If the item cannot be found in the inventory, return false
        return false;
    }

    public boolean removeItem(int slotNum) {
        //Find the item in the inventory and remove it
        if (checkIfItemInSlot(slotNum)) {
            items.put(slotNum, null);
            stackSize.put(slotNum, 0);
        }
        // If the item cannot be found in the inventory, return false
        return false;
    }

    public void moveItem(int sourceSlotNum, int destinationSlotNum) {
        Item sourceItem = getItemFromSlot(sourceSlotNum);
        Item destinationItem = getItemFromSlot(destinationSlotNum);
        items.put(destinationSlotNum, sourceItem);
        items.put(sourceSlotNum, destinationItem);
    }

    /**
     * Adds up the total weight of the items in the inventory. Returns that weight
     *
     * @return positive integer if items are present in the inventory, 0 if no items are found
     */
    public int getTotalWeight() {

        int totalWeight = 0;
        for (HashMap.Entry<Integer, Item> slot : items.entrySet()) {
            if (slot.getValue() != null) {
                totalWeight += slot.getValue().getWeight()
                        * stackSize.get(slot.getKey());
            }
        }
        return totalWeight;
    }

    /**
     * Adds up the total value of the items in the inventory. Returns that value
     *
     * @return positive integer if items are present in the inventory, 0 if no items are found
     */
    public int getTotalCost() {
        int totalValue = 0;
        for (HashMap.Entry<Integer, Item> slot : items.entrySet()) {
            if (slot.getValue() != null) {
                totalValue += slot.getValue().getCost()
                        * stackSize.get(slot.getKey());
            }
        }
        return totalValue;
    }

    /**
     * Checks to see if the inventory contains a specified item
     *
     * @param destinationInventory the inventory that the item will be moved to
     * @param itemToTransfer       the item to be transfered
     * @return true if the item was transfered successfully, false if the item could not be
     * transferred.
     */
    public boolean transferItem(Inventory destinationInventory, Item
            itemToTransfer) {
        if (this.containsItem(itemToTransfer)) {
            if (destinationInventory.addItem(itemToTransfer)) {
                this.removeItem(itemToTransfer);
                return true;
            }
        }
        return false;
    }

    /**
     * adds all items from one inventory to another
     *
     * @param destinationInventory the inventory that the item will be moved to
     * @return true if all the items were transfered successfully, false if the item could not be
     * transferred.
     */
    public boolean transferAllItems(Inventory destinationInventory) {
        for (HashMap.Entry<Integer, Item> slot : items.entrySet()) {
            Item item = slot.getValue();
            if (item != null) {
                /* if there is no room for the item, stop transferring */
                if (!destinationInventory.addItem(item)) {
                    return false;
                }
                this.removeItem(item);
            }
        }
        return true;
    }

    /**
     * Checks to see if the inventory contains a specified item
     *
     * @param item the item that the inventory may contain
     * @return true if the item is in the inventory, false if not
     */
    public boolean containsItem(Item item) {
        for (HashMap.Entry<Integer, Item> slot : items.entrySet()) {
            if (slot.getValue() != null && slot.getValue() == item) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfItemInSlot(int slotNumber) {
        if (!withinBounds(slotNumber)) {
            return true;
        }
        if (items.get(slotNumber) == null) {
            return false;
        }
        return true;
    }

    public Item getItemFromSlot(int slotNumber) {
        try {
            return items.get(slotNumber);
        } catch (Exception exception) {
            logger.error("Item not in slot. Remember to run checkIfItemInSlot() first", exception);
        }
        return null;
    }

    private boolean withinBounds(int slotNumber) {
        return !(slotNumber < 1 || slotNumber > this.inventorySize);
    }

    /**
     * Adds up the total total amount of items in the inventory
     *
     * @return positive integer if items are present in the inventory, 0 if no items are found
     */
    public int getItemCount() {
        int itemCount = 0;
        for (HashMap.Entry<Integer, Item> slot : items.entrySet()) {
            if (slot.getValue() != null) {
                itemCount += stackSize.get(slot.getKey());
            }
        }
        return itemCount;
    }

    /**
     * Check if inventory is empty
     */
    public boolean isEmpty() {
        for (Item item : items.values()) {
            if (item != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get remaining space of inventory
     */
    public int getSpace() {
        int count = 0;
        for (Item item : items.values()) {
            if (item == null) {
                count++;
            }
        }
        return count;
    }
    //TODO getAllItems
}

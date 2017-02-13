/*
 * Contact Group 1 - the_magic_karps for detail
 * Ticket Issue: #11 - Player Economy and shop
 */
package uq.deco2800.duxcom.shop;

import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.items.Item;
import uq.deco2800.duxcom.inventory.HeroInventory;

/**
 * Shop Manager for regulating shops
 *
 * @author Group 1 = the_magic_karps
 */
public class ShopManager {

    // Stores Items on the shelve
    private final ShopShelve shopShelves;
    
    private GameManager gameManager;

    /**
     * Constructor to ShopManager
     * Creates the instance of ShopShelve and add initial items onto the shelve
     */
    public ShopManager(GameManager gameManager) {
        this.gameManager = gameManager;
        shopShelves = new ShopShelve();
        shopShelves.addInitialItem();
    }

    /**
     * Retrieves the location of the shelves that's used to hold the products
     * for the shop
     *
     * @return The location of the shelves of the shop that holds the products
     */
    public ShopShelve getShelves() {
        return shopShelves;
    }

    /**
     * Make purchase to a selected item removes money from wallet
     *
     * @param inventory the location of the hero's inventory
     * @param item the item to be purchased
     * @return true of success, false on failure to buy item
     */
    public boolean buyItem(HeroInventory inventory, Item item) {
        if (item != null && inventory != null) {
            Item shopItem = shopShelves.getItem(item.getType(), item);
            if (shopItem != null
                    && gameManager.getGameWallet().getBalance() >= shopItem.getCost()) {
                inventory.addItem(shopItem);
                gameManager.getGameWallet().addBalance(-shopItem.getCost());
                return true;
            }
        }
        return false;
    }

    /**
     * Sell the Item to the shop in return for currency in the wallet
     *
     * @param inventory the location of the hero's inventory
     * @param item the item to be sold
     * @return true of successfully sell item, false on failure to sell
     */
    public boolean sellItem(HeroInventory inventory, Item item) {
        if (item != null && item.isTradable()) {
            int sellPrice = (int) (item.getCost() * 0.75);
            inventory.removeItem(item);
            gameManager.getGameWallet().addBalance(sellPrice);
            return true;
        }
        return false;
    }
}

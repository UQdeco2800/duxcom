package uq.deco2800.duxcom.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import uq.deco2800.duxcom.entities.Chest;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.controllers.InventoryController;
import uq.deco2800.duxcom.loot.LootRarity;

import static java.awt.geom.Point2D.distance;

/**
 * A class that manages all the chests on the map.
 *
 * Created by Ducksters
 */
public class ChestManager {

    /**
     * Array of all the chests on the map
     */
    private final ArrayList<Chest> chests;
    // Logger
    private static Logger logger = LoggerFactory.getLogger(InventoryController.class);
    private static ChestManager currentInstance = null;
    /**
     * Loot Range Permitted
     */
    private static final int LOOT_RANGE = 1;

    public ChestManager() {
        chests = new ArrayList<>();
        currentInstance = this;
    }

    /**
     * Get the current instance of the HeroPopUpController.
     *
     * @return the current instance of the HeroPopUpController
     */
    public static ChestManager getChestManager() {
        return currentInstance;
    }

    /**
     * Retrieves a list of dead enemies in loot area an return a list of it, given the hero entity
     *
     * @return a list of dead enemies within loot range
     */
    public Inventory getChestInvInHeroArea(AbstractHero hero) {
        logger.debug("Finding chest in local area");
        return getChestInLootArea(hero.getX(), hero.getY());
    }

    /**
     * Adds another chest to this manager
     *
     * @param chest a chest to be added
     */
    public void addChest(Chest chest) {
        LootInventory loot = new LootInventory(16);
        loot.addLoot(LootRarity.COMMON);
        chest.setInventory(loot);
        chests.add(chest);
    }

    /**
     * Check if any dead enemies are within loot area, and return all dead enemies that are within
     * loot area
     *
     * @param xHero x coordinate of the current hero
     * @param yHero y coordinate of the current hero
     * @return a list of dead enemies within loot range
     */
    private Inventory getChestInLootArea(int xHero, int yHero) {
        for (Chest chest : chests) {
            double distance
                    = distance(xHero, yHero, chest.getX(), chest.getY());
            if ((int) distance <= LOOT_RANGE) {
                logger.debug("FOUND A CHEST");
                return chest.getInventory();
            }
        }
        logger.debug("Could Not find a chest");
        return null;
    }

    /**
     * Retrieve the maximum allowed loot range
     *
     * @return the loot range
     */
    public int getLootRange() {
        return LOOT_RANGE;
    }

    public ArrayList<Chest> getChests() {
        return chests;
    }
}

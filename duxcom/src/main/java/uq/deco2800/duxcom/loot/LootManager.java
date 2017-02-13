package uq.deco2800.duxcom.loot;

import static java.awt.geom.Point2D.distance;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.entities.PickableEntities;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyBear;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyDarkMage;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyKnight;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemySupport;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.inventory.LootInventory;
import uq.deco2800.duxcom.items.Item;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

/**
 * Loot Manager for the loot system
 *
 * @author The_Magic_Karps
 */
public class LootManager {

    /**
     * HashMaps that stores all the loot inventories for each dead enemy
     */
    private final HashMap<PickableEntities, LootInventory> loots;

    /**
     * Loot Range Permitted
     */
    private static final int LOOT_RANGE = 1;

    /**
     * The MapAssembly
     */
    private final MapAssembly map;

    /**
     * Constructor of LootManager
     *
     * @param mapAssembly mapAssembly of the current game
     */
    public LootManager(MapAssembly mapAssembly) {
        loots = new HashMap<>();
        map = mapAssembly;
    }

    /**
     * Create a loot inventory of randomly 2 - 5 size. Generate items base on
     * loot rarity and append the loot inventory into loots hashmap
     *
     * @param deadEnemy the dead enemy entity
     * @param rarity the rarity of the loot to be generated
     */
    public void makeLoot(PickableEntities deadEnemy, LootRarity rarity) {
        LootInventory lootInventory = new LootInventory(ThreadLocalRandom
                .current().nextInt(2, 5));
        lootInventory.addLoot(rarity);
        loots.put(deadEnemy, lootInventory);
    }

    /**
     * Retrieve the loot inventory of the dead enemy entity.
     *
     * @param deadEnemy the dead enemy entity
     * @return loot inventory of the dead enemy entity
     */
    public LootInventory getLoot(PickableEntities deadEnemy) {
        return loots.get(deadEnemy);
    }

    /**
     * Take loot from the dead enemy entity and add it into the hero's inventory
     *
     * Deletes the item when successfully transfered to hero inventory.
     *
     * @param deadEnemy the dead enemy entity
     * @return true if all loot inventory added, false if hero inventory full
     */
    public boolean takeAllLoot(PickableEntities deadEnemy) {
        LootInventory lootInventory = loots.get(deadEnemy);
        takeCoins(lootInventory);
        for (Item item : lootInventory.getItems().values()) {
            if (map.getCurrentTurnHero().getInventory().addItem(item)) {
                lootInventory.removeItem(item);
            } else {
                return false;
            }
        }
        loots.remove(deadEnemy);
        return true;
    }

    /**
     * Takes an item from loot inventory and place it into hero inventory
     *
     * @param deadEnemy the dead enemy entity
     * @param item the item to be taken from loot inventory
     * @return true if success, false if no success
     */
    public boolean takeItemFromLoot(PickableEntities deadEnemy, Item item) {
        LootInventory lootInventory = loots.get(deadEnemy);
        if (lootInventory.containsItem(item)
                && map.getCurrentTurnHero().getInventory().addItem(item)) {
            lootInventory.removeItem(item);
            if (lootInventory.isEmpty() && lootInventory
                    .getCoinBalance() <= 0) {
                loots.remove(deadEnemy);
            }
            return true;
        }
        return false;
    }

    /**
     * Takes an item from loot inventory and place it into hero inventory with
     * only a reference to item
     *
     * @param item the item to be taken from loot inventory
     * @return true if success, false if no success
     */
    public boolean takeItemFromLoot(Item item) {
        for (LootInventory lootInventory : loots.values()) {
            if (lootInventory.containsItem(item)
                    && map.getCurrentTurnHero().getInventory().addItem(item)) {
                lootInventory.removeItem(item);
                if (lootInventory.isEmpty() && lootInventory
                        .getCoinBalance() <= 0) {
                    loots.values().remove(lootInventory);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Discards an item from loot inventory
     *
     * @param item item to be discarded
     */
    public void discardItem(Item item) {
        for (LootInventory lootInventory : loots.values()) {
            if (lootInventory.containsItem(item)) {
                lootInventory.removeItem(item);
                if (lootInventory.isEmpty() && lootInventory
                        .getCoinBalance() <= 0) {
                    loots.values().remove(lootInventory);
                }
            }
        }
    }

    /**
     * Discards an item from loot inventory given entity
     *
     * @param deadEnemy the dead enemy entity
     * @param item item to be discarded
     */
    public void discardItem(PickableEntities deadEnemy, Item item) {
        LootInventory lootInventory = loots.get(deadEnemy);
        if (lootInventory.containsItem(item)) {
            lootInventory.removeItem(item);
            if (lootInventory.isEmpty() && lootInventory
                    .getCoinBalance() <= 0) {
                loots.remove(deadEnemy);
            }
        }
    }

    /**
     * Retrieves a list of dead enemies in loot area and return a list of it,
     * given x and y coordinates
     *
     * @param xHero x coordinate of the current hero
     * @param yHero y coordinate of the current hero
     * @return a list of dead enemies within loot range
     */
    public List<PickableEntities> getInvInLootArea(int xHero, int yHero) {
        return getDeadInLootArea(xHero, yHero);
    }

    /**
     * Retrieves a list of dead enemies in loot area an return a list of it,
     * given the hero entity
     *
     * @param hero
     * @return a list of dead enemies within loot range
     */
    public List<PickableEntities> getInvInLootArea(AbstractHero hero) {
        return getDeadInLootArea(hero.getX(), hero.getY());
    }

    /**
     * Check if any dead enemies are within loot area, and return all dead
     * enemies that are within loot area
     *
     * @param xHero x coordinate of the current hero
     * @param yHero y coordinate of the current hero
     * @return a list of dead enemies within loot range
     */
    private List<PickableEntities> getDeadInLootArea(int xHero, int yHero) {
        List<PickableEntities> lootList = new ArrayList<>();
        for (PickableEntities deathMagma : loots.keySet()) {
            double distance
                    = distance(xHero, yHero, deathMagma.getX(), deathMagma.getY());
            if ((int) distance <= LOOT_RANGE) {
                lootList.add(deathMagma);
            }
        }
        return lootList;
    }

    /**
     * Retrieve the maximum allowed loot range
     *
     * @return the loot range
     */
    public int getLootRange() {
        return LOOT_RANGE;
    }

    /**
     * Retrieves all the unlooted entities
     *
     * @return list of unlooted entities
     */
    public List<PickableEntities> getAllUnlooted() {
        List<PickableEntities> unlooted = new ArrayList<>();
        for (PickableEntities enemy : loots.keySet()) {
            unlooted.add(enemy);
        }
        return unlooted;
    }

    /**
     * Defines the loot rarity of specific enemies
     *
     * @param enemy enemy's loot rarity to be set
     * @return loot rarity of the enemy
     */
    public LootRarity getDefinedRarity(AbstractEnemy enemy) {
        if (enemy instanceof EnemyBear) {
            return LootRarity.RANDOM_COMMON_RARE;
        } else if (enemy instanceof EnemyDarkMage) {
            return LootRarity.RARE;
        } else if (enemy instanceof EnemyKnight) {
            return LootRarity.RARE;
        } else if (enemy instanceof EnemySupport) {
            return LootRarity.RARE;
        }
        return LootRarity.COMMON;
    }

    public void takeCoins(LootInventory lootInventory) {
        GameLoop.getCurrentGameManager().getGameWallet().addBalance(
                lootInventory.getCoinBalance());
        lootInventory.addCoin(-lootInventory.getCoinBalance());
        if (lootInventory.isEmpty()) {
            loots.values().remove(lootInventory);
        }
    }

    public void discardCoins(LootInventory lootInventory) {
        lootInventory.addCoin(-lootInventory.getCoinBalance());
        if (lootInventory.isEmpty()) {
            loots.values().remove(lootInventory);
        }
    }
}

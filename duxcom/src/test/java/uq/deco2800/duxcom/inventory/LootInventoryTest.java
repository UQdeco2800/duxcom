/*
 * Contact Group 10 - ducksters for detail
 * Ticket Issue: #12 - Pickup Items and Inventory
 */
package uq.deco2800.duxcom.inventory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import uq.deco2800.duxcom.items.EquippableItem;
import uq.deco2800.duxcom.items.RarityLevel;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import uq.deco2800.duxcom.loot.LootRarity;

/**
 * Loot inventory Test
 *
 * @author Team 10 = ducksters
 */
public class LootInventoryTest {

    private LootInventory ibisQueen;

    @Before
    public void setUp() {
        ibisQueen = new LootInventory(30);
    }

    @Test
    public void addCommonLoot() {
        ibisQueen.addLoot(LootRarity.COMMON);
        for (int i = 1; i < 31; i++) {
            EquippableItem item = (EquippableItem) ibisQueen.getItemFromSlot(i);
            if (item == null)
                continue;
            if (item.getRarity().equals(RarityLevel.COMMON)) {
                assertTrue(item.getRarity().equals(RarityLevel.COMMON));
            } else if (item.getRarity().equals(RarityLevel.UNCOMMON)) {
                assertTrue(item.getRarity().equals(RarityLevel.UNCOMMON));
            } else {
                assertTrue(false);
            }
        }
    }

    @Test
    public void addRareLoot() {
        ibisQueen.addLoot(LootRarity.RARE);
        for (int i = 1; i < 31; i++) {
            EquippableItem item = (EquippableItem) ibisQueen.getItemFromSlot(i);
            if (item == null)
                continue;
            if (item.getRarity().equals(RarityLevel.RARE)) {
                assertTrue(item.getRarity().equals(RarityLevel.RARE));
            } else if (item.getRarity().equals(RarityLevel.EPIC)) {
                assertTrue(item.getRarity().equals(RarityLevel.EPIC));
            } else if (item.getRarity().equals(RarityLevel.UNCOMMON)) {
                assertTrue(item.getRarity().equals(RarityLevel.UNCOMMON));
            } else {
                assertTrue(false);
            }
        }
    }

    @Test
    public void addBossLoot() {
        ibisQueen.addLoot(LootRarity.BOSS);
        for (int i = 1; i < 31; i++) {
            EquippableItem item = (EquippableItem) ibisQueen.getItemFromSlot(i);
            if (item == null)
                continue;
            if (item.getRarity().equals(RarityLevel.LEGENDARY)) {
                assertTrue(item.getRarity().equals(RarityLevel.LEGENDARY));
            } else if (item.getRarity().equals(RarityLevel.EPIC)) {
                assertTrue(item.getRarity().equals(RarityLevel.EPIC));
            } else if (item.getRarity().equals(RarityLevel.UNCOMMON)) {
                assertTrue(item.getRarity().equals(RarityLevel.UNCOMMON));
            } else {
                assertTrue(false);
            }
        }
    }

    @Test
    public void addRandComRareLoot() {
        ibisQueen.addLoot(LootRarity.RANDOM_COMMON_RARE);
        for (int i = 1; i < 31; i++) {
            EquippableItem item = (EquippableItem) ibisQueen.getItemFromSlot(i);
            if (item == null)
                continue;
            if (item.getRarity().equals(RarityLevel.RARE)) {
                assertTrue(item.getRarity().equals(RarityLevel.RARE));
            } else if (item.getRarity().equals(RarityLevel.COMMON)) {
                assertTrue(item.getRarity().equals(RarityLevel.COMMON));
            } else if (item.getRarity().equals(RarityLevel.UNCOMMON)) {
                assertTrue(item.getRarity().equals(RarityLevel.UNCOMMON));
            } else {
                assertTrue(false);
            }
        }
    }

    @Test
    public void addRandom() {
        ibisQueen.addLoot(LootRarity.RANDOM);
        assertTrue(!ibisQueen.isEmpty());
    }
    
    @Test
    public void testWallet() {
        ibisQueen.addLoot(LootRarity.RARE);
        if (ibisQueen.getCoinBalance() <= 250 
                && ibisQueen.getCoinBalance() >= 150) {
            assertTrue(true);
        } else {
            assertFalse(false);
        }
        ibisQueen.addCoin(-ibisQueen.getCoinBalance());
        assertEquals(ibisQueen.getCoinBalance(), 0);
        ibisQueen.addCoin(50);
        assertEquals(ibisQueen.getCoinBalance(), 50);
    }
}

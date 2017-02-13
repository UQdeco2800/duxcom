package uq.deco2800.duxcom.inventory;

import java.util.concurrent.ThreadLocalRandom;

import uq.deco2800.duxcom.items.ItemGenerate;
import uq.deco2800.duxcom.items.RarityLevel;
import uq.deco2800.duxcom.loot.LootRarity;
import uq.deco2800.duxcom.loot.LootWallet;

/**
 * Created by User on 11-Sep-16. -> Taken over and redesigned it by The_Magic_Karps
 */
public class LootInventory extends Inventory {

    /**
     * LootWallet that holds the money
     */
    private final LootWallet lootWallet;

    /**
     * Constructor of LootInventory
     *
     * @param inventorySize size of the loot inventory
     */
    public LootInventory(int inventorySize) {
        super(inventorySize);
        lootWallet = new LootWallet();
    }

    /**
     * AddLoot into loot inventory by its rarity types
     *
     * @param rarity the rarity of the loot
     */
    public void addLoot(LootRarity rarity) {
        switch (rarity) {
            case COMMON:
                makeCommonLoot();
                break;
            case RARE:
                makeRareLoot();
                break;
            case BOSS:
                makeBossLoot();
                break;
            case RANDOM_COMMON_RARE:
                makeComRareLoot();
                break;
            case RANDOM:
                makeRandom();
                break;
            default:
                break;
        }
    }

    /**
     * Make Common/Uncommon items, add coin balance and put into loot inventory
     */
    private void makeCommonLoot() {
        for (int i = 1; i < inventorySize + 1; i++) {
            int randNum = randomNum(0, 3);
            if (randNum == 0) {
                generate(RarityLevel.UNCOMMON, i);
            } else {
                generate(RarityLevel.COMMON, i);
            }
        }
        addCoin(randomNum(50, 150));
    }

    /**
     * Make Epic/Rare/Common items, add coin balance and put into loot inventory
     */
    private void makeRareLoot() {
        for (int i = 1; i < inventorySize + 1; i++) {
            int randNum = randomNum(0, 8);
            if (randNum == 0) {
                generate(RarityLevel.EPIC, i);
            } else if (randNum == 1 || randNum == 2) {
                generate(RarityLevel.RARE, i);
            } else {
                generate(RarityLevel.UNCOMMON, i);
            }
        }
        addCoin(randomNum(150, 250));
    }

    /**
     * Gives a legendary, chance for legendary, epic and uncommon, add coin and put into loot
     * inventory
     */
    private void makeBossLoot() {
        generate(RarityLevel.LEGENDARY, 0);
        for (int i = 1; i < inventorySize + 1; i++) {
            int randNum = randomNum(0, 10);
            if (randNum == 0) {
                generate(RarityLevel.LEGENDARY, i);
            } else if (randNum >= 1 && randNum <= 5) {
                generate(RarityLevel.EPIC, i);
            } else {
                generate(RarityLevel.UNCOMMON, i);
            }
        }
        addCoin(randomNum(450, 999));
    }

    /**
     * Make Common/Uncommon/Rare items, add coin balance and put into loot inventory
     */
    private void makeComRareLoot() {
        for (int i = 1; i < inventorySize + 1; i++) {
            int randNum = randomNum(0, 8);
            if (randNum >= 0 && randNum <= 2) {
                generate(RarityLevel.COMMON, i);
            } else if (randNum > 2 && randNum <= 5) {
                generate(RarityLevel.UNCOMMON, i);
            } else if (randNum > 5 && randNum <= 8) {
                generate(RarityLevel.RARE, i);
            }
        }
        addCoin(randomNum(50, 350));
    }

    /**
     * Make random rarity items, add coin balance and put into loot inventory
     */
    private void makeRandom() {
        for (int i = 1; i < inventorySize + 1; i++) {
            int randNum = randomNum(0, 20);
            if (randNum == 0) {
                generate(RarityLevel.LEGENDARY, i);
            } else if (randNum > 0 && randNum <= 2) {
                generate(RarityLevel.EPIC, i);
            } else if (randNum > 2 && randNum <= 6) {
                generate(RarityLevel.RARE, i);
            } else if (randNum > 6 && randNum <= 12) {
                generate(RarityLevel.UNCOMMON, i);
            } else {
                generate(RarityLevel.COMMON, i);
            }
        }
        addCoin(randomNum(50, 600));
    }

    /**
     * Generate the item based on its rarity and put it into loot inventory
     *
     * @param rarity  rarity of the item to be generated
     * @param slotNum the slot number of the loot inventory to be put into
     */
    private void generate(RarityLevel rarity, int slotNum) {
        if (!(this.checkIfItemInSlot(slotNum))) {
            ItemGenerate.loot(rarity, this, slotNum);
        }
    }

    /**
     * Add Coins into the loot wallet
     *
     * @param amount number of coins to be added
     */
    public void addCoin(int amount) {
        lootWallet.addBalance(amount);
    }

    /**
     * Retrieves the coin balance of loot wallet
     *
     * @return the coin balance of loot wallet
     */
    public int getCoinBalance() {
        return lootWallet.getBalance();
    }

    /**
     * Helper method to generate a random number within range of min and max
     *
     * @param min minimum integer to generate
     * @param max maximum integer to generate
     * @return random generated integer in min and max range
     */
    private int randomNum(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}

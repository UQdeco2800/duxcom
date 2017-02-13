package uq.deco2800.duxcom.loot;

import uq.deco2800.duxcom.shop.PlayerWallet;

/**
 * Loot Wallet of the dead enemy entity
 *
 * @author The_Magic_Karps
 */
public class LootWallet extends PlayerWallet {

    /**
     * Constructor of the LootWallet with 0 balance at start
     */
    public LootWallet() {
        super();
    }

    /**
     * Constructor of the LootWallet with amount balance at start
     *
     * @param amount the amount of balance at start
     */
    public LootWallet(int amount) {
        super(amount);
    }
}

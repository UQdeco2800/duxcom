package uq.deco2800.duxcom.loot;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Trivial LootWalletTest but oh well.
 *
 * @author The_Magic_Karps
 */
public class LootWalletTest {

    private LootWallet wallet;

    @Test
    public void testNoInitBalance() {
        wallet = new LootWallet();
        assertEquals(wallet.getBalance(), 0);
        wallet.addBalance(50);
        assertEquals(wallet.getBalance(), 50);
        wallet.addBalance(-50);
        assertEquals(wallet.getBalance(), 0);
    }

    @Test
    public void testInitBalance() {
        wallet = new LootWallet(50);
        assertEquals(wallet.getBalance(), 50);
    }
}

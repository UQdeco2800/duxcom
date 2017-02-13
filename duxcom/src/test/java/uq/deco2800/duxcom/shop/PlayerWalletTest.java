/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uq.deco2800.duxcom.shop;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 *
 * @author user
 */
public class PlayerWalletTest {
    @Test
    public void addBalanceTest() {
        PlayerWallet wallet = new PlayerWallet();
        wallet.addBalance(50);
        assertEquals(wallet.getBalance(), 50);
        wallet.addBalance(-50);
        assertEquals(wallet.getBalance(), 0);
    }
    
    @Test
    public void initBalanceTest() {
        PlayerWallet wallet = new PlayerWallet(5);
        assertEquals(wallet.getBalance(), 5);
    }
    
    @Test
    public void setBalanceTest() {
        PlayerWallet wallet = new PlayerWallet(10);
        wallet.setBalance(-5);
        assertEquals(wallet.getBalance(), 10);
        wallet.setBalance(1000);
        assertEquals(wallet.getBalance(), 1000);
    }
}

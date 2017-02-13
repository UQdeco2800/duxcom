/*
 * Contact Group 1 - the_magic_karps for detail
 * Ticket Issue: #11 - Player Economy and shop
 */
package uq.deco2800.duxcom.shop;

/**
 * Player Wallet - Currency Name: "DollaryDoo"
 *
 * @author Group 1 = the_magic_karps
 */
public class PlayerWallet {

    /* Balance of player is stored in player wallet */
    private int balance;

    /**
     * Constructor for creating a new blank wallet balance
     */
    public PlayerWallet() {
        this.balance = 0;
    }

    /**
     * Constructor for creating a new wallet balance with a value
     *
     * @param value the money the player start with
     */
    public PlayerWallet(int value) {
        this.balance = value;
    }

    /**
     * Add or remove balance from player Accepts negative integers
     *
     * Use setBalance(int value) instead to hard set the balance in the wallet
     * 
     * @param value money to be changed
     */
    public void addBalance(int value) {
        this.balance += value;
    }

    /**
     * Set the balance of the wallet Does not accept negative value
     *
     * Use addBalance(int value) instead to increase/decrease from current 
     * balance
     * 
     * @param value the balance to set the wallet to
     */
    public void setBalance(int value) {
        if (value >= 0) {
            this.balance = value;
        }

    }

    /**
     * Returns the current balance for the player
     *
     * @return the balance of the player
     */
    public int getBalance() {
        return this.balance;
    }
}

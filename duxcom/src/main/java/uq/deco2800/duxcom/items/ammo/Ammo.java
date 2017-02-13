package uq.deco2800.duxcom.items.ammo;

import uq.deco2800.duxcom.items.Item;
import uq.deco2800.duxcom.items.ItemType;

/**
 * @author Ducksters Team 10
 *
 *         Ammo for weapons or other items.
 */
public class Ammo extends Item {

    // The amount of ammo the item has.
    protected int ammoQuantity = 0;

    /**
     * Creates a new Ammo object.
     *
     * @param name                 String, the name of the item for user readability
     * @param cost                 is the monetary value of the item
     * @param weight,              it the weight of the item
     * @param inventorySpriteName, the sprite location
     * @param tradable,            set to true if the item is to be tradable
     */
    public Ammo(String name, int cost, int weight, String inventorySpriteName,
                boolean tradable) {
        super(name, cost, weight, inventorySpriteName, tradable, ItemType.AMMO);
    }

    /**
     * Gets the amount of ammo in the ammo stash.
     *
     * @return int amount of ammo
     */
    public int getAmmo() {
        return this.ammoQuantity;
    }

    /**
     * Uses 1 unit of ammo.
     *
     * @return true if ammo was removed else false if no ammo was used
     */
    public boolean useAmmo() {
        if (this.ammoQuantity > 0) {
            this.ammoQuantity--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Use 'amount' worths of ammo
     *
     * @param amount of ammo to consume
     * @return true if the ammo was consumed, false if there was not enough ammo to consume (ammo
     * was not changed)
     */
    public boolean useAmmo(int amount) {
        // if there is enough ammo
        if (this.ammoQuantity - amount >= 0) {
            this.ammoQuantity -= amount;
            return true;
        }
        return false;
    }

    /**
     * Add amount to ammo stash
     *
     * @param amount to add to the ammo stash
     */
    public void addAmmo(int amount) {
        this.ammoQuantity += amount;
    }

    /**
     * Add all the ammo from one item to this item
     *
     * @param ammo stash
     */
    public void addAmmo(Ammo ammo) {
        this.ammoQuantity += ammo.getAmmo();
        ammo.dropAmmo();
    }

    /**
     * Remove all the ammo from this items stash
     */
    public void dropAmmo() {
        this.ammoQuantity = 0;
    }

    /**
     * Evaluates whether these items are the same
     *
     * @param o (ammoObject) is the object to compare against
     * @return true if the items have the same properties
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Ammo ammo1 = (Ammo) o;

        return ammoQuantity == ammo1.ammoQuantity;

    }

    @Override
    /**
     * Generates a hash code that is is dependent on the item properties
     * @return a hashcode
     */
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + ammoQuantity;
        return result;
    }
}

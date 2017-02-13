/*
 * Contact Group 10 - ducksters for detail
 * Ticket Issue: #12 - Pickup Items and Inventory
 */
package uq.deco2800.duxcom.inventory;

import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.items.EquippableItem;
import uq.deco2800.duxcom.items.Item;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.items.weapon.Weapon;

import java.util.ArrayList;
import java.util.List;

import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.listeners.ItemEquipListener;
import uq.deco2800.duxcom.sound.SoundPlayer;

/**
 * Base Item
 *
 * @author Team 10 = ducksters
 */
public class HeroInventory extends Inventory {

    private Item primaryWeapon;
    private Item secondaryWeapon;
    private Item shield;
    private Item armour;
    private AbstractHero hero;

    private List<ItemEquipListener> itemEquipListeners = new ArrayList<ItemEquipListener>();

    /**
     * Creates an Inventory
     *
     * @param inventorySize how many slots the inventory can hold
     */
    public HeroInventory(int inventorySize) {
        super(inventorySize);
        primaryWeapon = null;
        shield = null;
        armour = null;
    }

    /**
     * @return boolean, True if a weapon is equipped, false if not
     */
    public boolean checkIfPrimaryWeaponEquipped() {
        return !(primaryWeapon == null);
    }

    /**
     * @return boolean, True if the second weapon is equipped, false if not
     */
    public boolean checkIfSecondaryWeaponEquipped() {
        return !(secondaryWeapon == null);
    }

    /**
     * @return boolean, true if shield is equipped, false if not
     */
    public boolean checkIfShieldEquipped() {
        return !(shield == null);
    }

    /**
     * @return boolean, true if bodyArmour is equipped, false if not
     */
    public boolean checkIfArmourEquipped() {
        return !(armour == null);
    }

    /**
     * Returns the current primary weapon
     *
     * @return the primary weapon
     */
    public Item getPrimaryWeapon() {
        return primaryWeapon;
    }

    /**
     * Returns the current secondary weapon
     *
     * @return the secondary weapon
     */
    public Item getSecondaryWeapon() {
        return secondaryWeapon;
    }

    /**
     * @return the shield
     */
    public Item getShield() {
        return shield;
    }

    /**
     * @return the bodyArmour
     */
    public Item getArmour() {
        return armour;
    }

    /**
     * Bind Inventory with Hero
     */
    public void bindHero(AbstractHero hero) {
        this.hero = hero;
    }

    public AbstractHero getBindedHero() {
        return this.hero;
    }

    public boolean canWield(Item weapon) {

        Weapon thisWeapon;

        if (weapon instanceof Weapon) {
            thisWeapon = (Weapon) weapon;
        } else {
            return false;
        }

        if (GameLoop.getCurrentGameManager() != null
                && GameLoop.getCurrentGameManager().getHeroManager() != null
                && GameLoop.getCurrentGameManager().getHeroManager().getCurrentHero() != null) {
            hero = GameLoop.getCurrentGameManager().getHeroManager().getCurrentHero();
        }

        return (hero != null && hero.canWield(thisWeapon)
                || (hero == null && thisWeapon.setEquippedAsPrimary(true)));
    }

    /**
     * @return true iff equipped
     */
    public boolean equipPrimaryWeapon(Item weapon) {

        if (GameLoop.getCurrentGameManager() != null
                && GameLoop.getCurrentGameManager().getHeroManager() != null
                && GameLoop.getCurrentGameManager().getHeroManager().getCurrentHero() != null) {
            hero = GameLoop.getCurrentGameManager().getHeroManager().getCurrentHero();
        }

        if (!(weapon instanceof Weapon))
            return false;
        Weapon thisWeapon = (Weapon) weapon;

        //Enforce Single handed weapon not to equip
        if (secondaryWeapon != null
                && !thisWeapon.setTwoHanded(true)
                && secondaryWeapon.getType().equals(thisWeapon.getType())) {
            return false;
        }

        if ((hero != null && hero.canWield(thisWeapon) && thisWeapon.setEquippedAsPrimary(true))
                || (hero == null && thisWeapon.setEquippedAsPrimary(true))) {
            this.primaryWeapon = weapon;
            notifyItemEquip((EquippableItem) weapon);
            return true;
        } else {
            if (GameLoop.getCurrentGameManager() != null
                    && GameLoop.getCurrentGameManager().getController() != null
                    && GameLoop.getCurrentGameManager().getController().getUIController() != null) {

                GameLoop.getCurrentGameManager().getController().getUIController().messageAbilityDialogue("INCOMPATIBLE WEAPON TYPE.");
            }
        }

        return false;
    }

    /**
     * @return true iff equipped
     */
    public boolean equipSecondaryWeapon(Item weapon) {
        if (!(weapon instanceof Weapon))
            return false;
        Weapon thisWeapon = (Weapon) weapon;

        //Enforce Single handed weapon not to equip
        if (primaryWeapon != null && !thisWeapon.setTwoHanded(true)
                && thisWeapon.getType().equals(primaryWeapon.getType())) {
            return false;
        }

        if (thisWeapon.setEquippedAsSecondary(true)) {
            this.secondaryWeapon = weapon;
            notifyItemEquip((EquippableItem) weapon);
            return true;
        }
        return false;
    }

    /**
     * @return true iff equipped
     */
    public boolean equipPrimaryWeapon(Item weapon, int slotNumber) {
        if (!(weapon instanceof Weapon)
                || !this.checkIfItemInSlot(slotNumber)
                || !this.getItemFromSlot(slotNumber).equals(weapon)) // either return false
            return false;
        this.primaryWeapon = weapon;
        this.removeItem(slotNumber);
        notifyItemEquip((EquippableItem) weapon);
        return true;
    }

    /**
     * @return true iff equipped
     */
    public boolean equipSecondaryWeapon(Item weapon, int slotNumber) {
        if (!(weapon instanceof Weapon)
                || !this.checkIfItemInSlot(slotNumber)
                || !this.getItemFromSlot(slotNumber).equals(weapon)) // either return false
            return false;
        this.secondaryWeapon = weapon;
        this.removeItem(slotNumber);
        notifyItemEquip((EquippableItem) weapon);
        return true;
    }

    /**
     * @return true iff equipped
     */
    public boolean equipShield(Item shield) {
        if (!shield.getType().equals(ItemType.SHIELD))
            return false;
        this.shield = shield;
        SoundPlayer.playEquip();
        return true;
    }

    /**
     * @return true iff equipped
     */
    public boolean equipShield(Item shield, int slotNumber) {
        if (!shield.getType().equals(ItemType.SHIELD)
                || !this.checkIfItemInSlot(slotNumber)
                || !this.getItemFromSlot(slotNumber).equals(shield)) // either return false
            return false;
        this.shield = shield;
        this.removeItem(slotNumber);
        notifyItemEquip((EquippableItem) shield);
        return true;
    }

    /**
     * @return true iff equipped
     */
    public boolean equipArmor(Item armour) {
        if (!armour.getType().equals(ItemType.ARMOUR))
            return false;
        this.armour = armour;
        notifyItemEquip((EquippableItem) armour);
        return true;
    }

    /**
     * @return true iff equipped
     */
    public boolean equipArmour(Item armour, int slotNumber) {
        if (!armour.getType().equals(ItemType.ARMOUR)
                || !this.checkIfItemInSlot(slotNumber)
                || !this.getItemFromSlot(slotNumber).equals(armour))
            return false;
        this.armour = armour;
        this.removeItem(slotNumber);
        notifyItemEquip((EquippableItem) armour);
        return true;
    }

    /**
     * @return true iff unequipped
     */
    public boolean unEquipPrimaryWeapon() {
        if (this.primaryWeapon == null) {
            return false;
        }
        if (this.addItem(this.primaryWeapon)) {
            notifyItemDequip((EquippableItem) this.primaryWeapon);
            this.primaryWeapon = null;
            updateGraphics();
            return true;
        }
        return false;
    }

    /**
     * @param slotNumber the slot which the sword should be unequipped to
     * @return true iff un-equipped
     */
    public boolean unEquipPrimaryWeapon(int slotNumber) {
        if (this.primaryWeapon == null) {
            return false;
        }
        if (this.addItem(this.primaryWeapon, slotNumber)) {
            notifyItemDequip((EquippableItem) this.primaryWeapon);
            this.primaryWeapon = null;
            updateGraphics();
            return true;
        }
        return false;
    }

    /**
     * @return true iff un-equipped
     */
    public boolean unEquipSecondaryWeapon() {
        if (this.secondaryWeapon == null) {
            return false;
        }
        if (this.addItem(this.secondaryWeapon)) {
            notifyItemDequip((EquippableItem) this.secondaryWeapon);
            this.secondaryWeapon = null;
            updateGraphics();
            return true;
        }
        return false;
    }

    /**
     * @return true iff unequipped
     */
    public boolean unEquipSecondaryWeapon(int slotNumber) {
        if (this.secondaryWeapon == null) {
            return false;
        }
        if (this.addItem(this.secondaryWeapon, slotNumber)) {
            notifyItemDequip((EquippableItem) this.secondaryWeapon);
            this.secondaryWeapon = null;
            updateGraphics();
            return true;
        }
        return false;
    }

    /**
     * @return true iff un-equipped
     */
    public boolean unEquipShield() {
        if (this.shield == null) {
            return false;
        }
        if (this.addItem(this.shield)) {
            notifyItemDequip((EquippableItem) this.shield);
            this.shield = null;
            updateGraphics();
            return true;
        }
        return false;
    }

    /**
     * @return true iff unequipped
     */
    public boolean unEquipShield(int slotNumber) {
        if (this.shield == null) {
            return false;
        }
        if (this.addItem(this.shield, slotNumber)) {
            notifyItemDequip((EquippableItem) this.shield);
            this.shield = null;
            updateGraphics();
            return true;
        }
        return false;
    }

    /**
     * @return true iff unequipped
     */
    public boolean unEquipArmor() {
        if (this.armour == null) {
            return false;
        }
        if (this.addItem(this.armour)) {
            notifyItemDequip((EquippableItem) this.armour);
            this.armour = null;
            updateGraphics();
            return true;
        }
        return false;
    }

    /**
     * @return true iff un-equipped
     */
    public boolean unEquipArmor(int slotNumber) {
        if (this.armour == null) {
            return false;
        }
        if (this.addItem(this.armour, slotNumber)) {
        	notifyItemDequip((EquippableItem)this.armour);
        	this.armour = null;
        	updateGraphics();
            return true;
        }
        return false;
    }

    /*
     * Listener Operations 
     */
    public void updateGraphics(){
    	for(ItemEquipListener listeners:itemEquipListeners){
        	listeners.updateGraphics();
        }
    }
    
    public void notifyItemEquip(EquippableItem item){
    	for(ItemEquipListener listeners:itemEquipListeners){
        	listeners.itemEquip(item, false);
        }
    }

    public void notifyItemDequip(EquippableItem item) {
        for (ItemEquipListener listeners : itemEquipListeners) {
            listeners.itemEquip(item, true);
        }
    }

    public void addItemEquipListener(ItemEquipListener listeners) {
        if (!this.itemEquipListeners.contains(listeners)) {
            this.itemEquipListeners.add(listeners);
        }
    }

    public void removeItemEquipListener(ItemEquipListener listeners) {
        this.itemEquipListeners.remove(listeners);
    }
}

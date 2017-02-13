/*
 * Contact Group 10 - ducksters for detail
 * Ticket Issue: #12 - Pickup Items and Inventory
 */

package uq.deco2800.duxcom.inventory;

import org.junit.Test;

import uq.deco2800.duxcom.items.*;
import uq.deco2800.duxcom.items.armour.Armour;
import uq.deco2800.duxcom.items.shield.Shield;
import uq.deco2800.duxcom.items.weapon.*;

import static org.junit.Assert.*;

/**
 * Equipable Item Test
 *
 * @author Team 10 = ducksters
 */
public class HeroInventoryTest {

    @Test
    public void initInventoryTest() {
        HeroInventory ibisWarrior = new HeroInventory(20);
        assertEquals(ibisWarrior.getItemCount(), 0);
        assertEquals(ibisWarrior.getTotalCost(), 0);
        assertEquals(ibisWarrior.getTotalWeight(), 0);
    }

    @Test
    public void addingItemsTest() {
        Axe ironAxe = ItemGenerate.axe.BRONZE_AXE.generate();
        Sword ironSword = ItemGenerate.sword.VALYRIAN_STEEL_SWORD.generate();
        HeroInventory duck1 = new HeroInventory(12);
        duck1.addItem(ironAxe);
        duck1.addItem(ironSword);
        assertTrue(duck1.containsItem(ironAxe));
        assertTrue(duck1.containsItem(ironSword));
    }

    @Test
    public void equippingItemsTest() {
        Axe ironAxe = ItemGenerate.axe.BRONZE_AXE.generate();
        Sword ironSword = ItemGenerate.sword.VALYRIAN_STEEL_SWORD.generate();
        Dagger ironDagger = ItemGenerate.dagger.BRONZE_DAGGER.generate();
        Shield ironShield = ItemGenerate.shield.STEEL_SHIELD.generate();

        HeroInventory duck1 = new HeroInventory(12);

        duck1.addItem(ironAxe);
        duck1.addItem(ironSword);
        duck1.addItem(ironShield);

        assertFalse(duck1.checkIfPrimaryWeaponEquipped());
        assertFalse(duck1.checkIfSecondaryWeaponEquipped());
        assertFalse(duck1.checkIfShieldEquipped());

        duck1.equipPrimaryWeapon(ironSword);
        duck1.equipShield(ironShield, 11);
        duck1.equipShield(ironShield);
        duck1.unEquipShield(7);
        duck1.equipShield(ironShield, 3);

        assertTrue(duck1.checkIfShieldEquipped());
        assertTrue(duck1.unEquipShield(3));
        assertFalse(duck1.equipShield(ironShield, 1));
        assertTrue(duck1.equipShield(ironShield, 3));
        assertTrue(duck1.checkIfShieldEquipped());
        assertEquals(duck1.getShield(), ironShield);
        assertTrue(duck1.unEquipShield(6));
        assertFalse(duck1.unEquipShield(6));

        assertTrue(duck1.checkIfPrimaryWeaponEquipped());
        duck1.unEquipPrimaryWeapon(8);
        assertTrue(duck1.equipPrimaryWeapon(ironSword, 8));
        duck1.unEquipPrimaryWeapon(8);

        assertTrue(duck1.equipSecondaryWeapon(ironDagger));
        assertTrue(duck1.unEquipSecondaryWeapon(5));
        assertFalse(duck1.unEquipSecondaryWeapon(5));
        assertFalse(duck1.equipSecondaryWeapon(ironDagger, 1));
        assertFalse(duck1.equipSecondaryWeapon(ironDagger, 11));
        assertTrue(duck1.equipSecondaryWeapon(ironDagger, 5));
        assertTrue(duck1.checkIfSecondaryWeaponEquipped());
        assertEquals(duck1.getSecondaryWeapon(), ironDagger);
        duck1.unEquipSecondaryWeapon(5);

        /*
         * Two handed weapon equip test
         */
        //Not two handed weapon
        duck1.equipPrimaryWeapon(ironSword);
        assertFalse(duck1.equipSecondaryWeapon(ironSword));
        duck1.unEquipPrimaryWeapon();
        duck1.unEquipSecondaryWeapon();

        //Two Handed Weapon
        duck1.equipPrimaryWeapon(ironAxe);
        duck1.unEquipPrimaryWeapon();
        duck1.unEquipSecondaryWeapon();
        
        //TODO add tests for bodyArmour equipping
        assertFalse(duck1.equipPrimaryWeapon(ironSword, 11));
        assertFalse(duck1.equipPrimaryWeapon(ironSword, 7));
        assertTrue(duck1.equipPrimaryWeapon(ironSword, 8));
        assertEquals(duck1.getPrimaryWeapon(), ironSword);
        assertTrue(duck1.unEquipPrimaryWeapon(8));
        assertEquals(duck1.getPrimaryWeapon(), null);

    }


    /* TODO: needs fixing for new Item implementation
    @Test
    public void equipWeaponTest() throws Exception {
        HeroInventory ibisWarrior = new HeroInventory(15);
        
        Weapon sword = new EquippableItemInstanceClass(ItemType.SWORD, "normal sword", 1, 15, 15, "here", true, SlotType.PRIMARY_WEAPON, 60);
        BuildingMaterial stick = new EquippableItemInstanceClass(ItemType.STICK, "rotten stick", 1, 34, 25, "there", true, SlotType.GENERAL, 256);
        Weapon mace = new EquippableItemInstanceClass(ItemType.MACE, "gold mace", 2, 346, 19, "everywhere", false, SlotType.PRIMARY_WEAPON, 25);

        Weapon sword = new Weapon.Builder(ItemType.SWORD, "normal sword", new Sprite()).weight(110).cost(12).build();
        BuildingMaterial stick = new BuildingMaterial.Builder(ItemType.STICK, "rotten stick", new Sprite()).weight(140).cost(120).build();
        Weapon mace = new Weapon.Builder(ItemType.MACE, "gold mace", new Sprite()).weight(120).cost(102).build();
        Consumable food = new Consumable.Builder(ItemType.FOOD_BANANA, "yummy banana", new Sprite()).weight(120).cost(102).build();
        Armour chainMail = new Armour.Builder(ItemType.CHAIN_MAIL, "Strong chain linked thing", new Sprite()).weight(100).cost(100).build();

        ibisWarrior.addItem(sword);
        ibisWarrior.addItem(stick);
        ibisWarrior.addItem(mace);
        ibisWarrior.addItem(food);


        ibisWarrior.equipArmor(chainMail);
        ibisWarrior.equipPrimaryWeapon(sword);

        assertEquals(ibisWarrior.getBaseArmour(), chainMail);
        assertEquals(ibisWarrior.getPrimaryWeapon(), sword);

        assertFalse(ibisWarrior.unEquipPrimaryWeapon(20));
        assertTrue(ibisWarrior.unEquipPrimaryWeapon(10));

        ibisWarrior.equipPrimaryWeapon(sword, 10);


    }*/
}


package uq.deco2800.duxcom.items.weapon;


import uq.deco2800.duxcom.abilities.*;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.items.RarityLevel;

public class Staff extends Weapon{

    private static boolean tradable = true;
    private static boolean canBePrimary = true;
    private static boolean canBeSecondary = false;
    private static boolean canBeTwoHanded = false;

    public Staff(String name, int cost, int weight, String inventorySpriteName,
                 int durability, int damage, RarityLevel rarity, String texture) {
        super(name, texture, cost, weight, inventorySpriteName, tradable, durability,
                canBePrimary, canBeSecondary, canBeTwoHanded, damage, rarity, ItemType.STAFF);
        this.weaponAbility = new Fireball();
    }
}


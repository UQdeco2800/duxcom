package uq.deco2800.duxcom.items.weapon;

import uq.deco2800.duxcom.abilities.Slash;
import uq.deco2800.duxcom.abilities.Stab;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.items.RarityLevel;

/**
 * Its a lance
 *
 * Created by jay-grant on 3/11/2016.
 */
public class Lance extends Weapon {

    //all swords have certain properties
    private static boolean tradable = true;
    private static boolean canBePrimary = true;
    private static boolean canBeSecondary = false;
    private static boolean canBeTwoHanded = false;

    //some variation available between same class items at the moment
    public Lance(String name, int cost, int weight, String inventorySpriteName,
                 int durability, int damage, RarityLevel rarity, String texture) {
        super(name, texture, cost, weight, inventorySpriteName, tradable, durability,
                canBePrimary, canBeSecondary, canBeTwoHanded, damage, rarity, ItemType.LANCE);
        //placeholder generic ability
        this.weaponAbility = new Stab();
    }

}

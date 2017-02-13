package uq.deco2800.duxcom.items.weapon;

import uq.deco2800.duxcom.abilities.Slash;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.items.RarityLevel;


/**
 * @author Quackin
 */
public class Axe extends Weapon {
    //all axes have certain properties
    private static boolean tradable = true;
    private static boolean canBePrimary = true;
    private static boolean canBeSecondary = false;
    private static boolean canBeTwoHanded = true;

    //some variation available between same class items at the moment
    public Axe(String name, int cost, int weight, String inventorySpriteName,
               int durability, int damage, RarityLevel rarity, String texture) {
        super(name, texture, cost, weight, inventorySpriteName, tradable, durability,
                canBePrimary, canBeSecondary, canBeTwoHanded, damage, rarity, ItemType.AXE);
        //generic ability placeholder
        this.weaponAbility = new Slash();
    }
}

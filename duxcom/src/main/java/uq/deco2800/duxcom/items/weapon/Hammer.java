package uq.deco2800.duxcom.items.weapon;


import uq.deco2800.duxcom.abilities.Slash;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.items.RarityLevel;

public class Hammer extends Weapon {
    private static boolean tradable = true;
    private static boolean canBePrimary = true;
    private static boolean canBeSecondary = false;
    private static boolean canBeTwoHanded = false;

    public Hammer(String name, int cost, int weight, String inventorySpriteName,
                  int durability, int damage, RarityLevel rarity, String texture) {
        super(name, texture, cost, weight, inventorySpriteName, tradable, durability,
                canBePrimary, canBeSecondary, canBeTwoHanded, damage, rarity, ItemType.HAMMER);
        //placeholder generic ability, custom to come later
        this.weaponAbility = new Slash();
    }

}

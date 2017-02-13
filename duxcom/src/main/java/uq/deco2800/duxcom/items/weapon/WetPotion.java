package uq.deco2800.duxcom.items.weapon;


import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.items.RarityLevel;

public class WetPotion extends Weapon {

    private static boolean tradable = true;
    private static boolean canBePrimary = false;
    private static boolean canBeSecondary = true;
    private static boolean canBeTwoHanded = false;

    public WetPotion(String name, int cost, int weight, String inventorySpriteName,
                         int durability, int damage, RarityLevel rarity) {
        super(name, cost, weight, inventorySpriteName, tradable, durability, canBePrimary, canBeSecondary, canBeTwoHanded,
                damage, rarity, ItemType.WET_POTION);
        
    }

}

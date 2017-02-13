package uq.deco2800.duxcom.items;

import java.util.HashMap;

import uq.deco2800.duxcom.items.armour.Armour;
import uq.deco2800.duxcom.items.shield.Shield;
import uq.deco2800.duxcom.items.weapon.Weapon;

/**
 * Created by User on 20-Oct-16.
 */
public class DescriptionGenerate {
    // Stores every description in the form ( Item Name , Description)
    private static HashMap<String, String> desc = new HashMap<>();

    static {
        desc.put("Bronze Sword", "A cheap bronze sword for hackin' and slashin'");
        desc.put("Iron Sword", "A cheap iron sword for hackin' and slashin'");
        desc.put("Steel Sword", "A cheap steel sword for hackin' and slashin'");
        desc.put("Platinum Sword", "A cheap platinum sword for hackin' and slashin'");

        desc.put("Bronze Dagger", "A cheap bronze sword for hackin' and slashin'");
        desc.put("Iron Dagger", "A cheap bronze sword for hackin' and slashin'");
        desc.put("Steel Dagger", "A cheap bronze sword for hackin' and slashin'");
        desc.put("Platinum Dagger", "A cheap bronze sword for hackin' and slashin'");

        desc.put("Wooden Shield", "A shield for protecting yourself from bad guys");
        desc.put("Bronze Shield", "A shield for protecting yourself from bad guys");
        desc.put("Iron Shield", "A shield for protecting yourself from bad guys");
        desc.put("Steel Shield", "A shield for protecting yourself from bad guys");
        desc.put("Platinum Shield", "A shield for protecting yourself from bad guys");

        desc.put("Basic Armour", "A full suit of armor for your adventures");
        desc.put("Wooden Armour", "A full suit of armor for your adventures");
        desc.put("Bronze Armour", "A full suit of armor for your adventures");
        desc.put("Iron Armour", "A full suit of armor for your adventures");
        desc.put("Steel Armour", "A full suit of armor for your adventures");
        desc.put("Platinum Armour", "A full suit of armor for your adventures");

        desc.put("Bronze Axe", "");
        desc.put("Bronze Axe", "");
        desc.put("Bronze Axe", "");
        desc.put("Bronze Axe", "");
        desc.put("Bronze Axe", "");


        desc.put("Bronze Hammer", "A big scary hammer for crushin' big scary dudes");
        desc.put("Iron Hammer", "A big scary hammer for crushin' big scary dudes");
        desc.put("Steel Hammer", "A big scary hammer for crushin' big scary dudes");
        desc.put("Platinum Hammer", "A big scary hammer for crushin' big scary dudes");
        desc.put("Thors Hammer", "The hammer of gods (Marvel pls don't sue) ");

        desc.put("Wooden Staff", "A stick with magical properties");
        desc.put("Short Staff", "A stick with magical properties");
        desc.put("Mage Staff", "A stick with magical properties");
        desc.put("Wizard Staff", "A stick with magical properties");
        desc.put("Gandalf's Staff", "YOU SHALL NOT PASS! (tolkein pls don't sue) ");

        desc.put("Wooden Bow", "a curvy stick with some elastic material attached");

        desc.put("Poison Bomb", "don't drink this");

        desc.put("Health Potion", "smells of cinnamon with hints of cherry");
        desc.put("AP Potion", "the good stuff");

    }

    /**
     * Called by the item class only. To get an items description, use item.getDescription()
     *
     * @param item the item
     * @return the description
     */
    public static String descriptionGenerate(Item item) {
        if (item == null) {
            return ("Item does not exist");
        }
        if (item.getName() == null) {
            return ("This item has an invalid name");
        }
        String description = desc.get(item.getName());
        if (description == null) {
            return ("Description not yet written! Add a description to DescriptionGenerate under the Items package");
        }
        return description;
    }

    public static String statsGenerate(Item item) {
        if (item == null) {
            return ("Item does not exist");
        }
        if (item.getName() == null) {
            return ("This item has an invalid name");
        }
        String stats = "";
        if (item instanceof EquippableItem) {
            stats = stats + String.format("   Rarity: %s\n",
                    ((EquippableItem) item).getRarity().toString());
        }
        if (item instanceof Weapon) {
            stats = stats + String.format("   Damage: %d\n", ((Weapon) item).getDamage());
        } else if (item instanceof Shield) {
            stats = stats + String.format("   Armour: %d\n", ((Shield) item).getArmour());
        } else if (item instanceof Armour) {
            stats = stats + String.format("   Armour: %d\n", ((Armour) item).getArmour());
        }
        stats = stats + String.format("   Cost: %d\n", item.getCost());
        stats = stats + String.format("   Weight: %d\n", item.getWeight());
        return stats;
    }
}

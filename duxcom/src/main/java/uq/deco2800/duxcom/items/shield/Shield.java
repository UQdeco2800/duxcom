package uq.deco2800.duxcom.items.shield;

import uq.deco2800.duxcom.items.EquippableItem;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.items.RarityLevel;

/**
 * A class that creates the ability to have shields in the game.
 *
 * @author Quackin #15 #28
 * @author Team 10 = ducksters
 */
public class Shield extends EquippableItem {
	protected int armour;
	private static boolean tradable = true;

	/**
	 * Shield constructor used to create the shield. A shield has the ability to
	 * take damage that would otherwise be inflicted on the player causing
	 * health damage. Shields are apart of armour. Cost of the shield is used
	 * when trading in the shop if the tradable boolean is set to true.
	 * 
	 * @param name
	 *            String value of the shields name.
	 * @param cost
	 *            Integer used for determining the value of the shield when
	 *            trading in the shop.
	 * @param weight
	 *            Integer value determining the weight of the shield.
	 * @param inventorySpriteName
	 *            String value representing the location of the shield's image.
	 *            Should be similar to this example
	 *            "src/main/resources/<yourImage.png>"
	 * 
	 * @param durability
	 *            Integer value representing the strength of the shield.
	 * @param armour
	 *            Integer value
	 * @param rarity
	 *            RarityLevel enum determining the shields rarity value.
	 */
	public Shield(String name, int cost, int weight, String inventorySpriteName, int durability, int armour,
			RarityLevel rarity) {
		super(name, cost, weight, inventorySpriteName, tradable, durability, rarity, ItemType.SHIELD);
		this.armour = armour;
	}

	/**
	 * A function for setting the shield to be used.
	 * 
	 * @param armour
	 *            Integer value to be set as armour.
	 */
	public void setArmour(int armour) {
		this.armour = armour;
	}

	/**
	 * A function for retrieving the already set armour value.
	 * 
	 * @return Integer value of set armour.
	 */
	public int getArmour() {
		return armour;
	}
}

package uq.deco2800.duxcom.items.armour;

import uq.deco2800.duxcom.items.EquippableItem;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.items.RarityLevel;
import uq.deco2800.duxcom.entities.heros.*;

/**
 * Armour is equipped by the player in HeroInventory during the game. Damage
 * dealt to the hero, by enemies attacks, will deal less damage when armour is
 * equipped. Different hero types have different armour styles. As armour is
 * apart of Items which have the ability to be traded in the shop values such as
 * cost and tradable are assigned to armour as well. With each enemy attack,
 * damage is dealt to the armour which affects its ability to absorb an enemies
 * attack. This ultimately causes more damage to be dealt to the hero.
 *
 * @author Quackin #15
 * @author Team 10 = ducksters
 * @see HeroInventory
 */

public class Armour extends EquippableItem {

	private int effectiveness = 100; // 100% effective
	private int armourRating;
	private int damageDown;
	private int costActionPoint;

	private static final int MAX_ARMOUR = 100; // armourRating restrict to
												// MAX_ARMOUR 100
	private static final int MAX_EFFECTIVENESS = 100; // armourRating restrict
														// to MAX_EFFECTIVENESS
														// 100
	private static final int FACTOR = 5; // FACTOR = 5
	private static final int MAX_FACTOR = 10; // factor will be divided by
												// MAX_FACTOR

	/**
	 * Creates the armour to be used by the hero's.
	 * 
	 * @param name
	 *            String value representing the name of the armour
	 * @param cost
	 *            Integer representing the value of the armour to be used by the
	 *            shop.
	 * @param weight
	 *            Integer representing the weight of the armour.
	 * @param inventorySpriteName
	 *            String value representing the name of the associated inventory
	 *            sprite.
	 * @param durability
	 *            Integer representing the strength of the armour.
	 * @param armour
	 *            Integer representing the number of the armour.
	 * @param rarity
	 *            RarityLevel representing the rarity of the armour
	 */
	public Armour(String name, int cost, int weight, String inventorySpriteName, boolean tradable, int durability,
			int armour, RarityLevel rarity) {
		super(name, cost, weight, inventorySpriteName, tradable, durability, rarity, ItemType.ARMOUR);
		this.armourRating = armour;
		this.damageDown = 0; // initialize
	}

	/**
	 * Set Armour Change Armour value for future expansion tasks like upgrade,
	 * set, etc
	 * 
	 * @param armour
	 *            the number of the armour
	 */
	public void setArmour(int armour) {
		this.armourRating = armour > MAX_ARMOUR ? MAX_ARMOUR : armour;
	}

	/**
	 * Get the current value or armourRating (alt. DEF)
	 * 
	 * @return int Armour Value
	 */
	public int getArmour() {
		return armourRating;
	}

	/**
	 * Make damage to armourRating, affect durability and effectiveness
	 * 
	 * @param damage
	 *            the amount of damage
	 */
	public void damageArmour(int damage) {
		this.armourRating = this.armourRating > damage ? (this.armourRating - damage) : 0;
	}

	/**
	 * Apply Damage to Hero. Calculates the damage to be dealt to the hero when
	 * a enemy attacks the hero. The original damage dealt to the hero is taken
	 * in and used to calculate the damage to be dealt to the hero after the
	 * armour absorb's some of the impact dealt by the enemies attack.
	 * 
	 * @param originDamage
	 *            The amount of damage the enemy dealt to the hero.
	 * @return The actual damage to be delivered to the hero.
	 */
	public int applyDamage(int originDamage) {
		this.damageDown = originDamage - (originDamage * (effectiveness * armourRating)
				/ (MAX_ARMOUR * MAX_EFFECTIVENESS) * FACTOR / MAX_FACTOR);
		return damageDown;
	}

	/**
	 * Set the AP gain of the armourRating
	 * 
	 * @param ap
	 *            the ap gain
	 */
	public void setCostActionPoint(int ap) {
		costActionPoint = ap;
	}

	/**
	 * Get the AP gain of the armourRating
	 */
	public int getCostActionPoint() {
		return this.costActionPoint;
	}

	/**
	 * Decrease the Armours effectiveness to absorb damage delivered by enemy
	 * attacks.
	 */
	public void damage(int effectiveness) {
		this.effectiveness = this.effectiveness > effectiveness ? (this.effectiveness - effectiveness) : 0;
	}

	/**
	 * Change effectiveness of Armour decay against enemy attacks. Armour
	 * effectiveness can be increased by repairing the armour.
	 * 
	 * @param effectiveness
	 *            the effectiveness value to set
	 */

	public void setEffectiveness(int effectiveness) {
		this.effectiveness = effectiveness > MAX_EFFECTIVENESS ? MAX_EFFECTIVENESS : effectiveness;
	}

	/**
	 * Get current effectiveness of armour.
	 * 
	 * @return int Effectiveness
	 */
	public int getEffectiveness() {
		return effectiveness;
	}

	/**
	 * Equip to hero and set effect on the hero
	 * 
	 * @param hero
	 *            the hero to equip to
	 */
	public void Enquip(AbstractHero hero) {
		hero.changeAP(-costActionPoint);
	}
}

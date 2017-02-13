/*
 * Moved from items package. Seems like a better idea to have it here.
 */

package uq.deco2800.duxcom.items.weapon;

import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.items.EquippableItem;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.items.RarityLevel;
import uq.deco2800.duxcom.items.StatisticModifier;
import uq.deco2800.duxcom.items.StatisticModifyAction;
import uq.deco2800.duxcom.passives.AbstractPassive;
import uq.deco2800.duxcom.async.MusicAsyncProcess;
import uq.deco2800.duxcom.entities.heros.HeroStat;
import uq.deco2800.duxcom.sound.SoundPlayer;

/**
 * A class that creates a variety of weapons ranging from axe, hammer, sword,
 * dagger, and 3 different magical staffs. The three different magic staff types
 * consist of water, electricity and fire. Weapons have the capability of being
 * used as either a primary or secondary weapon, meaning you can have to weapons
 * in each hand. Weapons are equipped in the hero inventory.
 * 
 * @author Quackin
 * @see HeroInventory
 */
public abstract class Weapon extends EquippableItem {

	// Properties of all Weapons
	protected boolean canBePrimary;
	protected boolean canBeSecondary;
	protected boolean canBeTwoHanded;
	protected boolean isEquippedAsPrimary;
	protected boolean isEquippedAsSecondary;
	protected boolean isTwoHanded;
	protected MusicAsyncProcess musicAsyncProcess;
	protected SoundPlayer soundPlayer;
	protected int damage;
	protected AbstractAbility weaponAbility;
	protected AbstractPassive weaponPassive;

	/**
	 * Weapon constructor used to create the weapons. Each Weapon must contain a
	 * name,cost, weight, sprite location, tradable, durability, primary and
	 * secondary booleans, two handed booleans, damage, rarity, and type. Cost
	 * is used by the shop to determine the value of this weapon when trading if
	 * the tradable parameter is set to true. The inventorySpriteName name is
	 * the location that the sprite is in which should be in resources. The
	 * sprite location should look similar to this example,
	 * "src/main/resources/<yourSpriteHere.png>." Durability determines how
	 * strong the weapon is when in combat in game. A weapon can be either
	 * primary, secondary or both. The player can have equipped either 1 primary
	 * and 1 secondary or 2 secondaries. SOme weapons can be used by one hand
	 * such as the dagger which requires only one hand but the hammer is two
	 * handed as it requires to hands to operate. Each weapon has a set damage
	 * int that is used to determine how much health a enemy will lose when
	 * attacked by a particular weapon. Rarity is used when generating loot and
	 * determines how likely this particular weapon will be at appearing.
	 * 
	 * 
	 * @param name
	 *            String of the weapons name.
	 * @param cost
	 *            int of the weapons value when traded in the shop.
	 * @param weight
	 *            int of the weapons weight.
	 * @param inventorySpriteName
	 *            String of the resource location of the item sprite.
	 * @param tradable
	 *            boolean that allows the item to be tradable in shop.
	 * @param durability
	 *            int value for how tough the particular weapon is.
	 * @param canBePrimary
	 *            boolean representing whether item can be primary.
	 * @param canBeSecondary
	 *            boolean representing whether item can be secondary.
	 * @param canBeTwoHanded
	 *            boolean representing whether item can be two-handed.
	 * @param damage
	 *            int value for the weapons damage amount it can inflict on.
	 *            enemies
	 * @param rarity
	 *            RarityLevel enum for this weapon rarity.
	 * @param type
	 *            ItemType enum for determining the weapon's type as a weapon.
	 */
	public Weapon(String name, int cost, int weight, String inventorySpriteName, boolean tradable, int durability,
			boolean canBePrimary, boolean canBeSecondary, boolean canBeTwoHanded, int damage, RarityLevel rarity,
			ItemType type) {
		super(name, cost, weight, inventorySpriteName, tradable, durability, rarity, type);
		this.canBePrimary = canBePrimary;
		this.canBeSecondary = canBeSecondary;
		this.canBeTwoHanded = canBeTwoHanded;
		this.damage = damage;

		StatisticModifier statModifier = new StatisticModifier(HeroStat.DAMAGE, StatisticModifyAction.ADD, damage);
		this.addStatisticModifier(statModifier);
	}


	public Weapon(String name, String texture, int cost, int weight, String inventorySpriteName, boolean tradable, int durability,
				  boolean canBePrimary, boolean canBeSecondary, boolean canBeTwoHanded, int damage, RarityLevel rarity,
				  ItemType type) {
		super(name, cost, weight, inventorySpriteName, tradable, durability, rarity, type);
		super.image = texture;
		this.canBePrimary = canBePrimary;
		this.canBeSecondary = canBeSecondary;
		this.canBeTwoHanded = canBeTwoHanded;
		this.damage = damage;

		StatisticModifier statModifier = new StatisticModifier(HeroStat.DAMAGE, StatisticModifyAction.ADD, damage);
		this.addStatisticModifier(statModifier);
	}

	/**
	 * Function checks if the weapon is equipped as a primary weapon.
	 * 
	 * @return returns true if the weapon is equipped as primary, false
	 *         otherwise.
	 */
	public boolean isEquippedAsPrimary() {
		return isEquippedAsPrimary;
	}

	/**
	 * Function to equip to hero The function is used to check the current equip
	 * status of the weapon since the weapon cannot access the parent class of
	 * Inventory the equip function is done at Hero Inventory
	 * 
	 * @param isEquippedAsPrimary
	 *            the isEquippedAsPrimary to set
	 * @return isEquippedAsPrimary //Status after operation
	 */
	public boolean setEquippedAsPrimary(boolean isEquippedAsPrimary) {
		if (!this.canBePrimary)
			return false;
		this.isEquippedAsPrimary = isEquippedAsPrimary;
		return this.isEquippedAsPrimary;
	}

	/**
	 * A method that returns true or false if a weapon is equipped as
	 * secondary.Returns true if it is equipped as a secondary weapon, false
	 * otherwise.
	 * 
	 * @return the isEquippedAsSecondary
	 */
	public boolean isEquippedAsSecondary() {
		return isEquippedAsSecondary;
	}

	/**
	 * A method that set and equips a weapon as a secondary weapon. If the
	 * weapon can not be set as secondary returns false, else returns true.
	 * 
	 * @param isEquippedAsSecondary
	 *            the isEquippedAsSecondary to set
	 * @return isEquippedAsSecondary //Status after operation
	 */
	public boolean setEquippedAsSecondary(boolean isEquippedAsSecondary) {
		if (!this.canBeSecondary)
			return false;
		this.isEquippedAsSecondary = isEquippedAsSecondary;
		return this.isEquippedAsSecondary;
	}

	/**
	 * A function that determines if a weapon is one or two handed. Returns true
	 * if two handed, false otherwise.
	 * 
	 * @return the isTwoHanded
	 */

	public boolean isTwoHanded() {
		return isTwoHanded;
	}

	/**
	 * A function that sets a weapon to be two handed. Returns true if
	 * successful, false otherwise.
	 * 
	 * @param isTwoHanded
	 *            the isTwoHanded to set
	 * @return isTwoHanded //Status after operation
	 */
	public boolean setTwoHanded(boolean isTwoHanded) {
		if (!this.canBeTwoHanded)
			return false;
		this.isTwoHanded = isTwoHanded;
		return this.isTwoHanded;
	}

	/**
	 * A function that returns whether this weapon can be a primary weapon.
	 * Returns true iff the weapon is set to be a primary weapon.
	 * 
	 * @return the canBePrimary of the weapon. Can be true for yes, false for
	 *         no.
	 */
	public boolean canBePrimary() {
		return canBePrimary;
	}

	/**
	 * A function that returns if the weapon is set as a secondary weapon.
	 * Returns true iff this weapon is set true to being a secondary weapon.
	 * 
	 * @return the canBeSecondary of the weapon. Can be true for yes, false for
	 *         no.
	 */
	public boolean canBeSecondary() {
		return canBeSecondary;
	}

	/**
	 * A function that returns the set boolean for whether a weapon uses one or
	 * two hands.
	 * 
	 * @return the canBeTwoHanded boolean of the weapon. Can be true for yes,
	 *         false for no.
	 */
	public boolean canBeTwoHanded() {
		return canBeTwoHanded;
	}

	/**
	 * Sets the damage to be inflicted on enemies.
	 * 
	 * @param damage
	 *            the damage to set
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * Returns the damage the weapon delivers
	 *
	 * @return the damage
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Returns the AbstractAbility for the weapon.
	 * 
	 * @return the weaponAbility for the weapon
	 */
	public AbstractAbility getWeaponAbility() {
		return weaponAbility;
	}


	/**
	 * Sets the weaponAbility for the weapon to be used by the weapon.
	 * 
	 * @param weaponAbility
	 *            AbstractAbility to be set for the weapon.
	 */
	public void setWeaponAbility(AbstractAbility weaponAbility) {
		this.weaponAbility = weaponAbility;
	}

	/**
	 * Retrieves the AbstractPassive for the weapon.
	 * 
	 * @return the weaponPassive for the weapon.
	 */
	public AbstractPassive getWeaponPassive() {
		return weaponPassive;
	}

	/**
	 * Sets the AbstractPassive for the weapon as the provided weaponPassive.
	 * 
	 * @param weaponPassive
	 *            the AbstractPassive to set for the weapon
	 */
	public void setWeaponPassive(AbstractPassive weaponPassive) {
		this.weaponPassive = weaponPassive;
	}

	/**
	 * Change default comparison method Weapon with the same name should be
	 * considered as the same
	 */
	@Override
	public boolean equals(Object o) {
		// Same Object check
		if (this == o)
			return true;

		// Check if passed object is a weapon
		if (!(o instanceof Weapon))
			return false;

		// Check if weapon has the same name
		Weapon weapon = (Weapon) o;
		if (weapon.getName().equals(this.name))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

}

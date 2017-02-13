package uq.deco2800.duxcom.entities.heros;

import uq.deco2800.duxcom.abilities.Heal;
import uq.deco2800.duxcom.abilities.Projectile;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.HeroDataClass;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.items.ItemGenerate;

/**
 * Created by woody on 03-Aug-16.
 */
public class Duck extends AbstractHero {

	/**
	 * Attributes of 'Duck'
	 */
	private static final HeroDataClass heroDataClass =
			DataRegisterManager.getHeroDataRegister().getData(HeroType.DUCK);

	/**
	 * Constructs an instance of Duck with the given coordinates
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public Duck(int x, int y) {
		super(EntityType.DUCK, x, y);
		setStats(HeroType.DUCK, heroDataClass);

		super.abilities.add(new Projectile());
		super.utilityAbility = new Heal();
        
        /* Add default items */
        this.inventory.equipPrimaryWeapon(ItemGenerate.sword.VALYRIAN_STEEL_SWORD.generate());
        this.inventory.addItem(ItemGenerate.potion.AP_POTION.generate());
	}

}

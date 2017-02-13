package uq.deco2800.duxcom.entities.heros;

import uq.deco2800.duxcom.abilities.Heal;
import uq.deco2800.duxcom.abilities.Projectile;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.HeroDataClass;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.items.ItemGenerate;

/**
 * Another hero class
 *
 * Created by liamdm on 03-Aug-16.
 */
public class Ibis extends AbstractHero {

	/**
	 * Attributes of 'ibis'
	 */
	private static final HeroDataClass heroDataClass =
			DataRegisterManager.getHeroDataRegister().getData(HeroType.IBIS);

	/**
	 * Constructs an instance of Ibis with the given coordinates
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public Ibis(int x, int y) {
		super(EntityType.REAL_IBIS, x, y);
		setStats(HeroType.IBIS, heroDataClass);

		super.abilities.add(new Projectile());
		super.utilityAbility = new Heal();
        
        /* Add default items */
        this.inventory.equipPrimaryWeapon(ItemGenerate.sword.VALYRIAN_STEEL_SWORD.generate());
        this.inventory.addItem(ItemGenerate.potion.AP_POTION.generate());
        this.inventory.addItem(ItemGenerate.potion.AP_POTION.generate());
        this.inventory.addItem(ItemGenerate.potion.HEALTH_POTION.generate());
	}

}

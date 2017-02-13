package uq.deco2800.duxcom.abilities;

import uq.deco2800.duxcom.dataregisters.AbilityDataClass;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;

/**
 * Created by spress11
 */
public class Projectile extends AbstractAbility {

	/**
	 * Attributes of the ability 'Projectile'
	 */
	private static final AbilityDataClass abilityDataClass =
            DataRegisterManager.getAbilityDataRegister().getData(AbilityName.PROJECTILE);

	public Projectile() {
		setStats(abilityDataClass);
	}
}

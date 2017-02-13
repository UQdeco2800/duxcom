package uq.deco2800.duxcom.abilities;

import uq.deco2800.duxcom.dataregisters.AbilityDataClass;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;

/**
 * Created by spress11
 */
public class Heal extends AbstractAbility {

	/**
	 * Attributes of the ability 'Heal'
	 */
	private static final AbilityDataClass abilityDataClass =
            DataRegisterManager.getAbilityDataRegister().getData(AbilityName.HEAL);

	public Heal() {
		setStats(abilityDataClass);
	}

}

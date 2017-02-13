package uq.deco2800.duxcom.abilities;

import uq.deco2800.duxcom.dataregisters.AbilityDataClass;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;

/**
 * Created by spress11
 */
public class Slash extends AbstractAbility {

	/**
	 * Attributes of the ability 'Slash'
	 */
	private static final AbilityDataClass abilityDataClass =
            DataRegisterManager.getAbilityDataRegister().getData(AbilityName.SLASH);

	public Slash() {
		setStats(abilityDataClass);
	}
}

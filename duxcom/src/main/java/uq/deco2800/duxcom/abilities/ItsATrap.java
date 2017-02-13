package uq.deco2800.duxcom.abilities;

import uq.deco2800.duxcom.dataregisters.AbilityDataClass;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;

/**
 * Created by shamous123
 */
public class ItsATrap extends AbstractAbility {

	/**
	 * Attributes of the ability 'Bomb'
	 */
	private static final AbilityDataClass abilityDataClass =
            DataRegisterManager.getAbilityDataRegister().getData(AbilityName.TRAP);

	public ItsATrap() {
		setStats(abilityDataClass);
	}
}

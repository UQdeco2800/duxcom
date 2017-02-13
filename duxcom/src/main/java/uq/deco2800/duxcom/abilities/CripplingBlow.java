package uq.deco2800.duxcom.abilities;

import uq.deco2800.duxcom.dataregisters.AbilityDataClass;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;

/**
 * Created by spress11
 */
public class CripplingBlow extends AbstractAbility {

	/**
	 * Attributes of the ability 'CripplingBlow'
	 */
	private static final AbilityDataClass abilityDataClass =
            DataRegisterManager.getAbilityDataRegister().getData(AbilityName.CRIPPLINGBLOW);

	public CripplingBlow() {
		setStats(abilityDataClass);
	}
}

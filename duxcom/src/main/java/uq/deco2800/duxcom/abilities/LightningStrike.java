package uq.deco2800.duxcom.abilities;

import uq.deco2800.duxcom.dataregisters.AbilityDataClass;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;

/**
 * Created by spress11
 */
public class LightningStrike extends AbstractAbility {

	/**
	 * Attributes of the ability 'LightningStrike'
	 */
	private static final AbilityDataClass abilityDataClass =
            DataRegisterManager.getAbilityDataRegister().getData(AbilityName.LIGHTNINGSTRIKE);

	public LightningStrike() {
		setStats(abilityDataClass);
	}
}

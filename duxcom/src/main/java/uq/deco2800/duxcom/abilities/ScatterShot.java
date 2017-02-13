package uq.deco2800.duxcom.abilities;

import uq.deco2800.duxcom.dataregisters.AbilityDataClass;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;

/**
 * Created by spress11
 */
public class ScatterShot extends AbstractAbility {

	/**
	 * Attributes of the ability 'Scatter Shot'
	 */
	private static final AbilityDataClass abilityDataClass =
            DataRegisterManager.getAbilityDataRegister().getData(AbilityName.SCATTERSHOT);

	public ScatterShot() {
		setStats(abilityDataClass);
	}

}
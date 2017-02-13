package uq.deco2800.duxcom.abilities;

import uq.deco2800.duxcom.common.DamageType;
import uq.deco2800.duxcom.dataregisters.AbilityDataClass;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.entities.AbstractCharacter;

/**
 * Created by jay-grant
 */
public class ShortRangeTestAbility extends AbstractAbility {

	/**
	 * Attributes of the ability 'LongRangeTestAbility'
	 */
	private static final AbilityDataClass abilityDataClass =
            DataRegisterManager.getAbilityDataRegister().getData(AbilityName.SHORTRANGETESTABILITY);

	public ShortRangeTestAbility() {
		setStats(abilityDataClass);
	}

	@Override
	protected boolean useOnFoe(AbstractCharacter origin, Targetable target) {
		target.changeHealth(-12.0, DamageType.NORMAL);
		this.setCooldownTurnsLeft(getCooldownTurns());
		return true;
	}

    @Override
    public boolean useOnFriend(AbstractCharacter origin, Targetable target) {
    	return false;
    }
}

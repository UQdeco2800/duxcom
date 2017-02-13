package uq.deco2800.duxcom.abilities;

import uq.deco2800.duxcom.buffs.InSightBuff;
import uq.deco2800.duxcom.dataregisters.AbilityDataClass;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.entities.AbstractCharacter;

/**
 * Created by shamous123
 */
public class InSight extends AbstractAbility {

	/**
	 * Attributes of the ability 'Insight'
	 */
	private static final AbilityDataClass abilityDataClass =
            DataRegisterManager.getAbilityDataRegister().getData(AbilityName.INSIGHT);

	public InSight() {
		setStats(abilityDataClass);
	}

    @Override
    public boolean useOnFoe(AbstractCharacter origin, Targetable target) {
    	return false;
    }
    
    @Override
    public boolean useOnFriend(AbstractCharacter origin, Targetable target) {
    	target.addBuff(new InSightBuff(3,3));
    	this.setCooldownTurnsLeft(getCooldownTurns());
    	return true;
    }
}

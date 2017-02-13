package uq.deco2800.duxcom.abilities;

import uq.deco2800.duxcom.buffs.InnerStrengthArmourBuff;
import uq.deco2800.duxcom.buffs.InnerStrengthDamageBuff;
import uq.deco2800.duxcom.dataregisters.AbilityDataClass;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.entities.AbstractCharacter;

/**
 * Created by spress11
 */
public class InnerStrength extends AbstractAbility {

	/**
	 * Attributes of the ability 'InnerStrength'
	 */
	private static final AbilityDataClass abilityDataClass =
            DataRegisterManager.getAbilityDataRegister().getData(AbilityName.INNERSTRENGTH);

	public InnerStrength() {
		setStats(abilityDataClass);
	}
    
    @Override
    public boolean useOnFriend(AbstractCharacter origin, Targetable target) {
    	target.addBuff(new InnerStrengthArmourBuff(5,4));
    	target.addBuff(new InnerStrengthDamageBuff(1.5,4));
    	this.setCooldownTurnsLeft(getCooldownTurns());
    	return true;
    }
}

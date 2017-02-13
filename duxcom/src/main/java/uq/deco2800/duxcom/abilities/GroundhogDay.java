package uq.deco2800.duxcom.abilities;

import uq.deco2800.duxcom.buffs.GroundhogDayBuff;
import uq.deco2800.duxcom.dataregisters.AbilityDataClass;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.heros.AbstractHero;

/**
 * Created by spress11
 */
public class GroundhogDay extends AbstractAbility {
	
	/**
	 * Attributes of the ability 'GroundhogDay'
	 */
	private static final AbilityDataClass abilityDataClass =
            DataRegisterManager.getAbilityDataRegister().getData(AbilityName.GROUNDHOGDAY);

	public GroundhogDay() {
		setStats(abilityDataClass);
	}


	@Override
    protected boolean useOnFriend(AbstractCharacter origin, Targetable target) {
		origin.addBuff(new GroundhogDayBuff((AbstractHero)origin, 1, 1));
		this.setCooldownTurnsLeft(getCooldownTurns());
		return true;
    }

}
package uq.deco2800.duxcom.abilities;

import uq.deco2800.duxcom.dataregisters.AbilityDataClass;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.sound.SoundPlayer;

/**
 * Created by spress11
 */
public class Stab extends AbstractAbility {

	/**
	 * Attributes of the ability 'Stab'
	 */
	private static final AbilityDataClass abilityDataClass =
            DataRegisterManager.getAbilityDataRegister().getData(AbilityName.STAB);

	public Stab() {
		setStats(abilityDataClass);
		SoundPlayer.playAttack();
	}

    @Override
    public boolean useOnFriend(AbstractCharacter origin, Targetable target) {
    	return false;
    }
}

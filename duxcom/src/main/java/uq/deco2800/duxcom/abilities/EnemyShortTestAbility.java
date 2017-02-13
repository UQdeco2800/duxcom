package uq.deco2800.duxcom.abilities;

import uq.deco2800.duxcom.common.DamageType;
import uq.deco2800.duxcom.dataregisters.AbilityDataClass;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.entities.AbstractCharacter;

/**
 * Created by jay-grant on 12/10/2016.
 */
public class EnemyShortTestAbility extends AbstractAbility {

    /**
     * Attributes of the ability 'LongRangeTestAbility'
     */
    private static final AbilityDataClass abilityDataClass =
            DataRegisterManager.getAbilityDataRegister().getData(AbilityName.SHORTRANGETESTABILITY);

    public EnemyShortTestAbility() {
        setStats(abilityDataClass);
    }

    @Override
    protected boolean useOnFoe(AbstractCharacter origin, Targetable target) {
        target.changeHealth(-9.0, DamageType.NORMAL);
        this.setCooldownTurnsLeft(getCooldownTurns());
        return true;
    }

    @Override
    public boolean useOnFriend(AbstractCharacter origin, Targetable target) {
        return false;
    }
}

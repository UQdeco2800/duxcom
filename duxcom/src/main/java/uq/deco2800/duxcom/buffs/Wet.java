package uq.deco2800.duxcom.buffs;

import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.common.DamageType;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.heros.HeroStat;

public class Wet extends AbstractBuff{
	private static final String NAME = "Soaked with  Water";
	private static final String DESCRIPTION = "You are soaked, taking extra damage from electricity";
	private static final HeroStat STATAFFECTED = HeroStat.VISIBILITY_RANGE;

	public Wet(double strength, int duration) {
		super(strength, duration);
		super.type = BuffRegister.WET;
		super.name = NAME;
		super.description = DESCRIPTION;
		super.statAffected = STATAFFECTED;
	}

	@Override
	public void onAbilityEffect(Entity origin, AbstractAbility ability) {
		if(ability.getDamageType() == DamageType.ELECTRIC) {
			ability.addTemporaryDamageMultiplier(2);
		}
	}
}

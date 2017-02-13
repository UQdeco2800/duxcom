package uq.deco2800.duxcom.buffs;

import uq.deco2800.duxcom.entities.heros.HeroStat;

/**
 * 
 * Created by spress11 on 30-Aug-16.
 *
 */
public class InnerStrengthDamageBuff extends AbstractBuff{
	private static final String NAME = "Inner Strength Damage Buff";
	private static final String DESCRIPTION = "Increased damage from Inner Strength";
	private static final HeroStat STATAFFECTED = HeroStat.DAMAGE;
	
	public InnerStrengthDamageBuff(double strength, int duration) {
		super(strength, duration);
		super.type = BuffRegister.DAMAGE_PLUS;
		super.name = NAME;
		super.description = DESCRIPTION;
		super.statAffected = STATAFFECTED;
	}
}

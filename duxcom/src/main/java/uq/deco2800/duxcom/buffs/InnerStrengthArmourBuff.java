package uq.deco2800.duxcom.buffs;

import uq.deco2800.duxcom.entities.heros.HeroStat;

/**
 * 
 * Created by spress11 on 30-Aug-16.
 *
 */
public class InnerStrengthArmourBuff extends AbstractBuff{
	private static final String NAME = "Inner Strength Armour Buff";
	private static final String DESCRIPTION = "Increased bodyArmour from Inner Strength";
	private static final HeroStat STATAFFECTED = HeroStat.ARMOUR;
	
	public InnerStrengthArmourBuff(double strength, int duration) {
		super(strength, duration);
		super.type = BuffRegister.ARMOUR_PLUS;
		super.name = NAME;
		super.description = DESCRIPTION;
		super.statAffected = STATAFFECTED;
	}
}

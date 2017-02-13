package uq.deco2800.duxcom.buffs;

import uq.deco2800.duxcom.entities.heros.HeroStat;

/**
 * 
 * Created by shamous123 on 18-Sep-16.
 *
 */
public class InSightBuff extends AbstractBuff{
	private static final String NAME = "Insight vision buff";
	private static final String DESCRIPTION = "Increase the vision radius for 3 turns";
	private static final HeroStat STATAFFECTED = HeroStat.VISIBILITY_RANGE;

	public InSightBuff(double strength, int duration) {
		super(strength, duration);
		super.type = BuffRegister.SPOTTED;
		super.name = NAME;
		super.description = DESCRIPTION;
		super.statAffected = STATAFFECTED;
	}
}

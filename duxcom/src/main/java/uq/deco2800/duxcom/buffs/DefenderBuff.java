package uq.deco2800.duxcom.buffs;

import uq.deco2800.duxcom.entities.heros.HeroStat;

/**
 * 
 * Created by spress11.
 *
 */
public class DefenderBuff extends AbstractBuff{
	private static final String NAME = "Defender Buff";
	private static final String DESCRIPTION = "This unit is receiving increased armour from the Knight's Defender passive!";
	private static final HeroStat STATAFFECTED = HeroStat.ARMOUR;

	public DefenderBuff(double strength, int duration) {
		super(strength, duration);
		super.type = BuffRegister.DEFENDER;
		super.name = NAME;
		super.description = DESCRIPTION;
		super.statAffected = STATAFFECTED;
	}
}

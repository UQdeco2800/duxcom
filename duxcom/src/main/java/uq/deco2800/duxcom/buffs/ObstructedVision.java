package uq.deco2800.duxcom.buffs;

import uq.deco2800.duxcom.entities.heros.HeroStat;

/**
 * 
 * Created by spress11.
 *
 */
public class ObstructedVision extends AbstractBuff{
	private static final String NAME = "Vision Obstructed";
	private static final String DESCRIPTION = "Visibility range is decreased";
	private static final HeroStat STATAFFECTED = HeroStat.VISIBILITY_RANGE;

	public ObstructedVision(double strength, int duration) {
		super(strength, duration);
		super.type = BuffRegister.BLIND;
		super.name = NAME;
		super.description = DESCRIPTION;
		super.statAffected = STATAFFECTED;
	}
}

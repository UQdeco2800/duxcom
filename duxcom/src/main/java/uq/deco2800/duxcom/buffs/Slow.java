package uq.deco2800.duxcom.buffs;

import uq.deco2800.duxcom.entities.heros.HeroStat;

/**
 * 
 * Created by spress11
 *
 */
public class Slow extends AbstractBuff{
	private static final String NAME = "Slow";
	private static final String DESCRIPTION = "You are crippled, your speed is decreased";
	private static final HeroStat STATAFFECTED = HeroStat.SPEED;

	public Slow(double strength, int duration) {
		super(strength, duration);
		super.type = BuffRegister.SLOW;
		super.name = NAME;
		super.description = DESCRIPTION;
		super.statAffected = STATAFFECTED;
		
	}
}

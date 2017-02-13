package uq.deco2800.duxcom.buffs;

import uq.deco2800.duxcom.entities.heros.HeroStat;

/**
 * 
 * Created by spress11
 *
 */
public class Vulnerable extends AbstractBuff{
	private static final String NAME = "Vulnerable";
	private static final String DESCRIPTION = "You are vulnerable, your bodyArmour is decreased";
	private static final HeroStat STATAFFECTED = HeroStat.ARMOUR;

	public Vulnerable(double strength, int duration) {
		super(strength, duration);
		super.type = BuffRegister.VULNERABLE;
		super.name = NAME;
		super.description = DESCRIPTION;
		super.statAffected = STATAFFECTED;
	}
}

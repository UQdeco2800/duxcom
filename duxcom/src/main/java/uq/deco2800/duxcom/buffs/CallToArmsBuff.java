package uq.deco2800.duxcom.buffs;

import uq.deco2800.duxcom.entities.heros.HeroStat;

/**
 * 
 * Created by spress11.
 *
 */
public class CallToArmsBuff extends AbstractBuff{
	private static final String NAME = "Call To Arms Buff";
	private static final String DESCRIPTION = "Increased damage from Call To Arms";
	private static final HeroStat STATAFFECTED = HeroStat.DAMAGE;
	
	public CallToArmsBuff(double strength, int duration) {
		super(strength, duration);
		super.type = BuffRegister.CALL_TO_ARMS;
		super.name = NAME;
		super.description = DESCRIPTION;
		super.statAffected = STATAFFECTED;
	}
}

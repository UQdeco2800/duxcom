package uq.deco2800.duxcom.buffs;

import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.heros.HeroStat;

/**
 * 
 * Created by spress11
 *
 */
public class Bleeding extends AbstractBuff{
	private static final String NAME = "Bleeding";
	private static final String DESCRIPTION = "You are bleeding, losing health every turn";
	private static final HeroStat STATAFFECTED = HeroStat.HEALTH;

	public Bleeding(double strength, int duration) {
		super(strength, duration);
		super.type = BuffRegister.BLEEDING;
		super.name = NAME;
		super.description = DESCRIPTION;
		super.statAffected = STATAFFECTED;
	}


	@Override
	public void onTurn(AbstractCharacter character) {
		character.changeHealth(-strength);
	}
}
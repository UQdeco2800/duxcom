package uq.deco2800.duxcom.buffs;

import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.heros.HeroStat;

/**
 * 
 * Created by spress11 on 06-Sep-16.
 *
 */
public class Poisoned extends AbstractBuff{
	private static final String NAME = "Poisoned";
	private static final String DESCRIPTION = "You are poisoned, losing health every turn";
	private static final HeroStat STATAFFECTED = HeroStat.HEALTH;

	public Poisoned(double strength, int duration) {
		super(strength, duration);
		super.type = BuffRegister.POISONED;
		super.name = NAME;
		super.description = DESCRIPTION;
		super.statAffected = STATAFFECTED;
	}

	@Override
	public void onTurn(AbstractCharacter character) {
		character.changeDamage(-strength);
	}
}
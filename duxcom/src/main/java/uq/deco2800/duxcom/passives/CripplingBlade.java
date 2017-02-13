package uq.deco2800.duxcom.passives;

import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.Targetable;
import uq.deco2800.duxcom.buffs.Slow;
import uq.deco2800.duxcom.entities.heros.AbstractHero;

/**
 * Created by spress11
 */
public class CripplingBlade extends AbstractPassive{
	
	private static final String NAME = "Crippling Blade";
	private static final int ACTIVATIONTIMER = 1;
	
	public CripplingBlade(AbstractHero hero) {
		super.hero = hero;
		super.name = NAME;
		super.activationTimer = ACTIVATIONTIMER;
		super.description = "Slow the enemy when you attack";
	}

	@Override
	public void activateOnAbilityUse(Targetable target, AbstractAbility ability) {
		target.addBuff(new Slow(0.2,3));
	}

}

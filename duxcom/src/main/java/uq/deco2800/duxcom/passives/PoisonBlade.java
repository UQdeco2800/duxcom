package uq.deco2800.duxcom.passives;

import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.Targetable;
import uq.deco2800.duxcom.buffs.Poisoned;
import uq.deco2800.duxcom.entities.heros.AbstractHero;

/**
 * Created by spress11 on 06-Sep-16.
 */
public class PoisonBlade extends AbstractPassive{
	
	private static final String NAME = "Poison Blade";
	private static final int ACTIVATIONTIMER = 1;
	
	public PoisonBlade(AbstractHero hero) {
		super.hero = hero;
		super.name = NAME;
		super.activationTimer = ACTIVATIONTIMER;
		super.description = "Poison the enemy when you attack";
	}

	@Override
	public void activateOnAbilityUse(Targetable target, AbstractAbility ability) {
		target.addBuff(new Poisoned(4,3));
	}
	
}

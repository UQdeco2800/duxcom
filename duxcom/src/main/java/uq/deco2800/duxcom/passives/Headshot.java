package uq.deco2800.duxcom.passives;

import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.Targetable;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.heros.AbstractHero;

/**
 * Created by spress11
 * 
 * THIS PASSIVE IS NOT COMPLETE, DO NOT IMPLEMENT IT
 */
public class Headshot extends AbstractPassive{
	
	private static final String NAME = "Headshot";
	private static final int ACTIVATIONTIMER = 3;
	private int timerCount = 0;
	private boolean ready = false;
	
	public Headshot(AbstractHero hero) {
		super.hero = hero;
		super.name = NAME;
		super.activationTimer = ACTIVATIONTIMER;
		super.description = "Every three turns, the archer will headshot enemies and deal additional damage";
	}
	
	@Override
	public void turnTick() {
		if (timerCount >= 3) {
			ready = true;
		} else {
			timerCount++;
		}
	}

	@Override
	public void activateOnAbilityUse(Targetable target, AbstractAbility ability) {
		if (ready) {
			ability.addTemporaryDamageMultiplier(1.5);
			ready = false;
			timerCount = 0;
		}
	}

	@Override
	public void activateOnMove() {
		//do nothing
	}

	@Override
	public void activateOnAbilityEffect(Entity origin, AbstractAbility ability) {
		//do nothing
	}
	
}

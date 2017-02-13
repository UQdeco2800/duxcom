package uq.deco2800.duxcom.passives;

import uq.deco2800.duxcom.entities.heros.AbstractHero;

/**
 * Created by spress11 on 29-Aug-16.
 */
public class HealthRegen extends AbstractPassive{
	
	private static final String NAME = "Champion's Vigor";
	private static final int ACTIVATIONTIMER = 1;
	
	public HealthRegen(AbstractHero hero) {
		super.hero = hero;
		super.name = NAME;
		super.activationTimer = ACTIVATIONTIMER;
		super.description = "Regain some health each turn";
	}
	
	@Override
	public void turnTick() {
		super.hero.changeHealth(5);
	}
}

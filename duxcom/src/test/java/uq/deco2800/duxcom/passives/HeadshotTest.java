package uq.deco2800.duxcom.passives;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.Projectile;
import uq.deco2800.duxcom.abilities.Targetable;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.Archer;
import uq.deco2800.duxcom.entities.heros.Ibis;

public class HeadshotTest {
	@Test
	public void nameTest() {
		AbstractHero hero = new Archer(1, 2);
		Headshot headshot = new Headshot(hero);

		assertTrue("Headshot".equals(headshot.getName()));
	}

	@Test
	public void activationTimerTest() {
		AbstractHero hero = new Archer(1, 2);
		Headshot headshot = new Headshot(hero);

		assertTrue(headshot.getActivationTimer() == 3);
	}

	@Test
	public void descriptionTest() {
		AbstractHero hero = new Archer(1, 2);
		Headshot headshot = new Headshot(hero);

		assertTrue("Every three turns, the archer will headshot enemies and deal additional damage".equals(headshot.getDescription()));
	}
	
	@Ignore
	@Test
	public void activateOnAbilityUse() {
		AbstractPassive passive = new Headshot(new Ibis(0,0));
		AbstractAbility ability = new Projectile();
		AbstractHero hero = new Archer(1, 2);
		AbstractHero target = new Archer(1, 2);
		double damage = ability.getDamage();
		target.setHealth(100);
		passive.turnTick();
		passive.turnTick();
		passive.turnTick();
		ArrayList<AbstractPassive> passives = new ArrayList<AbstractPassive>();
		passives.add(passive);
		hero.setPassives(passives);
		ArrayList<AbstractAbility> abilities = new ArrayList<AbstractAbility>();
		abilities.add(ability);
		hero.setAbilities(abilities);
		hero.activatePassives(target, ability);
		//assertTrue(hero.useOnFoe((Entity)hero, (Targetable)target));
		assertEquals(100-(damage*1.5), target.getHealth(), 0);
	}

}

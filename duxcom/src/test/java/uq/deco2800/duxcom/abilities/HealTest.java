package uq.deco2800.duxcom.abilities;

import org.junit.Test;

import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.Duck;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by wondertroy on 16/08/2016.
 */
public class HealTest {
	@Test
	public void useOnHero() throws Exception {
		AbstractHero hero = new Duck(1, 1);
		AbstractHero target = new Duck(1,2);
		AbstractAbility heal = new Heal();

		target.setHealth(80);
		assertTrue(heal.useOnTarget(hero, target));
		assertEquals(92, (int) target.getHealth());

		assertFalse(heal.useOnTarget(hero, target));
		heal.turnTick();
		assertTrue(heal.useOnTarget(hero, target));
		assertEquals(100, (int) target.getHealth());
	}

	@Test
	public void useOnPoint() throws Exception {

	}

}
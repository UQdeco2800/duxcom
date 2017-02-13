package uq.deco2800.duxcom.passives;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.Ibis;
import uq.deco2800.duxcom.entities.heros.Knight;

public class AbstractPassiveTest {
	AbstractHero hero = new Knight(0, 0);
	AbstractPassive passive = new HealthRegen(hero);
	
	
	@Test
	public void getName() throws Exception {
		assertEquals("Champion's Vigor",passive.getName());
	}
	
	@Test
	public void setName() throws Exception {
		passive.setName("test");
		assertEquals("test", passive.getName());
	}
	
	@Test
	public void getDescription() throws Exception {
		assertEquals("Regain some health each turn", passive.getDescription());
	}
	
	@Test
	public void TurnTick() throws Exception  {
		ArrayList<AbstractPassive> passives = new ArrayList<>();
		passives.add(passive);
		hero.setPassives(passives);
		hero.setHealth(50);
		hero.onTurn();
		assertEquals(55,(int)hero.getHealth());
	}

	@Test
	public void setActivationTimerTest() {
		/* seems trivial, but ensures nobody changes the way in which the activation timer is 'calculated' */
		AbstractHero hero = new Knight(0, 0);
		AbstractPassive passive = new HealthRegen(hero);

		passive.setActivationTimer(1);
		assertTrue(1 == passive.getActivationTimer());
	}
}

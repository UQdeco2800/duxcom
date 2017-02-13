package uq.deco2800.duxcom.buffs;

import org.junit.Test;

import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.Priest;

import static org.junit.Assert.assertEquals;

public class OnFireBuffTest {
	@Test
	public void onHeroTurn() {
		AbstractHero hero = new Priest(1,1);
		int strength = 5;
		int duration = 3;
		AbstractBuff buff = new OnFire(strength, duration);
		int health = 100;
		hero.setHealth(health);
		
		while(buff.tickBuff()){
			buff.onTurn(hero);
			assertEquals(health-strength, (int)hero.getHealth());
			health = (int)hero.getHealth();
		}
		assertEquals(100-(strength*(duration)), (int)hero.getHealth());
	}
	
}

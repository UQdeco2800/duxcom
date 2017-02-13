package uq.deco2800.duxcom.abilities;

import org.junit.Test;

import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyTank;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.Knight;

import static org.junit.Assert.assertEquals;

/**
 * Created by spress11 on 03/09/2016.
 */
public class SlashTest {
	@Test
	public void useOnHero() throws Exception {
		AbstractHero hero = new Knight(1, 1);
		AbstractEnemy target1 = new EnemyTank(1,2);
		AbstractEnemy target2 = new EnemyTank(1,3);
		AbstractAbility slash = new Slash();
		target1.setHealth(100);
		target2.setHealth(100);
		slash.useOnTarget(hero, target1);
		assertEquals(85, (int) target1.getHealth());
		slash.useOnTarget(hero, target2);
		assertEquals(100, (int) target2.getHealth());

	}

	@Test
	public void useOnPoint() throws Exception {

	}

}
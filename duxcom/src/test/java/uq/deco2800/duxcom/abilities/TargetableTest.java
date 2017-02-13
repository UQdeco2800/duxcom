package uq.deco2800.duxcom.abilities;

import org.junit.Test;
import org.mockito.Mock;

import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.entities.dynamics.BreakableWoodStack;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyKnight;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyTank;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.BetaTester;
import uq.deco2800.duxcom.entities.heros.Duck;
import uq.deco2800.duxcom.maps.DemoMap;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Ignore;

/**
 * @author houraineet
 */
public class TargetableTest {
	@Mock
	private GameManager game;
	@Mock
	private MapAssembly map;

	@Ignore
	@Test
	public void useAbilityTest() throws Exception {
		//when(GameLoop.getCurrentGameManager()).thenReturn(game);
		when(game.getMap()).thenReturn(map);
		AbstractHero hero = new Duck(1, 1);
		AbstractEnemy target = new EnemyTank(1, 2);
		
		target.setHealth(100);
		assertTrue(hero.useAbility(0, target.getX(), target.getY())); // Projectile
		assertEquals(4, hero.getActionPoints());
		assertEquals(88, (int) target.getHealth());
	}
	
	@Test
	public void rangeTest() throws Exception {
		AbstractHero hero = new Duck(1, 1);
		AbstractEnemy target1 = new EnemyKnight(1, 2);
		AbstractEnemy target2 = new EnemyKnight(5, 5);
		AbstractAbility slash = new Slash(); // range of 1
		
		target1.setHealth(100);
		assertTrue(slash.useOnTarget(hero, target1));
		assertEquals(85, (int) target1.getHealth());
		
		assertFalse(slash.useOnTarget(hero, target2));
	}
	
	@Test
	public void abilityOnEnemyTest() throws Exception {
		AbstractHero hero = new Duck(1, 1);
		AbstractEnemy enemy = new EnemyKnight(1, 2);
		AbstractAbility slash = new Slash();
		AbstractAbility heal = new Heal();
		assertTrue(slash.useOnTarget(hero, enemy));
	}
	
//	@Test @Ignore
//	public void woodStackTest() throws Exception {
//		AbstractHero hero = new Duck(1, 1);
//		BreakableWoodStack target1 = new BreakableWoodStack(1, 2);
//		BreakableWoodStack target2 = new BreakableWoodStack(5, 5);
//		AbstractAbility slash = new Slash(); // range of 1
//		target1.setHealth(25.0);
//		assertTrue(slash.useOnTarget(hero, target1));
//		assertTrue(target1.getHealth() == (25.0 + DataRegisterManager.getAbilityDataRegister().getData(AbilityName.SLASH).getDamage()));
//		assertFalse(slash.useOnTarget(hero, target2)); // too far
//	}
	
//	@Test @Ignore
//	public void woodStackOnFireTest() throws Exception {
//		AbstractHero hero = new Duck(1, 1);
//		BreakableWoodStack target = new BreakableWoodStack(1, 2);
//		AbstractAbility fireball = new Fireball();
//		target.setHealth(25.0);
//		assertTrue(fireball.useOnTarget(hero, target));
//		assertTrue(target.isOnFire());
//	}
	
	@Test
	public void betaTesterImmunityTest() throws Exception {
		AbstractHero hero = new Duck(1, 1);
		BetaTester ibis = new BetaTester(1, 2);
		AbstractAbility slash = new Slash();
		AbstractAbility fireball = new Fireball();
		
		assertFalse(slash.useOnTarget(hero, ibis));
		assertFalse(fireball.useOnTarget(hero, ibis));
	}
	
	@Test
	public void useAbilityOutOfBoundsTest() throws Exception {
		AbstractHero hero = new Duck(1, 1);
		AbstractHero target = new Duck(1, 2);
		
		assertFalse(hero.useAbility(2147483646, target.getX(), target.getY())); // out of bounds
	}
}

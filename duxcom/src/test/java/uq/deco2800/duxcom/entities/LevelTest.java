package uq.deco2800.duxcom.entities;

import org.junit.Test;

import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.Knight;
import static org.junit.Assert.*;

public class LevelTest {

	@Test
	public void initialLevel() throws Exception {
		AbstractHero test = new Knight(1, 1);
		assertEquals(0, test.getLevel());
	}
	
	@Test
	public void initialXP() throws Exception {
		AbstractHero test = new Knight(1, 1);
		assertEquals(0, test.getXp());
	}
	
	@Test
	public void increaseXP() throws Exception {
		AbstractHero test = new Knight(1, 1);
		test.changeXP(10);
		assertEquals(10, test.getXp());
	}
	
	@Test
	public void increaseLevel() throws Exception {
		AbstractHero test = new Knight(1, 1);
		test.changeXP(1000);
		assertEquals(1, test.getLevel());
		assertEquals(0, test.getXp());
	}
	
	@Test
	public void carryOverXP() throws Exception {
		AbstractHero test = new Knight(1, 1);
		test.changeXP(1010);
		assertEquals(1, test.getLevel());
		assertEquals(10, test.getXp());
	}
	
	@Test
	public void multipleLevel() throws Exception {
		AbstractHero test = new Knight(1, 1);
		test.changeXP(3310);
		assertEquals(2, test.getLevel());
		assertEquals(1210, test.getXp());
		test.changeXP(500);
		assertEquals(3, test.getLevel());
		assertEquals(410, test.getXp());
	}
	
}

package uq.deco2800.duxcom.entities;

import org.junit.Test;

import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.Knight;
import static org.junit.Assert.*;

public class AbstractCharacterTest {

	@Test
	public void abstractCharacterDamageTest() throws Exception {
		AbstractHero test = new Knight(1, 1);
		double damage = 0;
		test.changeDamage(-100000);
		assertEquals(test.getDamage(), damage, 0);
		test.changeDamage(10);
		damage = 10;
		assertEquals(test.getDamage(), damage, 0);
		damage = 5;
		test.setDamage(5);
		assertEquals(test.getDamage(), damage, 0);
	}
	
	@Test
	public void abstractCharacterGraphicTest() throws Exception {
		AbstractHero test = new Knight(1, 1);
		test.setImageName("TROLLLOLOLOLOL");
		assertEquals(test.getImageName(), "TROLLLOLOLOLOL_sword1_none");
	}
	
	
	
}

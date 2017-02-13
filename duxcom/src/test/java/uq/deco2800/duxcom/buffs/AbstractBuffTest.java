package uq.deco2800.duxcom.buffs;

import org.junit.Test;

import uq.deco2800.duxcom.entities.heros.HeroStat;

import static org.junit.Assert.*;

/**
 * Created by spress11 on 03/09/2016.
 */
public class AbstractBuffTest {

	AbstractBuff buff = new InnerStrengthArmourBuff(5,3);

	@Test
	public void getName() throws Exception {
		assertEquals("Inner Strength Armour Buff", buff.getName());
	}

	@Test
	public void setName() throws Exception {
		buff.setName("test");
		assertEquals("test", buff.getName());
	}

	/**
	 * A buff must have a duration of at least 1
	 * @throws Exception
	 */
	@Test
	public void getDuration() throws Exception {
		assertTrue(buff.getDuration() >= 1);
	}

	@Test
	public void setDuration() throws Exception {
		buff.setDuration(6);
		assertEquals(6, buff.getDuration());
	}

	/**
	 * Buff must affect a HeroStat
	 * @throws Exception
	 */
	@Test
	public void getStatsAffected() throws Exception {
		assertTrue(buff.getStatAffected() instanceof HeroStat);
	}

	@Test
	public void getDescription() throws Exception {
		assertEquals("Increased bodyArmour from Inner Strength", buff.getDescription());
	}

	/**
	 * Buffs duration should decrement on turnTick
	 * @throws Exception
	 */
	@Test
	public void tickBuff() throws Exception {
		int duration = buff.getDuration();
		while(buff.tickBuff()) {
			assertTrue(buff.getDuration() == duration-1);
			duration = buff.getDuration();
		}
		assertTrue(buff.getDuration() <= 0);
	}


}
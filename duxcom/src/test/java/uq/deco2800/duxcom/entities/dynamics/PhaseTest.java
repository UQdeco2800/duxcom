package uq.deco2800.duxcom.entities.dynamics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import uq.deco2800.duxcom.entities.EntityType;

/**
 * @author houraineet
 */
public class PhaseTest {
	@Test
	public void entityTypeTest() {
		Phase p = new Phase(EntityType.CAVALIER);
		assertTrue(p.getEntityType() == EntityType.CAVALIER);
		
		p.entityType(EntityType.CAVALIER_AQUA);
		assertTrue(p.getEntityType() == EntityType.CAVALIER_AQUA);
	}
	
	@Test
	public void nameTest() {
		Phase p = new Phase().name("potato");
		assertTrue(p.getName().equals("potato"));
	}
	
	@Test
	public void transmissionRateTest() {
		Phase p = new Phase().transmissionRate(13);
		assertTrue(p.getTransmissionRate() == 13);
		assertTrue(p.transmits());
		
		Phase p2 = new Phase();
		assertTrue(p2.getTransmissionRate() == -1);
		assertFalse(p2.transmits());
	}
	
	@Test
	public void damageTest() {
		Phase p = new Phase().damage(1.5);
		assertTrue(p.getDamage() == 1.5);
		
		p.damage(3.5);
		assertTrue(p.getDamage() == 3.5);
		
		Phase p2 = new Phase();
		assertTrue(p2.getDamage() == 0);
	}
	
	@Test
	public void tickIntervalTest() {
		Phase p = new Phase().tickInterval(100L);
		assertTrue(p.getTickInterval() == 100L);
	}
}

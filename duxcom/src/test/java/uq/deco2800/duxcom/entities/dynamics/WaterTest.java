package uq.deco2800.duxcom.entities.dynamics;

import org.junit.Test;
import uq.deco2800.duxcom.entities.DynamicsManager;
import uq.deco2800.duxcom.maps.DynamicEntityTestMap;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import static org.junit.Assert.assertTrue;

/**
 * @author tiget
 */
public class WaterTest {
	@Test
	public void syncStringTest() {
		Water water = new Water(1, 2, 3);
        assertTrue(water.getSyncString() == "WATER");
	}

	@Test
	public void tickIntervalTest() {
		Water water = new Water(1, 2, -4);
        assertTrue(water.getTickInterval() == 150L);
	}

	@Test
	public void animationTickTest() {
		MapAssembly map = new MapAssembly(new DynamicEntityTestMap("test"));
		DynamicsManager manager = map.getDynamicsManager();

		Water water = new Water(0, 0, 3);
		map.addEntity(water);

		/* Make sure the entity is surrounded by water */
		assertTrue(map.getEntities(1, 0).isEmpty());
		water.animationTick();
		assertTrue(!map.getEntities(1, 0).isEmpty());
	}
}

package uq.deco2800.duxcom.entities.dynamics;

import org.junit.Ignore;
import org.junit.Test;
import uq.deco2800.duxcom.entities.DynamicsManager;
import uq.deco2800.duxcom.maps.DynamicEntityTestMap;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.maps.mapgen.bounds.Coordinate;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author houraineet
 */
public class DynamicsManagerTest {
	
	@Test
	public void getTowardsTest() {
		Coordinate p1 = DynamicsManager.getTowards(0, 0, 0, 5, 3);
		assertTrue(p1.x == 0);
		assertTrue(p1.y == 3);
		
		Coordinate p2 = DynamicsManager.getTowards(5, 5, 0, 0, 1); // rounded to 1, 1
		assertTrue(p2.x == 4);
		assertTrue(p2.y == 4);
		
		Coordinate p3 = DynamicsManager.getTowards(-5, -5, 0, 0, 3); // rounded to 2, 2
		assertTrue(p3.x == -3);
		assertTrue(p3.y == -3);
		
		Coordinate p4 = DynamicsManager.getTowards(-5, -5, 0, 0, 10); // reached target
		assertTrue(p4.x == 0);
		assertTrue(p4.y == 0);
	}
	
	@Test
	public void getAwayFromTest() {
		Coordinate p1 = DynamicsManager.getAwayFrom(0, 0, 0, 5, 3);
		assertTrue(p1.x == 0);
		assertTrue(p1.y == -3);
		
		Coordinate p2 = DynamicsManager.getAwayFrom(5, 5, 0, 0, 1); // rounded to 1, 1
		assertTrue(p2.x == 6);
		assertTrue(p2.y == 6);
		
		Coordinate p3 = DynamicsManager.getAwayFrom(-5, -5, 0, 0, 3); // rounded to 2, 2
		assertTrue(p3.x == -7);
		assertTrue(p3.y == -7);
		
		Coordinate p4 = DynamicsManager.getAwayFrom(0, 0, 0, 0, 10); // ??
		assertTrue(p4.x == 10);
		assertTrue(p4.y == 0);
	}

	@Test
	@Ignore
	public void theRealTest() {
		MapAssembly map = new MapAssembly(new DynamicEntityTestMap("test"));
		DynamicsManager dm = map.getDynamicsManager();
		
		assertTrue(dm.getMap().equals(map));
		assertTrue(dm.isEmpty(1, 1));
		dm.addEntity(new DiscoDoggo(1, 1));
		assertFalse(dm.isEmpty(1, 1));
		dm.destroyEntity(1, 1);
		assertTrue(dm.isEmpty(1, 1));
		
		List<Coordinate> surr = dm.getSurrounding(0, 0, 1, true);
		assertTrue(surr.contains(new Coordinate(1, 1)));
		assertFalse(surr.contains(new Coordinate(-1, -1)));
		
		surr = dm.getSurrounding(0, 0, 1, false);
		assertTrue(surr.contains(new Coordinate(1, 0)));
		assertFalse(surr.contains(new Coordinate(1, 1)));
		assertFalse(surr.contains(new Coordinate(-1, 0)));
		
		surr = dm.getSurrounding(dm.getMap().getHeight(), dm.getMap().getWidth(), 2, true);
		assertTrue(surr.contains(new Coordinate(dm.getMap().getHeight() - 2, dm.getMap().getWidth() - 2)));
		assertFalse(surr.contains(new Coordinate(dm.getMap().getHeight() + 2, dm.getMap().getWidth() + 2)));
		
		DynamicEntity.setManager(null);
	}
}

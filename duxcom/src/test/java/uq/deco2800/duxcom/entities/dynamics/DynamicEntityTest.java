package uq.deco2800.duxcom.entities.dynamics;

import org.junit.Test;
import uq.deco2800.duxcom.entities.DynamicsManager;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.maps.DynamicEntityTestMap;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author houraineet
 */
public class DynamicEntityTest {
	@Test
	public void phaseIndexTest() {
		DynamicEntity entity = new DiscoDoggo(1, 1);
		
		assertTrue(entity.getCurrentPhaseIndex() == 0); // Initial
		assertTrue(entity.advanceCurrentPhase());
		assertTrue(entity.getCurrentPhaseIndex() == 1);
		
		entity.setCurrentPhaseIndex(entity.getPhases().size() - 1);
		assertTrue(entity.getCurrentPhaseIndex() == entity.getPhases().size() - 1);
		
		assertFalse(entity.advanceCurrentPhase()); // should be out of range
		assertTrue(entity.getCurrentPhaseIndex() == 0);
		entity.setCurrentPhaseIndex(entity.getPhases().size());
		assertTrue(entity.getCurrentPhaseIndex() == 0);
		entity.setCurrentPhaseIndex(-1);
		assertTrue(entity.getCurrentPhaseIndex() == 0);
		assertTrue(entity.getCurrentPhase().equals(entity.getPhases().get(0)));
		
		entity.setPhases(new ArrayList<>());
		assertTrue(entity.getCurrentPhase() == null);
	}
	
	@Test
	public void entityTypeTest() {
		DynamicEntity entity = new DiscoDoggo(1, 1);
		assertTrue(entity.getEntityType() == EntityType.CAVALIER);
		assertTrue(entity.getSuperEntityType() == EntityType.CAVALIER);
		
		entity.advanceCurrentPhase();
		assertTrue(entity.getEntityType() == EntityType.CAVALIER_AQUA);
	}
	
	@Test
	public void constructorTest() {
		DiscoDoggo entity1 = new DiscoDoggo(1, 1);
		entity1.advanceCurrentPhase();
		DiscoDoggo entity2 = new DiscoDoggo(2, 2);
		assertFalse(entity1.equals(entity2));
		assertTrue(entity1.getSuperEntityType() == entity2.getSuperEntityType());
		assertTrue(entity1.getPhases() == entity2.getPhases());
	}

	@Test
	public void syncStringTest() {
	    DiscoDoggo doggo = new DiscoDoggo(0, 0);
		assertTrue(doggo.getSyncString() == "DISCO_DOGGO");
	}

	@Test
	public void tickIntervalTest() {
		DiscoDoggo doggo = new DiscoDoggo(0, 0);
		assertTrue(doggo.getTickInterval() == 1000L);
	}

	@Test
	public void animationTickTest() {
		DiscoDoggo doggo = new DiscoDoggo(0, 0);
		List<Phase> phases = doggo.getPhases();
		assertTrue(phases.indexOf(doggo.getCurrentPhase()) == 0);

		doggo.animationTick();
		assertTrue(phases.indexOf(doggo.getCurrentPhase()) == 1);
	}

	@Test
	public void turnTickTest() {
		MapAssembly map = new MapAssembly(new DynamicEntityTestMap("test"));
		DynamicsManager manager = map.getDynamicsManager();

		DiscoDoggo doggo = new DiscoDoggo(0, 0);
        doggo.getCurrentPhase().transmissionRate(1);
		map.addEntity(doggo);

		List<Entity> entities = map.getEntities(0, 1);
		assertTrue(entities.isEmpty());

		doggo.turnTick();

		entities = map.getEntities(0, 1);
		assertTrue(entities.get(0) instanceof DiscoDoggo);
	}
}

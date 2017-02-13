package uq.deco2800.duxcom.savegame;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import uq.deco2800.duxcom.maps.AbstractGameMap;
import uq.deco2800.duxcom.maps.DemoMap;
import uq.deco2800.duxcom.maps.Falador;
import uq.deco2800.duxcom.maps.MapType;

public class MapConstructorTest {
	
	private static ArrayList<MapType>  expectedOutput = new ArrayList<>();
	
	@BeforeClass
	public static void fillExpectedOutput() {
		addTypes();
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void MapFactoryTypetest() throws Exception {
		AbstractGameMap map;
		for(MapType type: expectedOutput){
			map = MapConstructor.getMap(type, false);
			assertEquals(type, map.getMapType());
		}
	}
	
	@Test
	public void sameMapTest() throws Exception {
		AbstractGameMap expectedMap = new DemoMap("Demo");
		AbstractGameMap expectedMap2 = new Falador("", false);
		
		AbstractGameMap actualMap = MapConstructor.getMap(MapType.DEMO, false);
		AbstractGameMap actualMap2 = MapConstructor.getMap(MapType.FALADOR, false);
		
		/* Check that the entities are the same for both maps */
		assertEquals(expectedMap.getEntities(), actualMap.getEntities());
		assertEquals(expectedMap2.getEntities(), actualMap2.getEntities());
		
		/* check that the maps are different*/
		expectedMap.removeEntity(); // removes all the heroes and enemies
		assertNotEquals(expectedMap.getEntities(), actualMap.getEntities());
		
		/* check that its removing the correct entities */
		actualMap.removeEntity();
		/* now the maps should be the same*/
		assertEquals(expectedMap2.getEntities(), actualMap2.getEntities());
		
	}
	
	/* Helper Methods */
	
	/**
	 * 
	 */
	private static void addTypes(){
		expectedOutput.add(MapType.DEMO);
		expectedOutput.add(MapType.EMPTY);
		expectedOutput.add(MapType.FALADOR);
		expectedOutput.add(MapType.KARAMJA);
		expectedOutput.add(MapType.MAP003);
	}
	
}

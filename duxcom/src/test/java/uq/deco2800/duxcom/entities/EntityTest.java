package uq.deco2800.duxcom.entities;

import org.junit.Test;
import uq.deco2800.duxcom.entities.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EntityTest {
	
	@Test
	public void basicTest(){
		Entity entity1 = new WoodStack(2, 3);
		Entity entity2 = new WoodStack(4, 5);
		Entity entity3 = new WoodStack(1, 1);
		
		List<Entity> entities = new ArrayList<Entity>();
		entities.add(entity1);
		entities.add(entity2);
		entities.add(entity3);
		
		Collections.sort(entities);
		
		assertTrue("Entity 0 incorrect!", entities.get(0).equals(entity3));
		assertTrue("Entity 1 incorrect!", entities.get(1).equals(entity1));
		assertTrue("Entity 2 incorrect!", entities.get(2).equals(entity2));	
		assertEquals("WOOD_STACK 2 3 1 1",entity1.encode());
		ArrayList<String> decode = new ArrayList<>();
		decode.add("Entity Type");
		decode.add("0");
		decode.add("0");
		decode.add("1");
		decode.add("1");
		entity1.decode(decode);
		assertEquals(0,entity1.getX());
		assertEquals(0,entity1.getY());
		assertEquals(1,entity1.getXLength());
		assertEquals(1,entity1.getXLength());
		
	}
	
	
	@Test
	public void adjacentTest1(){
		Entity entity1 = new WoodStack(3, 7);
		Entity entity2 = new WoodStack(4, 6);
		
		
		List<Entity> entities = new ArrayList<Entity>();
		entities.add(entity1);
		entities.add(entity2);
		
		assertTrue("Entity 1 incorrect!", entities.get(0).equals(entity1));
		assertTrue("Entity 2 incorrect!", entities.get(1).equals(entity2));	
		
		Collections.sort(entities);
		
		assertTrue("Entity 1 incorrect!", entities.get(0).equals(entity2));
		assertTrue("Entity 2 incorrect!", entities.get(1).equals(entity1));	
	}
	
	@Test
	public void adjacentTest2(){
		Entity foreground = new WoodStack(7, 3);
		Entity background = new WoodStack(6, 4);
		
		
		List<Entity> entities = new ArrayList<Entity>();
		entities.add(foreground);
		entities.add(background);
		
		assertTrue("Entity 1 incorrect!", entities.get(0).equals(foreground));
		assertTrue("Entity 2 incorrect!", entities.get(1).equals(background));	
		
		Collections.sort(entities);

		
		assertTrue("Entity 1 incorrect!", entities.get(0).equals(background));
		assertTrue("Entity 2 incorrect!", entities.get(1).equals(foreground));	
	}

	@Test
	public void adjacentTest3(){
		Entity background = new WoodStack(4, 6);
		Entity foreground = new WoodStack(5, 6);
//		Entity foreground = new Entity(6, 6, 1, 1);


		List<Entity> entities = new ArrayList<Entity>();
		entities.add(foreground);
		entities.add(background);

		assertTrue("Entity 1 incorrect!", entities.get(0).equals(foreground));
		assertTrue("Entity 2 incorrect!", entities.get(1).equals(background));

		Collections.sort(entities);


		assertTrue("Entity 1 incorrect!", entities.get(0).equals(background));
		assertTrue("Entity 2 incorrect!", entities.get(1).equals(foreground));
	}


}

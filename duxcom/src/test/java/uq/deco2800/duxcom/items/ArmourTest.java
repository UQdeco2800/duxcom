package uq.deco2800.duxcom.items;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import uq.deco2800.duxcom.items.armour.*;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.items.RarityLevel;

/**
 * Armour Test
 *
 * @author Quackin
 */
public class ArmourTest {
	private static final int MAX_ARMOUR = 100;
	private static final int MAX_EFFECTIVENESS = 100;
	
	/**
	 * Mock for Hero
	 */
    @Mock
    private GameManager gameManagerMock;
    @Mock
    private MapAssembly mapMock;
    @Mock
    private GameLoop gameLoopMock;
	
	@Test
    public void createArmourTest() {
		String name = "Test Armour";
		int cost = 10;
		int weight = 20;
		String sprite = "Test Sprite";
		int durability = 100;
		int armour = 30;
		RarityLevel rarity = RarityLevel.COMMON;
		
		Armour testArmour = new Armour(name, cost, weight, sprite, true, durability, armour, rarity);
        assertTrue(testArmour.getRarity().equals(rarity));
        assertTrue(testArmour.getName().equals(name));
	}
	
	@Test
	public void armourGetterSetterTest(){
		String name = "Test Armour";
		int cost = 10;
		int weight = 20;
		String sprite = "Test Sprite";
		int durability = 100;
		int armour = 30;
		RarityLevel rarity = RarityLevel.COMMON;
		
		Armour testArmour = new Armour(name, cost, weight, sprite, true, durability, armour, rarity);
		
		/* setArmour Test */
		//Test below MAX_ARMOUR
        int armour1 = 50;
        int armour2 = 27;
        int armour3 = 99;
        
        testArmour.setArmour(armour1);
        assertEquals(testArmour.getArmour(), armour1);
        testArmour.setArmour(armour2);
        assertEquals(testArmour.getArmour(), armour2);
        testArmour.setArmour(armour3);
        assertEquals(testArmour.getArmour(), armour3);
        
        //Test at MAX_ARMOUR
        testArmour.setArmour(MAX_ARMOUR);
        assertEquals(testArmour.getArmour(), MAX_ARMOUR);
        
        //Test Above MAX_ARMOUR
        testArmour.setArmour(130);
        assertEquals(testArmour.getArmour(), MAX_ARMOUR);
        
        /* setBounusActionPoint Test */
        int bounusActionPoint = -20;
        testArmour.setCostActionPoint(bounusActionPoint);
        assertEquals(testArmour.getCostActionPoint(), bounusActionPoint);
        
        /* setEffectiveness Test */
		//Test below MAX_ARMOUR
        int effectiveness1 = 50;
        int effectiveness2 = 27;
        int effectiveness3 = 99;
        
        testArmour.setEffectiveness(effectiveness1);
        assertEquals(testArmour.getEffectiveness(), armour1);
        testArmour.setEffectiveness(effectiveness2);
        assertEquals(testArmour.getEffectiveness(), effectiveness2);
        testArmour.setEffectiveness(effectiveness3);
        assertEquals(testArmour.getEffectiveness(), effectiveness3);
        
        //Test at MAX_ARMOUR
        testArmour.setEffectiveness(MAX_EFFECTIVENESS);
        assertEquals(testArmour.getEffectiveness(), MAX_EFFECTIVENESS);
        
        //Test Above MAX_ARMOUR
        testArmour.setEffectiveness(130);
        assertEquals(testArmour.getEffectiveness(), MAX_EFFECTIVENESS);
	}
	
	@Test
	public void armourEquipTest(){
		AbstractHero hero = Mockito.mock(AbstractHero.class);
		int costActionPoint = 25;
		
		String name = "Test Armour";
		int cost = 10;
		int weight = 20;
		String sprite = "Test Sprite";
		int durability = 100;
		int armour = 30;
		RarityLevel rarity = RarityLevel.COMMON;
		
		Armour testArmour = new Armour(name, cost, weight, sprite, true, durability, armour, rarity);
		testArmour.setCostActionPoint(costActionPoint);
		
		testArmour.Enquip(hero);
	}
	
	@Test
	public void armourFunctionalTest(){
		String name = "Test Armour";
		int cost = 10;
		int weight = 20;
		String sprite = "Test Sprite";
		int durability = 100;
		int armour = 30;
		RarityLevel rarity = RarityLevel.COMMON;
		
		Armour testArmour = new Armour(name, cost, weight, sprite, true, durability, armour, rarity);
		
		//Above 0 after apply
		testArmour.damage(30);
		assertEquals(70, testArmour.getEffectiveness());
		testArmour.damageArmour(13);
		assertEquals(17, testArmour.getArmour());
		
		//Below 0 after apply
		testArmour.damage(500);
		assertEquals(0, testArmour.getEffectiveness());
		testArmour.damageArmour(500);
		assertEquals(0, testArmour.getArmour());
		
		//Uneffective Armour
		assertEquals(50, testArmour.applyDamage(50));
	}
}

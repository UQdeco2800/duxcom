package uq.deco2800.duxcom.interfaces.gameinterface.heropopup;

import org.junit.Test;
import static org.junit.Assert.*;

import uq.deco2800.duxcom.entities.heros.Archer;
import uq.deco2800.duxcom.entities.heros.HeroStat;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.controllers.HeroStatisticsController;
import uq.deco2800.duxcom.items.EquippableItem;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.items.RarityLevel;
import uq.deco2800.duxcom.items.StatisticModifier;
import uq.deco2800.duxcom.items.StatisticModifyAction;

public class HeroStatisticsControllerTest {
	
	private Archer hero = new Archer(0,0);
	private Archer heroInverse = new Archer(0,0);	
	/**
     * Concrete Class for Mocking Abstract class Weapon
     * @author Quackin
     *
     */
    private class ConcreteEquippableItem extends EquippableItem{
		public ConcreteEquippableItem(String name, int cost, int weight, String inventorySpriteName, boolean tradable,
				int durability, RarityLevel rarity, ItemType type) {
			super(name, cost, weight, inventorySpriteName, tradable, durability, rarity, type);
		}
    }
	
    @Test
    public void modifyHeroStatTest(){
    	// Setup stage for testing
    	EquippableItem item = new ConcreteEquippableItem("Test Item", 0, 0, "Test", true,
    																0, RarityLevel.COMMON, ItemType.SWORD);
    	//Normal Value Test
    	StatisticModifier statModDamage = new StatisticModifier(HeroStat.DAMAGE, StatisticModifyAction.ADD, 50);
       	StatisticModifier statModArmour = new StatisticModifier(HeroStat.ARMOUR, StatisticModifyAction.MULTIPLY, 1.1);
    	StatisticModifier statModBaseAP = new StatisticModifier(HeroStat.BASE_AP, StatisticModifyAction.DIVIDE, 2);
    	StatisticModifier statModBaseHealth = new StatisticModifier(HeroStat.BASE_HEALTH, StatisticModifyAction.SUBTRACT, 5);
    	
    	item.addStatisticModifier(statModDamage);
    	item.addStatisticModifier(statModArmour);
    	item.addStatisticModifier(statModBaseAP);
    	item.addStatisticModifier(statModBaseHealth);
    	
    	//Grab default stats
    	double defaultDamage = hero.getDamage();
    	double defaultArmour = hero.getArmour();
    	int defaultBaseAP = hero.getBaseActionPoints();
    	double defaultBaseHealth = hero.getBaseHealth();
    	
    	//Equip the item and run the test
    	HeroStatisticsController.applyHeroStatistic(hero, item, false);
    	
    	assertEquals(defaultDamage + statModDamage.getValue(), hero.getDamage(), 0.1);
    	assertEquals(defaultArmour * statModArmour.getValue(), hero.getArmour(), 0.1);
    	assertEquals(defaultBaseAP / statModBaseAP.getValue(), hero.getBaseActionPoints(), 1);
    	assertEquals(defaultBaseHealth - statModBaseHealth.getValue(), hero.getBaseHealth(), 1);
 
    	
    }
}

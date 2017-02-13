package uq.deco2800.duxcom.entities.heros;

import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.stage.Stage;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.Fireball;
import uq.deco2800.duxcom.buffs.AbstractBuff;
import uq.deco2800.duxcom.buffs.InnerStrengthArmourBuff;
import uq.deco2800.duxcom.buffs.OnFire;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.heros.listeners.ActionPointListener;
import uq.deco2800.duxcom.entities.heros.listeners.HealthListener;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.passives.AbstractPassive;
import uq.deco2800.duxcom.passives.HealthRegen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxToolkit.setupStage;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class AbstractHeroTest extends ApplicationTest{

    private AbstractHero abstractHero;
    private AbstractAbility ability;
    
    @Mock
    private GameManager gameManagerMock;
    @Mock
    private MapAssembly mapMock;
    @Mock
    private AbstractEnemy abstractEnemyMock;
    @Mock
    private AbstractAbility abstractAbilityMock;
    @Mock
    private AbstractBuff abstractBuffMock;
    @Mock
    private HealthListener healthListenerMock;
    @Mock
    private ActionPointListener actionPointListenerMock;

    @Test
    public void testBasicGettersAndSetters() throws Exception {
        abstractHero = new Archer(0, 0);
        assertTrue(Math.abs(1 - abstractHero.getRatioHealthRemaining()) < 0.001d);
        assertEquals(HeroType.ARCHER, abstractHero.getHeroType());
        assertEquals("ARCHER 0 0 1 1", abstractHero.encode());
        assertEquals("archer", abstractHero.getImageName());
        abstractHero.turnTick();

        abstractHero.setSpeed(3);
        assertTrue(Math.abs(3 - abstractHero.getSpeed()) < 0.001d);

        abstractHero.setArmour(5);
        assertTrue(Math.abs(5 - abstractHero.getArmour()) < 0.001d);

        abstractHero.setVisibilityRange(6);
        assertTrue(Math.abs(7 - abstractHero.getVisibilityRange()) < 0.001d);

        abstractHero.setVisibilityRange(-6);
        assertTrue(Math.abs(1 - abstractHero.getVisibilityRange()) < 0.001d);

        abstractHero.setActionPoints(9);
        assertEquals(9, abstractHero.getActionPoints());

        abstractHero.setHealth(10);
        assertTrue(Math.abs(10 - abstractHero.getHealth()) < 0.001d);

    }

    @Test
    public void testGetPos() throws Exception {
        abstractHero = new Archer(0, 1);
        assertEquals(0, abstractHero.getX());
        assertEquals(1, abstractHero.getY());
    }

    @Test
    public void testMove() throws Exception {
        abstractHero = new Archer(0, 1);

        Float[][] movementCost = new Float[2][2];
        movementCost[0][0] = null;
        movementCost[1][1] = 1.0f;
        abstractHero.setMovementCost(movementCost);

        assertFalse(abstractHero.move(0, 0));
       // assertTrue(abstractHero.move(1, 1));
        //assertEquals(movementCost[1][1], abstractHero.getMovementCost()[1][1]);
    }

    @Test
    public void testAbilities() throws Exception {
        when(abstractAbilityMock.useOnPoint(anyObject(), anyInt(), anyInt(), anyInt())).thenReturn(true);
        when(abstractAbilityMock.getCostAP()).thenReturn(0);
        when(gameManagerMock.getMap()).thenReturn(mapMock);
        when(mapMock.getMovableEntity(anyInt(), anyInt())).thenReturn(abstractEnemyMock);
        abstractHero = new Archer(0, 1);
        List<AbstractAbility> heroAbilities = new ArrayList<>();
        heroAbilities.add(abstractAbilityMock);
        abstractHero.setAbilities(heroAbilities);

        assertFalse(abstractHero.useAbility(-1, 1, 1));
        assertFalse(abstractHero.useAbility(69, 1, 1));
        assertTrue(abstractHero.useAbility(0, 1, 1));
        assertEquals(abstractAbilityMock, abstractHero.getAbilities().get(0));


        abstractHero.setUtilityAbility(abstractAbilityMock);
        assertEquals(abstractAbilityMock, abstractHero.getUtilityAbility());

        abstractHero.setWeaponAbility(abstractAbilityMock);
        assertEquals(abstractAbilityMock, abstractHero.getWeaponAbility());

    }

    @Test
    public void testStats() throws Exception {

        abstractHero = new Archer(0, 1);
        abstractHero.setStats(abstractHero.getHeroType(),
                DataRegisterManager.getHeroDataRegister().getData(abstractHero.getHeroType()));

        assertTrue(1.2 - abstractHero.getStats().get(HeroStat.SPEED) < 0.001d);
    }

    @Test
    public void testBuffs() {
        abstractHero = new Archer(0, 1);
        assertTrue(abstractHero.getHeroBuffs().isEmpty());

        AbstractBuff buff = new InnerStrengthArmourBuff(5,3);
        abstractHero.addBuff(buff);;
        assertFalse(abstractHero.getHeroBuffs().isEmpty());
        
        abstractHero.removeBuff(new OnFire(5,3));
        assertFalse(abstractHero.getHeroBuffs().isEmpty());
        
        abstractHero.removeBuff(buff);
        assertTrue(abstractHero.getHeroBuffs().isEmpty());

        HeroStat buffStatAffected = HeroStat.ARMOUR;
        System.out.println(abstractBuffMock);
        when(abstractBuffMock.getStatAffected()).thenReturn(buffStatAffected);
        when(abstractBuffMock.getBuffStrength()).thenReturn(1.0);

        abstractHero.addBuff(abstractBuffMock);
        assertFalse(abstractHero.getHeroBuffs().isEmpty());
        when(abstractBuffMock.tickBuff()).thenReturn(true);
        abstractHero.onTurn();
        assertFalse(abstractHero.getHeroBuffs().isEmpty());
        when(abstractBuffMock.tickBuff()).thenReturn(false);
        abstractHero.onTurn();
        assertTrue(abstractHero.getHeroBuffs().isEmpty());
    }

    @Test
    public void testListeners() {
        abstractHero = new Archer(0, 1);

        abstractHero.addActionPointListener(actionPointListenerMock);
        abstractHero.addHealthListener(healthListenerMock);

        abstractHero.enableListeners();
        assertTrue(abstractHero.isHealthListenersEnabled());
        assertTrue(abstractHero.actionPointListenersEnabled);

        abstractHero.disableListeners();
        assertFalse(abstractHero.isHealthListenersEnabled());
        assertFalse(abstractHero.actionPointListenersEnabled);

        abstractHero.onTurn();
        assertTrue(abstractHero.isHealthListenersEnabled());
        assertTrue(abstractHero.actionPointListenersEnabled);

        abstractHero.removeActionPointListener(actionPointListenerMock);
        abstractHero.removeHealthListener(healthListenerMock);
    }

    @Test
    public void testChanges() {
        abstractHero = new Archer(0, 1);
        abstractHero.changeAP(-100);
        abstractHero.changeAP(Integer.MIN_VALUE);
        assertEquals(0, abstractHero.getActionPoints());
        abstractHero.changeAP(Integer.MAX_VALUE);
        abstractHero.changeAP(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, abstractHero.getActionPoints());

        abstractHero.changeHealth(101);
        abstractHero.changeHealth(-1);
        assertTrue(Math.abs(99 - abstractHero.getHealth()) < 0.001d);
        abstractHero.changeHealth(-1000);
        assertTrue(Math.abs(0 - abstractHero.getHealth()) < 0.001d);

        abstractHero.changeLevel(1);
        assertEquals(1, abstractHero.getLevel());
        abstractHero.changeLevel(11);
        assertEquals(10, abstractHero.getLevel());
        abstractHero.changeLevel(-9);

        abstractHero.changeXP(100);
        assertEquals(100, abstractHero.getXp());
        abstractHero.changeXP(3000);
        assertEquals(900, abstractHero.getXp());
        assertEquals(3, abstractHero.getLevel());

        abstractHero.changeLevel(Integer.MAX_VALUE);
        abstractHero.changeLevel(Integer.MAX_VALUE);
        assertEquals(10, abstractHero.getLevel());
        abstractHero.changeLevel(Integer.MIN_VALUE);
        abstractHero.changeLevel(Integer.MIN_VALUE);
        assertEquals(0, abstractHero.getLevel());


        abstractHero.changeXP(Integer.MAX_VALUE);
        abstractHero.changeXP(Integer.MAX_VALUE);
        assertEquals(10, abstractHero.getLevel());
        abstractHero.changeXP(Integer.MIN_VALUE);
        abstractHero.changeXP(Integer.MIN_VALUE);
        assertEquals(0, abstractHero.getLevel());

    }

    @Test
    public void testPassives() {
        abstractHero = new Archer(0, 1);
        List<AbstractPassive> passives = new ArrayList<>();
        passives.add(new HealthRegen(abstractHero));
        abstractHero.setPassives(passives);
        abstractHero.onTurn();
        assertEquals(passives, abstractHero.getPassives());
    }

    @Test
    public void basicEnemyTest() {
        AbstractHero hero0 = new Archer(1, 1);
        AbstractHero hero1 = new Archer(2, 2);

        List<AbstractHero> heroes = new ArrayList<AbstractHero>();
        heroes.add(hero0);
        heroes.add(hero1);

        Collections.sort(heroes);

        assertTrue("Hero 0 incorrect.", heroes.get(0).equals(hero0));
        assertTrue("Hero 1 incorrect.", heroes.get(1).equals(hero1));
    }
    
    @Test
    public void statUpgradeTest() {
    	abstractHero = new Archer(0, 1);
    	abstractHero.changeLevel(1);
        abstractHero.setUpgradePoints(50);
    	abstractHero.statUpgrade("health_1", 1);
    	assertEquals(110, abstractHero.getBaseHealth(), 0);
    	
    	assertFalse(abstractHero.statUpgrade("health_1", 1));
    	assertFalse(abstractHero.statUpgrade("health_2", 2));
    	
    	abstractHero.changeLevel(1);
    	abstractHero.statUpgrade("armour_1", 1);
    	assertEquals(6, abstractHero.getArmour(), 0);
    	abstractHero.statUpgrade("armour_2", 2);
    	assertEquals(7, abstractHero.getArmour(), 0);
    	
    	assertFalse(abstractHero.statUpgrade("beaned_2", 1));
    	
    	abstractHero.statUpgrade("health_2", 2);
    	assertEquals(120, abstractHero.getBaseHealth(), 0);
    	
    	assertFalse(abstractHero.statUpgrade("ap_2", 2));
    	abstractHero.statUpgrade("ap_1", 1);
    	assertEquals(16, abstractHero.getBaseActionPoints(), 0);
    }
    
    @Test
    public void abilityUpgradeTest() {
    	abstractHero = new Knight(0, 1);
    	ability = new Fireball();
    	abstractHero.changeLevel(1);
        abstractHero.setUpgradePoints(50);
    	ability.abilityUpgrade("range_1", 1, abstractHero);
    	assertEquals(5, ability.getRange(), 0);
    	assertFalse(ability.abilityUpgrade("range_1", 1, abstractHero));
    	
    	abstractHero.changeLevel(10);
    	
    	ability.abilityUpgrade("range_2", 2, abstractHero);
    	assertEquals(6, ability.getRange(), 0);
    	
    	assertFalse(ability.abilityUpgrade("damage_2", 2, abstractHero));
    	ability.abilityUpgrade("damage_1", 1, abstractHero);
    	assertEquals(-24, ability.getDamage(), 0);
    	ability.abilityUpgrade("damage_2", 2, abstractHero);
    	assertEquals(-29, ability.getDamage(), 0);
    }

	@Override
	public void start(Stage stage) throws Exception {
		new GameLoop(10, new AtomicBoolean(false), gameManagerMock);
	}
	
	@AfterClass
    public static void cleanUp() throws TimeoutException {
        setupStage(Stage::close);
        new GameLoop(0, null, null);
    }
}
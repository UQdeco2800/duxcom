package uq.deco2800.duxcom.abilities;

import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.stage.Stage;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.common.DamageType;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyKnight;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.Archer;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.tiles.Tile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxToolkit.setupStage;

/**
 * Created by spress11
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class AbstractAbilityTest extends ApplicationTest{

	@Mock
	private GameManager gameManagerMock;
	@Mock
	private MapAssembly mapMock;
	@Mock
	private Tile tileMock;
	
	
	
	AbstractHero test0 = new Archer(1,1);
	AbstractHero test1 = new Archer(2,1);
	AbstractHero test2 = new Archer(4,4);
	
	/**
	 * An ability must cost at least one action point
	 * @throws Exception
	 */
	@Test
	public void getterAndSetters() throws Exception {
		AbstractAbility ability = new Projectile();
		//must have AP cost > 0
		assertTrue(ability.getCostAP() > 0);
		//must have castRange > 0;
		assertTrue(ability.getRange() > 0);
		
		//testing damage multipliers
		assertTrue(ability.getBaseDamage() == ability.getDamage());
		ability.addTemporaryDamageMultiplier(1.5);
		assertTrue(ability.getTemporaryDamageMultiplier() == 1.5);
		assertTrue(ability.getBaseDamage()*1.5 == ability.getDamage());
		ability.resetTemporaryDamageMultiplier();
		assertTrue(ability.getBaseDamage() == ability.getDamage());
		
		assertTrue(ability.getDescription() == "Shoot an arrow at the target");
		assertTrue(ability.getName() == "Projectile");
		assertTrue(ability.getDamageType() == DamageType.PIERCING);
		assertTrue(ability.getAbilityType() == AbilityType.ARCHER);
		assertTrue(ability.getAoeRange() == 0);
	}
	
	@Test
	public void inRange() throws Exception {
		AbstractAbility ability = new Projectile();
		int castRange = ability.getRange();
		assertTrue(ability.inRange(0, 0, castRange, 0));
		assertTrue(ability.inRange(0, 0, 0, castRange));
		assertTrue(ability.inRange(castRange, 0, 0, 0));
		assertTrue(ability.inRange(0, castRange, 0, 0));
		
		assertFalse(ability.inRange(0, 0, castRange+1, 0));
		assertFalse(ability.inRange(0, 0, castRange, castRange));
		assertTrue(ability.inRange(0, 0, castRange-1, 1));
	}

	@Test
	public void CheckCooldowns() throws Exception {
		AbstractAbility ability = new Projectile();
		assertFalse(ability.onCooldown());
		ability.useOnFoe(test0, test1);
		assertTrue(ability.onCooldown());
		int cooldown = DataRegisterManager.getAbilityDataRegister().getData(AbilityName.PROJECTILE).getCooldownTurns();
		for(int i = cooldown; i > 0 ;i--) {
			ability.tickCooldown();
		}
		assertFalse(ability.onCooldown());
	}

	@Ignore
	@Test
	public void testUseOnPoint() throws Exception {
		AbstractAbility ability = new Projectile();
		AbstractEnemy testEnemy = new EnemyKnight(0, 0);
        when(gameManagerMock.getMap()).thenReturn(mapMock);
        when(mapMock.getTile(anyInt(), anyInt())).thenReturn(tileMock);
        when(mapMock.getMovableEntity(anyInt(), anyInt())).thenReturn(testEnemy);
		assertTrue(ability.useOnPoint(testEnemy, 0, 0, 0));
		assertEquals(88, testEnemy.getHealth(), 0);
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
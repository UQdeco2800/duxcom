package uq.deco2800.duxcom.entities.enemies.artificialincompetence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.abilities.Fireball;
import uq.deco2800.duxcom.abilities.Heal;
import uq.deco2800.duxcom.entities.heros.HeroManager;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.enemies.EnemyManager;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyKnight;
import uq.deco2800.duxcom.entities.heros.Knight;
import uq.deco2800.duxcom.maps.DemoMap;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.maps.mapgen.bounds.Coordinate;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnemyActionTest {

	@Test
	public void basicEnemyActionTest() {

		ArrayList<Coordinate> moves1 = new ArrayList<>(1);
		moves1.add(new Coordinate(0, 0));
		ArrayList<Coordinate> moves2 = new ArrayList<>(3);
		moves2.add(new Coordinate(0, 0));
		moves2.add(new Coordinate(1, 1));
		moves2.add(new Coordinate(2, 2));

		EnemyAction enemyAction1 = new EnemyAction(null, -1, -1, null, new ArrayList<>(), 1);
		EnemyAction enemyAction2 = new EnemyAction(new EnemyKnight(0, 0), 0, 0, new Heal(), new ArrayList<>(), 2);
		EnemyAction enemyAction3 = new EnemyAction(new Knight(0, 0), 0, 0, new Fireball(), new ArrayList<>(), 3.5);
		EnemyAction enemyAction4 = new EnemyAction(new Knight(0, 0), 0, 0, new Fireball(), moves1, 4);
		EnemyAction enemyAction5 = new EnemyAction(new Knight(0, 0), 0, 0, new Fireball(), moves2, 5);

		// getRating
		assertTrue("Incorrect EnemyAction Rating", enemyAction1.getRating() == 1);
		assertTrue("Incorrect EnemyAction Rating", enemyAction2.getRating() == 2);
		assertTrue("Incorrect EnemyAction Rating", enemyAction3.getRating() == 3.5);

		// getMove
		assertTrue("Incorrect move", enemyAction1.getMove(0) == null);
		assertTrue("Incorrect move", enemyAction2.getMove(5) == null);
		assertTrue("Incorrect move", enemyAction4.getMove(0).equals(new Coordinate(0, 0)));
		assertTrue("Incorrect move", enemyAction5.getMove(0).equals(new Coordinate(0, 0)));
		assertTrue("Incorrect move", enemyAction5.getMove(2).equals(new Coordinate(2, 2)));

		// dropMove
		enemyAction1.dropMove();
		assertTrue("number moves failed", enemyAction5.getNumberMoves() == 3);
		enemyAction5.dropMove();
		assertTrue("Drop move failed", enemyAction5.getMove(0).equals(new Coordinate(1, 1)));

		// hasMoves
		assertTrue("Has moves failed", !enemyAction1.hasMoves());
		assertTrue("Has moves failed", !enemyAction3.hasMoves());
		assertTrue("Has moves failed", enemyAction5.hasMoves());

		// getAbility
		assertTrue("get Ability failed", enemyAction1.getAbility() == null);
		assertTrue("get Ability failed", enemyAction2.getAbility().equals(new Heal()));
		assertTrue("get Ability failed", enemyAction3.getAbility().equals(new Fireball()));
		assertTrue("get Ability failed", !enemyAction3.getAbility().equals(new Heal()));

		// getNumberMoves
		assertTrue("number moves failed", enemyAction1.getNumberMoves() == 0);
		assertTrue("number moves failed", enemyAction2.getNumberMoves() == 0);
		assertTrue("number moves failed", enemyAction4.getNumberMoves() == 1);
		assertTrue("number moves failed", enemyAction5.getNumberMoves() == 2);

		// equals
		assertEquals(enemyAction1, enemyAction1);
		assertEquals(enemyAction2, enemyAction2);
		assertEquals(enemyAction3, enemyAction3);
		assertEquals(enemyAction4, enemyAction4);
		assertEquals(enemyAction5, enemyAction5);

		// notEquals
		assertNotEquals(enemyAction1, enemyAction2);
		assertNotEquals(enemyAction1, enemyAction5);
		assertNotEquals(enemyAction4, enemyAction5);
	}

	@Test
	public void actionDiffMovesCompareTest() {
		ArrayList<Coordinate> moves1 = new ArrayList<>(1);
		moves1.add(new Coordinate(0, 1));
		ArrayList<Coordinate> moves2 = new ArrayList<>(1);
		moves2.add(new Coordinate(1, 2));

		EnemyAction enemyAction1 = new EnemyAction(new Knight(0, 0), 0, 0, new Fireball(), moves1, 4);
		EnemyAction enemyAction2 = new EnemyAction(new Knight(0, 0), 0, 0, new Fireball(), moves2, 4);

		// Different Moves (Single Move)
		assertFalse(enemyAction1.equals(enemyAction2));
	}
	
	@Test
	public void actionDiffTargetsCompareTest() {
		ArrayList<Coordinate> moves1 = new ArrayList<>(1);
		moves1.add(new Coordinate(0, 1));
		
		EnemyAction enemyAction1 = new EnemyAction(new Knight(0, 0), 0, 0, new Fireball(), moves1, 4);
		EnemyAction enemyAction2 = new EnemyAction(new Knight(3, 3), 0, 0, new Fireball(), moves1, 4);
		EnemyAction enemyAction3 = new EnemyAction(null, 0, 0, new Fireball(), moves1, 4);
		
		// Current Target Null, That Target not Null
		assertFalse(enemyAction3.equals(enemyAction1));		
		// Current Target not Null, That Target Null
		assertFalse(enemyAction1.equals(enemyAction3));
		// Both Targets Not Null. Different Targets
		assertFalse(enemyAction1.equals(enemyAction2));
		//Both Targets Null
		assertTrue(enemyAction3.equals(enemyAction3));
	}
	
	@Test
	public void actionDiffAbilitiesCompareTest() {
		ArrayList<Coordinate> moves1 = new ArrayList<>(1);
		moves1.add(new Coordinate(0, 1));
		
		EnemyAction enemyAction1 = new EnemyAction(new Knight(0, 0), 0, 0, new Fireball(), moves1, 4);
		EnemyAction enemyAction2 = new EnemyAction(new Knight(0, 0), 0, 0, new Heal(), moves1, 4);
		EnemyAction enemyAction3 = new EnemyAction(new Knight(0, 0), 0, 0, null, moves1, 4);
		
		// Current Ability Null, Other ability not Null.
		assertFalse(enemyAction3.equals(enemyAction1));
		// Current Ability not Null, Other Ability Null.
		assertFalse(enemyAction1.equals(enemyAction3));
		// Both Abilities Not Null. Different Abilities.
		assertFalse(enemyAction1.equals(enemyAction2));
		// Both Abilities Null
		assertTrue(enemyAction3.equals(enemyAction3));
	}

	@Test
	public void actionHashCodeTest() {

		ArrayList<Coordinate> moves1 = new ArrayList<>(1);
		moves1.add(new Coordinate(0, 1));
		ArrayList<Coordinate> moves2 = new ArrayList<>(3);
		moves2.add(new Coordinate(2, 3));
		EnemyAction enemyAction1 = new EnemyAction(new Knight(0, 0), 0, 0, new Fireball(), moves1, 4);
		EnemyAction enemyAction2 = new EnemyAction(new Knight(0, 0), 0, 0, new Fireball(), moves2, 5);

		assertEquals(enemyAction1.hashCode(), enemyAction1.hashCode());
		assertEquals(enemyAction2.hashCode(), enemyAction2.hashCode());
		assertNotEquals(enemyAction1.hashCode(), enemyAction2.hashCode());
	}
	
	@Test
	public void actionTargetTest() {
		ArrayList<Coordinate> moves1 = new ArrayList<>(1);
		moves1.add(new Coordinate(0, 1));
		EnemyAction enemyAction1 = new EnemyAction(new Knight(0, 0), 0, 0, new Fireball(), moves1, 4);
		EnemyAction enemyAction2 = new EnemyAction(new Knight(8, 4), 8, 4, new Fireball(), moves1, 5);
		
		assertTrue(enemyAction1.getTargetPointX() == 0);
		assertTrue(enemyAction1.getTargetPointY() == 0);
		assertTrue(enemyAction2.getTargetPointX() == 8);
		assertTrue(enemyAction2.getTargetPointY() == 4);
	}

	private MapAssembly mapAssembly;
	@Mock
	private EnemyManager enemyManagerMock;
	@Mock
	private HeroManager heroManagerMock;
	@Mock
	private GameManager gameManagerMock;
	@Mock
	private DemoMap demoMapMock;

	@Test
	public void actionManagerTest() {
		AbstractEnemy owner = new EnemyKnight(0, 0);
		when(demoMapMock.getEnemyManager()).thenReturn(enemyManagerMock);
		when(demoMapMock.getHeroManager()).thenReturn(heroManagerMock);
		mapAssembly = new MapAssembly(demoMapMock);
		owner.getActionManager().setMap(mapAssembly);

		// Check for Empty Action Manager
		assertTrue("Empty Action Manager check failed.", owner.getActionManager().isEmpty());

		// Create some Actions to do for EnemyKnight
		EnemyAction enemyAction1 = new EnemyAction(new Knight(0, 0), 0, 0, new Fireball(), new ArrayList<>(), 3.5);
		EnemyAction enemyAction2 = new EnemyAction(new EnemyKnight(0, 0), 0, 0, new Heal(), new ArrayList<>(), 2);
		EnemyAction enemyAction3 = new EnemyAction(new Knight(0, 0), 0, 0, new Fireball(), new ArrayList<>(), 5);

		// Assign Actions to Enemy Knight
		owner.getActionManager().addAction(enemyAction1);
		owner.getActionManager().addAction(enemyAction2);
		owner.getActionManager().addAction(enemyAction3);

		// Checking addAction function
		assertFalse("Action Manager not Empty (addAction failed)", owner.getActionManager().isEmpty());
		assertTrue("Action Manager contrains failed.", owner.getActionManager().contains(enemyAction1));
		assertTrue("Action Manager contrains failed.", owner.getActionManager().contains(enemyAction2));
		assertTrue("Action Manager contrains failed.", owner.getActionManager().contains(enemyAction3));

		// Checking Sorting (based on Rating) and dropAction
		owner.getActionManager().dropAction(enemyAction3);
		assertFalse("dropAction failed. ActionManager still contains enemyAction3",
				owner.getActionManager().contains(enemyAction3));
		owner.getActionManager().getAction(1);
		assertEquals(enemyAction1, owner.getAction());
		owner.getActionManager().getAction();
		assertEquals(enemyAction2, owner.getAction());

		// Clear Actions
		assertFalse("Action Manager not Empty", owner.getActionManager().isEmpty());
		owner.getActionManager().clear();
		assertTrue("Action Manager not Empty (clear failed)", owner.getActionManager().isEmpty());

	}

	// @Test
	// public void actionManagerTest() {
	//
	// GameManager gameManager = new GameManager();
	// AbstractEnemy owner = new EnemyKnight(0, 0);
	// //gameManager.setMap(new MapAssembly(LevelType.STATIC_TEST));
	//
	// ArrayList<Point> moves1 = new ArrayList<>(1);
	// moves1.add(new Point(0, 0));
	// ArrayList<Point> moves2 = new ArrayList<>(3);
	// moves2.add(new Point(0, 0));
	// moves2.add(new Point(1, 1));
	// moves2.add(new Point(2, 2));
	//
	// EnemyAction enemyAction1 = new EnemyAction(null, null, new ArrayList<>(),
	// 1);
	// EnemyAction enemyAction2 = new EnemyAction(new EnemyKnight(0, 0), new
	// Heal(), new ArrayList<>(), 2);
	// EnemyAction enemyAction3 = new EnemyAction(new Knight(0, 0), new
	// Fireball(), new ArrayList<>(), 3);
	// EnemyAction enemyAction4 = new EnemyAction(new Knight(0, 0), new
	// Fireball(), moves1, 4);
	// EnemyAction enemyAction5 = new EnemyAction(new Knight(0, 0), new
	// Fireball(), moves2, 5);
	//
	// // setMap
	// //owner.getActionManager().setMap(map);
	//
	// // isEmpty
	// assertTrue("isEmpty failed", owner.getActionManager().isEmpty());
	//
	// // addAction
	// owner.getActionManager().addAction(enemyAction1);
	// owner.getActionManager().addAction(enemyAction2);
	// owner.getActionManager().addAction(enemyAction3);
	// owner.getActionManager().addAction(enemyAction4);
	// owner.getActionManager().addAction(enemyAction5);
	// assertTrue("addAction failed", !owner.getActionManager().isEmpty());
	// assertTrue("contains failed",
	// owner.getActionManager().contains(enemyAction3));
	//
	// // Test sorting
	// assertEquals(enemyAction5, owner.getAction());
	// assertNotEquals(enemyAction1, owner.getAction());
	//
	// // dropAction
	// owner.getActionManager().dropAction(enemyAction3);
	// assertTrue("dropAction failed", !owner.getActionManager().isEmpty());
	// assertTrue("dropAction failed",
	// !owner.getActionManager().contains(enemyAction3));
	// owner.getActionManager().getAction(2);
	// assertEquals(owner.getAction(), enemyAction4);
	//
	// // getAction, getAction(i), getAction(i, moves)
	// owner.getActionManager().getAction();
	// assertEquals(owner.getAction(), enemyAction5);
	// owner.getActionManager().getAction(4);
	// assertEquals(owner.getAction(), enemyAction1);
	// owner.getActionManager().getAction(0, 3);
	// }
}

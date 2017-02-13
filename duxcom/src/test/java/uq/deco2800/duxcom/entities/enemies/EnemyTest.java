package uq.deco2800.duxcom.entities.enemies;

import org.junit.Test;

import uq.deco2800.duxcom.entities.enemies.artificialincompetence.EnemyAttitude;
import uq.deco2800.duxcom.entities.enemies.enemychars.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EnemyTest {

	@Test
	public void basicEnemyTest() {
		AbstractEnemy enemy0 = new EnemyArcher(1, 1);
		AbstractEnemy enemy1 = new EnemyBrute(2, 2);
		AbstractEnemy enemy2 = new EnemyDarkMage(3, 3);
		AbstractEnemy enemy3 = new EnemyGrunt(4, 4);
		AbstractEnemy enemy4 = new EnemyKnight(5, 5);
		AbstractEnemy enemy5 = new EnemyRogue(6, 6);
		AbstractEnemy enemy6 = new EnemySupport(7, 7);
		AbstractEnemy enemy7 = new EnemyTank(8, 8);

		List<AbstractEnemy> enemies = new ArrayList<AbstractEnemy>();
		enemies.add(enemy0);
		enemies.add(enemy1);
		enemies.add(enemy2);
		enemies.add(enemy3);
		enemies.add(enemy4);
		enemies.add(enemy5);
		enemies.add(enemy6);
		enemies.add(enemy7);

		Collections.sort(enemies);

		assertTrue("Enemy 0 incorrect.", enemies.get(0).equals(enemy0));
		assertTrue("Enemy 1 incorrect.", enemies.get(1).equals(enemy1));
		assertTrue("Enemy 2 incorrect.", enemies.get(2).equals(enemy2));
		assertTrue("Enemy 3 incorrect.", enemies.get(3).equals(enemy3));
		assertTrue("Enemy 4 incorrect.", enemies.get(4).equals(enemy4));
		assertTrue("Enemy 5 incorrect.", enemies.get(5).equals(enemy5));
		assertTrue("Enemy 6 incorrect.", enemies.get(6).equals(enemy6));
		assertTrue("Enemy 7 incorrect.", enemies.get(7).equals(enemy7));
	}

	@Test
	public void basicEnemyHealthTest() {
		AbstractEnemy knight = new EnemyKnight(1, 1);
		AbstractEnemy archer = new EnemyArcher(2, 2);

		double origHealthKnight = knight.getHealth();
		double origHealthArcher = archer.getHealth();

		knight.changeHealth(-10);
		archer.changeHealth(10);

		assertTrue("Enemy Health incorrect - Knight.",
				knight.getHealth() == origHealthKnight - 10);
		assertTrue("Enemy Health incorrect - Archer.",
				archer.getHealth() == origHealthArcher);
	}

	@Test
	public void basicEnemyPositionTest() {
		AbstractEnemy knight = new EnemyKnight(1, 5);
		AbstractEnemy archer = new EnemyArcher(7, 2);

		// Testing Initial Positions
		assertTrue("Enemy Position incorrect.", knight.getX() == 1);
		assertTrue("Enemy Position incorrect.", knight.getY() == 5);
		assertTrue("Enemy Position incorrect.", archer.getX() == 7);
		assertTrue("Enemy Position incorrect.", archer.getY() == 2);
		assertTrue("Enemy Point incorrect", knight.getX() == 1);
		assertTrue("Enemy Point incorrect", archer.getX() == 7);

		// Testing Move with X,Y.
		knight.move(2, 6);
		archer.move(8, 3);

		assertTrue("Enemy Position incorrect.", knight.getX() == 2);
		assertTrue("Enemy Position incorrect.", knight.getY() == 6);
		assertTrue("Enemy Position incorrect.", archer.getX() == 8);
		assertTrue("Enemy Position incorrect.", archer.getY() == 3);
	}

	@Test
	public void basicEnemyJobTest() {
		AbstractEnemy knight = new EnemyKnight(2, 7);
		AbstractEnemy archer = new EnemyArcher(8, 2);

		assertTrue("Enemy Job Incorrect.", knight.getEnemyType() == EnemyType.ENEMY_KNIGHT);
		assertTrue("Enemy Job Incorrect.", archer.getEnemyType() == EnemyType.ENEMY_ARCHER);
	}

	@Test
	public void basicEnemyGraphicTest() {
		AbstractEnemy knight = new EnemyKnight(2, 7);
		AbstractEnemy archer = new EnemyArcher(8, 2);

		assertTrue("Wrong Hero Image.",
				knight.getImageName() == "enemy_knight");
		assertTrue("Wrong Hero Image.",
				archer.getImageName() == "enemy_archer");
	}

	@Test
	public void basicEnemyActionManagerTest() {
		AbstractEnemy knight = new EnemyKnight(8, 44);
		AbstractEnemy archer = new EnemyArcher(19, 3);

		assertTrue("ActionManager does not exst.",
				knight.getActionManager() != null);
		assertTrue("ActionManager does not exst.",
				archer.getActionManager() != null);

	}

	@Test
	public void basicCommittedTest() {
		AbstractEnemy knight = new EnemyKnight(7, 37);
		AbstractEnemy archer = new EnemyArcher(26, 11);

		assertTrue("Enemy committed state incorrect.",
				!knight.isCommitted());
		assertTrue("Enemy committed state incorrect.",
				!knight.isCommitted());

		knight.setCommit(true);
		knight.setCommit(false);
		archer.setCommit(false);
		archer.setCommit(true);

		assertTrue("Enemy committed state incorrect.",
				!knight.isCommitted());
		assertTrue("Enemy committed state incorrect.",
				!knight.isCommitted());
	}

	@Test
	public void basicEnemyManagerTest() {
		EnemyManager enemyManager = new EnemyManager();

		// Initilise Enemies
		AbstractEnemy knight1 = new EnemyKnight(12, 5);
		AbstractEnemy knight2 = new EnemyKnight(12, 4);
		AbstractEnemy archer = new EnemyArcher(35, 24);

		// Add Enemies to the EnemyManager
		enemyManager.addEnemy(knight1);
		enemyManager.addEnemy(archer);
		enemyManager.addEnemy(knight2);

		// For knight1
		enemyManager.setEnemy(0);

		assertTrue("Wrong Enemy for index 0",
				enemyManager.getCurrentEnemy().equals(knight1));
		assertFalse("Enemy with index 0, should not equal Knight2",
				enemyManager.getCurrentEnemy().equals(knight2));
		
		// For first nextEnemy call (should go to first enemy)
		enemyManager.nextEnemy();
		
		assertTrue("Wrong Enemy for index 1",
				enemyManager.getCurrentEnemy().equals(knight1));
		
		// For knight2
		enemyManager.nextEnemy();
		
		assertTrue("Wrong Enemy for index 0",
				enemyManager.getCurrentEnemy().equals(archer));
		assertFalse("Enemy with index 0, should not equal Knight2",
				enemyManager.getCurrentEnemy().equals(knight2));
		
		// Check the Size of the Enemy Array.
		assertTrue("Wrong Size of Enemies", enemyManager.numberEnemies() == 3);
		
	}
	
	@Test
	public void basicEnemyAttitudeTest() {
		// Initilise Enemies
		AbstractEnemy knight = new EnemyKnight(12, 5);
		AbstractEnemy archer = new EnemyArcher(35, 24);
		
		assertTrue("Wrong Enemy attitude", knight.getAttitude().equals(EnemyAttitude.BALANCED));
		assertTrue("Wrong Enemy attitude", archer.getAttitude().equals(EnemyAttitude.EVASIVE));
	}
	
	@Test
    public void moveRangeTest() {
		AbstractEnemy knight = new EnemyKnight(12, 5);
		AbstractEnemy archer = new EnemyArcher(35, 24);
		
		assertTrue(knight.getMoveRange() == 5);
		assertTrue(archer.getMoveRange() == 8);
    }

//	@Test
//	public void enemyValidMoveTest() {
//		MapAssembly map = new MapAssembly(new EmptyMap(""));
//		AbstractEnemy knight1 = new EnemyKnight(0, 0);
//		AbstractEnemy knight2 = new EnemyKnight(12, 12);
//		map.addEntity(knight1, new Point(0,0));
//		map.addEntity(knight2, new Point(12, 12));
//
//		// No move
//		assertEquals(new Point(0, 0), knight1.getValidCoordinate(map, new Point(0, 0)));
//		// Minimum diagonal move
//		assertEquals(new Point(1, 1), knight1.getValidCoordinate(map, new Point(1, 1)));
//		// Maximum diagonal move
//		assertEquals(new Point(5, 5), knight1.getValidCoordinate(map, new Point(5, 5)));
//		// Maximum straight move
//		assertEquals(new Point(5, 0), knight1.getValidCoordinate(map, new Point(5, 0)));
//		// Backwards move
//		assertEquals(new Point(11, 11), knight2.getValidCoordinate(map, new Point(11, 11)));
//		// Backwards move 2
//		assertEquals(new Point(7, 7), knight2.getValidCoordinate(map, new Point(7, 7)));
//
//		// Invalid move 1
//		assertNotEquals(new Point(6, 6), knight1.getValidCoordinate(map, new Point(6, 6)));
//		assertEquals(new Point(5, 5), knight1.getValidCoordinate(map, new Point(6, 6)));
//		// Invalid move 2
//		assertNotEquals(new Point(24, 24), knight1.getValidCoordinate(map, new Point(24, 24)));
//		assertEquals(new Point(5, 5), knight1.getValidCoordinate(map, new Point(24, 24)));
//		// Invalid move 3
//		assertNotEquals(new Point(0, 0), knight2.getValidCoordinate(map, new Point(0, 0)));
//		assertEquals(new Point(7, 7), knight2.getValidCoordinate(map, new Point(0, 0)));
//		// invalid move 4
//		assertNotEquals(new Point(3, 7), knight1.getValidCoordinate(map, new Point(3, 7)));
//		assertEquals(new Point(3, 5), knight1.getValidCoordinate(map, new Point(3, 7)));
//		// Invalid move 5
//		assertNotEquals(new Point(13, 2), knight1.getValidCoordinate(map, new Point(13, 2)));
//		assertEquals(new Point(5, 2), knight1.getValidCoordinate(map, new Point(13, 2)));
//
//		// Not on map move
//		assertNotEquals(new Point(25, 25), knight1.getValidCoordinate(map, new Point(25, 25)));
//		assertEquals(new Point(5, 5), knight1.getValidCoordinate(map, new Point(25, 25)));
//	}

}

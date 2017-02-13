package uq.deco2800.duxcom.entities;

import org.junit.Test;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyArcher;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyKnight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class EnemyTest {

	@Test
	public void basicEnemyTest() {
		AbstractEnemy enemy0 = new EnemyKnight(1,1);
		AbstractEnemy enemy1 = new EnemyKnight(2,2);

		List<AbstractEnemy> enemies = new ArrayList<AbstractEnemy>();
		enemies.add(enemy0);
		enemies.add(enemy1);

		Collections.sort(enemies);

		assertTrue("Enemy 0 incorrect.", enemies.get(0).equals(enemy0));
		assertTrue("Enemy 1 incorrect.", enemies.get(1).equals(enemy1));
	}

	@Test
	public void basicEnemyHealthTest() {
		AbstractEnemy knight = new EnemyKnight(1,1);
		AbstractEnemy archer = new EnemyArcher(2,2);

		double origHealthKnight = knight.getHealth();
		double origHealthArcher = archer.getHealth();

		knight.changeHealth(-10);
		archer.changeHealth(10);

		assertTrue("Enemy Health incorrect - Knight.", knight.getHealth() == origHealthKnight - 10);
		assertTrue("Enemy Health incorrect - Archer.", archer.getHealth() == origHealthArcher);
	}

	@Test
	public void basicEnemyPositionTest(){
		AbstractEnemy knight = new EnemyKnight(1,5);
		AbstractEnemy archer = new EnemyArcher(7,2);

		assertTrue("Enemy Position incorrect.", knight.getX() == 1);
		assertTrue("Enemy Position incorrect.", knight.getY() == 5);
		assertTrue("Enemy Position incorrect.", archer.getX() == 7);
		assertTrue("Enemy Position incorrect.", archer.getY() == 2);

		knight.move(2, 6);
		archer.move(8, 3);

		assertTrue("Enemy Position incorrect.", knight.getX() == 2);
		assertTrue("Enemy Position incorrect.", knight.getY() == 6);
		assertTrue("Enemy Position incorrect.", archer.getX() == 8);
		assertTrue("Enemy Position incorrect.", archer.getY() == 3);

	}

}

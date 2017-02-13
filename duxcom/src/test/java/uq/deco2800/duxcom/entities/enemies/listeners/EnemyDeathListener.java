package uq.deco2800.duxcom.entities.enemies.listeners;

import org.junit.Test;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.enemies.EnemyManager;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyArcher;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyKnight;

import static org.junit.Assert.assertTrue;

public class EnemyDeathListener {

	private EnemyManager enemyManager;
	private AbstractEnemy currentEnemy;
	int enemyDeaths = 0;
	
	@Test
	public void enemyDeathTest() {
		// Set up Enemy Manger with test Enemies.
		enemyManager = new EnemyManager();
		enemyManager.addEnemy(new EnemyKnight(0,0));
		enemyManager.addEnemy(new EnemyArcher(5,5));
		enemyManager.setEnemy(0);
		
		class EnemyDeathListenerTest implements DeathListener {

			@Override
			public void onDeath(AbstractCharacter character) {
				if (character instanceof AbstractEnemy) {
					enemyDeaths++;
				}
			}
		}
		
		// Init a Death Listener
		EnemyDeathListenerTest testClass = new EnemyDeathListenerTest();
		enemyManager.addDeathListenerAllEnemies(testClass);
		
		currentEnemy = enemyManager.getCurrentEnemy();
		assertTrue("Current Enemy should not be null", currentEnemy!= null);
		
		// Check for Enemy 0 (Knight)
		assertTrue("Error in Init for EnemyDeaths", enemyDeaths == 0);
		currentEnemy.changeHealth(-currentEnemy.getHealth());
		assertTrue("DeathListener did not fire", enemyDeaths == 1);
		
		// CHeck for Enemy 1 (Archer)
		enemyManager.nextEnemy();
        assertTrue("DeathListener fired incorrectly", enemyDeaths == 1);
		currentEnemy = enemyManager.getCurrentEnemy();
		assertTrue("DeathListener fired incorrectly", enemyDeaths == 1);
		currentEnemy.changeHealth(-currentEnemy.getHealth()+10); // Leaves the Enemy with 10 Health (DeathListener should not fire)
		assertTrue("DeathListener fired incorrectly", enemyDeaths == 1);
		currentEnemy.changeHealth(-10);
		assertTrue("DeathListener fired incorrectly", enemyDeaths == 2);
		
	}
}

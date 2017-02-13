package uq.deco2800.duxcom.entities.enemies.enemychars;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the EnemyDarkMage
 *
 * @author Alex McLean
 */
public class EnemyDarkMageTest {

    private EnemyDarkMage enemyDarkMage;

    @Test
    public void testEnemyDarkMage() {
        enemyDarkMage = new EnemyDarkMage(0,0);
        assertEquals(enemyDarkMage.getX(), 0);
    }

}
package uq.deco2800.duxcom.entities.enemies.enemychars;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the EnemyArcher
 *
 * @author Alex McLean
 */
public class EnemyArcherTest {

    private EnemyArcher enemyArcher;

    @Test
    public void testEnemyArcher() {
        enemyArcher = new EnemyArcher(0,0);
        assertEquals(enemyArcher.getX(), 0);
    }

}
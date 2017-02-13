package uq.deco2800.duxcom.entities.enemies.enemychars;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the EnemyBrute
 *
 * @author Alex McLean
 */
public class EnemyBruteTest {

    private EnemyBrute enemyBrute;

    @Test
    public void testEnemyBrute() {
        enemyBrute = new EnemyBrute(0,0);
        assertEquals(enemyBrute.getX(), 0);
    }

}
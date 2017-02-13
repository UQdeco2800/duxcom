package uq.deco2800.duxcom.entities.enemies.enemychars;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the EnemySupport
 *
 * @author Alex McLean
 */
public class EnemySupportTest {

    private EnemySupport enemySupport;

    @Test
    public void testEnemySupport() {
        enemySupport = new EnemySupport(0,0);
        assertEquals(enemySupport.getX(), 0);
    }

}
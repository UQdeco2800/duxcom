package uq.deco2800.duxcom.entities.enemies.enemychars;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the EnemyTank
 *
 * @author Alex McLean
 */
public class EnemyTankTest {

    private EnemyTank enemyTank;

    @Test
    public void testEnemyTank() {
        enemyTank = new EnemyTank(0,0);
        assertEquals(enemyTank.getX(), 0);
    }

}
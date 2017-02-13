package uq.deco2800.duxcom.entities.enemies.enemychars;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the EnemyKnight
 *
 * @author Alex McLean
 */
public class EnemyKnightTest {

    private EnemyKnight enemyKnight;

    @Test
    public void testEnemyKnight() {
        enemyKnight = new EnemyKnight(0,0);
        assertEquals(enemyKnight.getX(), 0);
    }

}
package uq.deco2800.duxcom.entities.enemies.enemychars;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the EnemyGrunt
 *
 * @author Alex McLean
 */
public class EnemyGruntTest {

    private EnemyGrunt enemyGrunt;

    @Test
    public void testEnemyGrunt() {
        enemyGrunt = new EnemyGrunt(0,0);
        assertEquals(enemyGrunt.getX(), 0);
    }

}
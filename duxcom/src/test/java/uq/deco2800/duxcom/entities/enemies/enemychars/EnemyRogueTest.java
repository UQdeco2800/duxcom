package uq.deco2800.duxcom.entities.enemies.enemychars;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the EnemyRogue
 *
 * @author Alex McLean
 */
public class EnemyRogueTest {

    private EnemyRogue enemyRogue;

    @Test
    public void testEnemyRogue() {
        enemyRogue = new EnemyRogue(0,0);
        assertEquals(enemyRogue.getX(), 0);
    }

}
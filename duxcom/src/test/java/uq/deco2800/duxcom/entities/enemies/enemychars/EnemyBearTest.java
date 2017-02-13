package uq.deco2800.duxcom.entities.enemies.enemychars;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EnemyBearTest {

	private EnemyBear enemyBear;

    @Test
    public void testEnemyBear() {
    	enemyBear = new EnemyBear(0,0);
        assertEquals(enemyBear.getX(), 0);
    }
}

package uq.deco2800.duxcom.entities.enemies.enemychars;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EnemyWolfTest {

	private EnemyWolf enemyWolf;

    @Test
    public void testEnemyWolf() {
    	enemyWolf = new EnemyWolf(0,0);
        assertEquals(enemyWolf.getX(), 0);
    }
}

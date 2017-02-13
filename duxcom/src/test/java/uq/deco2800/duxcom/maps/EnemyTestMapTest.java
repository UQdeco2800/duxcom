package uq.deco2800.duxcom.maps;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the EnemyTest map class
 *
 * @author Alex McLean
 */
public class EnemyTestMapTest {

    private EnemyTestMap enemyTestMap;

    @Test
    public void testCreation() {
        enemyTestMap = new EnemyTestMap("test");
        assertNotNull(enemyTestMap);
    }

}
package uq.deco2800.duxcom.entities.enemies;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Test EnemyStat enum accessibility
 *
 * @author Alex McLean
 */
public class EnemyStatTest {

    @Test
    public void testAccess() {
        assertNotNull(EnemyStat.ACTION_POINTS);
    }

}
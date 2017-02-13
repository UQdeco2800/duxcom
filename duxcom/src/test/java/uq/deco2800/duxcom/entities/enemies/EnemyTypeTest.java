package uq.deco2800.duxcom.entities.enemies;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Test EnemyType enum accessibility
 *
 * @author Alex McLean
 */
public class EnemyTypeTest {

    @Test
    public void testAccess() {
        assertNotNull(EnemyType.ENEMY_ARCHER);
    }

}
package uq.deco2800.duxcom.entities;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Test EntityType enum accessibility
 *
 * @author Alex McLean
 */
public class EntityTypeTest {

    @Test
    public void testAccess() {
        assertNotNull(EntityType.BETA_IBIS);
    }

}